/*
 * Copyright 2021 nghiatc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ntc.vntok.lang.bigram;

/**
 *
 * @author nghiatc
 * @since May 7, 2021
 */
public class BigramJson {
    private String f; // first token
    private String s; // second token
    private int c; // count freqs

    public BigramJson() {
    }

    public BigramJson(String f, String s, int c) {
        this.f = f;
        this.s = s;
        this.c = c;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "BigramJson{" + "f=" + f + ", s=" + s + ", c=" + c + '}';
    }
}
