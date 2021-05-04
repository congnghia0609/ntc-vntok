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

import java.util.regex.Pattern;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class LexerRule {

    /**
     * The name of the lexical category that this rule matches
     */
    private final String name;

    /**
     * The regular expression used for matching
     */
    private final String regex;
    /**
     * A pre-compiled pattern object, kept to save processing time
     */
    private Pattern pattern;

    /**
     * Instantiate a new lexical rule with a name
     *
     * @param name a name
     */
    public LexerRule(String name) {
        this.name = name;
        this.regex = "";
    }

    /**
     * Instantiate a new lexical rule with a name and a regex
     *
     * @param name a name
     * @param regex a regular expression
     */
    public LexerRule(String name, String regex) {
        this.name = name;
        this.regex = regex;
    }

    /**
     * Get the category name
     *
     * @return the name of rule
     */
    public String getName() {
        return name;
    }

    /**
     * Get the regex defining the rule
     *
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Return the pattern object. Create one if it hasn't been created already.
     *
     * @return the pattern object
     */
    public Pattern getPattern() {
        if (pattern == null) {
            pattern = Pattern.compile(regex);
        }
        return pattern;
    }

    /**
     * Return a string representation of the rule
     * @return String
     */
    @Override
    public String toString() {
        return "[" + name + "]";
    }
}
