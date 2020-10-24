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
import com.github.vladislavsevruk.converter.converter.picker.TypeConverterPickerImpl;
import com.github.vladislavsevruk.converter.converter.storage.TypeConverterStorage;
import com.github.vladislavsevruk.converter.converter.storage.TypeConverterStorageImpl;
import com.github.vladislavsevruk.converter.engine.ConversionEngine;
import com.github.vladislavsevruk.converter.engine.ConversionEngineImpl;
import com.github.vladislavsevruk.converter.mapper.GetterSetterMapper;
import com.github.vladislavsevruk.converter.mapper.method.GetterSetterMapperImpl;
import com.github.vladislavsevruk.resolver.resolver.executable.ExecutableTypeMetaResolver;
import com.github.vladislavsevruk.resolver.resolver.executable.ExecutableTypeResolver;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

/**
 * Implementation of <code>ConversionContext</code>.
 *
 * @see ConversionContext
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Log4j2
final class ConversionContextImpl implements ConversionContext {

    ConversionEngine conversionEngine;
    ExecutableTypeResolver<TypeMeta<?>> executableTypeResolver;
    GetterSetterMapper getterSetterMapper;
    TypeConverterPicker typeConverterPicker;
    TypeConverterStorage typeConverterStorage;

    /**
     * Creates new instance using received modules or default implementations for nulls.
     *
     * @param conversionEngineFactoryMethod       factory method for <code>ConversionEngine</code> module
     *                                            implementation.
     * @param getterSetterMapperFactoryMethod     factory method for <code>GetterSetterMapper</code> module
     *                                            implementation.
     * @param executableTypeResolverFactoryMethod factory method for <code>ExecutableTypeResolver</code> module
     *                                            implementation.
     * @param typeConverterPickerFactoryMethod    factory method for <code>TypeConverterPicker</code> module
     *                                            implementation.
     * @param typeConverterStorageFactoryMethod   factory method for <code>TypeConverterStorage</code> module
     *                                            implementation.
     */
    ConversionContextImpl(ConversionModuleFactoryMethod<ConversionEngine> conversionEngineFactoryMethod,
            ConversionModuleFactoryMethod<GetterSetterMapper> getterSetterMapperFactoryMethod,
            ConversionModuleFactoryMethod<ExecutableTypeResolver<TypeMeta<?>>> executableTypeResolverFactoryMethod,
            ConversionModuleFactoryMethod<TypeConverterPicker> typeConverterPickerFactoryMethod,
            ConversionModuleFactoryMethod<TypeConverterStorage> typeConverterStorageFactoryMethod) {
        this.conversionEngine = orDefault(conversionEngineFactoryMethod, ConversionEngineImpl::new);
        log.debug(() -> String.format("Using '%s' as conversion engine.", conversionEngine.getClass().getName()));
        this.executableTypeResolver = orDefault(executableTypeResolverFactoryMethod,
                context -> new ExecutableTypeMetaResolver());
        log.debug(() -> String
                .format("Using '%s' as method type resolver.", executableTypeResolver.getClass().getName()));
        this.typeConverterStorage = orDefault(typeConverterStorageFactoryMethod, TypeConverterStorageImpl::new);
        log.debug(() -> String
                .format("Using '%s' as type converter storage.", typeConverterStorage.getClass().getName()));
        this.typeConverterPicker = orDefault(typeConverterPickerFactoryMethod, TypeConverterPickerImpl::new);
        log.debug(
                () -> String.format("Using '%s' as type converter picker.", typeConverterPicker.getClass().getName()));
        this.getterSetterMapper = orDefault(getterSetterMapperFactoryMethod, GetterSetterMapperImpl::new);
        log.debug(() -> String.format("Using '%s' as getter setter mapper.", getterSetterMapper.getClass().getName()));
    }

    private <T> T orDefault(ConversionModuleFactoryMethod<T> factoryMethod,
            ConversionModuleFactoryMethod<T> defaultFactoryMethod) {
        if (factoryMethod != null) {
            T module = factoryMethod.get(this);
            if (module != null) {
                return module;
            }
        }
        return defaultFactoryMethod.get(this);
    }
}
