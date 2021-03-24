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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
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

    private static Map<String, String> map;

    private StringNormalizer(String mapFile) {
        map = new HashMap<String, String>();
        init(mapFile);
    }

    private void init(String mapFile) {
        try {
            InputStream stream = new FileInputStream(mapFile); //getClass().getResourceAsStream(mapFile);
            List<String> rules;
            rules = IOUtils.readLines(stream, "UTF-8");

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @return an instance of the class.
     */
    public static StringNormalizer getInstance() {
        return new StringNormalizer(IConstants.NORMALIZATION_RULES);
    }

    /**
     * @param properties
     * @return an instance of the class.
     */
    public static StringNormalizer getInstance(Properties properties) {
        return new StringNormalizer(properties.getProperty("normalizationRules"));
    }

    /**
     * Normalize a string.
     *
     * @return a normalized string
     * @param s a string
     */
    public String normalize(String s) {
        String result = new String(s);
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
            String from = it.next();
            String to = map.get(from);
            if (result.indexOf(from) >= 0) {
                result = result.replace(from, to);
            }
        }
        return result;
    }
}
