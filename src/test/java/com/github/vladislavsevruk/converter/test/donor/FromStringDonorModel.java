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

public class FromStringDonorModel {

    public String toByteNeg() {
        return "129";
    }

    public String toBytePos() {
        return "127";
    }

    public String toCharNeg() {
        return "";
    }

    public String toCharPos() {
        return "1";
    }

    public String toDoubleNeg() {
        return "1.1.1";
    }

    public String toDoublePos() {
        return "1.1";
    }

    public String toEnumNeg() {
        return "THREE";
    }

    public String toEnumPos() {
        return "ONE";
    }

    public String toFloatNeg() {
        return "1.1.1";
    }

    public String toFloatPos() {
        return "1.1";
    }

    public String toIntegerNeg() {
        return "1.1";
    }

    public String toIntegerPos() {
        return "1";
    }

    public String toLongNeg() {
        return "1.1";
    }

    public String toLongPos() {
        return "1";
    }

    public String toShortNeg() {
        return "1.1";
    }

    public String toShortPos() {
        return "1";
    }
}
