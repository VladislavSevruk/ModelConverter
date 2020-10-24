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
package com.github.vladislavsevruk.converter.mapper;

import com.github.vladislavsevruk.converter.mapper.method.MappedMethod;
import com.github.vladislavsevruk.resolver.type.TypeMeta;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Maps getters from donor type to setters from acceptor type.
 */
public interface GetterSetterMapper {

    /**
     * Maps getter from donor type to setter from acceptor type.
     *
     * @param donorMethodName  <code>String</code> with name of getter method from donor method.
     * @param donorMethodValue value of getter method from donor type.
     * @param acceptorMeta     acceptor <code>TypeMeta</code> with actual types for generic parameters.
     * @param acceptorMethods  <code>List</code> of acceptor setter possible.
     * @return <code>MappedMethodPair</code> with mapped getter from donor type, setter from acceptor type and type
     * converter if needed.
     */
    MappedMethod mapMatchingSetter(String donorMethodName, Object donorMethodValue, TypeMeta<?> acceptorMeta,
            List<Method> acceptorMethods);
}
