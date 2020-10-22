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

import com.github.vladislavsevruk.converter.exception.InstanceCreationException;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.Arrays;

/**
 * Utility methods for instance creation.
 */
@Log4j2
public final class InstanceCreationUtil {

    private InstanceCreationUtil() {
    }

    /**
     * Creates new instance of received class using public constructor without arguments.
     *
     * @param clazz <code>Class</code> of instance to create.
     * @param <T>   the type represented by class.
     * @return new instance of received class.
     * @throws InstanceCreationException if target class is abstract or has no public constructor without arguments.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createItem(Class<T> clazz) {
        try {
            log.debug(() -> String.format("Trying to create %s instance.", clazz.getName()));
            Object instance = getNonArgsConstructor(clazz.getConstructors()).newInstance();
            log.debug(() -> String.format("Successfully created %s instance.", clazz.getName()));
            return (T) instance;
        } catch (ReflectiveOperationException reOpEx) {
            String message = "Failed to create target model.";
            log.error(message, reOpEx);
            throw new InstanceCreationException(message, reOpEx);
        }
    }

    private static Constructor<?> getNonArgsConstructor(Constructor<?>[] constructors) {
        return Arrays.stream(constructors).filter(InstanceCreationUtil::hasNoArgs).findAny().orElseThrow(() -> {
            String message = "Target model should have public constructor without arguments.";
            log.error(message);
            return new InstanceCreationException(message);
        });
    }

    private static boolean hasNoArgs(Executable executable) {
        return executable.getParameterTypes().length == 0;
    }
}
