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

import java.time.Instant;

public class FromInstantDonorModel {

    public Instant toByte() {
        return Instant.ofEpochMilli(1);
    }

    public Instant toDate() {
        return Instant.ofEpochMilli(2);
    }

    public Instant toDouble() {
        return Instant.ofEpochMilli(3);
    }

    public Instant toFloat() {
        return Instant.ofEpochMilli(4);
    }

    public Instant toInteger() {
        return Instant.ofEpochMilli(5);
    }

    public Instant toLong() {
        return Instant.ofEpochMilli(6);
    }

    public Instant toShort() {
        return Instant.ofEpochMilli(7);
    }
}
