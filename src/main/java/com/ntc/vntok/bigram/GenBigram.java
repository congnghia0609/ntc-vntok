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
package com.ntc.vntok.bigram;

import com.ntc.vntok.TCommon;
import com.ntc.vntok.utils.FileUtil;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.UTF8FileUtility;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class GenBigram {

    /**
     * A map of couples. We use a map to speed up the search of a couple.
     *
     */
    private Map<Couple, Couple> bigram;

    public GenBigram() {
        init();
        loadCorpora();
    }

    public GenBigram(boolean isCoded) {
        init();
        // load corpora, do statistics
        loadCorpora();
    }

    /**
     * Load all corpora.
     *
     */
    private void loadCorpora() {
        // get the corpora directory
        File corporaDir = new File(TCommon.CORPORA_DIRECTORY);
        // list its files
        File[] corpora = corporaDir.listFiles();
        for (int i = 0; i < corpora.length; i++) {
            String corpus = corpora[i].getPath();
            if (!isDirectory(corpus)) {
                try {
                    loadCorpus(corpus);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Total " + corpora.length + " files loaded.");
    }

    private boolean isDirectory(String filename) {
        File file = new File(filename);
        return file.isDirectory();
    }

    /**
     * Load a corpus and update the bigram set
     *
     * @param corpus
     * @throws IOException
     */
    private void loadCorpus(String corpus) throws IOException {

        String[] lines = UTF8FileUtility.getLines(corpus);
        String first = "";
        for (int i = 0; i < lines.length; i++) {
            String second = lines[i];
            Couple couple = new Couple(first, second);
            if (!bigram.keySet().contains(couple)) {
                bigram.put(couple, couple);
            } else {
                // search for the couple
                Couple c = bigram.get(couple);
                c.incFreq();
            }
            // update the first token
            first = second;
        }
    }

    private void init() {
        bigram = new HashMap<>();
    }

    /**
     * Get the bigram set.
     *
     * @return
     */
    public Map<Couple, Couple> getBigram() {
        return bigram;
    }

    /**
     * Output bigram to a text file.
     *
     * @param filename
     * @see {@link #marshalResults(String)}.
     */
    public void print(String filename) {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(filename), "UTF-8");
            BufferedWriter bufWriter = new BufferedWriter(writer);
            Iterator<Couple> couples = bigram.keySet().iterator();
            while (couples.hasNext()) {
                Couple couple = couples.next();
                bufWriter.write(couple + "\n");
            }
            bufWriter.flush();
            writer.close();
            System.out.println("# of couples = " + bigram.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Marshal the map to an JSON file using the lexicon format.
     *
     * @param filename
     */
    public void marshal(String filename) {
        // prepare a map for marshalling
        Map<String, Integer> map = new HashMap<>();
        for (Couple c : bigram.keySet()) {
            String key = c.getFirst() + "," + c.getSecond();
            int value = c.getFreq();
            map.put(key, value);
        }
        // Write map file json.
        String smap = JsonUtils.Instance.toJson(map);
        FileUtil.writeFileJson(filename, smap);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        GenBigram bimodel = new GenBigram(false);
        bimodel.marshal(TCommon.BIGRAM_MODEL);
        System.out.println("Done!");
    }
}
