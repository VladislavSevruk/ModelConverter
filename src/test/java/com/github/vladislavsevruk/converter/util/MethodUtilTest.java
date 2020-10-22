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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class MethodUtilTest {

    @Test
    void methodHasArgsTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("equals", Object.class);
        Assertions.assertFalse(MethodUtil.hasNoArgs(methodToCheck));
    }

    @Test
    void methodHasNoArgsTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("toString");
        Assertions.assertTrue(MethodUtil.hasNoArgs(methodToCheck));
    }

    @Test
    void nonObjectMethodTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("intValue");
        Assertions.assertTrue(MethodUtil.nonObjectMethod(methodToCheck));
    }

    @Test
    void nonStaticMethodTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("intValue");
        Assertions.assertTrue(MethodUtil.nonStatic(methodToCheck));
    }

    @Test
    void objectMethodTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("toString");
        Assertions.assertFalse(MethodUtil.nonObjectMethod(methodToCheck));
    }

    @Test
    void overriddenObjectMethodTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("hashCode");
        Assertions.assertFalse(MethodUtil.nonObjectMethod(methodToCheck));
    }

    @Test
    void staticMethodTest() throws Throwable {
        Method methodToCheck = Long.class.getMethod("decode", String.class);
        Assertions.assertFalse(MethodUtil.nonStatic(methodToCheck));
    }
}
