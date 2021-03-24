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

import com.ntc.vntok.lexicon.jaxb.Body;
import com.ntc.vntok.lexicon.jaxb.Corpus;
import com.ntc.vntok.lexicon.jaxb.ObjectFactory;
import com.ntc.vntok.lexicon.jaxb.W;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class LexiconMarshaller {

    JAXBContext jaxbContext;

    Marshaller marshaller;

    /**
     * Default constructor.
     */
    public LexiconMarshaller() {
        // create JAXB context
        //
        createContext();
    }

    private void createContext() {
        jaxbContext = null;
        try {
            ClassLoader cl = ObjectFactory.class.getClassLoader();
            jaxbContext = JAXBContext.newInstance(LexiconMarshaller.class.getName(), cl);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the marshaller object.
     *
     * @return the marshaller object
     */
    protected Marshaller getMarshaller() {
        if (marshaller == null) {
            try {
                // create the marshaller
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        return marshaller;
    }

    /**
     * Marshal a map of objects to a file.
     *
     * @param filename the filename of the corpus. This file usually has extension .xml.
     */
    public void marshal(Map<?, ?> map, String filename) {
        // create the corpus object from the map
        // 
        ObjectFactory factory = new ObjectFactory();
        Corpus corpus = factory.createCorpus();
        corpus.setId(filename);

        Body body = factory.createBody();
        corpus.setBody(body);

        for (Iterator<?> it = map.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            Object value = map.get(key);
            W w = factory.createW();
            w.setContent(key.toString());
            w.setMsd(value.toString());
            body.getW().add(w);
        }
        // marshal the corpus
        // 
        OutputStream os = null;
        try {
            os = new FileOutputStream(filename);
            getMarshaller().marshal(corpus, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
