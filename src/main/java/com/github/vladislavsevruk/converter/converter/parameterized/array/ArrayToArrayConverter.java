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
import com.github.vladislavsevruk.resolver.type.TypeMeta;

import java.lang.reflect.Array;
import java.util.stream.Stream;

/**
 * Converts one array to another with component type casting if necessary.
 */
public final class ArrayToArrayConverter extends ArrayConverter<Object[]> {

    public ArrayToArrayConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected boolean checkToType(Class<?> toType) {
        return toType.isArray();
    }

    @Override
    protected Object[] createFromStream(Stream<?> itemsStream, TypeMeta<? extends Object[]> toMeta) {
        return itemsStream
                .toArray(length -> (Object[]) Array.newInstance(toMeta.getGenericTypes()[0].getType(), length));
    }

    @Override
    protected Class<?> getToType() {
        return Object[].class;
    }
}
