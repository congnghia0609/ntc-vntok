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
package com.ntc.vntok.lexicon.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Corpus }
     *
     * @return Corpus
     */
    public Corpus createCorpus() {
        return new Corpus();
    }

    /**
     * Create an instance of {@link Body }
     *
     * @return Body
     */
    public Body createBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link W }
     *
     * @return W
     */
    public W createW() {
        return new W();
    }
}
