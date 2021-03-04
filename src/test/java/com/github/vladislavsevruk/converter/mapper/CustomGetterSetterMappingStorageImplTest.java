/*
 * MIT License
 *
 * Copyright (c) 2021 Uladzislau Seuruk
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

import com.github.vladislavsevruk.converter.context.ConversionContextManager;
import com.github.vladislavsevruk.converter.mapper.method.MappedMethod;
import com.github.vladislavsevruk.converter.test.acceptor.AcceptorSuperclassModel;
import com.github.vladislavsevruk.converter.test.acceptor.CustomMappingAcceptorModel;
import com.github.vladislavsevruk.converter.test.donor.CustomMappingDonorModel;
import com.github.vladislavsevruk.resolver.type.TypeMeta;
import com.github.vladislavsevruk.resolver.type.TypeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class CustomGetterSetterMappingStorageImplTest {

    @Test
    void addCustomMappingAcceptorMethodFromAnotherClassType() throws Throwable {
        Method donorMethod = CustomMappingDonorModel.class.getMethod("donorMatchingType");
        Method acceptorMethod = CustomMappingAcceptorModel.class.getMethod("acceptorMatchingType", String.class);
        CustomGetterSetterMappingStorage storage = new CustomGetterSetterMappingStorageImpl(
                ConversionContextManager.getContext());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.addGetterSetterMapping(donorMethod, acceptorMethod, AcceptorSuperclassModel.class));
    }

    @Test
    void addCustomMappingAcceptorMethodWithSeveralParametersType() throws Throwable {
        Method donorMethod = CustomMappingDonorModel.class.getMethod("donorNonMatchingType");
        Method acceptorMethod = CustomMappingAcceptorModel.class
                .getMethod("acceptorMethodWithSeveralParameters", String.class, String.class);
        CustomGetterSetterMappingStorage storage = new CustomGetterSetterMappingStorageImpl(
                ConversionContextManager.getContext());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.addGetterSetterMapping(donorMethod, acceptorMethod));
    }

    @Test
    void addCustomMappingAcceptorMethodWithoutParametersType() throws Throwable {
        Method donorMethod = CustomMappingDonorModel.class.getMethod("donorNonMatchingType");
        Method acceptorMethod = CustomMappingAcceptorModel.class.getMethod("acceptorMethodWithoutParameters");
        CustomGetterSetterMappingStorage storage = new CustomGetterSetterMappingStorageImpl(
                ConversionContextManager.getContext());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.addGetterSetterMapping(donorMethod, acceptorMethod));
    }

    @Test
    void addCustomMappingDonorMethodVoidReturnType() throws Throwable {
        Method donorMethod = CustomMappingDonorModel.class.getMethod("donorVoidMethod");
        Method acceptorMethod = CustomMappingAcceptorModel.class.getMethod("acceptorMatchingType");
        CustomGetterSetterMappingStorage storage = new CustomGetterSetterMappingStorageImpl(
                ConversionContextManager.getContext());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.addGetterSetterMapping(donorMethod, acceptorMethod));
    }

    @Test
    void addCustomMappingDonorMethodWithParametersType() throws Throwable {
        Method donorMethod = CustomMappingDonorModel.class.getMethod("donorMethodWithParameters", String.class);
        Method acceptorMethod = CustomMappingAcceptorModel.class.getMethod("acceptorMatchingType");
        CustomGetterSetterMappingStorage storage = new CustomGetterSetterMappingStorageImpl(
                ConversionContextManager.getContext());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> storage.addGetterSetterMapping(donorMethod, acceptorMethod));
    }

    @Test
    void subclassesMappingsArePreferableTest() throws Throwable {
        Method donorMethod = CustomMappingDonorModel.class.getMethod("donorMatchingType");
        Method mapMethod = Map.class.getMethod("get", Object.class);
        Method linkedHashMapMethod = LinkedHashMap.class.getMethod("get", Object.class);
        Method hashMapMethod = HashMap.class.getMethod("get", Object.class);
        TypeMeta<?> mapTypeMeta = new TypeProvider<Map<String, String>>() {}.getTypeMeta();
        CustomGetterSetterMappingStorage storage = new CustomGetterSetterMappingStorageImpl(
                ConversionContextManager.getContext());
        storage.addGetterSetterMapping(donorMethod, mapMethod, mapTypeMeta);
        TypeMeta<?> linkedHashMapTypeMeta = new TypeProvider<LinkedHashMap<String, String>>() {}.getTypeMeta();
        storage.addGetterSetterMapping(donorMethod, linkedHashMapMethod, linkedHashMapTypeMeta);
        TypeMeta<?> hashMapTypeMeta = new TypeProvider<HashMap<String, String>>() {}.getTypeMeta();
        storage.addGetterSetterMapping(donorMethod, hashMapMethod, hashMapTypeMeta);
        MappedMethod mapMappedMethod = storage.getMapping(donorMethod, "a", mapTypeMeta);
        MappedMethod linkedHashMapMappedMethod = storage.getMapping(donorMethod, "a", linkedHashMapTypeMeta);
        MappedMethod hashMapMappedMethod = storage.getMapping(donorMethod, "a", hashMapTypeMeta);
        Assertions.assertEquals(mapMethod, mapMappedMethod.getAcceptorMethod());
        Assertions.assertNull(mapMappedMethod.getTypeConverter());
        Assertions.assertEquals(linkedHashMapMethod, linkedHashMapMappedMethod.getAcceptorMethod());
        Assertions.assertNull(linkedHashMapMappedMethod.getTypeConverter());
        Assertions.assertEquals(hashMapMethod, hashMapMappedMethod.getAcceptorMethod());
        Assertions.assertNull(hashMapMappedMethod.getTypeConverter());
    }
}
