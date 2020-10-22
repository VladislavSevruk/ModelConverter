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
import com.github.vladislavsevruk.converter.util.InstanceCreationUtil;
import com.github.vladislavsevruk.resolver.type.TypeMeta;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Converts array to list with component type casting if necessary.
 */
public final class ArrayToListConverter extends ArrayConverter<List<?>> {

    public ArrayToListConverter(ConversionContext conversionContext) {
        super(conversionContext);
    }

    @Override
    protected boolean checkToType(Class<?> toType) {
        return getToType().isAssignableFrom(toType) || toType.isAssignableFrom(getToType());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<?> createFromStream(Stream<?> itemsStream, TypeMeta<? extends List<?>> toMeta) {
        return itemsStream.collect(Collectors.toCollection(getTypeSupplier(toMeta.getType())));
    }

    @Override
    protected Class<List> getToType() {
        return List.class;
    }

    private Supplier<List> getTypeSupplier(Class<?> type) {
        if (type.isAssignableFrom(AbstractList.class)) {
            return ArrayList::new;
        }
        return () -> (List) InstanceCreationUtil.createItem(type);
    }
}
