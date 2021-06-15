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

    /**
     * Gets the value of the src property.
     *
     */
    public int getSrc() {
        return src;
    }

    /**
     * Sets the value of the src property.
     *
     */
    public void setSrc(int value) {
        this.src = value;
    }

    /**
     * Gets the value of the tar property.
     *
     */
    public int getTar() {
        return tar;
    }

    /**
     * Sets the value of the tar property.
     *
     */
    public void setTar(int value) {
        this.tar = value;
    }

    /**
     * Gets the value of the inp property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getInp() {
        return inp;
    }

    /**
     * Sets the value of the inp property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setInp(String value) {
        this.inp = value;
    }

    /**
     * Gets the value of the out property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getOut() {
        return out;
    }

    /**
     * Sets the value of the out property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setOut(String value) {
        this.out = value;
    }
}
