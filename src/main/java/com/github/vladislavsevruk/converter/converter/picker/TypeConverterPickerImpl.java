/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.converter.converter.picker;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.TypeConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.ClassTypeConverterAdapter;
import com.github.vladislavsevruk.converter.converter.parameterized.ParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.custom.CustomTypeConverter;
import com.github.vladislavsevruk.converter.converter.simple.AssignableObjectConverter;
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.converter.mapper.method.MethodMeta;
import com.github.vladislavsevruk.converter.mapper.method.MethodMetaConverterPair;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.util.PrimitiveWrapperUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * Implementation of <code>TypeConverterPicker</code>.
 *
 * @see TypeConverterPicker
 */
@Log4j2
public final class TypeConverterPickerImpl implements TypeConverterPicker {

    private final ConversionContext conversionContext;
    private final ParameterizedTypeConverter<?> customTypeConverter;

    public TypeConverterPickerImpl(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
        customTypeConverter = new CustomTypeConverter(conversionContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, U> ClassTypeConverter<U> pickConverter(Class<T> from, TypeMeta<U> toMeta) {
        log.debug(() -> String.format("Trying to find matching converter from '%s' to '%s' type.", from.getName(),
                toMeta.getType().getName()));
        ClassTypeConverter<U> pickedConverter = doPickConverter(from, toMeta);
        log.debug(() -> Objects.isNull(pickedConverter) ? "Didn't find any matching converter."
                : "Found matching converter: " + pickedConverter.getName());
        return pickedConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> MethodMetaConverterPair<?> pickConverter(Class<T> from, Iterable<MethodMeta> candidateMetas) {
        ClassTypeConverter<?> pickedConverter = null;
        ClassTypeConverter<?> pickedCustomTypeConverter = null;
        MethodMeta pickedMeta = null;
        for (MethodMeta candidateMeta : candidateMetas) {
            TypeMeta<?> candidateType = candidateMeta.getArgumentType();
            ClassTypeConverter<?> candidateConverter = pickConverter(from, candidateType);
            if (candidateConverter != null) {
                if (!conversionContext.getTypeConverterStorage().isDefaultConverter(candidateConverter)) {
                    // if custom converter was set then use it
                    return new MethodMetaConverterPair<>(candidateConverter, candidateMeta);
                }
                if (pickedConverter == null) {
                    // otherwise use first found default
                    pickedConverter = candidateConverter;
                    pickedMeta = candidateMeta;
                }
            } else if (pickedConverter == null && pickedCustomTypeConverter == null) {
                // otherwise use first found custom converter
                pickedCustomTypeConverter = getCustomTypeConverter(from, candidateType);
                pickedMeta = candidateMeta;
            }
        }
        if (pickedConverter == null) {
            if (pickedCustomTypeConverter == null) {
                log.debug(() -> "Didn't find any matching converter for any getter method.");
                return null;
            }
            return getPair(pickedCustomTypeConverter, pickedMeta);
        }
        return getPair(pickedConverter, pickedMeta);
    }

    private <T, U> ClassTypeConverter<U> doPickConverter(Class<T> from, TypeMeta<U> toMeta) {
        Class<?> toType = PrimitiveWrapperUtil.wrap(toMeta.getType());
        ClassTypeConverter<U> pickedConverter = null;
        if (ClassUtil.nonParameterized(toMeta) && toMeta.getType().isAssignableFrom(from)) {
            // if non-parameterized then we guarantee won't have any casting issues for generic parameters
            return new AssignableObjectConverter<>();
        }
        for (TypeConverter converter : conversionContext.getTypeConverterStorage().getAll()) {
            if (converter.canConvert(from, toType)) {
                if (!conversionContext.getTypeConverterStorage().isDefaultConverter(converter)) {
                    // if custom converter was set then use it
                    ClassTypeConverter<U> nonDefaultConverter = getClassTypeConverter(converter, toMeta);
                    log.debug(() -> "Found matching non-default converter: " + nonDefaultConverter.getName());
                    return nonDefaultConverter;
                }
                if (pickedConverter == null) {
                    // otherwise use first found default
                    pickedConverter = getClassTypeConverter(converter, toMeta);
                }
            }
        }
        return Objects.nonNull(pickedConverter) ? pickedConverter : getCustomTypeConverter(from, toMeta);
    }

    @SuppressWarnings("unchecked")
    private <U> ClassTypeConverter<U> getClassTypeConverter(TypeConverter converter, TypeMeta<U> toMeta) {
        if (ClassTypeConverter.class.isAssignableFrom(converter.getClass())) {
            return (ClassTypeConverter<U>) converter;
        }
        return new ClassTypeConverterAdapter<>((ParameterizedTypeConverter<U>) converter, toMeta);
    }

    private <T, U> ClassTypeConverter<U> getCustomTypeConverter(Class<T> from, TypeMeta<U> toMeta) {
        Class<?> toType = PrimitiveWrapperUtil.wrap(toMeta.getType());
        if (customTypeConverter.canConvert(from, toType)) {
            return getClassTypeConverter(customTypeConverter, toMeta);
        }
        return null;
    }

    private MethodMetaConverterPair<?> getPair(ClassTypeConverter<?> converter, MethodMeta methodMeta) {
        MethodMetaConverterPair<?> pickedPair = new MethodMetaConverterPair<>(converter, methodMeta);
        log.debug(() -> String
                .format("Picked '%s' with '%s' converter.", pickedPair.getMethodMeta().getMethod().getName(),
                        pickedPair.getConverter().getName()));
        return pickedPair;
    }
}
