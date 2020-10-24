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
package com.github.vladislavsevruk.converter.converter.simple;

import com.github.vladislavsevruk.converter.converter.AbstractTypeConverter;
import com.github.vladislavsevruk.converter.exception.TypeConversionException;
import lombok.extern.log4j.Log4j2;

/**
 * Abstract converter with common logic for class converters.
 */
@Log4j2
public abstract class AbstractClassConverter<T, U> extends AbstractTypeConverter implements ClassTypeConverter<U> {

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public U convert(Object from) {
        if (from == null) {
            log.debug(() -> "Received value is 'null' so returning 'null' as well.");
            return null;
        }
        if (!checkFromType(from.getClass())) {
            String message = String.format("Expected to convert assignable from '%s' type but received: '%s'.",
                    getFromType().getName(), from.getClass().getName());
            throw new TypeConversionException(message);
        }
        log.debug(
                () -> String.format("Converting from '%s' to '%s'.", from.getClass().getName(), getToType().getName()));
        U value = convertNonNullObject((T) from);
        log.debug(() -> String.format("Converted '%s' to '%s'.", from, value));
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getClass().getName();
    }

    protected abstract U convertNonNullObject(T from);
}
