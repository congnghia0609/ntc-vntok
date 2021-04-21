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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="src" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="tar" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="inp" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="out" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "t")
public class T {

    @XmlAttribute(required = true)
    protected int src;
    @XmlAttribute(required = true)
    protected int tar;
    @XmlAttribute(required = true)
    protected String inp;
    @XmlAttribute
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
