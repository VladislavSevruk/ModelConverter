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
package com.github.vladislavsevruk.converter.converter.parameterized;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.AbstractTypeConverter;
import com.github.vladislavsevruk.converter.engine.ConversionEngine;
import com.github.vladislavsevruk.converter.exception.TypeConversionException;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

/**
 * Abstract converter with common logic for parameterized type converters.
 *
 * @param <T> type of conversion result.
 */
@Log4j2
public abstract class AbstractParameterizedTypeConverter<T> extends AbstractTypeConverter
        implements ParameterizedTypeConverter<T> {

    private final ConversionContext conversionContext;

    protected AbstractParameterizedTypeConverter(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T convert(Object from, TypeMeta<? extends T> toMeta) {
        if (from == null) {
            log.debug(() -> "Received value is 'null' so returning 'null' as well.");
            return null;
        }
        validateInput(from, toMeta);
        log.debug(() -> String
                .format("Converting from '%s' to '%s'.", from.getClass().getName(), toMeta.getType().getName()));
        T value = convertNonNullObject(from, toMeta);
        log.debug(() -> String
                .format("Converted from '%s' to '%s'.", from.getClass().getName(), toMeta.getType().getName()));
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getClass().getName();
    }

    protected abstract T convertNonNullObject(Object from, TypeMeta<? extends T> toMeta);

    protected ConversionContext getContext() {
        return conversionContext;
    }

    protected ConversionEngine getEngine() {
        return getContext().getConversionEngine();
    }

    protected void validateInput(Object from, TypeMeta<?> toMeta) {
        if (!checkFromType(from.getClass())) {
            String message = String.format("Expected to convert assignable from '%s' type but received: '%s'.",
                    getFromType().getName(), from.getClass().getName());
            throw new TypeConversionException(message);
        }
        Class<?> toType = toMeta.getType();
        if (!checkToType(toType)) {
            String message = String
                    .format("Expected to convert to type that can be assigned to '%s' type but received: '%s'.",
                            getToType().getName(), toType.getName());
            throw new TypeConversionException(message);
        }
    }
}
