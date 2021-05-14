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
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since Apr 21, 2021
 */
public abstract class Simulator implements ISimulator {

    /**
     * List of simulator listeners
     */
    protected List<ISimulatorListener> listeners;

    /**
     * Default constructor
     */
    public Simulator() {
        listeners = new ArrayList<>();
    }

    /**
     * Add a simulator listener.
     *
     * @param simulatorListener
     */
    public void addSimulatorListener(ISimulatorListener simulatorListener) {
        listeners.add(simulatorListener);
    }

    /**
     * Remove a simulator listener.
     *
     * @param simulatorListener
     */
    public void removeSimulatorListener(ISimulatorListener simulatorListener) {
        listeners.remove(simulatorListener);
    }

    /**
     * Notify all registered listeners about a configuration.
     *
     * @param configEvent
     */
    public void notify(ConfigurationEvent configEvent) {
        for (ISimulatorListener simulatorListener : listeners) {
            simulatorListener.update(configEvent);
        }
    }

    /**
     * Dispose the simulator. Remove all registered listeners.
     */
    public void dispose() {
        listeners.clear();
        listeners = null;
    }


    @Override
    public boolean accept(String input) {
        return false;
    }

    @Override
    public String run(String input) {
        return null;
    }

    @Override
    public Configuration track(String input) {
        return null;
    }
}
