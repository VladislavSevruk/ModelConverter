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

public class FromArrayDonorModel {

    @SuppressWarnings("unchecked")
    public List<Integer>[] arrayMatchingBoundedWildcardTypeAcceptor() {
        return new List[]{ Collections.singletonList(1), Collections.singletonList(2) };
    }

    public Long[] arrayMatchingType() {
        return new Long[]{ 1L, 2L };
    }

    public List<?>[] arrayMatchingWildcardType() {
        return new List<?>[]{ Collections.singletonList(2L), Collections.singletonList("arrayMatchingWildcardType") };
    }

    @SuppressWarnings("unchecked")
    public List<Boolean>[] arrayMatchingWildcardTypeAcceptor() {
        return new List[]{ Collections.singletonList(true) };
    }

    @SuppressWarnings("unchecked")
    public List<String>[] arrayMismatchingBoundedWildcardTypeAcceptor() {
        return new List[]{ Collections.singletonList("arrayMismatchingBoundedWildcardTypeAcceptor") };
    }

    @SuppressWarnings("unchecked")
    public List<? extends Number>[] arrayMismatchingBoundedWildcardTypeDonorNeg() {
        return new List[]{ Collections.singletonList(2.3), Collections.singletonList(3) };
    }

    @SuppressWarnings("unchecked")
    public List<? extends Number>[] arrayMismatchingBoundedWildcardTypeDonorPos() {
        return new List[]{ Collections.singletonList(4), Collections.singletonList(5) };
    }

    public Integer[] arrayMismatchingTypeWithKnownConverter() {
        return new Integer[]{ 5, 6 };
    }

    public Character[] arrayMismatchingTypeWithoutKnownConverter() {
        return new Character[]{ 6, 7 };
    }

    public List<?>[] arrayMismatchingWildcardTypeDonorNeg() {
        return new List[]{ Collections.singletonList(8.8),
                Collections.singletonList("arrayMismatchingWildcardTypeDonorNeg") };
    }

    public List<?>[] arrayMismatchingWildcardTypeDonorPos() {
        return new List[]{ Collections.singletonList(9.9), Collections.singletonList(10.10) };
    }
}
