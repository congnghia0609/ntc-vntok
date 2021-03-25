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
package com.ntc.vntok.io;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class FormatterFactory {

    public static final String PLAIN_OUTPUTER_NAME = "PLAIN";

    public static final String TWO_COLUMNS_OUTPUTER_NAME = "2COLS";

    public static final String XML_OUTPUTER_NAME = "XML";

    /**
     * Private constructor
     *
     */
    private FormatterFactory() {
    }

    /**
     * @return an instance of this class.
     */
    public static FormatterFactory getDefault() {
        return new FormatterFactory();
    }

    /**
     * Get an formatter
     *
     * @param name name of the formatter
     * @return a formatter
     */
    public static IOutputFormatter getFormater(String name) {
        if (name.equals(FormatterFactory.PLAIN_OUTPUTER_NAME)) {
            return new PlainFormatter();
        } else if (name.equals(FormatterFactory.XML_OUTPUTER_NAME)) {
            return new XMLFormatter();
        } else if (name.equals(FormatterFactory.TWO_COLUMNS_OUTPUTER_NAME)) {
            return new TwoColumnsFormatter();
        } else {
            return null;
        }
    }
}
