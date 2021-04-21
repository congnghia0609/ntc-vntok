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
package com.ntc.fsm.fst;

import com.ntc.fsm.State;
import com.ntc.fsm.fsa.DFAConfiguration;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class FSTConfiguration extends DFAConfiguration {

    /**
     * The current output of the fst.
     */
    private String currentOutput;

    /**
     * @param state the current state
     * @param parent the parent configuration
     * @param totalInput the total input
     * @param unprocessedInput the unprocessed input
     */
    public FSTConfiguration(State state, FSTConfiguration parent,
            String totalInput, String unprocessedInput) {
        super(state, parent, totalInput, unprocessedInput);
        currentOutput = "";
    }

    /**
     * @param state the current state
     * @param parent the parent configuration
     * @param totalInput the total input
     * @param unprocessedInput the unprocessed input
     * @param currentOutput the current output
     */
    public FSTConfiguration(State state, FSTConfiguration parent,
            String totalInput, String unprocessedInput, String currentOutput) {
        this(state, parent, totalInput, unprocessedInput);
        this.currentOutput = currentOutput;
    }

    /**
     * Set the current output
     *
     * @return the current output
     */
    public String getCurrentOutput() {
        return currentOutput;
    }
}
