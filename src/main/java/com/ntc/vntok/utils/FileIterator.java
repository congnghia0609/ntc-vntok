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

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class FileIterator {

    private FileIterator() {
    }

    /**
     * Get all files in a directory which satisfy a given file filter.
     *
     * @param directory a directory to look for files
     * @param fileFilter a file filter
     * @return an array of file
     */
    public static File[] listFiles(File directory, FileFilter fileFilter) {
        List<File> result = new ArrayList<>();
        if (directory.isDirectory()) {
            // get all sub directories and files in this directory 
            File[] files = directory.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    // recursively get files
                    result.addAll(Arrays.asList(listFiles(f, fileFilter)));
                } else {
                    if (fileFilter.accept(f)) {
                        result.add(f);
                    }
                }
            }
        }
        return result.toArray(new File[result.size()]);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        FileFilter textFileFilter = new TextFileFilter();
        File directory = new File("samples");
        File[] files = FileIterator.listFiles(directory, textFileFilter);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
