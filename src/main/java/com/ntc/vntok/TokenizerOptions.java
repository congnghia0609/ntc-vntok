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
 * @since Mar 25, 2021
 */
public class TokenizerOptions {

    /**
     * Use a sentence detector before tokenizing text or not.
     */
    public static boolean USE_SENTENCE_DETECTOR = false;

    /**
     * Use underscores for separating syllbles of words or not.
     */
    public static boolean USE_UNDERSCORE = true;

    /**
     * Export results as XML format or not.
     */
    public static boolean XML_OUTPUT = false;

    /**
     * Default file extension for tokenization.
     */
    public static String TEXT_FILE_EXTENSION = ".txt";
}
