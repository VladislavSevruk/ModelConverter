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
package com.github.vladislavsevruk.converter.test.donor;

import java.util.Collections;
import java.util.Map;

public class FromMapDonorModel {

    public Map<String, String> toAbstractMapMatchingTypes() {
        return Collections.singletonMap("key1", "value1");
    }

    public Map<String, String> toHashMapMatchingTypes() {
        return Collections.singletonMap("key1", "value1");
    }

    public Map<String, String> toLinkedMapMatchingTypes() {
        return Collections.singletonMap("key1", "value1");
    }

    public Map<String, String> toMapMatchingTypes() {
        return Collections.singletonMap("key1", "value1");
    }

    public Map<Long, String> toMapMismatchingKeyTypeWithConverter() {
        return Collections.singletonMap(2L, "value2");
    }

    public Map<Character, String> toMapMismatchingKeyTypeWithoutConverter() {
        return Collections.singletonMap('3', "value3");
    }

    public Map<Long, Long> toMapMismatchingTypesWithConverter() {
        return Collections.singletonMap(4L, 5L);
    }

    public Map<Character, Character> toMapMismatchingTypesWithoutConverter() {
        return Collections.singletonMap('5', '6');
    }

    public Map<String, Long> toMapMismatchingValueTypeWithConverter() {
        return Collections.singletonMap("key6", 6L);
    }

    public Map<String, Character> toMapMismatchingValueTypeWithoutConverter() {
        return Collections.singletonMap("key7", '7');
    }
}
