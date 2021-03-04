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
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Provides replaceable modules schemas required for conversion mechanism.
 */
@Log4j2
public final class ConversionModuleFactory {

    private static final ReadWriteLock CONVERSION_ENGINE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock CUSTOM_GETTER_SETTER_MAPPING_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock EXECUTABLE_TYPE_RESOLVER_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock GETTER_SETTER_MAPPER_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock TYPE_CONVERTER_PICKER_LOCK = new ReentrantReadWriteLock();
    private static final ReadWriteLock TYPE_CONVERTER_STORAGE_LOCK = new ReentrantReadWriteLock();
    private static ConversionModuleFactoryMethod<ConversionEngine> conversionEngine;
    private static ConversionModuleFactoryMethod<CustomGetterSetterMappingStorage> customGetterSetterMappingStorage;
    private static ConversionModuleFactoryMethod<ExecutableTypeResolver<TypeMeta<?>>> executableTypeResolver;
    private static ConversionModuleFactoryMethod<GetterSetterMapper> getterSetterMapper;
    private static ConversionModuleFactoryMethod<TypeConverterPicker> typeConverterPicker;
    private static ConversionModuleFactoryMethod<TypeConverterStorage> typeConverterStorage;

    private ConversionModuleFactory() {
    }

    /**
     * Returns current instance of <code>ConversionModuleFactoryMethod</code> for <code>ConversionEngine</code>.
     */
    public static ConversionModuleFactoryMethod<ConversionEngine> conversionEngine() {
        CONVERSION_ENGINE_LOCK.readLock().lock();
        ConversionModuleFactoryMethod<ConversionEngine> engineToReturn = ConversionModuleFactory.conversionEngine;
        CONVERSION_ENGINE_LOCK.readLock().unlock();
        return engineToReturn;
    }

    /**
     * Returns current instance of <code>ConversionModuleFactoryMethod</code> for <code>CustomGetterSetterMappingStorage</code>.
     */
    public static ConversionModuleFactoryMethod<CustomGetterSetterMappingStorage> customGetterSetterMappingStorage() {
        CUSTOM_GETTER_SETTER_MAPPING_STORAGE_LOCK.readLock().lock();
        ConversionModuleFactoryMethod<CustomGetterSetterMappingStorage> storageToReturn
                = ConversionModuleFactory.customGetterSetterMappingStorage;
        CUSTOM_GETTER_SETTER_MAPPING_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }

    /**
     * Returns current instance of <code>Supplier</code> for <code>ExecutableTypeResolver</code>.
     */
    @SuppressWarnings("java:S1452")
    public static ConversionModuleFactoryMethod<ExecutableTypeResolver<TypeMeta<?>>> executableTypeResolver() {
        EXECUTABLE_TYPE_RESOLVER_LOCK.readLock().lock();
        ConversionModuleFactoryMethod<ExecutableTypeResolver<TypeMeta<?>>> executableTypeResolverToReturn
                = ConversionModuleFactory.executableTypeResolver;
        EXECUTABLE_TYPE_RESOLVER_LOCK.readLock().unlock();
        return executableTypeResolverToReturn;
    }

    /**
     * Returns current instance of <code>ConversionModuleFactoryMethod</code> for <code>GetterSetterMapper</code>.
     */
    public static ConversionModuleFactoryMethod<GetterSetterMapper> getterSetterMapper() {
        GETTER_SETTER_MAPPER_LOCK.readLock().lock();
        ConversionModuleFactoryMethod<GetterSetterMapper> getterSetterMapperToReturn
                = ConversionModuleFactory.getterSetterMapper;
        GETTER_SETTER_MAPPER_LOCK.readLock().unlock();
        return getterSetterMapperToReturn;
    }

    /**
     * Replaces instance of <code>ConversionModuleFactoryMethod</code> for <code>ConversionEngine</code>. All further
     * conversions will use new instance.
     *
     * @param engine new instance of <code>ConversionModuleFactoryMethod</code> for <code>ConversionEngine</code>.
     */
    public static void replaceConversionEngine(ConversionModuleFactoryMethod<ConversionEngine> engine) {
        CONVERSION_ENGINE_LOCK.writeLock().lock();
        log.info("Replacing ConversionEngine by '{}'.", engine == null ? null : engine.getClass().getName());
        ConversionModuleFactory.conversionEngine = engine;
        CONVERSION_ENGINE_LOCK.writeLock().unlock();
        if (ConversionContextManager.isAutoRefreshContext()) {
            ConversionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ConversionModuleFactoryMethod</code> for <code>CustomGetterSetterMappingStorage</code>.
     * All further conversions will use new instance.
     *
     * @param storage new instance of <code>ConversionModuleFactoryMethod</code> for <code>CustomGetterSetterMappingStorage</code>.
     */
    public static void replaceCustomGetterSetterMappingStorage(
            ConversionModuleFactoryMethod<CustomGetterSetterMappingStorage> storage) {
        CUSTOM_GETTER_SETTER_MAPPING_STORAGE_LOCK.writeLock().lock();
        log.info("Replacing CustomGetterSetterMappingStorage by '{}'.",
                storage == null ? null : storage.getClass().getName());
        ConversionModuleFactory.customGetterSetterMappingStorage = storage;
        CUSTOM_GETTER_SETTER_MAPPING_STORAGE_LOCK.writeLock().unlock();
        if (ConversionContextManager.isAutoRefreshContext()) {
            ConversionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ConversionModuleFactoryMethod</code> for <code>ExecutableTypeResolver</code>. All
     * further conversions will use new instance.
     *
     * @param resolver new instance of <code>ConversionModuleFactoryMethod</code> for <code>ExecutableTypeResolver</code>.
     */
    public static void replaceExecutableTypeResolver(
            ConversionModuleFactoryMethod<ExecutableTypeResolver<TypeMeta<?>>> resolver) {
        EXECUTABLE_TYPE_RESOLVER_LOCK.writeLock().lock();
        log.info("Replacing ExecutableTypeResolver by '{}'.", resolver == null ? null : resolver.getClass().getName());
        ConversionModuleFactory.executableTypeResolver = resolver;
        EXECUTABLE_TYPE_RESOLVER_LOCK.writeLock().unlock();
        if (ConversionContextManager.isAutoRefreshContext()) {
            ConversionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ConversionModuleFactoryMethod</code> for <code>GetterSetterMapper</code>. All further
     * conversions will use new instance.
     *
     * @param mapper new instance of <code>ConversionModuleFactoryMethod</code> for <code>GetterSetterMapper</code>.
     */
    public static void replaceGetterSetterMapper(ConversionModuleFactoryMethod<GetterSetterMapper> mapper) {
        GETTER_SETTER_MAPPER_LOCK.writeLock().lock();
        log.info("Replacing GetterSetterMapper by '{}'.", mapper == null ? null : mapper.getClass().getName());
        ConversionModuleFactory.getterSetterMapper = mapper;
        GETTER_SETTER_MAPPER_LOCK.writeLock().unlock();
        if (ConversionContextManager.isAutoRefreshContext()) {
            ConversionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ConversionModuleFactoryMethod</code> for <code>TypeConverterPicker</code>. All further
     * conversions will use new instance.
     *
     * @param picker new instance of <code>ConversionModuleFactoryMethod</code> for <code>TypeConverterPicker</code>.
     */
    public static void replaceTypeConverterPicker(ConversionModuleFactoryMethod<TypeConverterPicker> picker) {
        TYPE_CONVERTER_PICKER_LOCK.writeLock().lock();
        log.info("Replacing TypeConverterPicker by '{}'.", picker == null ? null : picker.getClass().getName());
        ConversionModuleFactory.typeConverterPicker = picker;
        TYPE_CONVERTER_PICKER_LOCK.writeLock().unlock();
        if (ConversionContextManager.isAutoRefreshContext()) {
            ConversionContextManager.refreshContext();
        }
    }

    /**
     * Replaces instance of <code>ConversionModuleFactoryMethod</code> for <code>TypeConverterStorage</code>. All
     * further conversions will use new instance.
     *
     * @param storage new instance of <code>ConversionModuleFactoryMethod</code> for <code>TypeConverterStorage</code>.
     */
    public static void replaceTypeConverterStorage(ConversionModuleFactoryMethod<TypeConverterStorage> storage) {
        TYPE_CONVERTER_STORAGE_LOCK.writeLock().lock();
        log.info("Replacing TypeConverterStorage by '{}'.", storage == null ? null : storage.getClass().getName());
        ConversionModuleFactory.typeConverterStorage = storage;
        TYPE_CONVERTER_STORAGE_LOCK.writeLock().unlock();
        if (ConversionContextManager.isAutoRefreshContext()) {
            ConversionContextManager.refreshContext();
        }
    }

    /**
     * Returns current instance of <code>ConversionModuleFactoryMethod</code> for <code>TypeConverterPicker</code>.
     */
    public static ConversionModuleFactoryMethod<TypeConverterPicker> typeConverterPicker() {
        TYPE_CONVERTER_PICKER_LOCK.readLock().lock();
        ConversionModuleFactoryMethod<TypeConverterPicker> pickerToReturn = ConversionModuleFactory.typeConverterPicker;
        TYPE_CONVERTER_PICKER_LOCK.readLock().unlock();
        return pickerToReturn;
    }

    /**
     * Returns current instance of <code>ConversionModuleFactoryMethod</code> for <code>TypeConverterStorage</code>.
     */
    public static ConversionModuleFactoryMethod<TypeConverterStorage> typeConverterStorage() {
        TYPE_CONVERTER_STORAGE_LOCK.readLock().lock();
        ConversionModuleFactoryMethod<TypeConverterStorage> storageToReturn
                = ConversionModuleFactory.typeConverterStorage;
        TYPE_CONVERTER_STORAGE_LOCK.readLock().unlock();
        return storageToReturn;
    }
}