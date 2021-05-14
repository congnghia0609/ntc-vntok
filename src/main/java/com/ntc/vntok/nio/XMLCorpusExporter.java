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
package com.ntc.vntok.nio;

import com.ntc.vntok.corpus.CorpusMarshaller;
import com.ntc.vntok.corpus.jaxb.Body;
import com.ntc.vntok.corpus.jaxb.Corpus;
import com.ntc.vntok.corpus.jaxb.ObjectFactory;
import com.ntc.vntok.corpus.jaxb.S;
import com.ntc.vntok.corpus.jaxb.W;
import com.ntc.vntok.tokens.TaggedWord;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class XMLCorpusExporter implements IExporter {

    private CorpusMarshaller corpusMarshaller;

    public XMLCorpusExporter() throws JAXBException {
        corpusMarshaller = new CorpusMarshaller();
    }

    @Override
    public String export(List<List<TaggedWord>> list) {
        ObjectFactory factory = CorpusMarshaller.getFactory();
        Corpus corpus = factory.createCorpus();
        corpus.setId(new Date().toString());
        Body body = factory.createBody();
        corpus.setBody(body);

        Iterator<List<TaggedWord>> iter = list.iterator();
        while (iter.hasNext()) {
            List<TaggedWord> list2 = iter.next();
            if (list2.size() == 1 && list2.get(0).getText().equals("\n")) {
                body.getPOrS().add(factory.createP());
            } else {
                S s = factory.createS();
                for (Iterator<TaggedWord> it = list2.iterator(); it.hasNext();) {
                    TaggedWord tw = it.next();
                    W w = factory.createW();
                    w.setContent(tw.getText());
                    w.setT(tw.getRule().getName());
                    s.getW().add(w);
                }
                body.getPOrS().add(s);
            }
        }

        StringWriter writer = new StringWriter();

        try {
            corpusMarshaller.getMarshaller().marshal(corpus, writer);
            writer.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
