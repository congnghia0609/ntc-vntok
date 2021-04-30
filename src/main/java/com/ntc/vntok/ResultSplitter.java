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

import com.ntc.vntok.lexicon.LexiconUnmarshaller;
import com.ntc.vntok.lexicon.jaxb.Corpus;
import com.ntc.vntok.lexicon.jaxb.W;
import com.ntc.vntok.tokens.LexerRule;
import com.ntc.vntok.tokens.TaggedWord;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class ResultSplitter {

    /**
     * Set of predefined prefixes.
     */
    private Set<String> prefix;

    /**
     * Default constructor.
     */
    public ResultSplitter() {
        // load the prefix lexicon
        // 
        LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
        InputStream namedEntityPrefixStream = ResourceUtil.getResourceAsStream(IConstants.NAMED_ENTITY_PREFIX);
        Corpus lexicon = lexiconUnmarshaller.unmarshal(namedEntityPrefixStream);
        List<W> ws = lexicon.getBody().getW();
        prefix = new HashSet<String>();
        // add all prefixes to the set after converting them to lowercase
        for (W w : ws) {
            prefix.add(w.getContent().toLowerCase());
        }
    }

    /**
     * Creates a result splitter from a named entity prefix filename.
     *
     * @param namedEntityPrefixFilename
     */
    public ResultSplitter(String namedEntityPrefixFilename) {
        // load the prefix lexicon
        // 
        LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
        Corpus lexicon = lexiconUnmarshaller.unmarshal(namedEntityPrefixFilename);
        List<W> ws = lexicon.getBody().getW();
        prefix = new HashSet<String>();
        // add all prefixes to the set after converting them to lowercase
        for (W w : ws) {
            prefix.add(w.getContent().toLowerCase());
        }
    }

    /**
     * Creates a result splitter from a properties filename.
     *
     * @param properties a properties file.
     */
    public ResultSplitter(Properties properties) {
        this(properties.getProperty("namedEntityPrefix"));
    }

    private boolean isPrefix(String syllable) {
        return prefix.contains(syllable.toLowerCase());
    }

    /**
     * Splits a named entity token into two tokens.
     *
     * @param token
     * @return two tagged tokens
     */
    public TaggedWord[] split(TaggedWord token) {
        // split the token basing on spaces or underscore 
        String[] syllables = token.getText().split("\\s+");
        if (syllables.length > 1) {
            // extract the first syllable of token
            if (isPrefix(syllables[0])) {
//				System.err.println("Split " + token.getText());
                int position = syllables[0].length() + 1;
                // it is sure that postion > 0
                String suffix = token.getText().substring(position);
                TaggedWord[] result = new TaggedWord[2];
                result[0] = new TaggedWord(new LexerRule("word"), syllables[0]);
                result[1] = new TaggedWord(new LexerRule("name"), suffix.trim());
                return result;
            }
        }
        return null;
    }
}
