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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author nghiatc
 * @since May 3, 2021
 */
public class UnigramModel extends AbstractResolver {

    private Map<String, Integer> unigram = new HashMap<>();

    private static Lock lock = new ReentrantLock();
	private static UnigramModel instance;

	public static UnigramModel getInstance() {
		if(instance == null) {
			lock.lock();
			try {
				if(instance == null) {
					instance = new UnigramModel();
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}
    
    public UnigramModel() {
        loadUnigram();
    }
    
    public UnigramModel(String unigramFilename) throws FileNotFoundException {
        loadUnigram(unigramFilename);
    }
    
    private void loadUnigram() {
        System.out.print("Loading unigram model...");
        // load unigram model
        InputStream stream = ResourceUtil.getResourceAsStream(TCommon.UNIGRAM_MODEL);
        unigram = JsonUtils.Instance.getObject(stream, new TypeReference<Map<String, Integer>>(){});
        System.out.println("OK");
    }
    
    private void loadUnigram(String unigramFilename) throws FileNotFoundException {
        System.out.print("Loading unigram model from: "+unigramFilename+" ...");
        // load unigram model
        InputStream stream = new FileInputStream(unigramFilename);
        unigram = JsonUtils.Instance.getObject(stream, new TypeReference<Map<String, Integer>>(){});
        System.out.println("OK");
    }
    
    /**
     * Unigram resolver for segmentations. Given a list of segmentations, this method calculates the probabilites of
     * segmentations and choose the one with highest probabilities. Since in the unigram model, the probabilities of
     * words are represented by their frequencies, we can calculate and compare the sum of frequencies of words of
     * segmentations. It is always much more rapid to perform simple operations (like addition) on integers than complex
     * operations (like multiply or division) on doubles.
     * @return Array String
     */
    @Override
    public String[] resolve(List<String[]> segmentations) {
        String[] choice = null;
        int maxFrequency = 0;
        for (String[] segmentation : segmentations) {
            int frequency = 0;
            for (String word : segmentation) {
                int wordFreq = 0;
                if (unigram.containsKey(word)) {
                    wordFreq = unigram.get(word);
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
