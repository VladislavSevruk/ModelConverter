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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractElementSequenceDonorModel {

    public String[] arrayToAbstractListMatchingType() {
        return new String[]{ "arrayToAbstractListMatchingType" };
    }

    public Integer[] arrayToAbstractListNonMatchingTypeWithConverter() {
        return new Integer[]{ 1, 2 };
    }

    public Character[] arrayToAbstractListNonMatchingTypeWithoutConverter() {
        return new Character[]{ 2, 3 };
    }

    public String[] arrayToAbstractSetMatchingType() {
        return new String[]{ "arrayToAbstractSetMatchingType" };
    }

    public Integer[] arrayToAbstractSetNonMatchingTypeWithConverter() {
        return new Integer[]{ 3, 4 };
    }

    public Character[] arrayToAbstractSetNonMatchingTypeWithoutConverter() {
        return new Character[]{ 4, 5 };
    }

    public String[] arrayToCollectionMatchingType() {
        return new String[]{ "arrayToCollectionMatchingType" };
    }

    public Integer[] arrayToCollectionNonMatchingTypeWithConverter() {
        return new Integer[]{ 5, 6 };
    }

    public Character[] arrayToCollectionNonMatchingTypeWithoutConverter() {
        return new Character[]{ 6, 7 };
    }

    public String[] arrayToIterableMatchingType() {
        return new String[]{ "arrayToIterableMatchingType" };
    }

    public Integer[] arrayToIterableNonMatchingTypeWithConverter() {
        return new Integer[]{ 7, 8 };
    }

    public Character[] arrayToIterableNonMatchingTypeWithoutConverter() {
        return new Character[]{ 8, 9 };
    }

    public List<String> listToAbstractSetMatchingType() {
        return Collections.singletonList("listToAbstractSetMatchingType");
    }

    public List<Integer> listToAbstractSetNonMatchingTypeWithConverter() {
        return Arrays.asList(9, 10);
    }

    public List<Character> listToAbstractSetNonMatchingTypeWithoutConverter() {
        return Arrays.asList('0', '1');
    }

    public List<Integer> listToCollectionNonMatchingTypeWithConverter() {
        return Arrays.asList(11, 12);
    }

    public List<Character> listToCollectionNonMatchingTypeWithoutConverter() {
        return Arrays.asList('2', '3');
    }

    public List<Integer> listToIterableNonMatchingTypeWithConverter() {
        return Arrays.asList(13, 14);
    }

    public List<Character> listToIterableNonMatchingTypeWithoutConverter() {
        return Arrays.asList('4', '5');
    }

    public Set<String> setToAbstractListMatchingType() {
        return Collections.singleton("setToAbstractListMatchingType");
    }

    public Set<Integer> setToAbstractListNonMatchingTypeWithConverter() {
        return Stream.of(15, 16).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<Character> setToAbstractListNonMatchingTypeWithoutConverter() {
        return Collections.singleton('6');
    }
}
