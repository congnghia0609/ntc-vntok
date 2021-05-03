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

import com.ntc.fsm.FSM;
import com.ntc.fsm.ISimulator;
import com.ntc.fsm.Simulator;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public class FST extends FSM {

    /**
     * Default constructor.
     */
    public FST() {
        super();
    }

    /* (non-Javadoc)
	 * @see FSM#getSimulator()
     */
    @Override
    public ISimulator getSimulator() {
        return new FSTSimulator(this);
    }

    /* (non-Javadoc)
	 * @see FSM#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        ((Simulator) getSimulator()).dispose();
    }
}
