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
package com.github.vladislavsevruk.converter.converter.simple.string.number;

import com.github.vladislavsevruk.converter.converter.simple.string.CharSequenceConverter;
import lombok.extern.log4j.Log4j2;

/**
 * Abstract converter with common logic for char sequence to number type converters.
 *
 * @param <T> type of conversion result.
 */
@Log4j2
public abstract class CharSequenceToNumberConverter<T extends Number> extends CharSequenceConverter<T> {

    @Override
    protected T convertNonNullObject(CharSequence from) {
        try {
            return parseNumber(from.toString());
        } catch (NumberFormatException nuFoEx) {
            log.warn(() -> String
                    .format("Failed to convert '\"%s\"' to '%s'. Returning null.", from, getToType().getName()));
            return null;
        }
    }

    protected abstract T parseNumber(String from);
}
