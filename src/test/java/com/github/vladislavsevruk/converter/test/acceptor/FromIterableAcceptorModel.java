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
package com.github.vladislavsevruk.converter.test.acceptor;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Accessors(fluent = true)
@Data
public class FromIterableAcceptorModel {

    private List<Float> listMatchingParameterType;
    private List<?> listMatchingWildcardType;
    private LinkedList<Double> listMismatchingDescendantsMatchingTypes;
    private LinkedList<Long> listMismatchingDescendantsMismatchingTypesWithKnownConverter;
    private LinkedList<Calendar> listMismatchingDescendantsMismatchingTypesWithoutKnownConverter;
    private List<Integer> listMismatchingParameterTypeWithKnownConverter;
    private List<Instant> listMismatchingParameterTypeWithoutKnownConverter;
    private List<?> listMismatchingWildcardTypeAcceptor;
    private List<Character> listMismatchingWildcardTypeDonor;
    private Set<Character> setMatchingParameterType;
    private Set<?> setMatchingWildcardType;
    private HashSet<Double> setMismatchingDescendantsMatchingTypes;
    private HashSet<Float> setMismatchingDescendantsMismatchingTypesWithKnownConverter;
    private HashSet<Calendar> setMismatchingDescendantsMismatchingTypesWithoutKnownConverter;
    private Set<Boolean> setMismatchingParameterTypeWithKnownConverter;
    private Set<Date> setMismatchingParameterTypeWithoutKnownConverter;
    private Set<?> setMismatchingWildcardTypeAcceptor;
    private Set<Boolean> setMismatchingWildcardTypeDonor;
}
