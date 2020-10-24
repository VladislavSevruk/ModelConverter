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
package com.github.vladislavsevruk.converter.converter.simple.string;

import lombok.extern.log4j.Log4j2;

/**
 * Converts char sequence to boolean.
 */
@Log4j2
public final class CharSequenceToBooleanConverter extends CharSequenceConverter<Boolean> {

    private static final String FALSE = Boolean.FALSE.toString();
    private static final String TRUE = Boolean.TRUE.toString();

    @Override
    protected Boolean convertNonNullObject(CharSequence from) {
        String fromString = from.toString();
        if (FALSE.equalsIgnoreCase(fromString) || TRUE.equalsIgnoreCase(fromString)) {
            return Boolean.valueOf(fromString);
        }
        if ("Y".equalsIgnoreCase(fromString) || "yes".equalsIgnoreCase(fromString) || "T".equalsIgnoreCase(fromString)
                || "1".equalsIgnoreCase(fromString)) {
            return Boolean.TRUE;
        }
        if ("N".equalsIgnoreCase(fromString) || "no".equalsIgnoreCase(fromString) || "F".equalsIgnoreCase(fromString)
                || "0".equalsIgnoreCase(fromString)) {
            return Boolean.FALSE;
        }
        log.info(() -> String
                .format("Failed to convert '\"%s\"' to '%s'. Returning null.", from, Boolean.class.getName()));
        return null;
    }

    @Override
    protected Class<Boolean> getToType() {
        return Boolean.class;
    }
}
