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
 * @since May 4, 2021
 */
public class RuleTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Map<String, String> rules = new LinkedHashMap<>();
            LexiconUnmarshaller unmarshaller = new LexiconUnmarshaller();
            InputStream lexersStream = ResourceUtil.getResourceAsStream(com.ntc.vntok.segmenter.IConstants.LEXERS);
            Corpus corpus = unmarshaller.unmarshal(lexersStream);
            List<W> lexers = corpus.getBody().getW();
            for (W w : lexers) {
    			//System.out.println(w.getMsd() + ": " + w.getContent());
                rules.put(w.getMsd(), w.getContent());
            }
            System.out.println("Rule lexers loaded.");
            
            // Write dfaLexicon file json.
            String ruleLexiconFile = "src/main/resources/models/tok/lexers/lexers.json";
            String sRuleLexicon = JsonUtils.Instance.toJson(rules);
            FileUtil.writeFileJson(ruleLexiconFile, sRuleLexicon);
            System.out.println("Done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
