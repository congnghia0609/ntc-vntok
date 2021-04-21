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
public interface IConstants {

    /**
     * Blank character.
     */
    public static final char BLANK_CHARACTER = ' ';

    /**
     * An empty string.
     */
    public static final String EMPTY_STRING = "";

    /**
     * The package name for JAXB context.
     */
    public static final String JAXB_CONTEXT = "com.ntc.fsm.jaxb";

    /**
     * The Vietnamese lexicon.
     */
    public final static String VIETNAMESE_LEXICON = "samples/lexicon_v3_set.txt";
    /**
     * The file that encode Vietnamese lexicon in the form of a simple DFA.
     */
    public static final String VIETNAMESE_LEXICON_DFA_SIMPLE = "samples/lexicon_dfa_simple.xml";
    /**
     * The file that encode Vietnamese lexicon in the form of a minimal DFA.
     */
    public static final String VIETNAMESE_LEXICON_DFA_MINIMAL = "samples/lexicon_dfa_minimal.xml";

    /**
     * DFA type.
     */
    public static final String FSM_DFA = "DFA";
    /**
     * FST type.
     */
    public static final String FSM_FST = "FST";
}
