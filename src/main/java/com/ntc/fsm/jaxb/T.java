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
package com.ntc.fsm.jaxb;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class T {

    protected int src;
    protected int tar;
    protected String inp;
    protected String out;

    public int getSrc() {
        return src;
    }

    public void setSrc(int value) {
        this.src = value;
    }

    public int getTar() {
        return tar;
    }

    public void setTar(int value) {
        this.tar = value;
    }

    public String getInp() {
        return inp;
    }

    public void setInp(String value) {
        this.inp = value;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String value) {
        this.out = value;
    }
}
