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

import com.ntc.fsm.fsa.DFA;
import com.ntc.fsm.fsa.DFASimulator;
import com.ntc.vntok.TCommon;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class DFALexiconRecognizer extends AbstractLexiconRecognizer {

    private static DFA lexiconDFA = null;
    private static DFASimulator simulator = null;
    private static DFALexiconRecognizer recognizer = null;

    /**
     * Private constructor.
     *
     * @param dfaLexiconFilename
     */
    private DFALexiconRecognizer() {
        if (lexiconDFA == null) {
            // build the lexicon DFA
            System.out.print("Load the lexicon automaton...");
            lexiconDFA = new DFA();
            InputStream dfaLexiconStream = ResourceUtil.getResourceAsStream(TCommon.LEXICON_DFA);
            lexiconDFA.load(dfaLexiconStream);
            System.out.println("OK.");
        }
    }
    
    /**
     * Private constructor.
     *
     * @param dfaLexiconFilename
     */
    private DFALexiconRecognizer(String dfaLexiconFilename) throws FileNotFoundException {
        if (lexiconDFA == null) {
            // build the lexicon DFA
            System.out.print("Load the lexicon automaton from: "+dfaLexiconFilename+" ...");
            lexiconDFA = new DFA();
            InputStream dfaLexiconStream = new FileInputStream(dfaLexiconFilename);
            lexiconDFA.load(dfaLexiconStream);
            System.out.println("OK.");
        }
    }

    /**
     * @return The singleton instance of the lexicon DFA.
     */
    public static DFALexiconRecognizer getInstance() {
        if (recognizer == null) {
            recognizer = new DFALexiconRecognizer();
        }
        return recognizer;
    }
    
    /**
     * @param dfaLexiconFilename the DFA lexicon file
     * @return The singleton instance of the lexicon DFA.
     * @throws java.io.FileNotFoundException
     */
    public static DFALexiconRecognizer getInstance(String dfaLexiconFilename) throws FileNotFoundException {
        if (recognizer == null) {
            recognizer = new DFALexiconRecognizer(dfaLexiconFilename);
        }
        return recognizer;
    }

    /**
     * @return the DFA simulator
     */
    private DFASimulator getDFASimulator() {
        if (simulator == null) {
            simulator = (DFASimulator) lexiconDFA.getSimulator();
        }
        return simulator;
    }

    @Override
    public boolean accept(String token) {
        return getDFASimulator().accept(token);
    }

    @Override
    public void dispose() {
        lexiconDFA.dispose();
    }
}
