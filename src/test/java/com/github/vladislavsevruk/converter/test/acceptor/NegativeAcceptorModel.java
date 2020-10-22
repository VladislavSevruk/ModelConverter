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

import lombok.Getter;

import java.util.Objects;

@Getter
public class NegativeAcceptorModel {

    private static String staticIndicator;
    private String indicator;

    public static String getStaticIndicator() {
        return staticIndicator;
    }

    public static void staticMethod(String parameter) {
        staticIndicator = parameter;
    }

    public void acceptorMethodWithSeveralParameters(String parameter1, String parameter2) {
        indicator = "acceptorMethodWithSeveralParameters";
    }

    public void acceptorMethodWithoutParameters() {
        indicator = "acceptorMethodWithoutParameters";
    }

    public void donorMethodWithParameters(String parameter) {
        indicator = "donorMethodWithParameters";
    }

    public void donorNullValue(String parameter) {
        indicator = "donorNullValue";
    }

    // override to check that methods from java.lang.Object aren't used
    @Override
    public boolean equals(Object object) {
        indicator = "equals";
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof NegativeAcceptorModel)) {
            return false;
        }
        NegativeAcceptorModel model = (NegativeAcceptorModel) object;
        return Objects.equals(indicator, model.indicator);
    }

    public void nonMatchingMethodNameAcceptor(String parameter) {
        indicator = "nonMatchingMethodNameAcceptor";
    }

    public void voidDonorMethod(String parameter) {
        indicator = "voidDonorMethod";
    }
}
