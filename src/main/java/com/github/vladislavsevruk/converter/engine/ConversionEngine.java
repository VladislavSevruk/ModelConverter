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
package com.github.vladislavsevruk.converter.engine;

import com.github.vladislavsevruk.converter.exception.InstanceCreationException;
import com.github.vladislavsevruk.resolver.type.TypeMeta;

/**
 * Converts one POJO model to another with casting inner types of initial model to inner types of target model where
 * it's possible.
 */
public interface ConversionEngine {

    /**
     * Checks if this engine can convert from one received value to another.
     *
     * @param from <code>TypeValue</code> with object to convert and its type meta information.
     * @param to   <code>TypeMeta</code> with actual types for generic parameters of target type.
     * @return <code>true</code> if this engine can convert from one received value to another, <code>false</code>
     * otherwise.
     */
    boolean canConvert(Class<?> from, TypeMeta<?> to);

    /**
     * Converts received object to received target type.
     *
     * @param from object to convert.
     * @param to   <code>TypeMeta</code> with actual types for generic parameters of target type.
     * @param <T>  the type represented by target type meta.
     * @return instance of target type with converted values.
     * @throws InstanceCreationException if target class is abstract or has no public constructor without arguments.
     */
    <T> T convert(Object from, TypeMeta<T> to);
}
