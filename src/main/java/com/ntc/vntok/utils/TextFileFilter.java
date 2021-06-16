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

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class TextFileFilter implements FileFilter {

    private String extension = ".txt";

    /**
     * Default constructor.
     */
    public TextFileFilter() {
        // do nothing, use the default extension
    }

    /**
     * Constructs a text file filter given an extension.
     *
     * @param extension
     */
    public TextFileFilter(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File pathname) {
        if (!pathname.isFile()) {
            return false;
        }
        if (pathname.getName().endsWith(extension)) {
            return true;
        }
        return false;
    }
}
