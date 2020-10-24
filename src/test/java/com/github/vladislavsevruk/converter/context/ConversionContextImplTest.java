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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConversionContextImplTest {

    @Mock
    private ConversionEngine conversionEngine;
    @Mock
    private ExecutableTypeResolver<TypeMeta<?>> executableTypeResolver;
    @Mock
    private GetterSetterMapper getterSetterMapper;
    @Mock
    private TypeConverterPicker typeConverterPicker;
    @Mock
    private TypeConverterStorage typeConverterStorage;

    @Test
    void customConversionEngineFactoryMethodReturnsNullTest() {
        ConversionContext conversionContext = new ConversionContextImpl(context -> null, null, null, null, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customConversionEngineTest() {
        ConversionContext conversionContext = new ConversionContextImpl(context -> conversionEngine, null, null, null,
                null);
        Assertions.assertEquals(conversionEngine, conversionContext.getConversionEngine());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customGetterSetterMapperFactoryMethodReturnsNullTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, context -> null, null, null, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customGetterSetterMapperTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, context -> getterSetterMapper, null, null,
                null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(getterSetterMapper, conversionContext.getGetterSetterMapper());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customMethodTypeResolverFactoryMethodReturnsNullTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, context -> null, null, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customMethodTypeResolverTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, context -> executableTypeResolver,
                null, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(executableTypeResolver, conversionContext.getExecutableTypeResolver());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customModulesFactoryMethodReturnNullTest() {
        ConversionContext conversionContext = new ConversionContextImpl(context -> null, context -> null,
                context -> null, context -> null, context -> null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customModulesTest() {
        ConversionContext conversionContext = new ConversionContextImpl(context -> conversionEngine,
                context -> getterSetterMapper, context -> executableTypeResolver, context -> typeConverterPicker,
                context -> typeConverterStorage);
        Assertions.assertEquals(conversionEngine, conversionContext.getConversionEngine());
        Assertions.assertEquals(getterSetterMapper, conversionContext.getGetterSetterMapper());
        Assertions.assertEquals(executableTypeResolver, conversionContext.getExecutableTypeResolver());
        Assertions.assertEquals(typeConverterPicker, conversionContext.getTypeConverterPicker());
        Assertions.assertEquals(typeConverterStorage, conversionContext.getTypeConverterStorage());
    }

    @Test
    void customTypeConverterPickerFactoryMethodReturnsNullTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, null, context -> null, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customTypeConverterPickerTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, null,
                context -> typeConverterPicker, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(typeConverterPicker, conversionContext.getTypeConverterPicker());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customTypeConverterStorageFactoryMethodReturnsNullTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, null, null, context -> null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }

    @Test
    void customTypeConverterStorageTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, null, null,
                context -> typeConverterStorage);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(typeConverterStorage, conversionContext.getTypeConverterStorage());
    }

    @Test
    void defaultModulesTest() {
        ConversionContext conversionContext = new ConversionContextImpl(null, null, null, null, null);
        Assertions.assertEquals(ConversionEngineImpl.class, conversionContext.getConversionEngine().getClass());
        Assertions.assertEquals(GetterSetterMapperImpl.class, conversionContext.getGetterSetterMapper().getClass());
        Assertions.assertEquals(ExecutableTypeMetaResolver.class,
                conversionContext.getExecutableTypeResolver().getClass());
        Assertions.assertEquals(TypeConverterPickerImpl.class, conversionContext.getTypeConverterPicker().getClass());
        Assertions.assertEquals(TypeConverterStorageImpl.class, conversionContext.getTypeConverterStorage().getClass());
    }
}
