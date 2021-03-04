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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConversionModuleFactoryTest {

    private static boolean initialAutoRefreshContext;

    @Mock
    private ConversionEngine conversionEngine;
    @Mock
    private CustomGetterSetterMappingStorage customGetterSetterMappingStorage;
    @Mock
    private ExecutableTypeResolver<TypeMeta<?>> executableTypeResolver;
    @Mock
    private GetterSetterMapper getterSetterMapper;
    @Mock
    private TypeConverterPicker typeConverterPicker;
    @Mock
    private TypeConverterStorage typeConverterStorage;

    @BeforeAll
    static void disableContextRefresh() {
        initialAutoRefreshContext = ConversionContextManager.isAutoRefreshContext();
        ConversionContextManager.disableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        if (initialAutoRefreshContext) {
            ConversionContextManager.enableContextAutoRefresh();
        }
    }

    @Test
    void defaultModulesTest() {
        Assertions.assertNull(ConversionModuleFactory.conversionEngine());
        Assertions.assertNull(ConversionModuleFactory.customGetterSetterMappingStorage());
        Assertions.assertNull(ConversionModuleFactory.getterSetterMapper());
        Assertions.assertNull(ConversionModuleFactory.executableTypeResolver());
        Assertions.assertNull(ConversionModuleFactory.typeConverterPicker());
        Assertions.assertNull(ConversionModuleFactory.typeConverterStorage());
    }

    @Test
    void replaceConversionEngineTest() {
        ConversionModuleFactoryMethod<ConversionEngine> factoryMethod = context -> conversionEngine;
        ConversionModuleFactory.replaceConversionEngine(factoryMethod);
        Assertions.assertEquals(factoryMethod, ConversionModuleFactory.conversionEngine());
    }

    @Test
    void replaceCustomGetterSetterMappingStorageTest() {
        ConversionModuleFactoryMethod<CustomGetterSetterMappingStorage> factoryMethod
                = context -> customGetterSetterMappingStorage;
        ConversionModuleFactory.replaceCustomGetterSetterMappingStorage(factoryMethod);
        Assertions.assertEquals(factoryMethod, ConversionModuleFactory.customGetterSetterMappingStorage());
    }

    @Test
    void replaceExecutableTypeResolverTest() {
        ConversionModuleFactoryMethod<ExecutableTypeResolver<TypeMeta<?>>> factoryMethod
                = context -> executableTypeResolver;
        ConversionModuleFactory.replaceExecutableTypeResolver(factoryMethod);
        Assertions.assertEquals(factoryMethod, ConversionModuleFactory.executableTypeResolver());
    }

    @Test
    void replaceGetterSetterMapperTest() {
        ConversionModuleFactoryMethod<GetterSetterMapper> factoryMethod = context -> getterSetterMapper;
        ConversionModuleFactory.replaceGetterSetterMapper(factoryMethod);
        Assertions.assertEquals(factoryMethod, ConversionModuleFactory.getterSetterMapper());
    }

    @Test
    void replaceTypeConverterPickerTest() {
        ConversionModuleFactoryMethod<TypeConverterPicker> factoryMethod = context -> typeConverterPicker;
        ConversionModuleFactory.replaceTypeConverterPicker(factoryMethod);
        Assertions.assertEquals(factoryMethod, ConversionModuleFactory.typeConverterPicker());
    }

    @Test
    void replaceTypeConverterStorageTest() {
        ConversionModuleFactoryMethod<TypeConverterStorage> factoryMethod = context -> typeConverterStorage;
        ConversionModuleFactory.replaceTypeConverterStorage(factoryMethod);
        Assertions.assertEquals(factoryMethod, ConversionModuleFactory.typeConverterStorage());
    }

    @BeforeEach
    @AfterEach
    void resetModulesAndContext() {
        ContextUtil.resetModulesAndContext();
    }
}
