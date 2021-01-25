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

import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.resolver.type.TypeMeta;

/**
 * Adapts <code>ParameterizedTypeConverter</code> to <code>ClassTypeConverter</code>.
 *
 * @param <T> type of conversion result.
 * @see ClassTypeConverter
 * @see ParameterizedTypeConverter
 */
public final class ClassTypeConverterAdapter<T> implements ClassTypeConverter<T> {

    private final ParameterizedTypeConverter<T> parameterizedTypeConverter;
    private final TypeMeta<? extends T> toTypeMeta;

    public ClassTypeConverterAdapter(ParameterizedTypeConverter<T> parameterizedTypeConverter,
            TypeMeta<? extends T> toTypeMeta) {
        this.parameterizedTypeConverter = parameterizedTypeConverter;
        this.toTypeMeta = toTypeMeta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConvert(Class<?> from, Class<?> to) {
        return parameterizedTypeConverter.canConvert(from, to);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T convert(Object from) {
        return parameterizedTypeConverter.convert(from, toTypeMeta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return parameterizedTypeConverter.getName();
    }
}
