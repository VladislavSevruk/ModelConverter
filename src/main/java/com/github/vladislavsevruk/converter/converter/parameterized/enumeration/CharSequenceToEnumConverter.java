/*
 * MIT License
 *
 * Copyright (c) 2021 Uladzislau Seuruk
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
package com.github.vladislavsevruk.converter.converter.parameterized.enumeration;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.parameterized.AbstractParameterizedTypeConverter;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

/**
 * Converts char sequence to enum.
 *
 * @param <T> enum type of conversion result.
 */
@Log4j2
public class CharSequenceToEnumConverter<T extends Enum<T>> extends AbstractParameterizedTypeConverter<T> {

    public CharSequenceToEnumConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected boolean checkToType(Class<?> toType) {
        return toType.isEnum();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T convertNonNullObject(Object from, TypeMeta<? extends T> toMeta) {
        try {
            return Enum.valueOf((Class<T>) toMeta.getType(), from.toString());
        } catch (IllegalArgumentException iaEx) {
            log.warn(() -> String
                    .format("Failed to convert '\"%s\"' to '%s'. Returning null.", from, toMeta.getType().getName()));
            return null;
        }
    }

    @Override
    protected Class<?> getFromType() {
        return CharSequence.class;
    }

    @Override
    protected Class<?> getToType() {
        return Enum.class;
    }
}
