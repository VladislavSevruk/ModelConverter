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
package com.github.vladislavsevruk.converter.converter.picker;

import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.converter.mapper.method.MethodMeta;
import com.github.vladislavsevruk.converter.mapper.method.MethodMetaConverterPair;
import com.github.vladislavsevruk.resolver.type.TypeMeta;

/**
 * Picks type converters for various types meta.
 */
public interface TypeConverterPicker {

    /**
     * Picks suitable type converter for received types meta.
     *
     * @param from   <code>Class</code> to convert.
     * @param toMeta <code>TypeMeta</code> with actual types for target type.
     * @param <T>    the type represented by type meta to convert.
     * @param <U>    the type represented by target type meta.
     * @return <code>ClassTypeConverter</code> that can convert from received type to target type or <code>null</code>
     * if there is no suitable <code>ClassTypeConverter</code>.
     */
    <T, U> ClassTypeConverter<U> pickConverter(Class<T> from, TypeMeta<U> toMeta);

    /**
     * Picks most suitable method meta and type converter for received type meta from received candidates.
     *
     * @param from           <code>Class</code> to convert.
     * @param candidateMetas <code>Iterable</code> with <code>MethodMeta</code>s to pick most suitable one from.
     * @param <T>            the type represented by type meta.
     * @return <code>MethodMetaConverterPair</code> with most suitable <code>MethodMeta</code> and
     * <code>ClassTypeConverter</code> or <code>null</code> if there is no suitable <code>ClassTypeConverter</code> for
     * any of received <code>MethodMeta</code>.
     */
    @SuppressWarnings("java:S1452")
    <T> MethodMetaConverterPair<?> pickConverter(Class<T> from, Iterable<MethodMeta> candidateMetas);
}
