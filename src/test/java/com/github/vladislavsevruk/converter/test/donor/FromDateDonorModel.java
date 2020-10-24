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
import java.util.Date;

public class FromDateDonorModel {

    public Date toByte() {
        return Date.from(Instant.ofEpochMilli(1));
    }

    public Date toDouble() {
        return Date.from(Instant.ofEpochMilli(2));
    }

    public Date toFloat() {
        return Date.from(Instant.ofEpochMilli(3));
    }

    public Date toInstant() {
        return Date.from(Instant.ofEpochMilli(4));
    }

    public Date toInteger() {
        return Date.from(Instant.ofEpochMilli(5));
    }

    public Date toLong() {
        return Date.from(Instant.ofEpochMilli(6));
    }

    public Date toShort() {
        return Date.from(Instant.ofEpochMilli(7));
    }
}
