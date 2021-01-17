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

package com.ntc.vntok.corpus;

import com.ntc.vntok.corpus.jaxb.Corpus;
import com.ntc.vntok.corpus.jaxb.ObjectFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author nghiatc
 * @since Jan 18, 2021
 */
public class CorpusUnmarshaller {
    JAXBContext jaxbContext;
	Unmarshaller unmarshaller;
	
	/**
	 * Default constructor.
	 */
	public CorpusUnmarshaller() throws JAXBException {
		ClassLoader cl = ObjectFactory.class.getClassLoader();
        jaxbContext = JAXBContext.newInstance("com.ntc.vntok.corpus", cl);
	}
	
	/**
	 * Get the marshaller object.
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
	 * @param filename a lexicon file
	 * @return a Corpus object.
	 */
	public Corpus unmarshal(String filename) {
		try {
			Object object = getUnmarshaller().unmarshal(new FileInputStream(filename));
			if (object instanceof Corpus) {
				Corpus corpus = (Corpus) object;
				return corpus;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
    
}
