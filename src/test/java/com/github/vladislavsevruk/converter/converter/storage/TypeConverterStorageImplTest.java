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

import com.github.vladislavsevruk.converter.context.ConversionContext;
import com.github.vladislavsevruk.converter.converter.TypeConverter;
import com.github.vladislavsevruk.converter.converter.parameterized.ParameterizedTypeConverter;
import com.github.vladislavsevruk.converter.converter.simple.ClassTypeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TypeConverterStorageImplTest {

    @Mock
    private ClassTypeConverter classTypeConverter;
    @Mock
    private ConversionContext conversionContext;
    @Mock
    private ParameterizedTypeConverter parameterizedTypeConverter;

    @Test
    void addExistentClassConverterAfterExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addAfter(classConverter, classConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentClassConverterAfterNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addAfter(classConverter, classTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentClassConverterAfterNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addAfter(classConverter, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentClassConverterBeforeExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addBefore(classConverter, classConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentClassConverterBeforeNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addBefore(classConverter, classTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentClassConverterBeforeNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addBefore(classConverter, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentClassConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        typeConverterStorage.add(getClassTypeConverter(typeConverters));
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterAfterExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addAfter(parameterizedConverter, parameterizedConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterAfterNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addAfter(parameterizedConverter, parameterizedTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterAfterNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addAfter(parameterizedConverter, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterBeforeExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addBefore(parameterizedConverter, parameterizedConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterBeforeNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addBefore(parameterizedConverter, parameterizedTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterBeforeNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addBefore(parameterizedConverter, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addExistentParameterizedConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        typeConverterStorage.add(getParameterizedTypeConverter(typeConverters));
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNewClassConverterAfterExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        int indexBefore = getIndexOfConverter(typeConverters, classConverter.getClass());
        typeConverterStorage.addAfter(classTypeConverter, classConverter.getClass());
        typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(indexBefore + 1, getIndexOfConverter(typeConverters, classTypeConverter.getClass()));
        Assertions.assertEquals(indexBefore, getIndexOfConverter(typeConverters, classConverter.getClass()));
    }

    @Test
    void addNewClassConverterAfterNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter(classTypeConverter, classTypeConverter.getClass());
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, classTypeConverter.getClass()));
    }

    @Test
    void addNewClassConverterAfterNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter(classTypeConverter, null);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, classTypeConverter.getClass()));
    }

    @Test
    void addNewClassConverterBeforeExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        int indexBefore = getIndexOfConverter(typeConverters, classConverter.getClass());
        typeConverterStorage.addBefore(classTypeConverter, classConverter.getClass());
        typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(indexBefore, getIndexOfConverter(typeConverters, classTypeConverter.getClass()));
        Assertions.assertEquals(indexBefore + 1, getIndexOfConverter(typeConverters, classConverter.getClass()));
    }

    @Test
    void addNewClassConverterBeforeNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore(classTypeConverter, classTypeConverter.getClass());
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, classTypeConverter.getClass()));
    }

    @Test
    void addNewClassConverterBeforeNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore(classTypeConverter, null);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, classTypeConverter.getClass()));
    }

    @Test
    void addNewClassConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.add(classTypeConverter);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
    }

    @Test
    void addNewParameterizedConverterAfterExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        int indexBefore = getIndexOfConverter(typeConverters, parameterizedConverter.getClass());
        typeConverterStorage.addAfter(parameterizedTypeConverter, parameterizedConverter.getClass());
        typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(indexBefore + 1,
                getIndexOfConverter(typeConverters, parameterizedTypeConverter.getClass()));
        Assertions.assertEquals(indexBefore, getIndexOfConverter(typeConverters, parameterizedConverter.getClass()));
    }

    @Test
    void addNewParameterizedConverterAfterNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter(parameterizedTypeConverter, parameterizedTypeConverter.getClass());
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, parameterizedTypeConverter.getClass()));
    }

    @Test
    void addNewParameterizedConverterAfterNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter(parameterizedTypeConverter, null);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, parameterizedTypeConverter.getClass()));
    }

    @Test
    void addNewParameterizedConverterBeforeExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        int indexBefore = getIndexOfConverter(typeConverters, parameterizedConverter.getClass());
        typeConverterStorage.addBefore(parameterizedTypeConverter, parameterizedConverter.getClass());
        typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions
                .assertEquals(indexBefore, getIndexOfConverter(typeConverters, parameterizedTypeConverter.getClass()));
        Assertions
                .assertEquals(indexBefore + 1, getIndexOfConverter(typeConverters, parameterizedConverter.getClass()));
    }

    @Test
    void addNewParameterizedConverterBeforeNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore(parameterizedTypeConverter, parameterizedTypeConverter.getClass());
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, parameterizedTypeConverter.getClass()));
    }

    @Test
    void addNewParameterizedConverterBeforeNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore(parameterizedTypeConverter, null);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersAfter = typeConverters.size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
        Assertions.assertEquals(numberOfConvertersAfter - 1,
                getIndexOfConverter(typeConverters, parameterizedTypeConverter.getClass()));
    }

    @Test
    void addNewParameterizedConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.add(parameterizedTypeConverter);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore + 1, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterAfterExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addAfter((ClassTypeConverter) null, classConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterAfterNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter((ClassTypeConverter) null, classTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterAfterNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter((ClassTypeConverter) null, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterBeforeExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ClassTypeConverter classConverter = getClassTypeConverter(typeConverters);
        typeConverterStorage.addBefore((ClassTypeConverter) null, classConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterBeforeNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore((ClassTypeConverter) null, classTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterBeforeNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore((ClassTypeConverter) null, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullClassConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.add((ClassTypeConverter) null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterAfterExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addAfter((ParameterizedTypeConverter) null, parameterizedConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterAfterNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter((ParameterizedTypeConverter) null, parameterizedTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterAfterNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addAfter((ParameterizedTypeConverter) null, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterBeforeExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        List<TypeConverter> typeConverters = typeConverterStorage.getAll();
        int numberOfConvertersBefore = typeConverters.size();
        ParameterizedTypeConverter parameterizedConverter = getParameterizedTypeConverter(typeConverters);
        typeConverterStorage.addBefore((ParameterizedTypeConverter) null, parameterizedConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterBeforeNonExistentTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore((ParameterizedTypeConverter) null, parameterizedTypeConverter.getClass());
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterBeforeNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.addBefore((ParameterizedTypeConverter) null, null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void addNullParameterizedConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        int numberOfConvertersBefore = typeConverterStorage.getAll().size();
        typeConverterStorage.add((ParameterizedTypeConverter) null);
        int numberOfConvertersAfter = typeConverterStorage.getAll().size();
        Assertions.assertEquals(numberOfConvertersBefore, numberOfConvertersAfter);
    }

    @Test
    void isDefaultConverterDefaultClassConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        ClassTypeConverter defaultConveter = getClassTypeConverter(typeConverterStorage.getAll());
        Assertions.assertTrue(typeConverterStorage.isDefaultConverter(defaultConveter));
    }

    @Test
    void isDefaultConverterDefaultParameterizedConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        ParameterizedTypeConverter defaultConveter = getParameterizedTypeConverter(typeConverterStorage.getAll());
        Assertions.assertTrue(typeConverterStorage.isDefaultConverter(defaultConveter));
    }

    @Test
    void isDefaultConverterForNullTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        Assertions.assertFalse(typeConverterStorage.isDefaultConverter(null));
    }

    @Test
    void isDefaultConverterNonDefaultClassConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        Assertions.assertFalse(typeConverterStorage.isDefaultConverter(classTypeConverter));
    }

    @Test
    void isDefaultConverterNonDefaultParameterizedConverterTest() {
        TypeConverterStorage typeConverterStorage = new TypeConverterStorageImpl(conversionContext);
        Assertions.assertFalse(typeConverterStorage.isDefaultConverter(parameterizedTypeConverter));
    }

    private ClassTypeConverter getClassTypeConverter(List<TypeConverter> typeConverters) {
        for (TypeConverter typeConverter : typeConverters) {
            if (ClassTypeConverter.class.isAssignableFrom(typeConverter.getClass())) {
                return (ClassTypeConverter) typeConverter;
            }
        }
        throw new AssertionError("Failed to find any ClassTypeConverter.");
    }

    private int getIndexOfConverter(List<TypeConverter> converters, Class<?> converterClass) {
        for (int i = 0; i < converters.size(); ++i) {
            if (converterClass.equals(converters.get(i).getClass())) {
                return i;
            }
        }
        throw new AssertionError("Failed to find converter at list.");
    }

    private ParameterizedTypeConverter getParameterizedTypeConverter(List<TypeConverter> typeConverters) {
        for (TypeConverter typeConverter : typeConverters) {
            if (ParameterizedTypeConverter.class.isAssignableFrom(typeConverter.getClass())) {
                return (ParameterizedTypeConverter) typeConverter;
            }
        }
        throw new AssertionError("Failed to find any ParameterizedTypeConverter.");
    }
}
