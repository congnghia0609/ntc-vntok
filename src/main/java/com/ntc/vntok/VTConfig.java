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
    // Unigram model
    private String unigramModel;
    // Bigram model
    private String bigramModel;
    // The lexicon dfa.
    private String lexiconDFA;
    // The external lexicon
    private String lexiconEx;
    // The file contains normalization rules for Vietnamese accents.
    private String normalRules;
    // Rule lexers
    private String lexers;
    // The named entity prefix.
    private String namePrefix;
    // Is resolve the ambiguity.
    private boolean isAmbiguity = true;
    // Sentence detector model
    private String sdModel;

    public VTConfig() {
    }

    public VTConfig(String unigramModel, String bigramModel, String lexiconDFA, String lexiconEx, String normalRules, String lexers, String namePrefix, boolean isAmbiguity, String sdModel) {
        this.unigramModel = unigramModel;
        this.bigramModel = bigramModel;
        this.lexiconDFA = lexiconDFA;
        this.lexiconEx = lexiconEx;
        this.normalRules = normalRules;
        this.lexers = lexers;
        this.namePrefix = namePrefix;
        this.isAmbiguity = isAmbiguity;
        this.sdModel = sdModel;
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

    public String getSdModel() {
        return sdModel;
    }

    public void setSdModel(String sdModel) {
        this.sdModel = sdModel;
    }

    @Override
    public String toString() {
        return "VTConfig{" + "unigramModel=" + unigramModel + ", bigramModel=" + bigramModel + ", lexiconDFA=" + lexiconDFA + ", lexiconEx=" + lexiconEx + ", normalRules=" + normalRules + ", lexers=" + lexers + ", namePrefix=" + namePrefix + ", isAmbiguity=" + isAmbiguity + ", sdModel=" + sdModel + '}';
    }
}
