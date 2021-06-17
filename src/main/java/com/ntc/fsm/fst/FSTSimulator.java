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

import com.ntc.fsm.Simulator;
import com.ntc.fsm.State;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class FSTSimulator extends Simulator {

    /**
     * The underlying fst on which the simulator runs.
     */
    protected FST fst;

    /**
     * The configuration the machine could possibly be in at a given moment in the simulation.
     */
    protected FSTConfiguration configuration = null;

    /**
     * @param fst the fst that this simulator operates on.
     */
    public FSTSimulator(FST fst) {
        super();
        this.fst = fst;
    }

    /**
     * Find the next configuration of the FST.
     *
     * @param configuration FSTConfiguration
     * @return The next configuration of current configuration or null if the simulator cannot go further.
     */
    protected FSTConfiguration next(FSTConfiguration configuration) {
        FSTConfiguration nextConfiguration = null;
        // get information of current configuration
        State currentState = configuration.getCurrentState();
        String unprocessedInput = configuration.getUnprocessedInput();
        String currentOutput = configuration.getCurrentOutput();
        int len = unprocessedInput.length();
        if (len > 0) {
            // get all inputs of outtransitions of the current state
            char[] outTransitionInputs = currentState.getOutTransitionInputs();
            // get the first character of the unprocessed input
            char nextInput = unprocessedInput.charAt(0);
            // find the next configuration
            for (int i = 0; i < outTransitionInputs.length; i++) {
                if (outTransitionInputs[i] == nextInput) {
                    // get the next state (possible null)
                    State nextState = fst.getNextState(currentState, nextInput);
                    // get the output
                    String nextOutput = fst.getNextOutput(currentState, nextInput);
                    if (nextState != null) {
                        // create the next configuration
                        if (unprocessedInput.length() > 0) {
                            unprocessedInput = unprocessedInput.substring(1);
                            currentOutput += nextOutput;
                        }
                        nextConfiguration = new FSTConfiguration(nextState, configuration, configuration.getTotalInput(), unprocessedInput, currentOutput);
                    }
                }
            }
        }
        return nextConfiguration;
    }

    /**
     * Track an input on the FST.
     *
     * @param input an input
     * @return the configuration at which the machine cannot go further on the input.
     */
    @Override
    public FSTConfiguration track(String input) {
        // create the initial configuration of the simulation
        // that start at the initial state of the machine, has no parent
        // (null), input, and output.
        configuration = new FSTConfiguration(fst.getInitialState(), null, input, input, "");

        while (configuration != null) {
            // get the next configuration
            FSTConfiguration nextConfiguration = next(configuration);
            // if the simulator cannot go further
            if (nextConfiguration == null) {
                return configuration;
            }
            configuration = nextConfiguration;
        }
        // return the initial state if
        // there is not any part of the input that is accepted by
        // the machine
        return configuration;
    }

    @Override
    public boolean accept(String input) {
        // first track the input
        FSTConfiguration config = track(input);
        // the input is accepted if the current state is final
        // and there is no unprocessed input
        return config.getCurrentState().isFinalState() && config.getUnprocessedInput().length() == 0;
    }

    @Override
    public String run(String input) {
        // track the input
        FSTConfiguration config = track(input);
        // get the output
        return config.getCurrentOutput();
    }
}
