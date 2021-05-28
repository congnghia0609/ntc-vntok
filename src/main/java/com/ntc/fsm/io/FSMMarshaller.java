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
//import com.ntc.fsm.jaxb.Fsm;
//import com.ntc.fsm.jaxb.ObjectFactory;
//import com.ntc.fsm.jaxb.S;
//import com.ntc.fsm.jaxb.States;
//import com.ntc.fsm.jaxb.T;
//import com.ntc.fsm.jaxb.Transitions;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//
///**
// *
// * @author nghiatc
// * @since Apr 21, 2021
// */
//public class FSMMarshaller {
//
//    private JAXBContext jaxbContext;
//
//    private Marshaller marshaller;
//
//    /**
//     * Default constructor.
//     */
//    public FSMMarshaller() {
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
//     * @return
//     */
//    private Marshaller getMarshaller() {
//        if (marshaller == null) {
//            try {
//                // create the marshaller
//                marshaller = jaxbContext.createMarshaller();
//                marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
//                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            } catch (JAXBException e) {
//                e.printStackTrace();
//            }
//        }
//        return marshaller;
//    }
//
//    /**
//     * Create a FSM object from a state machine
//     *
//     * @param fsm a FSM
//     * @param name the name of FSM
//     * @return a Fsm object.
//     */
//    private Fsm createFsm(FSM fsm, String name) {
//        // create the marshaller
//        getMarshaller();
//        // build the machine object
//        ObjectFactory of = new ObjectFactory();
//        Fsm _fsm = of.createFsm();
//        _fsm.setName(name);
//        States _states = of.createStates();
//        _fsm.setStates(_states);
//        Transitions _transitions = of.createTransitions();
//        _fsm.setTransitions(_transitions);
//        Map<Integer, State> states = fsm.getStates();
//        for (Iterator<Integer> it = states.keySet().iterator(); it.hasNext();) {
//            Integer id = it.next();
//            State state = states.get(id);
//            // create state objects
//            S _s = of.createS();
//            _s.setId(id.intValue());
//            _s.setType(state.getType());
//            _states.getS().add(_s);
//            // create transition objects
//            List<Transition> outTransitions = state.getOutTransitions();
//            for (Iterator<Transition> i = outTransitions.iterator(); i.hasNext();) {
//                Transition t = i.next();
//                T _t = of.createT();
//                _t.setSrc(t.getSource());
//                _t.setTar(t.getTarget());
//                _t.setInp("" + t.getInput());
//                if (t.getOutput() != IConstants.EMPTY_STRING) {
//                    _t.setOut("" + t.getOutput());
//                }
//                _transitions.getT().add(_t);
//            }
//        }
//        return _fsm;
//    }
//
//    /**
//     * Marshal a fsm to a file.
//     *
//     * @param fsm a finite state machine.
//     * @param filename a file.
//     */
//    public void marshal(FSM fsm, String filename) {
//        Fsm _fsm = createFsm(fsm, filename);
//        // marshal the object
//        try {
//            marshaller.marshal(_fsm, new FileOutputStream(new File(filename)));
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Marshal a fsm to an output stream. This method is used only to test the created machine.
//     *
//     * @param fsm
//     * @param os
//     */
//    public void marshal(FSM fsm, OutputStream os) {
//        Fsm _fsm = createFsm(fsm, "sample_fsm");
//        // marshal the object
//        try {
//            marshaller.marshal(_fsm, os);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//}
