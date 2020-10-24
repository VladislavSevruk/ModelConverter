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
package com.github.vladislavsevruk.converter.converter.parameterized.iterable;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.parameterized.AbstractParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.exception.TypeConversionException;
import com.github.vladislavsevruk.converter.util.ClassUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Abstract converter with common logic for iterable type converters.
 *
 * @param <T> type of conversion result.
 */
@Log4j2
public abstract class IterableConverter<T> extends AbstractParameterizedTypeConverter<T> {

    protected IterableConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected T convertNonNullObject(Object from, TypeMeta<? extends T> toMeta) {
        Iterable<?> fromValue = (Iterable<?>) from;
        Class<?> innerFromType = ClassUtil.getCommonClass(fromValue);
        TypeMeta<?> innerToTypeMeta = getGenericType(toMeta);
        if (getEngine().canConvert(innerFromType, innerToTypeMeta)) {
            return createFromStream(StreamSupport.stream(fromValue.spliterator(), false)
                    .map(value -> getEngine().convert(value, innerToTypeMeta)), toMeta);
        }
        log.warn(() -> String
                .format("Cannot convert iterable inner type '%s' to '%s'. Returning null.", innerFromType.getName(),
                        innerToTypeMeta.getType().getName()));
        return null;
    }

    protected abstract T createFromStream(Stream<?> itemsStream, TypeMeta<? extends T> toMeta);

    @Override
    protected Class<? extends Iterable> getFromType() {
        return Iterable.class;
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

    private TypeMeta<?> getGenericType(TypeMeta<?> typeMeta) {
        return typeMeta.getGenericTypes().length == 0 ? TypeMeta.OBJECT_META : typeMeta.getGenericTypes()[0];
    }
}
