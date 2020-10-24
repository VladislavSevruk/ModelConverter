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
package com.github.vladislavsevruk.converter.converter.parameterized.custom;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.parameterized.AbstractParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.converter.mapper.method.MappedMethod;
import com.github.vladislavsevruk.converter.util.InstanceCreationUtil;
import com.github.vladislavsevruk.converter.util.MethodUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Converts one type to another with casting inner types of initial model to inner types of target model if necessary.
 * Specified for conversion of complex custom types.
 */
@Log4j2
public final class CustomTypeConverter extends AbstractParameterizedTypeConverter<Object> {

    public CustomTypeConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected boolean checkFromType(Class<?> fromType) {
        return isCustomType(fromType);
    }

    @Override
    protected boolean checkToType(Class<?> toType) {
        return isCustomType(toType);
    }

    @Override
    protected Object convertNonNullObject(Object from, TypeMeta<?> toMeta) {
        Class<?> toType = toMeta.getType();
        log.debug(() -> String
                .format("Trying to convert from '%s' to '%s'.", from.getClass().getName(), toType.getName()));
        Object targetModel = InstanceCreationUtil.createItem(toType);
        setValues(from, targetModel, toMeta);
        log.debug(() -> String
                .format("Successfully converted from '%s' to '%s'.", from.getClass().getName(), toType.getName()));
        return targetModel;
    }

    @Override
    protected Class<?> getFromType() {
        return Object.class;
    }

    @Override
    protected Class<?> getToType() {
        return Object.class;
    }

    private Object getDonorMethodValue(Object donor, Method donorMethod) {
        log.debug(() -> String.format("Trying to get value from '%s' donor method.", donorMethod.getName()));
        try {
            Object donorValue = donorMethod.invoke(donor);
            if (Objects.isNull(donorValue)) {
                log.debug(() -> "Donor value is 'null'.");
            }
            return donorValue;
        } catch (ReflectiveOperationException reOpEx) {
            log.warn(() -> String.format("Failed to get value to '%s' donor method.", donorMethod.getName()), reOpEx);
            return null;
        }
    }

    @SuppressWarnings("java:S3864")
    private List<Method> getPotentialSetters(Class<?> clazz) {
        log.debug(() -> "Collecting potential setters.");
        return Arrays.stream(clazz.getMethods()).filter(MethodUtil::nonStatic).filter(MethodUtil::nonObjectMethod)
                .filter(MethodUtil::canBeSetter)
                .peek(method -> log.debug(() -> String.format("Found potential setter - '%s'.", method.getName())))
                .collect(Collectors.toList());
    }

    private boolean isCustomType(Class<?> type) {
        return !type.isPrimitive() && !type.isArray() && !type.isEnum() && !Iterable.class.isAssignableFrom(type)
                && !Map.class.isAssignableFrom(type) && !Number.class.isAssignableFrom(type) && !CharSequence.class
                .isAssignableFrom(type) && !Boolean.class.isAssignableFrom(type) && !Character.class
                .isAssignableFrom(type) && !TemporalAccessor.class.isAssignableFrom(type) && !Date.class
                .isAssignableFrom(type) && !Calendar.class.isAssignableFrom(type) && !TimeZone.class
                .isAssignableFrom(type) && !Dictionary.class.isAssignableFrom(type);
    }

    private <T> void pickMatchingSetterAndSetValue(Object donor, Method donorMethod, T acceptor,
            TypeMeta<?> acceptorMeta, List<Method> acceptorMethods) {
        Object donorMethodValue = getDonorMethodValue(donor, donorMethod);
        if (Objects.nonNull(donorMethodValue)) {
            MappedMethod methodPair = getContext().getGetterSetterMapper()
                    .mapMatchingSetter(donorMethod.getName(), donorMethodValue, acceptorMeta, acceptorMethods);
            if (Objects.nonNull(methodPair)) {
                setAcceptorMethodValue(acceptor, methodPair, donorMethodValue);
            }
        }
    }

    private void setAcceptorMethodValue(Object acceptor, MappedMethod methodPair, Object donorValue) {
        Method acceptorMethod = methodPair.getAcceptorMethod();
        log.debug(() -> String.format("Trying to set value to '%s' acceptor method.", acceptorMethod.getName()));
        try {
            ClassTypeConverter<?> donorValueConverter = methodPair.getTypeConverter();
            if (Objects.nonNull(donorValueConverter)) {
                log.debug(() -> String.format("Trying to convert value using '%s'.", donorValueConverter.getName()));
                donorValue = donorValueConverter.convert(donorValue);
            }
            acceptorMethod.invoke(acceptor, donorValue);
        } catch (ReflectiveOperationException reOpEx) {
            log.warn(() -> String.format("Failed to set value to '%s' acceptor method.", acceptorMethod.getName()),
                    reOpEx);
        }
    }

    @SuppressWarnings("java:S3864")
    private <T> void setValues(Object donor, T acceptor, TypeMeta<?> acceptorMeta) {
        List<Method> acceptorMethods = getPotentialSetters(acceptorMeta.getType());
        Arrays.stream(donor.getClass().getMethods()).filter(MethodUtil::nonStatic).filter(MethodUtil::nonObjectMethod)
                .filter(MethodUtil::canBeGetter)
                .peek(method -> log.debug(() -> String.format("Found potential getter - '%s'.", method.getName())))
                .forEach(method -> pickMatchingSetterAndSetValue(donor, method, acceptor, acceptorMeta,
                        acceptorMethods));
    }
}
