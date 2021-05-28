///*
// * Copyright 2021 nghiatc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.ntc.vntok.tool;
//
//import com.ntc.vntok.lexicon.LexiconUnmarshaller;
//import com.ntc.vntok.lexicon.jaxb.Corpus;
//import com.ntc.vntok.lexicon.jaxb.W;
//import com.ntc.vntok.segmenter.IConstants;
//import com.ntc.vntok.utils.FileUtil;
//import com.ntc.vntok.utils.JsonUtils;
//import com.ntc.vntok.utils.ResourceUtil;
//import java.io.InputStream;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// *
// * @author nghiatc
// * @since May 4, 2021
// */
//public class LexiconTool {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        try {
//            Set<String> externalLexicon = new HashSet<>();
//            // load the prefix lexicon
//            LexiconUnmarshaller lexiconUnmarshaller = new LexiconUnmarshaller();
//            InputStream externalLexiconStream = ResourceUtil.getResourceAsStream(IConstants.EXTERNAL_LEXICON);
//            Corpus lexicon = lexiconUnmarshaller.unmarshal(externalLexiconStream);
//            List<W> ws = lexicon.getBody().getW();
//            // add all prefixes to the set after converting them to lowercase
//            for (W w : ws) {
//                externalLexicon.add(w.getContent().toLowerCase());
//            }
//            System.out.println("External lexicon loaded.");
//            
//            // Write dfaLexicon file json.
//            String externalLexiconFile = "src/main/resources/models/tok/dfa/externalLexicon.json";
//            String sExternalLexicon = JsonUtils.Instance.toJson(externalLexicon);
//            FileUtil.writeFileJson(externalLexiconFile, sExternalLexicon);
//            System.out.println("Done!!!...");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
