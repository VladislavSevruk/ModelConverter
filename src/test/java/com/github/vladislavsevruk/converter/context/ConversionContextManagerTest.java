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
import com.github.vladislavsevruk.converter.mapper.GetterSetterMapper;
import com.github.vladislavsevruk.resolver.resolver.executable.ExecutableTypeResolver;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConversionContextManagerTest {

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

    @BeforeAll
    static void enableContextRefresh() {
        ConversionContextManager.enableContextAutoRefresh();
    }

    @AfterAll
    static void setInitialAutoContextRefresh() {
        resetModulesAndContext();
    }

    @Test
    void autoRefreshContextAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.enableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceEngine(context -> conversionEngine);
        ConversionModuleFactory.replaceGetterSetterMapper(context -> getterSetterMapper);
        ConversionModuleFactory.replaceExecutableTypeResolver(context -> executableTypeResolver);
        ConversionModuleFactory.replaceTypeConverterPicker(context -> typeConverterPicker);
        ConversionModuleFactory.replaceTypeConverterStorage(context -> typeConverterStorage);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertSame(conversionEngine, conversionContext2.getConversionEngine());
        Assertions.assertSame(getterSetterMapper, conversionContext2.getGetterSetterMapper());
        Assertions.assertSame(executableTypeResolver, conversionContext2.getExecutableTypeResolver());
        Assertions.assertSame(typeConverterPicker, conversionContext2.getTypeConverterPicker());
        Assertions.assertSame(typeConverterStorage, conversionContext2.getTypeConverterStorage());
    }

    @Test
    void autoRefreshContextAfterContextEngineUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.enableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceEngine(context -> conversionEngine);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertSame(conversionEngine, conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void autoRefreshContextAfterExecutableTypeResolverUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.enableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceExecutableTypeResolver(context -> executableTypeResolver);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertSame(executableTypeResolver, conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void autoRefreshContextAfterGetterSetterMapperUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.enableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceGetterSetterMapper(context -> getterSetterMapper);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions.assertSame(getterSetterMapper, conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void autoRefreshContextAfterTypeConverterPickerUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.enableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceTypeConverterPicker(context -> typeConverterPicker);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertSame(typeConverterPicker, conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void autoRefreshContextAfterTypeConverterStorageUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.enableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceTypeConverterStorage(context -> typeConverterStorage);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertSame(typeConverterStorage, conversionContext2.getTypeConverterStorage());
    }

    @Test
    void equalContextAfterRefreshWithoutUpdatesTest() {
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1, conversionContext2);
    }

    @Test
    void newContextAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceEngine(context -> conversionEngine);
        ConversionModuleFactory.replaceGetterSetterMapper(context -> getterSetterMapper);
        ConversionModuleFactory.replaceExecutableTypeResolver(context -> executableTypeResolver);
        ConversionModuleFactory.replaceTypeConverterPicker(context -> typeConverterPicker);
        ConversionModuleFactory.replaceTypeConverterStorage(context -> typeConverterStorage);
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertSame(conversionEngine, conversionContext2.getConversionEngine());
        Assertions.assertSame(getterSetterMapper, conversionContext2.getGetterSetterMapper());
        Assertions.assertSame(executableTypeResolver, conversionContext2.getExecutableTypeResolver());
        Assertions.assertSame(typeConverterPicker, conversionContext2.getTypeConverterPicker());
        Assertions.assertSame(typeConverterStorage, conversionContext2.getTypeConverterStorage());
    }

    @Test
    void newContextAfterRefreshAfterContextEngineUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceEngine(context -> conversionEngine);
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertSame(conversionEngine, conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void newContextAfterRefreshAfterExecutableTypeResolverUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceExecutableTypeResolver(context -> executableTypeResolver);
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertSame(executableTypeResolver, conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void newContextAfterRefreshAfterGetterSetterMapperUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceGetterSetterMapper(context -> getterSetterMapper);
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions.assertSame(getterSetterMapper, conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void newContextAfterRefreshAfterTypeConverterPickerUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceTypeConverterPicker(context -> typeConverterPicker);
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertSame(typeConverterPicker, conversionContext2.getTypeConverterPicker());
        Assertions.assertNotSame(conversionContext1.getTypeConverterStorage(),
                conversionContext2.getTypeConverterStorage());
    }

    @Test
    void newContextAfterRefreshAfterTypeConverterStorageUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceTypeConverterStorage(context -> typeConverterStorage);
        ConversionContextManager.refreshContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertNotSame(conversionContext1, conversionContext2);
        Assertions.assertNotSame(conversionContext1.getConversionEngine(), conversionContext2.getConversionEngine());
        Assertions
                .assertNotSame(conversionContext1.getGetterSetterMapper(), conversionContext2.getGetterSetterMapper());
        Assertions.assertNotSame(conversionContext1.getExecutableTypeResolver(),
                conversionContext2.getExecutableTypeResolver());
        Assertions.assertNotSame(conversionContext1.getTypeConverterPicker(),
                conversionContext2.getTypeConverterPicker());
        Assertions.assertSame(typeConverterStorage, conversionContext2.getTypeConverterStorage());
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterConversionEngineUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceEngine(context -> conversionEngine);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterExecutableTypeResolverUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceExecutableTypeResolver(context -> executableTypeResolver);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterGetterSetterMapperUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceGetterSetterMapper(context -> getterSetterMapper);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterRefreshAfterAllModulesUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceEngine(context -> conversionEngine);
        ConversionModuleFactory.replaceGetterSetterMapper(context -> getterSetterMapper);
        ConversionModuleFactory.replaceExecutableTypeResolver(context -> executableTypeResolver);
        ConversionModuleFactory.replaceTypeConverterPicker(context -> typeConverterPicker);
        ConversionModuleFactory.replaceTypeConverterStorage(context -> typeConverterStorage);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterTypeConverterPickerUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceTypeConverterPicker(context -> typeConverterPicker);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    @Test
    void sameContextIsReturnedIfAutoRefreshDisabledAfterTypeConverterStorageUpdatesTest() {
        resetModulesAndContext();
        ConversionContextManager.disableContextAutoRefresh();
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionModuleFactory.replaceTypeConverterStorage(context -> typeConverterStorage);
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    @Test
    void sameContextIsReturnedTest() {
        ConversionContext conversionContext1 = ConversionContextManager.getContext();
        ConversionContext conversionContext2 = ConversionContextManager.getContext();
        Assertions.assertSame(conversionContext1, conversionContext2);
    }

    private static void resetModulesAndContext() {
        ConversionContextManager.disableContextAutoRefresh();
        ConversionModuleFactory.replaceEngine(null);
        ConversionModuleFactory.replaceGetterSetterMapper(null);
        ConversionModuleFactory.replaceExecutableTypeResolver(null);
        ConversionModuleFactory.replaceTypeConverterPicker(null);
        ConversionModuleFactory.replaceTypeConverterStorage(null);
        ConversionContextManager.refreshContext();
    }
}
