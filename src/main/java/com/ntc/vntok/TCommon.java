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
 * @since May 3, 2021
 */
public class TCommon {

    // Unigram model
    public static final String UNIGRAM_MODEL = "models/tok/ngram/unigram.json";
    
    // Bigram model
    public static final String BIGRAM_MODEL = "models/tok/ngram/bigram.json";

    // The reference corpora directory that contains text files to train the model.
    public static final String CORPORA_DIRECTORY = "corpora/ref";

    // The conditional probabilities.
    public static final String CONDITIONAL_PROBABILITIES = "resources/prob.json";
    
    // The lexicon dfa.
    public static String LEXICON_DFA = "models/tok/dfa/dfaLexicon.json";
    
    // The external lexicon
    public static String EXTERNAL_LEXICON = "models/tok/dfa/externalLexicon.json";
    
    // The file contains normalization rules for Vietnamese accents.
    public static String NORMALIZATION_RULES = "models/tok/rules/rules.txt";
    
    // Rule lexers
    public static String LEXERS = "models/tok/lexers/lexers.json";
    
    // The named entity prefix.
    public static final String NAME_PREFIX = "models/tok/prefix/namePrefix.json";
}
