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

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class Transition {

    /**
     * The id of source state
     */
    private int source;
    /**
     * The id of target state
     */
    private int target;
    /**
     * The input character.
     */
    private final char input;
    /**
     * The output string.
     */
    private final String output;

    public Transition() {
        source = -1;
        target = -1;
        input = IConstants.BLANK_CHARACTER;
        output = IConstants.EMPTY_STRING;
    }

    public Transition(int src, int tar) {
        source = src;
        target = tar;
        input = IConstants.BLANK_CHARACTER;
        output = IConstants.EMPTY_STRING;
    }

    public Transition(int src, int tar, char inp) {
        source = src;
        target = tar;
        input = inp;
        output = IConstants.EMPTY_STRING;
    }

    public Transition(int src, int tar, char inp, String out) {
        source = src;
        target = tar;
        input = inp;
        output = out;
    }

    /**
     * Copy constructor.
     *
     * @param t
     */
    public Transition(Transition t) {
        source = t.getSource();
        target = t.getTarget();
        input = t.getInput();
        output = t.getOutput();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Transition)) {
            return false;
        }
        Transition t = (Transition) obj;
        return (source == t.getSource()) && (target == t.getTarget()) && (input == t.getInput());
    }

    /**
     * @return the source state
     */
    public int getSource() {
        return source;
    }

    /**
     * @return the target state
     */
    public int getTarget() {
        return target;
    }

    /**
     * Set the source state of the transition.
     *
     * @param source the source state
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * Set the target state of the transition.
     *
     * @param target the target state
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * @return the input character
     */
    public char getInput() {
        return input;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return ("[" + source + "," + input + ":" + output + "," + target + "]");
    }
}
