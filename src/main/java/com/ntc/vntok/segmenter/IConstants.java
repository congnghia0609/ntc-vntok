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
package com.ntc.vntok.segmenter;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public interface IConstants {

    /**
     * The lexicon dfa.
     */
    //static String LEXICON_DFA = "models/tokenization/automata/dfaLexicon.xml";
    static String LEXICON_DFA = "models/tok/dfa/dfaLexicon.xml";

    /**
     * The external lexicon
     */
    //static String EXTERNAL_LEXICON = "models/tokenization/automata/externalLexicon.xml";
    static String EXTERNAL_LEXICON = "models/tok/dfa/externalLexicon.xml";
    
    /**
     * The file contains normalization rules for Vietnamese accents.
     */
    //static String NORMALIZATION_RULES = "models/tokenization/normalization/rules.txt";
    static String NORMALIZATION_RULES = "models/tok/rules/rules.txt";
}
