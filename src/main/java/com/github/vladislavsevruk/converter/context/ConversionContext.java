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
package com.github.vladislavsevruk.converter.context;

import com.github.vladislavsevruk.converter.converter.picker.TypeConverterPicker;
import com.github.vladislavsevruk.converter.converter.storage.TypeConverterStorage;
import com.github.vladislavsevruk.converter.engine.ConversionEngine;
import com.github.vladislavsevruk.converter.mapper.CustomGetterSetterMappingStorage;
import com.github.vladislavsevruk.converter.mapper.GetterSetterMapper;
import com.github.vladislavsevruk.resolver.resolver.executable.ExecutableTypeResolver;
import com.github.vladislavsevruk.resolver.type.TypeMeta;

/**
 * Conversion context with replaceable modules.
 */
public interface ConversionContext {

    /**
     * Returns current instance of <code>ConversionEngine</code> stored at context.
     */
    ConversionEngine getConversionEngine();

    /**
     * Returns current instance of <code>CustomGetterSetterMappingStorage</code> stored at context.
     */
    CustomGetterSetterMappingStorage getCustomGetterSetterMappingStorage();

    /**
     * Returns current instance of <code>ExecutableTypeResolver</code> stored at context.
     */
    @SuppressWarnings("java:S1452")
    ExecutableTypeResolver<TypeMeta<?>> getExecutableTypeResolver();

    /**
     * Returns current instance of <code>GetterSetterMapper</code> stored at context.
     */
    GetterSetterMapper getGetterSetterMapper();

    /**
     * Returns current instance of <code>TypeConverterPicker</code> stored at context.
     */
    TypeConverterPicker getTypeConverterPicker();

    /**
     * Returns current instance of <code>TypeConverterStorage</code> stored at context.
     */
    TypeConverterStorage getTypeConverterStorage();
}
