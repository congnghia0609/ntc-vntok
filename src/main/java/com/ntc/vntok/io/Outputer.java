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
package com.ntc.vntok.io;

import com.ntc.vntok.tokens.TaggedWord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class Outputer {

    /**
     * An output formatter
     */
    private IOutputFormatter formatter;

    /**
     * A list of output listeners
     */
    private final List<IOutputListener> listeners;

    /**
     * Default constructor.
     */
    public Outputer() {
        // use plain outputer by default
        this.formatter = new PlainFormatter();
        listeners = new ArrayList<IOutputListener>();
    }

    /**
     * This outputer uses a formatter to output the result.
     *
     * @param formatter a formatter
     */
    public Outputer(IOutputFormatter formatter) {
        this.formatter = formatter;
        listeners = new ArrayList<IOutputListener>();
    }

    /**
     * Output the list of tokens.
     *
     * @param tokens a list of tokens to be outputed.
     * @return a string represents the list.
     */
    public String output(List<TaggedWord> tokens) {
        String r = "";
        Iterator<TaggedWord> it = tokens.iterator();

        while (it.hasNext()) {
            TaggedWord token = it.next();
            r += formatter.outputLexeme(token);
            // notifies the outputed token to all listeners
            notifyAllListeners(token);
        }
        return r;
    }

    /**
     * @return Return the formatter in use.
     */
    public IOutputFormatter getFormatter() {
        return formatter;
    }

    /**
     * Set the formatter to use.
     *
     * @param formatter The formatter to set.
     */
    public void setFormatter(IOutputFormatter formatter) {
        this.formatter = formatter;
    }

    /**
     * Return the list of output listener
     *
     * @return the list of output listener
     */
    public List<IOutputListener> getOutputListeners() {
        return listeners;
    }

    /**
     * Add an output listener to the list of listeners.
     *
     * @param listener an ouput listener to add.
     */
    public void addOutputListener(IOutputListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Remove a listener from the list of listener
     *
     * @param listener an output listener.
     */
    public void removeOutputListener(IOutputListener listener) {
        listeners.remove(listener);
    }

    /**
     * Remove all listeners of the outputer.
     *
     */
    public void removeAllListeners() {
        listeners.clear();
    }

    /**
     * Notifies all output registered listeners of the ouputed token. This invokes the method
     * <code>processToken()</code> of all register output listeners.
     *
     * @param token an outputed token.
     */
    private void notifyAllListeners(TaggedWord token) {
        for (IOutputListener listener : listeners) {
            listener.outputToken(token);
        }
    }
}
