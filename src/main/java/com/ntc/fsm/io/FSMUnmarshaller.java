///*
// * Copyright 2021 nghiatc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.ntc.fsm.io;
//
//import com.ntc.fsm.FSM;
//import com.ntc.fsm.IConstants;
//import com.ntc.fsm.State;
//import com.ntc.fsm.Transition;
//import com.ntc.fsm.fsa.DFA;
//import com.ntc.fsm.fst.FST;
//import com.ntc.fsm.jaxb.Fsm;
//import com.ntc.fsm.jaxb.ObjectFactory;
//import com.ntc.fsm.jaxb.S;
//import com.ntc.fsm.jaxb.States;
//import com.ntc.fsm.jaxb.T;
//import com.ntc.fsm.jaxb.Transitions;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Iterator;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//
///**
// *
// * @author nghiatc
// * @since Apr 21, 2021
// */
//public class FSMUnmarshaller {
//
//    private JAXBContext jaxbContext;
//
//    private Unmarshaller unmarshaller;
//
//    /**
//     * Default constructor.
//     */
//    public FSMUnmarshaller() {
//        // create JAXB context
//        //
//        createContext();
//    }
//
//    private void createContext() {
//        jaxbContext = null;
//        try {
//            ClassLoader cl = ObjectFactory.class.getClassLoader();
//            jaxbContext = JAXBContext.newInstance(IConstants.JAXB_CONTEXT, cl);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Get the marshaller object.
//     *
//     * @return the marshaller object.
//     */
//    public Unmarshaller getUnmarshaller() {
//        if (unmarshaller == null) {
//            try {
//                // create the marshaller
//                unmarshaller = jaxbContext.createUnmarshaller();
//            } catch (JAXBException e) {
//                e.printStackTrace();
//            }
//        }
//        return unmarshaller;
//    }
//
//    /**
//     * Unmarshal a fsm from a file.
//     *
//     * @param filename a file.
//     * @return a state machine
//     */
//    public FSM unmarshal(String filename, String machineType) {
//        FSM fsm;
//        if (machineType.equalsIgnoreCase(IConstants.FSM_DFA)) {
//            fsm = new DFA();
//        } else {
////			System.out.println("Unmarshal a FST");
//            fsm = new FST();
//        }
//
//        getUnmarshaller();
//        try {
//
//            InputStream stream = new FileInputStream(filename); //getClass().getResourceAsStream(filename);
//
//            Object obj = unmarshaller.unmarshal(stream);
//            if (obj != null) {
//                Fsm fsm2 = (Fsm) obj;
//                // fill the states 
//                States states = fsm2.getStates();
//                for (Iterator<S> it = states.getS().iterator(); it.hasNext();) {
//                    S s = it.next();
//                    State state = new State(s.getId());
//                    state.setType(s.getType());
//                    fsm.addState(state);
//                }
//                // fill the transitions
//                Transitions transitions = fsm2.getTransitions();
//                for (Iterator<T> it = transitions.getT().iterator(); it.hasNext();) {
//                    T t = it.next();
//                    char input = t.getInp().charAt(0);
//                    String output = t.getOut();
//                    Transition transition;
//                    if (output != null && output.equals(IConstants.EMPTY_STRING)) {
//                        transition = new Transition(t.getSrc(), t.getTar(), input);
//                    } else {
//                        transition = new Transition(t.getSrc(), t.getTar(), input, output);
//                    }
//                    fsm.addTransition(transition);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Error when unmarshalling the machine.");
//            e.printStackTrace();
//        }
//        return fsm;
//    }
//    
//    /**
//     * load a fsm from a InputStream.
//     *
//     * @param stream InputStream
//     * @param machineType String
//     * @return a state machine
//     */
//    public FSM unmarshal(InputStream stream, String machineType) {
//        FSM fsm;
//        if (machineType.equalsIgnoreCase(IConstants.FSM_DFA)) {
//            fsm = new DFA();
//        } else {
//			//System.out.println("Unmarshal a FST");
//            fsm = new FST();
//        }
//
//        getUnmarshaller();
//        try {
//            //InputStream stream = new FileInputStream(filename); //getClass().getResourceAsStream(filename);
//            Object obj = unmarshaller.unmarshal(stream);
//            if (obj != null) {
//                Fsm fsm2 = (Fsm) obj;
//                // fill the states 
//                States states = fsm2.getStates();
//                for (S s : states.getS()) {
//                    State state = new State(s.getId());
//                    state.setType(s.getType());
//                    fsm.addState(state);
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
//                    fsm.addTransition(transition);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Error when unmarshalling the machine.");
//            e.printStackTrace();
//        }
//        return fsm;
//    }
//}
