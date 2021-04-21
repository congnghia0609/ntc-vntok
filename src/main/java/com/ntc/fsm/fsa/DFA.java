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

import com.ntc.fsm.FSM;
import com.ntc.fsm.ISimulator;
import com.ntc.fsm.Simulator;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class DFA extends FSM {

    /**
     * Default constructor of the DFA.
     */
    public DFA() {
        // init the state machine
        super();
    }

    /* (non-Javadoc)
	 * @see vn.hus.fsm.FSM#getSimulator()
     */
    @Override
    public ISimulator getSimulator() {
        return new DFASimulator(this);
    }

    /* (non-Javadoc)
	 * @see vn.hus.fsm.FSM#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        ((Simulator) getSimulator()).dispose();
    }
}
