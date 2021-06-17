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
public interface ISimulator {

    /**
     * Track an input on the machine to find a stop configuration.
     *
     * @param input an input to track
     * @return the stop configuration of a track.
     */
    public Configuration track(String input);

    /**
     * Run the FSM on an input string and get the result.
     *
     * @param input String
     * @return the result of the run.
     */
    public String run(String input);

    /**
     * An input is accepted by the machine that the simulator operates on or not.
     *
     * @param input an input string
     * @return <tt>true/false</tt>
     */
    public boolean accept(String input);
}
