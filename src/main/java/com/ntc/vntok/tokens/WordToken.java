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
package com.ntc.vntok.tokens;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class WordToken extends TaggedWord {

    private final String pos;

    /**
     * Instantiate a new token
     *
     * @param rule a lexical rule
     * @param text the text
     * @param line line location of the text in a file
     * @param column column position of the text in a file
     */
    public WordToken(LexerRule rule, String text, int line, int column) {
        super(rule, text, line, column);
        pos = null;
    }

    /**
     * Instantiate a new token
     *
     * @param rule a lexical rule
     * @param text the text
     * @param line line location of the text in a file
     * @param column column position of the text in a file
     * @param pos parts-of-speech of the word token
     */
    public WordToken(LexerRule rule, String text, int line, int column, String pos) {
        super(rule, text, line, column);
        this.pos = pos;
    }

    /**
     * Get the parts-of-speech of the token
     *
     * @return parts-of-speech of the token
     */
    public String getPOS() {
        return pos;
    }

    /**
     * Return a string representation of a word token
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
