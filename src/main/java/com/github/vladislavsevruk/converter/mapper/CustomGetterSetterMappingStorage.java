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
package com.github.vladislavsevruk.converter.mapper;

import com.github.vladislavsevruk.converter.mapper.method.MappedMethod;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeProvider;

import java.lang.reflect.Method;

/**
 * Stores custom getter setter pair that should be used for related model conversions.
 */
public interface CustomGetterSetterMappingStorage {

    /**
     * Adds custom getter setter pair that will be used for related model conversions.
     *
     * @param donorMethod    getter <code>Method</code> from donor model.
     * @param acceptorMethod setter <code>Method</code> from acceptor model.
     */
    void addGetterSetterMapping(Method donorMethod, Method acceptorMethod);

    /**
     * Adds custom getter setter pair that will be used for related model conversions.
     *
     * @param donorMethod    getter <code>Method</code> from donor model.
     * @param acceptorMethod setter <code>Method</code> from acceptor model.
     */
    void addGetterSetterMapping(Method donorMethod, Method acceptorMethod, Class<?> acceptorClass);

    /**
     * Adds custom getter setter pair that will be used for related model conversions.
     *
     * @param donorMethod    getter <code>Method</code> from donor model.
     * @param acceptorMethod setter <code>Method</code> from acceptor model.
     */
    void addGetterSetterMapping(Method donorMethod, Method acceptorMethod, TypeMeta<?> acceptorMeta);

    /**
     * Adds custom getter setter pair that will be used for related model conversions.
     *
     * @param donorMethod    getter <code>Method</code> from donor model.
     * @param acceptorMethod setter <code>Method</code> from acceptor model.
     */
    void addGetterSetterMapping(Method donorMethod, Method acceptorMethod, TypeProvider<?> acceptorTypeProvider);

    /**
     * Returns custom mapping for received getter method from donor model.
     *
     * @param donorMethod      getter <code>Method</code> from donor model.
     * @param donorMethodValue value of getter method from donor type.
     * @param acceptorMeta     acceptor <code>TypeMeta</code> with actual types for generic parameters.
     * @return custom mapped setter <code>Method</code> from acceptor model or <code>null</code> if there are no custom
     * mapping at this storage.
     */
    MappedMethod getMapping(Method donorMethod, Object donorMethodValue, TypeMeta<?> acceptorMeta);
}
