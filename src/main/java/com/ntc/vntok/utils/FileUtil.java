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

package com.ntc.vntok.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author nghiatc
 * @since May 2, 2021
 */
public class FileUtil {
    
    // https://www.baeldung.com/java-write-to-file
    public static void writeFileJson(String file, String json) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // https://www.baeldung.com/reading-file-in-java
    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder rs = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i > 0) {
                    rs.append("\n");
                }
                rs.append(line);
                i++;
            }
        }
        return rs.toString();
    }
    
    public static String readSmallFile(String file) throws IOException {
        Path path = Paths.get(file);
        return Files.readAllLines(path).get(0);
    }

    public static String readLargeFile(String file) throws IOException {
        Path path = Paths.get(file);
        BufferedReader reader = Files.newBufferedReader(path);
        return reader.readLine();
    }
    
    public static String readStreamFiles(String file) throws IOException {
        Path path = Paths.get(file);
        String rs;
        try (Stream<String> lines = Files.lines(path)) {
            rs = lines.collect(Collectors.joining("\n"));
        }
        return rs;
    }
}
