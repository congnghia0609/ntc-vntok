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
public class ConfigurationEvent {

    /**
     * The parent configuration
     */
    Configuration parentConf;

    /**
     * The current configuration
     */
    Configuration conf;

    /**
     * The involved input that take a simulator from parent configuration to the current configuration.
     */
    char input;

    /**
     * The involved output.
     */
    String output;

    /**
     * Constructor.
     *
     * @param parentConf the parent configuration
     * @param conf current configuration
     * @param input current input
     */
    public ConfigurationEvent(Configuration parentConf, Configuration conf, char input) {
        this.parentConf = parentConf;
        this.conf = conf;
        this.input = input;
        this.output = IConstants.EMPTY_STRING;
    }

    /**
     * Constructor.
     *
     * @param parentConf the parent configuration
     * @param conf configuration
     * @param input current input
     * @param output current output
     */
    public ConfigurationEvent(Configuration parentConf, Configuration conf, char input, String output) {
        this(parentConf, conf, input);
        this.output = output;
    }

    public Configuration getParentConfiguration() {
        return parentConf;
    }

    public Configuration getConfiguration() {
        return conf;
    }

    /**
     * @return the input
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
        return "[" + parentConf.getCurrentState().getId() + ","
                + input + ":" + output + "," + conf.getCurrentState().getId() + "]";
    }
}
