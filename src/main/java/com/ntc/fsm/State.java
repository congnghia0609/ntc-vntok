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
package com.ntc.fsm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class State implements Comparable<State> {

    /**
     * The id of a state, each state in the dfa has an unique id.
     */
    private int id;

    /**
     * The type of state, the initial state has type 0, a normal state has type 1 and the final state has type 2. State
     * that has type -1 is undefined.
     */
    private byte type;

    /**
     * Outgoing transition from the state.
     */
    private final List<Transition> outTransitions;

    /**
     * Instantiate a new state
     *
     * @param id
     */
    public State(int id) {
        this.id = id;
        this.type = 1; // normal state
        this.outTransitions = new ArrayList<>();
    }

    /**
     * Copy constructor
     *
     * @param s
     */
    public State(State s) {
        this.id = s.getId();
        this.type = s.getType();
        this.outTransitions = s.getOutTransitions();
    }

    /**
     * Set the type of state
     *
     * @param type the type to set
     *
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * Get the type of state
     *
     * @return the type of state
     *
     */
    public byte getType() {
        return type;
    }

    /**
     * Get the id of state
     *
     * @return the state id
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of state
     *
     * @param id the state id
     *
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the number of outtransitions of the state.
     *
     * @return the number of outtransitions of the state
     */
    public int getNumberOfOutTransitions() {
        return outTransitions.size();
    }

    /**
     * Get outtransitions.
     *
     * @return outtransitions.
     */
    public List<Transition> getOutTransitions() {
        return outTransitions;
    }

    /**
     * Get inputs of its outtransitions.
     *
     * @return an array of inputs.
     */
    public char[] getOutTransitionInputs() {
        char[] inputs = new char[outTransitions.size()];
        int i = 0;
        for (Transition t : outTransitions) {
            inputs[i++] = t.getInput();
        }
        return inputs;
    }

    @Override
    public String toString() {
        return "(" + getId() + "," + getType() + ")" + outTransitions.toString();
    }

    /**
     * Get the name of the state
     *
     * @return the name of the state
     */
    public String getName() {
        return "q_{" + getId() + "}";
    }

    /**
     * Test if this state is equal to another state
     *
     * @param q
     * @return <code>true</code> or <code>false</code>
     */
    @Override
    public boolean equals(Object q) {
        if (q instanceof State) {
            return getId() == ((State) q).getId();
        }
        return false;
    }

    /**
     * Test if state is final
     *
     * @return <CODE>true</CODE> if state is final, <CODE>false</CODE> otherwise
     */
    public boolean isFinalState() {
        return (type == 2);
    }

    @Override
    public int compareTo(State s) {
        return this.id - s.getId();
    }
}
