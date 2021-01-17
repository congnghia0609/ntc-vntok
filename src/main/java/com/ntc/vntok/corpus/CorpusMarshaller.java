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

import com.ntc.vntok.corpus.jaxb.ObjectFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author nghiatc
 * @since Jan 18, 2021
 */
public class CorpusMarshaller {
    private JAXBContext jaxbContext; 
	private Marshaller marshaller;
	static ObjectFactory factory = new ObjectFactory();
	
	/**
	 * Default constructor.
	 */
	public CorpusMarshaller() throws JAXBException {
		ClassLoader cl = ObjectFactory.class.getClassLoader();
        jaxbContext = JAXBContext.newInstance("com.ntc.vntok.corpus", cl);
	}
	
	/**
	 * Get the marshaller object.
	 * @return the marshaller object
	 */
	public Marshaller getMarshaller() {
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
	 * Gets the factory.
	 * @return the corpus factory.
	 */
	public static ObjectFactory getFactory() {
		return factory;
	}
    
}
