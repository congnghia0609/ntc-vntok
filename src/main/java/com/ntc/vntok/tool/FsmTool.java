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

package com.ntc.vntok.tool;

import com.ntc.fsm.IConstants;
import com.ntc.fsm.State;
import com.ntc.fsm.Transition;
import com.ntc.fsm.jaxb.Fsm;
import com.ntc.fsm.jaxb.ObjectFactory;
import com.ntc.fsm.jaxb.S;
import com.ntc.fsm.jaxb.States;
import com.ntc.fsm.jaxb.T;
import com.ntc.fsm.jaxb.Transitions;
import com.ntc.vntok.utils.FileUtil;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author nghiatc
 * @since May 3, 2021
 */
public class FsmTool {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ClassLoader cl = ObjectFactory.class.getClassLoader();
            JAXBContext jaxbContext = JAXBContext.newInstance(IConstants.JAXB_CONTEXT, cl);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            System.out.print("Load the lexicon automaton...");
            InputStream dfaLexiconStream = ResourceUtil.getResourceAsStream(com.ntc.vntok.segmenter.IConstants.LEXICON_DFA);
            System.out.println("OK.");
            Object obj = unmarshaller.unmarshal(dfaLexiconStream);
            if (obj != null) {
                Fsm fsm = (Fsm) obj;
                
                // Write dfaLexicon file json.
                String dfaLexiconFile = "src/main/resources/models/tok/dfa/dfaLexicon.json";
                String sDfaLexicon = JsonUtils.Instance.toJson(fsm);
                FileUtil.writeFileJson(dfaLexiconFile, sDfaLexicon);
                System.out.println("Done!!!...");
                
//                // fill the states 
//                States states = fsm2.getStates();
//                for (S s : states.getS()) {
//                    State state = new State(s.getId());
//                    state.setType(s.getType());
//                    //fsm.addState(state);
//                }
//                // fill the transitions
//                Transitions transitions = fsm2.getTransitions();
//                for (T t : transitions.getT()) {
//                    char input = t.getInp().charAt(0);
//                    String output = t.getOut();
//                    Transition transition;
//                    if (output != null && output.equals(IConstants.EMPTY_STRING)) {
//                        transition = new Transition(t.getSrc(), t.getTar(), input);
//                    } else {
//                        transition = new Transition(t.getSrc(), t.getTar(), input, output);
//                    }
//                    //fsm.addTransition(transition);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
