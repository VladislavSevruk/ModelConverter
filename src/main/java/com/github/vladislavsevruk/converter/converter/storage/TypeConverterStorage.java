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
package com.github.vladislavsevruk.converter.converter.storage;

import com.github.vladislavsevruk.converter.converter.TypeConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.ParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;

import java.util.List;

/**
 * Contains converters for types casting.
 *
 * @see TypeConverter
 */
public interface TypeConverterStorage {

    /**
     * Adds new <code>ClassTypeConverter</code> to list. Converter will not be added provided it is <code>null</code> or
     * list already contains converter of such type.
     *
     * @param customConverter custom converter to add.
     */
    void add(ClassTypeConverter<?> customConverter);

    /**
     * Adds new <code>ParameterizedTypeConverter</code> to list. Converter will not be added provided it is
     * <code>null</code> or list already contains converter of such type.
     *
     * @param customConverter custom converter to add.
     */
    void add(ParameterizedTypeConverter<?> customConverter);

    /**
     * Adds new <code>ClassTypeConverter</code> to list after specified <code>TypeConverter</code> or at list end
     * provided target <code>TypeConverter</code> is missed. New converter will not be added provided it is
     * <code>null</code> or list already contains converter of such type.
     *
     * @param customConverter custom converter to add.
     * @param targetType      type after which new converter should be added.
     */
    void addAfter(ClassTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType);

    /**
     * Adds new <code>ParameterizedTypeConverter</code> to list after specified <code>TypeConverter</code> or at list
     * end provided target <code>TypeConverter</code> is missed. New converter will not be added provided it is
     * <code>null</code> or list already contains converter of such type.
     *
     * @param customConverter custom converter to add.
     * @param targetType      type after which new converter should be added.
     */
    void addAfter(ParameterizedTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType);

    /**
     * Adds new <code>ClassTypeConverter</code> to list before specified <code>TypeConverter</code> or at list end
     * provided target <code>TypeConverter</code> is missed. New converter will not be added provided it is
     * <code>null</code> or list already contains converter of such type.
     *
     * @param customConverter custom converter to add.
     * @param targetType      type after which new converter should be added.
     */
    void addBefore(ClassTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType);

    /**
     * Adds new <code>ParameterizedTypeConverter</code> to list before specified <code>TypeConverter</code> or at list
     * end provided target <code>TypeConverter</code> is missed. New converter will not be added provided it is
     * <code>null</code> or list already contains converter of such type.
     *
     * @param customConverter custom converter to add.
     * @param targetType      type after which new converter should be added.
     */
    void addBefore(ParameterizedTypeConverter<?> customConverter, Class<? extends TypeConverter> targetType);

    /**
     * Returns list of all <code>ClassTypeConverter</code>-s that are present at storage.
     */
    List<TypeConverter> getAll();

    /**
     * Checks if converter is one of predefined ones.
     *
     * @param typeConverter <code>TypeConverter</code> to check.
     * @return <code>true</code> if received converter is one of predefined ones, <code>false</code> otherwise.
     */
    boolean isDefaultConverter(TypeConverter typeConverter);
}
