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
package com.ntc.vntok.lexicon;

import com.ntc.vntok.lexicon.jaxb.Corpus;
import com.ntc.vntok.lexicon.jaxb.ObjectFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class LexiconUnmarshaller {

    JAXBContext jaxbContext;

    Unmarshaller unmarshaller;

    /**
     * Default constructor.
     */
    public LexiconUnmarshaller() {
        // create JAXB context
        //
        createContext();
    }

    private void createContext() {
        jaxbContext = null;
        try {
            ClassLoader cl = ObjectFactory.class.getClassLoader();
            jaxbContext = JAXBContext.newInstance(LexiconUnmarshaller.class.getName(), cl);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the marshaller object.
     *
     * @return the marshaller object
     */
    protected Unmarshaller getUnmarshaller() {
        if (unmarshaller == null) {
            try {
                // create the unmarshaller
                unmarshaller = jaxbContext.createUnmarshaller();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        return unmarshaller;
    }

    /**
     * Unmarshal a lexicon.
     *
     * @param filename a lexicon file
     * @return a Corpus object.
     */
    public Corpus unmarshal(String filename) {
        try {
            InputStream stream = new FileInputStream(filename); //getClass().getResourceAsStream(filename);

            if (stream != null) {
                Object object = getUnmarshaller().unmarshal(stream);
                if (object instanceof Corpus) {
                    Corpus corpus = (Corpus) object;
                    return corpus;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
