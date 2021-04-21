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
package com.ntc.fsm.fsa;

import com.ntc.fsm.ConfigurationEvent;
import com.ntc.fsm.ISimulatorListener;
import com.ntc.fsm.Simulator;
import com.ntc.fsm.State;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class DFASimulator extends Simulator {

    /**
     * The dfa that the simulator operates on.
     */
    protected DFA dfa;
    /**
     * The configuration the machine could possibly be in at a given moment in the simulation.
     */
    protected DFAConfiguration configuration = null;

    /**
     * A simple logger for the simulator.
     */
    protected SimulatorLogger logger = null;

    /**
     * Print out the trace of the simulator or not (DEBUG mode).
     */
    private final boolean DEBUG = false;

    /**
     * A simple logger for the {@link DFASimulator} to log its processing.
     */
    class SimulatorLogger implements ISimulatorListener {

        private final Logger logger;

        public SimulatorLogger() {
            logger = Logger.getLogger(DFASimulator.class.getName());
            // use a console handler to trace the log
            logger.addHandler(new ConsoleHandler());
            logger.setLevel(Level.FINEST);
        }

        @Override
        public void update(ConfigurationEvent configurationEvent) {
            // log the configuration event
            logger.log(Level.INFO, configurationEvent.toString());
        }

    }

    /**
     * Default constructor.
     *
     * @param dfa an dfa.
     */
    public DFASimulator(DFA dfa) {
        // invoke the parent's constructor to
        // init listeners
        super();
        this.dfa = dfa;
        if (DEBUG) {
            logger = new SimulatorLogger();
            addSimulatorListener(logger);
        }
    }

    /**
     * Find the next configuration of the DFA.
     *
     * @param configuration
     * @return The next configuration of current configuration or null if the simulator cannot go further.
     */
    protected DFAConfiguration next(DFAConfiguration configuration) {
        DFAConfiguration nextConfiguration = null;
        // get information of current configuration
        State currentState = configuration.getCurrentState();
        String unprocessedInput = configuration.getUnprocessedInput();
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
                    State nextState = dfa.getNextState(currentState, nextInput);
                    if (nextState != null) {
                        // create the next configuration
                        if (unprocessedInput.length() > 0) {
                            unprocessedInput = unprocessedInput.substring(1);
                        }
                        nextConfiguration = new DFAConfiguration(nextState, configuration,
                                configuration.getTotalInput(), unprocessedInput);
                        // create a configuration event and notify all registered listeners
                        if (DEBUG) {
                            notify(new ConfigurationEvent(configuration, nextConfiguration, nextInput)); // DEBUG
                        }
                    }
                }
            }
        }
        return nextConfiguration;
    }

    /**
     * Track an input on the DFA.
     *
     * @param input an input
     * @return the configuration at which the machine cannot go further on the input.
     */
    @Override
    public DFAConfiguration track(String input) {
        // create the initial configuration of the simulation
        // that start at the initial state of the machine, has no parent
        // (null), and the input.
        configuration = new DFAConfiguration(dfa.getInitialState(), null, input, input);

        while (configuration != null) {
            // get the next configuration
            DFAConfiguration nextConfiguration = next(configuration);
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
        DFAConfiguration config = track(input);
        // the input is accepted if the current state is final
        // and there is no unprocessed input
        return (config.getCurrentState().isFinalState()
                && (config.getUnprocessedInput().length() == 0));
    }

    /**
     * A run of the dfa on an input does not return any result.
     *
     */
    @Override
    public String run(String input) {
        return null;
    }
}
