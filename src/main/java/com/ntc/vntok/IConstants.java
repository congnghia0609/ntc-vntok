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
 * @since Mar 24, 2021
 */
public interface IConstants {

    /**
     * Vietnamese word set
     */
    public static final String WORD_SET = "data/dictionaries/words_v4.txt";

    /**
     * The Vietnamese lexicon
     */
    public static final String LEXICON = "data/dictionaries/words_v4.xml";

    /**
     * The Vietnamese DFA lexicon
     */
    public static final String LEXICON_DFA = "models/tokenization/automata/lexicon_dfa_minimal.xml";

    /**
     * The named entity prefix.
     */
    public static final String NAMED_ENTITY_PREFIX = "models/tokenization/prefix/namedEntityPrefix.xml";
}
