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

import com.ntc.vntok.TCommon;
import com.ntc.vntok.utils.FileUtil;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nghiatc
 * @since May 7, 2021
 */
public class NamePrefixTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
//            // load the prefix lexicon
//            Set<String> prefix = new LinkedHashSet<>();
//            LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
//            InputStream namedEntityPrefixStream = ResourceUtil.getResourceAsStream(TCommon.NAME_PREFIX);
//            Corpus lexicon = lexiconUnmarshaller.unmarshal(namedEntityPrefixStream);
//            List<W> ws = lexicon.getBody().getW();
//            // add all prefixes to the set after converting them to lowercase
//            for (W w : ws) {
//                prefix.add(w.getContent().toLowerCase());
//            }
//            
//            // Write prefix file json.
//            String prefixFile = "src/main/resources/models/tok/prefix/namePrefix.json";
//            String sprefix = JsonUtils.Instance.toJson(prefix);
//            FileUtil.writeFileJson(prefixFile, sprefix);
//            
//            System.out.println("Done!!!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
