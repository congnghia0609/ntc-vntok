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

import com.ntc.vntok.lexicon.LexiconUnmarshaller;
import com.ntc.vntok.lexicon.jaxb.Corpus;
import com.ntc.vntok.lexicon.jaxb.W;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class ExternalLexiconRecognizer extends AbstractLexiconRecognizer {

    private Set<String> externalLexicon;

    /**
     * Default constructor.
     */
    public ExternalLexiconRecognizer() {
        this(IConstants.EXTERNAL_LEXICON);
    }

    /**
     * Creates an external lexicon recognizer given a lexicon.
     *
     * @param externalLexiconFilename a lexicon filename
     */
    public ExternalLexiconRecognizer(String externalLexiconFilename) {
        // load the prefix lexicon
        // 
        LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
        Corpus lexicon = lexiconUnmarshaller.unmarshal(externalLexiconFilename);
        List<W> ws = lexicon.getBody().getW();
        externalLexicon = new HashSet<String>();
        // add all prefixes to the set after converting them to lowercase
        for (W w : ws) {
            externalLexicon.add(w.getContent().toLowerCase());
        }
        System.out.println("External lexicon loaded.");
    }

    public ExternalLexiconRecognizer(Properties properties) {
        this(properties.getProperty("externalLexicon"));
    }

    /* (non-Javadoc)
	 * @see vn.hus.nlp.tokenizer.segmenter.AbstractLexiconRecognizer#accept(java.lang.String)
     */
    @Override
    public boolean accept(String token) {
        return externalLexicon.contains(token);
    }

    /* (non-Javadoc)
	 * @see vn.hus.nlp.tokenizer.segmenter.AbstractLexiconRecognizer#dispose()
     */
    @Override
    public void dispose() {
        externalLexicon.clear();
        externalLexicon = null;
    }

    /**
     * Gets the external lexicon.
     *
     * @return the external lexicon.
     */
    public Set<String> getExternalLexicon() {
        return externalLexicon;
    }
}