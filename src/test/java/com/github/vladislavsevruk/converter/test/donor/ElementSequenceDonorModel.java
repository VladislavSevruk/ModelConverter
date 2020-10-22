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
import java.util.List;
import java.util.Set;

public class ElementSequenceDonorModel {

    public String[] arrayToArrayListMatchingType() {
        return new String[]{ "arrayToArrayListMatchingType" };
    }

    public String[] arrayToLinkedHashSetMatchingType() {
        return new String[]{ "arrayToLinkedHashSetMatchingType" };
    }

    public String[] arrayToListMatchingType() {
        return new String[]{ "arrayToListMatchingType" };
    }

    public Integer[] arrayToListNonMatchingTypeWithConverter() {
        return new Integer[]{ 1 };
    }

    public Character[] arrayToListNonMatchingTypeWithoutConverter() {
        return new Character[]{ 2 };
    }

    public String[] arrayToSetMatchingType() {
        return new String[]{ "arrayToSetMatchingType" };
    }

    public Integer[] arrayToSetNonMatchingTypeWithConverter() {
        return new Integer[]{ 3 };
    }

    public Character[] arrayToSetNonMatchingTypeWithoutConverter() {
        return new Character[]{ 4 };
    }

    public List<String> listToArrayMatchingType() {
        return Collections.singletonList("listToArrayMatchingType");
    }

    public List<Integer> listToArrayNonMatchingTypeWithConverter() {
        return Collections.singletonList(5);
    }

    public List<Character> listToArrayNonMatchingTypeWithoutConverter() {
        return Collections.singletonList('6');
    }

    public String[] listToLinkedHashSetMatchingType() {
        return new String[]{ "listToLinkedHashSetMatchingType" };
    }

    public List<String> listToSetMatchingType() {
        return Collections.singletonList("listToSetMatchingType");
    }

    public List<Integer> listToSetNonMatchingTypeWithConverter() {
        return Collections.singletonList(7);
    }

    public List<Character> listToSetNonMatchingTypeWithoutConverter() {
        return Collections.singletonList('8');
    }

    public String[] setToArrayListMatchingType() {
        return new String[]{ "setToArrayListMatchingType" };
    }

    public Set<String> setToArrayMatchingType() {
        return Collections.singleton("setToArrayMatchingType");
    }

    public Set<Integer> setToArrayNonMatchingTypeWithConverter() {
        return Collections.singleton(9);
    }

    public Set<Character> setToArrayNonMatchingTypeWithoutConverter() {
        return Collections.singleton('0');
    }

    public Set<String> setToListMatchingType() {
        return Collections.singleton("setToListMatchingType");
    }

    public Set<Integer> setToListNonMatchingTypeWithConverter() {
        return Collections.singleton(11);
    }

    public Set<Character> setToListNonMatchingTypeWithoutConverter() {
        return Collections.singleton('2');
    }
}
