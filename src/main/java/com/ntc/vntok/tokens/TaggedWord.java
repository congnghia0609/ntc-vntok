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
public class TaggedWord implements Comparable<TaggedWord> {

    /**
     * A lexer rule
     */
    private final LexerRule rule;
    /**
     * The text
     */
    private final String text;
    /**
     * The line location of the text in the file
     */
    private int line;
    /**
     * The column location of the text in the file
     */
    private int column;

    /**
     * Create a LexerToken
     *
     * @param rule a rule
     * @param text the text
     * @param line the line location of the text in a file
     * @param column the column location of the text in a file
     */
    public TaggedWord(LexerRule rule, String text, int line, int column) {
        this.rule = rule;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    /**
     * Create a lexer token from a text
     *
     * @param text a text
     */
    public TaggedWord(String text) {
        this.rule = null;
        this.text = text;
        this.line = -1;
        this.column = -1;
    }

    /**
     * Create a lexer token from a lexer rule and a text.
     *
     * @param rule LexerRule
     * @param text String
     */
    public TaggedWord(LexerRule rule, String text) {
        this.rule = rule;
        this.text = text;
        this.line = -1;
        this.column = -1;
    }

    /**
     * Return the rule that matched this token
     *
     * @return the rule that match this token
     */
    public LexerRule getRule() {
        return rule;
    }

    /**
     * Return the text that matched by this token
     *
     * @return the text matched by this token
     */
    public String getText() {
        return text.trim();
    }

    /**
     * Test if this rule is a phrase rule. A phrase is processed by a lexical segmenter.
     *
     * @return true/false
     */
    public boolean isPhrase() {
        return rule.getName().equals("phrase");
    }

    /**
     * Test if this rule is a named entity rule.
     *
     * @return true/false
     */
    public boolean isNamedEntity() {
        return rule.getName().startsWith("name");
    }

    /**
     * @return true/false
     */
    public boolean isDate() {
        return rule.getName().startsWith("date");
    }

    /**
     * @return true/false
     */
    public boolean isDateDay() {
        return rule.getName().contains("day");
    }

    /**
     * @return true/false
     */
    public boolean isDateMonth() {
        return rule.getName().contains("month");
    }

    public boolean isDateYear() {
        return rule.getName().contains("year");
    }

    public boolean isNumber() {
        return rule.getName().startsWith("number");
    }

    /**
     * @return Returns the column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column The column to set.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return Returns the line.
     */
    public int getLine() {
        return line;
    }

    /**
     * @param line The line to set.
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * Return a string representation of the token
     */
    @Override
    public String toString() {
        return text.trim();
    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TaggedWord)) {
            return false;
        }
        // two lexer is considered equal if their text are equal.
        return ((TaggedWord) obj).getText().equals(getText());
    }

    @Override
    public int compareTo(TaggedWord o) {
        return getText().compareTo(o.getText());
    }
}
