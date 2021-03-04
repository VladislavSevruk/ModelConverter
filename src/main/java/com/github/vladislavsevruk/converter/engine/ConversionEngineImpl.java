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

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.context.ConversionContextManager;
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import com.github.vladislavsevruk.converter.exception.TypeConversionException;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

/**
 * Implementation of <code>ConversionEngine</code>.
 *
 * @see ConversionEngine
 */
@Log4j2
public final class ConversionEngineImpl implements ConversionEngine {

    private final ConversionContext conversionContext;

    public ConversionEngineImpl() {
        this(ConversionContextManager.getContext());
    }

    public ConversionEngineImpl(ConversionContext conversionContext) {
        this.conversionContext = conversionContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConvert(Class<?> from, TypeMeta<?> toMeta) {
        return Objects.nonNull(conversionContext.getTypeConverterPicker().pickConverter(from, toMeta));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T convert(Object from, TypeMeta<T> toMeta) {
        ClassTypeConverter<T> matchingConverter = conversionContext.getTypeConverterPicker()
                .pickConverter(from.getClass(), toMeta);
        if (Objects.isNull(matchingConverter)) {
            String message = String
                    .format("Failed to find any matching converter from '%s' to '%s' type. You can add your converter "
                                    + "implementation using 'ConversionContextManager.getContext().getTypeConverterStorage().add*' methods.",
                            from.getClass().getName(), toMeta.getType().getName());
            log.error(message);
            throw new TypeConversionException(message);
        }
        return matchingConverter.convert(from);
    }
}
