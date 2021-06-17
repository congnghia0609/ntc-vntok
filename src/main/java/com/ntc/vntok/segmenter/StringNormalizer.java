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

import com.ntc.vntok.TCommon;
import com.ntc.vntok.VTConfig;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class StringNormalizer {

    private static StringNormalizer instance;
    private static Map<String, String> map = new HashMap<>();

    private StringNormalizer() {
        init();
    }
    
    private StringNormalizer(String mapFile) {
        init(mapFile);
    }

    private void init() {
        try {
            InputStream stream = ResourceUtil.getResourceAsStream(TCommon.NORMALIZATION_RULES);
            List<String> rules = IOUtils.readLines(stream, "UTF-8");
            for (int i = 0; i < rules.size(); i++) {
                String rule = rules.get(i);
                String[] s = rule.split("\\s+");
                if (s.length == 2) {
                    map.put(s[0], s[1]);
                } else {
                    System.err.println("Wrong syntax in the map file " + TCommon.NORMALIZATION_RULES + " at line " + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void init(String mapFile) {
        try {
            InputStream stream = new FileInputStream(mapFile);
            List<String> rules = IOUtils.readLines(stream, "UTF-8");
            for (int i = 0; i < rules.size(); i++) {
                String rule = rules.get(i);
                String[] s = rule.split("\\s+");
                if (s.length == 2) {
                    map.put(s[0], s[1]);
                } else {
                    System.err.println("Wrong syntax in the map file " + mapFile + " at line " + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return an instance of the class.
     */
    public static StringNormalizer getInstance() {
        if (instance == null) {
            instance = new StringNormalizer();
        }
        return instance;
    }
    
    /**
     * @param cfg VTConfig
     * @return an instance of the class.
     */
    public static StringNormalizer getInstance(VTConfig cfg) {
        if (instance == null) {
            if (cfg.getNormalRules() == null || cfg.getNormalRules().isEmpty()) {
                instance = new StringNormalizer();
            } else {
                instance = new StringNormalizer(cfg.getNormalRules());
            }
        }
        return instance;
    }

    /**
     * @param filename a filename
     * @return an instance of the class.
     */
    public static StringNormalizer getInstance(String filename) {
        if (instance == null) {
            instance = new StringNormalizer(filename);
        }
        return instance;
    }
    
    /**
     * @param properties a Properties
     * @return an instance of the class.
     */
    public static StringNormalizer getInstance(Properties properties) {
        if (instance == null) {
            instance = new StringNormalizer(properties.getProperty("normalizationRules"));
        }
        return instance;
    }

    /**
     * Normalize a string.
     *
     * @return a normalized string
     * @param s a string
     */
    public String normalize(String s) {
        String result = s;
        for (String from : map.keySet()) {
            String to = map.get(from);
            if (result.contains(from)) {
                result = result.replace(from, to);
            }
        }
        return result;
    }
}
