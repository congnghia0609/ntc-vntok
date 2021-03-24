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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class UnigramResolver extends AbstractResolver {

    /**
     * A lexicon unmarshaller to unmarshal the unigram model.
     */
    private LexiconUnmarshaller unmarshaller;
    /**
     * The unigram probabilities.
     */
    private Map<String, Integer> unigram;

    /**
     * Default construtor.
     *
     * @param unigramFilename the unigram filename.
     */
    public UnigramResolver(String unigramFilename) {
        init();
        // load the unigram model.
        loadUnigram(unigramFilename);
    }

    private void init() {
        // create a lexicon unmarshaller
        unmarshaller = new LexiconUnmarshaller();
        // init the unigram model
        unigram = new HashMap<String, Integer>();
    }

    /**
     * Load unigram model and calculate frequencies.
     *
     * @param unigramFilename the unigram filename
     */
    private void loadUnigram(String unigramFilename) {
        System.out.print("Loading unigram model...");
        // load unigram model
        Corpus unigramCorpus = unmarshaller.unmarshal(unigramFilename);
        List<W> ws = unigramCorpus.getBody().getW();
        for (Iterator<W> iterator = ws.iterator(); iterator.hasNext();) {
            W w = iterator.next();
            String freq = w.getMsd();
            String word = w.getContent();
            unigram.put(word, Integer.parseInt(freq));
        }
        System.out.println("OK");
    }

    /**
     * Unigram resolver for segmentations. Given a list of segmentations, this method calculates the probabilites of
     * segmentations and choose the one with highest probabilities. Since in the unigram model, the probabilities of
     * words are represented by their frequencies, we can calculate and compare the sum of frequencies of words of
     * segmentations. It is always much more rapid to perform simple operations (like addition) on integers than complex
     * operations (like multiply or division) on doubles.
     *
     * @see vn.hus.nlp.tokenizer.segmenter.AbstractResolver#resolve(java.util.List)
     */
    @Override
    public String[] resolve(List<String[]> segmentations) {
        String[] choice = null;
        int maxFrequency = 0;
        for (Iterator<String[]> it = segmentations.iterator(); it.hasNext();) {
            String[] segmentation = it.next();
            int frequency = 0;
            for (int i = 0; i < segmentation.length; i++) {
                String word = segmentation[i];
                int wordFreq = 0;
                if (unigram.containsKey(word)) {
                    wordFreq = unigram.get(word).intValue();
                }
                frequency += wordFreq;
            }
            if (frequency >= maxFrequency) {
                maxFrequency = frequency;
                choice = segmentation;
            }
        }
        return choice;
    }
}