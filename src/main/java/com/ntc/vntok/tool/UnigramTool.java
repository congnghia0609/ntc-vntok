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

import com.ntc.vntok.lexicon.LexiconUnmarshaller;
import com.ntc.vntok.lexicon.jaxb.Corpus;
import com.ntc.vntok.lexicon.jaxb.W;
import com.ntc.vntok.utils.FileUtil;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nghiatc
 * @since May 2, 2021
 */
public class UnigramTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LexiconUnmarshaller unmarshaller = new LexiconUnmarshaller();
            Map<String, Integer> unigram = new LinkedHashMap<>();

            System.out.print("Loading unigram model...");
            // load unigram model
            InputStream stream = ResourceUtil.getResourceAsStream(com.ntc.vntok.lang.IConstants.UNIGRAM_MODEL);
            Corpus unigramCorpus = unmarshaller.unmarshal(stream);
            List<W> ws = unigramCorpus.getBody().getW();
            for (W w : ws) {
                String freq = w.getMsd();
                String word = w.getContent();
                unigram.put(word, Integer.parseInt(freq));
            }
            System.out.println("OK");
            
            // Write unigram file json.
            String unigramFile = "src/main/resources/models/tok/ngram/unigram.json";
            String sunigram = JsonUtils.Instance.toJson(unigram);
            FileUtil.writeFileJson(unigramFile, sunigram);
            
            System.out.println("Done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
