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

package com.ntc.vntok;

/**
 *
 * @author nghiatc
 * @since Jun 1, 2021
 */
public class VTConfig {
    private String unigramModel;
    private String bigramModel;
    private String lexiconDFA;
    private String lexiconEx;
    private String normalRules;
    private String lexers;
    private String namePrefix;
    private boolean isAmbiguity = true;

    public VTConfig() {
    }

    public String getUnigramModel() {
        return unigramModel;
    }

    public void setUnigramModel(String unigramModel) {
        this.unigramModel = unigramModel;
    }

    public String getBigramModel() {
        return bigramModel;
    }

    public void setBigramModel(String bigramModel) {
        this.bigramModel = bigramModel;
    }

    public String getLexiconDFA() {
        return lexiconDFA;
    }

    public void setLexiconDFA(String lexiconDFA) {
        this.lexiconDFA = lexiconDFA;
    }

    public String getLexiconEx() {
        return lexiconEx;
    }

    public void setLexiconEx(String lexiconEx) {
        this.lexiconEx = lexiconEx;
    }

    public String getNormalRules() {
        return normalRules;
    }

    public void setNormalRules(String normalRules) {
        this.normalRules = normalRules;
    }

    public String getLexers() {
        return lexers;
    }

    public void setLexers(String lexers) {
        this.lexers = lexers;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public boolean isIsAmbiguity() {
        return isAmbiguity;
    }

    public void setIsAmbiguity(boolean isAmbiguity) {
        this.isAmbiguity = isAmbiguity;
    }

    @Override
    public String toString() {
        return "VTConfig{" + "unigramModel=" + unigramModel + ", bigramModel=" + bigramModel + ", lexiconDFA=" + lexiconDFA + ", lexiconEx=" + lexiconEx + ", normalRules=" + normalRules + ", lexers=" + lexers + ", namePrefix=" + namePrefix + ", isAmbiguity=" + isAmbiguity + '}';
    }
}
