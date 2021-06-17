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
import com.ntc.vntok.utils.UTF8FileUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class GenUnigram {

    /**
     * A map that stores strings and their corresponding frequencies.
     */
    private static Map<String, Integer> UNIGRAM;

    /**
     * The unigram model
     */
    private static GenUnigram MODEL;

    /**
     * Private constructor
     */
    private GenUnigram() {
        init();
    }

    /**
     * Initialize the map of unigrams
     */
    private void init() {
        UNIGRAM = new HashMap<>();
    }

    /**
     * Get the unique instance of a unigram model.
     *
     * @return an empty unigram model
     */
    public static GenUnigram getInstance() {
        if (MODEL == null) {
            MODEL = new GenUnigram();
        }
        return MODEL;
    }

    /**
     * Test if a file is a directory
     *
     * @param filename a filename
     * @return true or false
     */
    private static boolean isDirectory(String filename) {
        File file = new File(filename);
        return file.isDirectory();
    }

    /**
     * Load all flat text files from a directory.
     *
     * @param directoryName name of a directory that contains corpora.
     */
    public static void loadCorpora(String directoryName) {
        // get the corpora directory
        File corporaDir = new File(TCommon.CORPORA_DIRECTORY);
        // list its files
        File[] corpora = corporaDir.listFiles();
        // load all of the files
        // don't take into account subdirectories
        for (File f : corpora) {
            String corpus = f.getPath();
            if (!isDirectory(corpus)) {
                try {
                    loadCorpus(corpus);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.err.println("Total " + corpora.length + " files loaded.");
    }

    private static void processLoadedCorpus(List<String> lines) {
        for (String token : lines) {
            if (!UNIGRAM.containsKey(token)) {
                UNIGRAM.put(token, 1);
            } else {
                int v = UNIGRAM.get(token);
                UNIGRAM.put(token, v + 1);
            }
        }
    }

    public static void loadCorpusFromStream(InputStream stream) throws IOException {
        IOUtils.readLines(stream, "UTF-8");
    }

    /**
     * Load a corpus and update the frequencies.
     *
     * @param corpus a corpus
     * @throws java.io.IOException a IOException
     */
    public static void loadCorpus(String corpus) throws IOException {
        List<String> lines = FileUtils.readLines(new File(corpus), "UTF-8");

        processLoadedCorpus(lines);
    }

    /**
     * Get the frequencies map.
     *
     * @return the frequencies map.
     */
    public static Map<String, Integer> getFrequencies() {
        return UNIGRAM;
    }

    /**
     * Output the unigram to a plain text file in the form of two columns.
     *
     * @param filename a flat text filename
     */
    public static void print(String filename) {
        // create a file writer
        UTF8FileUtility.createWriter(filename);
        // create a string buffer for storing the text
        StringBuilder sBuffer = new StringBuilder();
        int numTokens = 0;
        int freq = 0;
        for (String token : UNIGRAM.keySet()) {
            freq = UNIGRAM.get(token);
            numTokens += freq;
            sBuffer.append(token).append('\t').append(freq).append("\n");
        }
        // write the string buffer to the file
        UTF8FileUtility.write(sBuffer.toString());
        // close the writer
        UTF8FileUtility.closeWriter();
        System.err.println("# of   tokens = " + numTokens);
        System.err.println("# of unigrams = " + UNIGRAM.size());
    }

    /**
     * Marshal the map to an JSON file using the lexicon format.
     *
     * @param filename the JSON file containing the unigram model.
     */
    public static void marshal(String filename) {
        String sunigram = JsonUtils.Instance.toJson(UNIGRAM);
        FileUtil.writeFileJson(filename, sunigram);
    }
}
