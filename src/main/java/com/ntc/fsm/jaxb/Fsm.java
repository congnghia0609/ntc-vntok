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
public class Fsm {

    protected States states;
    protected Transitions transitions;
    protected String name;

    /**
     * Gets the value of the states property.
     *
     * @return possible object is {@link States }
     *
     */
    public States getStates() {
        return states;
    }

    /**
     * Sets the value of the states property.
     *
     * @param value allowed object is {@link States }
     *
     */
    public void setStates(States value) {
        this.states = value;
    }

    /**
     * Gets the value of the transitions property.
     *
     * @return possible object is {@link Transitions }
     *
     */
    public Transitions getTransitions() {
        return transitions;
    }

    /**
     * Sets the value of the transitions property.
     *
     * @param value allowed object is {@link Transitions }
     *
     */
    public void setTransitions(Transitions value) {
        this.transitions = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }
}
