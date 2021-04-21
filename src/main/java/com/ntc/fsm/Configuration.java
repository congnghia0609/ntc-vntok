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
public class Configuration {

    /**
     * The state the machine is currently in.
     */
    private State currentState;

    /**
     * The parent for this configuration.
     */
    private final Configuration parent;

    /**
     * Instantiates a new configuration.
     *
     * @param state the state the machine is currently in.
     * @param parent the parent configuration of the current state
     */
    public Configuration(State state, Configuration parent) {
        currentState = state;
        this.parent = parent;
    }

    /**
     * Returns the state the machine is currently occupying.
     *
     * @return the state the machine is currently occupying.
     *
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Sets current state.
     *
     * @param state the state the machine is currently in.
     *
     */
    public void setCurrentState(State state) {
        currentState = state;
    }

    /**
     * Returns a string representation of this object. The string returned is the string representation of the current
     * state.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "[" + getCurrentState().toString() + "]";
    }

    /**
     * Returns the "parent" for this configuration, that is, the configuration that led to this configuration.
     *
     * @return the <code>Configuration</code> that led to this configuration, or <code>null</code> if this is the
     * initial configuration
     *
     */
    public Configuration getParent() {
        return parent;
    }

    /**
     * The basic equals method considers two configurations equal if they both have the same state, and proceeded from
     * the same configuration. By "the same configuration" it is meant a comparison of the parents via the == operation
     * rather than the <code>.equals()</code> operation, since the latter would lead to rather lengthly traversions.
     *
     * @param configuration the configuration to test for equality
     */
    @Override
    public boolean equals(Object configuration) {
        Configuration config = (Configuration) configuration;
        if (parent != config.parent) {
            return false;
        }
        return config.currentState == currentState;
    }

    /**
     * Returns the base hash code for a configuration. Subclasses should override so as not to have all configurations
     * with the same parent configuration and state map to the same hash entry.
     *
     * @return a value for hashing
     */
    @Override
    public int hashCode() {
        return currentState.hashCode() ^ (parent == null ? 0 : parent.primitiveHashCode());
    }

    /**
     * Returns the "primitive" hash code of the superclass, which is the generic hash code of the object.
     */
    private int primitiveHashCode() {
        return super.hashCode();
    }
}
