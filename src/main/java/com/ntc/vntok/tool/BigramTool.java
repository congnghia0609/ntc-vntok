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

package com.ntc.vntok.tool;

import com.ntc.vntok.lang.bigram.BigramJson;
import com.ntc.vntok.lexicon.LexiconUnmarshaller;
import com.ntc.vntok.lexicon.jaxb.Corpus;
import com.ntc.vntok.lexicon.jaxb.W;
import com.ntc.vntok.utils.FileUtil;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since May 7, 2021
 */
public class BigramTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<BigramJson> bigrams = new ArrayList<>();
            // load bigram model
            // and initialize the token map 
            LexiconUnmarshaller unmarshaller = new LexiconUnmarshaller();
            InputStream stream = ResourceUtil.getResourceAsStream(com.ntc.vntok.lang.IConstants.BIGRAM_MODEL);
            Corpus bigramCorpus = unmarshaller.unmarshal(stream);
            List<W> ws = bigramCorpus.getBody().getW();
            for (W w : ws) {
                String freq = w.getMsd();
                String words = w.getContent();
                // split the word using a comma. 
                // In general, there are only 2 words, but if a word itself is a
                // comma, we simply do not consider this case :-)
                String[] two = words.split(",");

                if (two.length == 2) {
                    // update the bigram model
                    String first = two[0];
                    String second = two[1];
                    int count = Integer.valueOf(freq);
                    // put the couple to the bigram
                    BigramJson bj = new BigramJson(first, second, count);
                    bigrams.add(bj);
                }
            }
            
            // Write bigram file json.
            String bigramFile = "src/main/resources/models/tok/ngram/bigram.json";
            String sbigram = JsonUtils.Instance.toJson(bigrams);
            FileUtil.writeFileJson(bigramFile, sbigram);
            
            System.out.println("Done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
