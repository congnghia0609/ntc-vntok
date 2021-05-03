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
package com.ntc.vntok;

import com.ntc.vntok.segmenter.AbstractResolver;
import com.ntc.vntok.segmenter.Segmenter;
import com.ntc.vntok.segmenter.UnigramModel;
import com.ntc.vntok.segmenter.UnigramResolver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class TokenizerProvider {

    /**
     * A lexical segmenter
     */
    private Segmenter segmenter;
    
    /**
     * An ambiguity resolver
     */
    private AbstractResolver resolver;
    
    /**
     * The tokenizer
     */
    private Tokenizer tokenizer;
    
    /**
     * An instance flag
     */
    private static boolean instanceFlag = false;

    private static TokenizerProvider provider;

    /**
     * Private constructor
     */
//    private TokenizerProvider() {
//        Properties properties = new Properties();
//        try {
//            File wdir = new File("");
//            //String conf = wdir.getAbsolutePath() + File.separator + "tokenizer.properties";
//            String conf = wdir.getAbsolutePath() + File.separator + "conf" + File.separator + "config.properties";
//            //properties.load(getClass().getResourceAsStream(conf));
//            properties.load(new FileInputStream(conf));
//            System.out.println("properties: " + properties);
//            // create a unigram resolver. 
//            //
//            resolver = new UnigramResolver(properties.getProperty("unigramModel"));
//            // create a lexical segmenter that use the unigram resolver
//            //
//            System.out.println("Creating lexical segmenter...");
//            segmenter = new Segmenter(properties, resolver);
//            System.out.println("Lexical segmenter created.");
//            // init the tokenizer
//            System.out.print("Initializing tokenizer...");
//            tokenizer = new Tokenizer(properties, segmenter);
//            // Do not resolve the ambiguity.
////			tokenizer.setAmbiguitiesResolved(false);
//            System.out.println("OK");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    private TokenizerProvider() {
        try {
            // create a unigram resolver. 
            //
            //resolver = new UnigramResolver(properties.getProperty("unigramModel"));
            //resolver = new UnigramResolver();
            resolver = new UnigramModel();
            // create a lexical segmenter that use the unigram resolver
            //
            System.out.println("Creating lexical segmenter...");
            //segmenter = new Segmenter(properties, resolver);
            segmenter = new Segmenter(resolver);
            System.out.println("Lexical segmenter created.");
            // init the tokenizer
            System.out.print("Initializing tokenizer...");
            //tokenizer = new Tokenizer(properties, segmenter);
            tokenizer = new Tokenizer(segmenter);
            // Do not resolve the ambiguity.
			//tokenizer.setAmbiguitiesResolved(false);
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TokenizerProvider(String propertiesFilename) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFilename));
            System.out.println("properties: " + properties);
            // create a unigram resolver. 
            //
            resolver = new UnigramResolver(properties.getProperty("unigramModel"));
            // create a lexical segmenter that use the unigram resolver
            //
            System.out.println("Creating lexical segmenter...");
            segmenter = new Segmenter(properties, resolver);
            System.out.println("Lexical segmenter created.");
            // init the tokenizer
            System.out.print("Initializing tokenizer...");
            tokenizer = new Tokenizer(properties, segmenter);
            // Do not resolve the ambiguity.
//			tokenizer.setAmbiguitiesResolved(false);
            System.out.println("OK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TokenizerProvider(Properties properties) {
        // DEBUG 
        System.out.println("lexiconDFA = " + properties.getProperty("lexiconDFA"));
        System.out.println("unigramModel = " + properties.getProperty("unigramModel"));
        System.out.println("bigramModel = " + properties.getProperty("bigramModel"));

        // create a unigram resolver. 
        //
        resolver = new UnigramResolver(properties.getProperty("unigramModel"));
        // create a lexical segmenter that use the unigram resolver
        //
        System.out.println("Creating lexical segmenter...");
        segmenter = new Segmenter(properties, resolver);
        System.out.println("Lexical segmenter created.");
        // init the tokenizer
        System.out.print("Initializing tokenizer...");
        tokenizer = new Tokenizer(properties, segmenter);
        // Do not resolve the ambiguity.
//		tokenizer.setAmbiguitiesResolved(false);
        System.out.println("OK");
    }

    /**
     * Instantiate a tokenizer provider object.
     *
     * @return a provider object
     */
    public static TokenizerProvider getInstance() {
        if (!instanceFlag) {
            instanceFlag = true;
            provider = new TokenizerProvider();
        }
        return provider;
    }

    /**
     * Instantiate a tokenizer provider object, parameters are read from a properties file.
     *
     * @return a provider object
     */
    public static TokenizerProvider getInstance(String propertiesFilename) {
        if (!instanceFlag) {
            instanceFlag = true;
            provider = new TokenizerProvider(propertiesFilename);
        }
        return provider;
    }

    /**
     * Instantiate a tokenizer provider object, parameters are read from a properties object.
     *
     * @return a provider object
     */
    public static TokenizerProvider getInstance(Properties properties) {
        if (!instanceFlag) {
            instanceFlag = true;
            provider = new TokenizerProvider(properties);
        }
        return provider;
    }

    /**
     * Get the lexical segmenter
     *
     * @return the lexical segmenter
     */
    public Segmenter getSegmenter() {
        return segmenter;
    }

    /**
     * Returns the tokenizer
     *
     * @return
     */
    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     * Dispose the data provider
     *
     */
    public void dispose() {
        // dispose the tokenizer
        // this will dispose the lexical tokenizer and the automata
        tokenizer.dispose();
    }
}
