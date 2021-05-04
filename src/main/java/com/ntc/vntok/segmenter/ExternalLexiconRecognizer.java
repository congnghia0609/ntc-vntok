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

import com.fasterxml.jackson.core.type.TypeReference;
import com.ntc.vntok.TCommon;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class ExternalLexiconRecognizer extends AbstractLexiconRecognizer {

    private Set<String> externalLexicon = new HashSet<>();

    /**
     * Default constructor.
     */
//    public ExternalLexiconRecognizer() {
//        // load the prefix lexicon
//        // 
//        LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
//        InputStream externalLexiconStream = ResourceUtil.getResourceAsStream(IConstants.EXTERNAL_LEXICON);
//        Corpus lexicon = lexiconUnmarshaller.unmarshal(externalLexiconStream);
//        List<W> ws = lexicon.getBody().getW();
//        // add all prefixes to the set after converting them to lowercase
//        for (W w : ws) {
//            externalLexicon.add(w.getContent().toLowerCase());
//        }
//        System.out.println("External lexicon loaded.");
//    }
    public ExternalLexiconRecognizer() {
        // load the prefix lexicon
        InputStream externalLexiconStream = ResourceUtil.getResourceAsStream(TCommon.EXTERNAL_LEXICON);
        externalLexicon = JsonUtils.Instance.getObject(externalLexiconStream, new TypeReference<Set<String>>(){});
        System.out.println("External lexicon loaded.");
    }

    /**
     * Creates an external lexicon recognizer given a lexicon.
     *
     * @param externalLexiconFilename a lexicon filename
     */
//    public ExternalLexiconRecognizer(String externalLexiconFilename) {
//        // load the prefix lexicon
//        // 
//        LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
//        Corpus lexicon = lexiconUnmarshaller.unmarshal(externalLexiconFilename);
//        List<W> ws = lexicon.getBody().getW();
//        // add all prefixes to the set after converting them to lowercase
//        for (W w : ws) {
//            externalLexicon.add(w.getContent().toLowerCase());
//        }
//        System.out.println("External lexicon loaded.");
//    }
    public ExternalLexiconRecognizer(String externalLexiconFilename) throws FileNotFoundException {
        // load the prefix lexicon
        InputStream externalLexiconStream = new FileInputStream(externalLexiconFilename);
        externalLexicon = JsonUtils.Instance.getObject(externalLexiconStream, new TypeReference<Set<String>>(){});
        System.out.println("External lexicon loaded.");
    }

    public ExternalLexiconRecognizer(Properties properties) throws FileNotFoundException {
        this(properties.getProperty("externalLexicon"));
    }

    @Override
    public boolean accept(String token) {
        return externalLexicon.contains(token);
    }

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
