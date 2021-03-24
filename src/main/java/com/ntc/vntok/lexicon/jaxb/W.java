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
package com.ntc.vntok.lexicon.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "w")
public class W {

    @XmlValue
    protected String content;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String msd;

    /**
     * Gets the value of the content property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the msd property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMsd() {
        return msd;
    }

    /**
     * Sets the value of the msd property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMsd(String value) {
        this.msd = value;
    }
}
