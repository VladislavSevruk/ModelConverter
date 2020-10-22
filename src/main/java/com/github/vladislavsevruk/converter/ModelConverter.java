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
package com.github.vladislavsevruk.converter;

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.context.ConversionContextManager;
import com.github.vladislavsevruk.converter.exception.InstanceCreationException;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeProvider;
import com.github.vladislavsevruk.resolver.util.PrimitiveWrapperUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * This tool is designed for conversion POJOs from one type to another using public constructors without arguments and
 * public getters and setters.
 */
@Log4j2
public class ModelConverter {

    private ConversionContext conversionContext;

    /**
     * Creates new instance with default context.
     */
    public ModelConverter() {
        this(ConversionContextManager.getContext());
    }

    /**
     * Creates new instance with provided context.
     *
     * @param conversionContext <code>ConversionContext</code> that will be used for conversion operations.
     */
    public ModelConverter(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
    }

    /**
     * Converts one model to another. Creates instance of received type using public constructor without arguments and
     * fills it using public getters from donor model and setters from target model.
     *
     * @param from   model to convert.
     * @param toType target model class.
     * @param <T>    the type represented by target class.
     * @return converted model instance.
     * @throws InstanceCreationException if target class is abstract or has no public constructor without arguments.
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Object from, Class<T> toType) {
        if (from == null) {
            logReceivedNullValue();
            return null;
        }
        Objects.requireNonNull(toType, "Target type should not be null.");
        Class<T> toTypeWrapper = PrimitiveWrapperUtil.wrap(toType);
        if (toTypeWrapper.isAssignableFrom(from.getClass())) {
            return (T) from;
        }
        TypeMeta<T> toTypeMeta = new TypeMeta<>(toTypeWrapper);
        return conversionContext.getConversionEngine().convert(from, toTypeMeta);
    }

    /**
     * Converts one model to another. Uses descendants of <code>TypeProvider</code> for receiving meta information of
     * generic types. Creates instance of received type using public constructor without arguments and fills it using
     * public getters from donor model and setters from target model.
     *
     * @param from   model to convert.
     * @param toType <code>TypeProvider</code> that provides generic type of target model.
     * @param <T>    descendant of <code>TypeProvider</code> with target type.
     * @return converted model instance.
     * @throws InstanceCreationException if target class is abstract or has no public constructor without arguments.
     * @see TypeProvider
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Object from, TypeProvider<T> toType) {
        if (from == null) {
            logReceivedNullValue();
            return null;
        }
        Objects.requireNonNull(toType, "Target type provider should not be null.");
        TypeMeta<?> toTypeMeta = toType.getTypeMeta();
        return conversionContext.getConversionEngine().convert(from, (TypeMeta<T>) toTypeMeta);
    }

    private void logReceivedNullValue() {
        log.info("Received value is 'null'. Returning 'null'.");
    }
}
