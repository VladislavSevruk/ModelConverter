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

import com.github.vladislavsevruk.resolver.util.PrimitiveWrapperUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Utility methods for class Method.
 */
public final class MethodUtil {

    private static final List<Method> OBJECT_METHODS = Arrays.asList(Object.class.getMethods());

    private MethodUtil() {
    }

    /**
     * Checks if received method can potentially be getter.
     *
     * @param method <code>Method</code> to check.
     * @return <code>true</code> if received method has no argument parameters and has non-void return type,
     * <code>false</code> otherwise.
     */
    public static boolean canBeGetter(Method method) {
        return MethodUtil.hasNoArgs(method) && !PrimitiveWrapperUtil.unwrap(method.getReturnType()).equals(void.class);
    }

    /**
     * Checks if received method can potentially be setter.
     *
     * @param method <code>Method</code> to check.
     * @return <code>true</code> if received method has single argument parameter, <code>false</code> otherwise.
     */
    public static boolean canBeSetter(Method method) {
        return method.getParameterTypes().length == 1;
    }

    /**
     * Checks if received method has no argument parameters.
     *
     * @param method <code>Method</code> to check.
     * @return <code>true</code> if received method has no argument parameters, <code>false</code> otherwise.
     */
    public static boolean hasNoArgs(Method method) {
        return method.getParameterTypes().length == 0;
    }

    /**
     * Checks if received method is one of <code>Object</code> methods or overrides one of them.
     *
     * @param method <code>Method</code> to check.
     * @return <code>true</code> if received method is one of object methods or overrides one of them,
     * <code>false</code> otherwise.
     */
    public static boolean nonObjectMethod(Method method) {
        return !OBJECT_METHODS.contains(method) && !isInheritedSignature(OBJECT_METHODS, method);
    }

    /**
     * Checks if received method has <code>static</code> modifier.
     *
     * @param method <code>Method</code> to check.
     * @return <code>true</code> if received method has static modifier, <code>false</code> otherwise.
     */
    public static boolean nonStatic(Method method) {
        return !Modifier.isStatic(method.getModifiers());
    }

    private static boolean hasSameSignature(Method method1, Method method2) {
        if (!method1.getName().equals(method2.getName())) {
            return false;
        }
        Class<?>[] method1ParameterTypes = method1.getParameterTypes();
        Class<?>[] method2ParameterTypes = method2.getParameterTypes();
        return Arrays.equals(method1ParameterTypes, method2ParameterTypes);
    }

    private static boolean isInheritedSignature(List<Method> superClassMethods, Method method) {
        for (Method superClassMethod : superClassMethods) {
            if (hasSameSignature(superClassMethod, method)) {
                return true;
            }
        }
        return false;
    }
}
