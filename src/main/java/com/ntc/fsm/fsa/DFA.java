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

import com.fasterxml.jackson.core.type.TypeReference;
import com.ntc.fsm.FSM;
//import com.ntc.fsm.IConstants;
import com.ntc.fsm.ISimulator;
import com.ntc.fsm.Simulator;
import com.ntc.fsm.State;
import com.ntc.fsm.Transition;
import com.ntc.fsm.jaxb.Fsm;
import com.ntc.fsm.jaxb.S;
import com.ntc.fsm.jaxb.States;
import com.ntc.fsm.jaxb.T;
import com.ntc.fsm.jaxb.Transitions;
import com.ntc.vntok.utils.JsonUtils;
import java.io.InputStream;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class DFA extends FSM {

    public DFA() {
        // init the state machine
        super();
    }

    /**
     * Load a DFA from a InputStream.
     *
     * @param stream InputStream
     */
    public void load(InputStream stream) {
        Fsm fsm = JsonUtils.Instance.getObject(stream, new TypeReference<Fsm>() {});
        // fill the states 
        States states = fsm.getStates();
        for (S s : states.getS()) {
            State state = new State(s.getId());
            state.setType(s.getType());
            this.addState(state);
        }
        // fill the transitions
        Transitions transitions = fsm.getTransitions();
        for (T t : transitions.getT()) {
            char input = t.getInp().charAt(0);
            String output = t.getOut();
            Transition transition;
            //if (output != null && output.equals(IConstants.EMPTY_STRING)) {
            if (output != null && output.isEmpty()) {
                transition = new Transition(t.getSrc(), t.getTar(), input);
            } else {
                transition = new Transition(t.getSrc(), t.getTar(), input, output);
            }
            this.addTransition(transition);
        }
    }

    @Override
    public ISimulator getSimulator() {
        return new DFASimulator(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        ((Simulator) getSimulator()).dispose();
    }
}
