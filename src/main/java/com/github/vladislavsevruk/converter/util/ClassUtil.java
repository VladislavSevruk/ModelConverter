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
package com.github.vladislavsevruk.converter.util;

import com.github.vladislavsevruk.resolver.type.TypeMeta;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Contains utility methods for getting superclasses and implemented interfaces.
 */
@Log4j2
public final class ClassUtil {

    private ClassUtil() {
    }

    /**
     * Gets closest common type of array elements.
     *
     * @param values array with elements to get common type of.
     * @param <T>    array elements type.
     * @return <code>Class</code> that is closest common type of array elements.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> getCommonClass(T[] values) {
        Set<Class<?>> classes = Arrays.stream(values).filter(Objects::nonNull).map(Object::getClass)
                .collect(Collectors.toSet());
        Class<? extends T> commonClass = getCommonClass(classes);
        return Object.class.equals(commonClass) ? (Class<T>) values.getClass().getComponentType() : commonClass;
    }

    /**
     * Gets closest common type of iterable elements.
     *
     * @param values <code>Iterable</code> with elements to get common type of.
     * @param <T>    iterable elements type.
     * @return <code>Class</code> that is closest common type of iterable elements.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> getCommonClass(Iterable<T> values) {
        Set<Class<?>> classes = StreamSupport.stream(values.spliterator(), false).filter(Objects::nonNull)
                .map(Object::getClass).collect(Collectors.toSet());
        return (Class<? extends T>) getCommonClass(classes);
    }

    /**
     * Gets index of element that has received type from received list.
     *
     * @param elements   <code>List</code> with target type elements.
     * @param targetType <code>Class</code> with type to get index of.
     * @param <T>        type of list elements.
     * @return index of element that has received <code>Class</code> from received <code>List</code> or <code>-1</code>
     * if there is no element with such type.
     */
    public static <T> int getIndexOfType(List<T> elements, Class<? extends T> targetType) {
        int targetTypeIndex = -1;
        if (targetType == null) {
            log.info("Target type is null.");
            return -1;
        }
        for (int i = 0; i < elements.size(); ++i) {
            if (targetType.equals(elements.get(i).getClass())) {
                targetTypeIndex = i;
                break;
            }
        }
        return targetTypeIndex;
    }

    /**
     * Checks if received type meta represents non-parameterized type.
     *
     * @param typeMeta <code>TypeMeta</code> with type metadata.
     * @return <code>true</code> if received type meta has no generic types or represents array with non-generic
     * component type, <code>false</code> otherwise.
     */
    public static boolean nonParameterized(TypeMeta<?> typeMeta) {
        if (typeMeta.getGenericTypes().length == 0) {
            return true;
        }
        if (typeMeta.getType().isArray()) {
            return nonParameterized(typeMeta.getGenericTypes()[0]);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends T> getCommonClass(Set<Class<?>> classes) {
        if (classes.isEmpty() || classes.contains(Object.class)) {
            return (Class<T>) Object.class;
        }
        if (classes.size() == 1) {
            return (Class<? extends T>) classes.iterator().next();
        }
        return (Class<? extends T>) getCommonSuperclass(classes);
    }

    private static Class<?> getCommonSuperclass(Set<Class<?>> classes) {
        Class<?> clazz = classes.iterator().next();
        do {
            if (classes.stream().allMatch(clazz::isAssignableFrom)) {
                return clazz;
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return Object.class;
    }
}
