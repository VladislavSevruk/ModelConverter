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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FromIterableDonorModel {

    public List<Float> listMatchingParameterType() {
        return Arrays.asList(1.1f, 2.2f);
    }

    public List<?> listMatchingWildcardType() {
        return Arrays.asList(2.2, "listMatchingWildcardType");
    }

    public ArrayList<Double> listMismatchingDescendantsMatchingTypes() {
        return Stream.of(3.3, 4.4).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> listMismatchingDescendantsMismatchingTypesWithKnownConverter() {
        return Stream.of("5", "listMismatchingDescendantsMismatchingTypesWithKnownConverter")
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Character> listMismatchingDescendantsMismatchingTypesWithoutKnownConverter() {
        return Stream.of('0', '1').collect(Collectors.toCollection(ArrayList::new));
    }

    public List<String> listMismatchingParameterTypeWithKnownConverter() {
        return Arrays.asList("listMismatchingParameterTypeWithKnownConverter", "6");
    }

    public List<Character> listMismatchingParameterTypeWithoutKnownConverter() {
        return Arrays.asList((char) 55, (char) 66);
    }

    public List<String> listMismatchingWildcardTypeAcceptor() {
        return Arrays.asList("listMismatchingWildcardTypeAcceptor-1", "listMismatchingWildcardTypeAcceptor-2");
    }

    public List<?> listMismatchingWildcardTypeDonor() {
        return Arrays.asList((char) 66, "listMismatchingWildcardTypeDonor");
    }

    public Set<Character> setMatchingParameterType() {
        return Stream.of((char) 77, (char) 88).collect(Collectors.toSet());
    }

    public Set<?> setMatchingWildcardType() {
        return Stream.of(8.8, "setMatchingWildcardType").collect(Collectors.toSet());
    }

    public TreeSet<Double> setMismatchingDescendantsMatchingTypes() {
        return Stream.of(9.9, 10.10).collect(Collectors.toCollection(TreeSet::new));
    }

    public TreeSet<String> setMismatchingDescendantsMismatchingTypesWithKnownConverter() {
        return Stream.of("setMismatchingDescendantsMismatchingTypesWithKnownConverter", "10.10")
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public TreeSet<Character> setMismatchingDescendantsMismatchingTypesWithoutKnownConverter() {
        return Stream.of('2', '3').collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<Integer> setMismatchingParameterTypeWithKnownConverter() {
        return Stream.of(0, 13).collect(Collectors.toSet());
    }

    public Set<Character> setMismatchingParameterTypeWithoutKnownConverter() {
        return Stream.of('4', '5').collect(Collectors.toSet());
    }

    public Set<Character> setMismatchingWildcardTypeAcceptor() {
        return Stream.of((char) 131, (char) 141).collect(Collectors.toSet());
    }

    public Set<?> setMismatchingWildcardTypeDonor() {
        return Stream.of(14.14, "setMismatchingWildcardTypeDonor").collect(Collectors.toSet());
    }
}
