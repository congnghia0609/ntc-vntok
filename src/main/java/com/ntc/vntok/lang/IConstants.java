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
package com.ntc.vntok.lang;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public interface IConstants {

    /**
     * Debug the package or not
     */
    static final boolean DEBUG = true;
    /**
     * The reference corpora directory that contains text files to train the model.
     */
    static final String CORPORA_DIRECTORY = "corpora/ref";
    /**
     * Unigram model
     */
    static final String UNIGRAM_MODEL = "resources/unigram.xml";
    /**
     * Bigram model
     */
    static final String BIGRAM_MODEL = "resources/bigram.xml";

    /**
     * The conditional probabilities.
     */
    static final String CONDITIONAL_PROBABILITIES = "resources/prob.xml";
}
