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
package com.github.vladislavsevruk.converter.converter.parameterized.array;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.parameterized.AbstractParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.exception.TypeConversionException;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Abstract converter with common logic for arrays type converters.
 *
 * @param <T> type of conversion result.
 */
@Log4j2
public abstract class ArrayConverter<T> extends AbstractParameterizedTypeConverter<T> {

    protected ArrayConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected boolean checkFromType(Class<?> from) {
        return from.isArray();
    }

    @Override
    protected T convertNonNullObject(Object from, TypeMeta<? extends T> toMeta) {
        Object[] fromValue = (Object[]) from;
        Class<?> innerFromType = ClassUtil.getCommonClass(fromValue);
        TypeMeta<?> innerToTypeMeta = getArrayType(toMeta);
        if (getEngine().canConvert(innerFromType, innerToTypeMeta)) {
            return createFromStream(Arrays.stream(fromValue).map(value -> getEngine().convert(value, innerToTypeMeta)),
                    toMeta);
        }
        log.warn(() -> String
                .format("Cannot convert array component type '%s' to '%s'. Returning null.", innerFromType.getName(),
                        innerToTypeMeta.getType().getName()));
        return null;
    }

    protected abstract <U> T createFromStream(Stream<U> itemsStream, TypeMeta<? extends T> toMeta);

    @Override
    protected Class<?> getFromType() {
        return Object[].class;
    }

    @Override
    protected void validateInput(Object from, TypeMeta<?> toMeta) {
        super.validateInput(from, toMeta);
        int innerTypesNumber = toMeta.getGenericTypes().length;
        if (innerTypesNumber > 1) {
            String message = String
                    .format("Expected type meta size for target type: 0 or 1 but was: %d.", innerTypesNumber);
            throw new TypeConversionException(message);
        }
    }

    private TypeMeta<?> getArrayType(TypeMeta<?> typeMeta) {
        return typeMeta.getGenericTypes().length == 0 ? TypeMeta.OBJECT_META : typeMeta.getGenericTypes()[0];
    }
}
