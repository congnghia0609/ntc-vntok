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
package com.ntc.vntok.bigram;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ntc.vntok.TCommon;
import com.ntc.vntok.utils.FileUtil;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class Estimator {

    /**
     * Epsilon value
     */
    private static double EPSILON = 0.01;

    private double lambda1;

    private double lambda2;

    /**
     * Probabilities P(w_i) = P(s)
     */
    private Map<String, Integer> unigram;

    /**
     * Probabilities P(w_{i-1}, w_i) = P(f, s)
     */
    private Map<Couple, Integer> bigram;

    /**
     * Conditional probabilities P(w_i | w_{i-1}) = P(s | f)
     */
    private List<Couple> probabilities;

    private Map<String, List<Couple>> tokenMap;

    public Estimator() {
        init();
        loadModels();
    }
    
    /**
     * Construct an estimator given data files.
     *
     * @param unigramDataFile
     * @param bigramDataFile
     * @throws java.io.FileNotFoundException
     */
    public Estimator(String unigramDataFile, String bigramDataFile) throws FileNotFoundException {
        init();
        loadModels(unigramDataFile, bigramDataFile);
    }

    /**
     * Initialize the maps.
     *
     */
    private void init() {
        // init the collections
        unigram = new HashMap<>();
        bigram = new HashMap<>();
        probabilities = new ArrayList<>();
        tokenMap = new HashMap<>();
    }

    /**
     * Find all couples in the bigram model that has the first string is <code>first</code>.
     *
     * @param first a string
     * @return
     */
    private Couple[] findFirst(String first) {
        List<Couple> couples = tokenMap.get(first);
        if (couples != null) {
            return couples.toArray(new Couple[couples.size()]);
        } else {
            return null;
        }
    }

    /**
     * Estimate conditional probabilities P(s | f), for all f and s in the model. This method is called once to estimate
     * conditional probabilities. The result is saved to a file by method <code>outputConditionalProbabilities</code>.
     */
    private void estimateConditionalProb() {
        System.out.println("Estimating conditional probabilities...");
        for (String first : unigram.keySet()) {
            // get the c(first)
            int cFirst = unigram.get(first);
            // find all couple(first, second) in the bigram given the first
            Couple[] biCouples = findFirst(first);
            if (biCouples != null) {
                // create probabilities P(s | f) and add them to the conditional probabilities list
                for (int i = 0; i < biCouples.length; i++) {
                    Couple c = new Couple(biCouples[i].getSecond(), first);
                    double prob = (double) (biCouples[i].getFreq()) / cFirst;
                    c.setProb(prob);
                    probabilities.add(c);
                }
            }
        }
        System.out.println("Sorting the probability list...");
        // sort the list of conditional probs using the couple comparator
        Collections.sort(probabilities, new CoupleComparator());
    }

    /**
     * Estimates lambda values
     *
     */
    private void estimate() {
        // calculate conditional probabilities
        estimateConditionalProb();
        System.out.println("Estimating lambda values...");

        long beginTime = System.currentTimeMillis();

        lambda1 = 0.5;
        lambda2 = 0.5;

        double hatEpsilon = 0;
        double hatLambda1 = 0;
        double hatLambda2 = 0;
        double c1 = 0;
        double c2 = 0;

        // number of loops
        int m = 0;

        do {
            hatLambda1 = lambda1;
            hatLambda2 = lambda2;
            // calculate c1 and c2
            c1 = 0;
            c2 = 0;
            for (Couple couple : bigram.keySet()) {
                String first = couple.getFirst(); // w_{i-1}
                String second = couple.getSecond(); // w_i
                // calculate the denominator
                double denominator = lambda1 * getConditionalProbability(second, first) + lambda2 * getUnigramProbability(second);
				//System.out.println("denominator = " + denominator);
				//System.out.println("getConditionalProbability(second, first) = " + getConditionalProbability(second, first));
				//System.out.println("getUnigramProbability(second) = " + getUnigramProbability(second));
                if (denominator > 0) {
                    // calculate c1
                    c1 += (couple.getFreq() * lambda1 * getConditionalProbability(second, first)) / denominator;
                    // calculate c2
                    c2 += (couple.getFreq() * lambda2 * getUnigramProbability(second)) / denominator;
					//System.out.println("c1 = " + c1 + " c2 = " + c2);
                }
            }
            // re-estimate lamda1 and lambda2
            lambda1 = c1 / (c1 + c2);
            validateProbabilityValue(lambda1);
            lambda2 = 1 - lambda1;
            hatEpsilon = Math.sqrt((lambda1 - hatLambda1) * (lambda1 - hatLambda1) + (lambda2 - hatLambda2) * (lambda2 - hatLambda2));
            System.out.println("m = " + m);
            System.out.println("lambda1 = " + lambda1);
            System.out.println("lambda2 = " + lambda2);
            System.out.println("hatEpsilon = " + hatEpsilon);
            // inc number of loops
            m++;
            if (m > 10) {
                break;
            }
        } while (hatEpsilon > EPSILON);
        long endTime = System.currentTimeMillis();
        System.out.println("Executed time (ms) = " + (endTime - beginTime));
        System.out.println("Loop terminated!");
        System.out.println("m = " + m);
        System.out.println("lambda1 = " + lambda1);
        System.out.println("lambda2 = " + lambda2);
        System.out.println("hatEpsilon = " + hatEpsilon);
    }

    /**
     * Validate a probability value (between 0 and 1)
     *
     * @param prob
     */
    private void validateProbabilityValue(double prob) {
        if (prob < 0 || prob > 1) {
            System.err.println("Error! Invalid probability!");
        }
    }

    /**
     * Get the probability of a token in the unigram model P(w_i)
     *
     * @param token
     * @return
     */
    private double getUnigramProbability(String token) {
        if (unigram.keySet().contains(token)) {
            return (unigram.get(token)*1.0) / getTokenCount();
        }
        // if the token is not in the bigram model
        return 0;
    }

    /**
     * Get the conditional probability of a couple of tokens.
     *
     * @param first
     * @param second
     * @return
     */
    private double getConditionalProbability(String first, String second) {
        return getConditionalProbability(new Couple(first, second));
    }

    /**
     * Get the conditional probability of a couple
     *
     * @param couple
     * @return double probability
     */
    private double getConditionalProbability(Couple couple) {
        int index = Collections.binarySearch(probabilities, couple, new CoupleComparator());
        if (index >= 0) {
            return probabilities.get(index).getProb();
        }
        return 0;
    }

    /**
     * Load data files and fill unigram and bigram models.
     *
     * @param unigramDataFile
     * @param bigramDataFile
     */
    private void loadModels() {
        System.out.println("Loading models...");
        // load unigram model
        InputStream unistream = ResourceUtil.getResourceAsStream(TCommon.UNIGRAM_MODEL);
        unigram = JsonUtils.Instance.getObject(unistream, new TypeReference<Map<String, Integer>>(){});
        // load bigram model and initialize the token map
        InputStream bistream = ResourceUtil.getResourceAsStream(TCommon.BIGRAM_MODEL);
        List<BigramJson> listbigrams = JsonUtils.Instance.getObject(bistream, new TypeReference<List<BigramJson>>(){});
        for (BigramJson bj : listbigrams) {
            String first = bj.getF();
            String second = bj.getS();
            int freq = bj.getC();
            // create a couple
            Couple couple = new Couple(first, second);
            // put the couple to the bigram
            bigram.put(couple, freq);
            // update the token map
            List<Couple> secondTokens = tokenMap.get(first);
            if (secondTokens == null) {
                secondTokens = new ArrayList<>();
                secondTokens.add(couple);
                tokenMap.put(first, secondTokens);
            } else {
                secondTokens.add(couple);
            }
        }
        System.out.println("tokenMap's size = " + tokenMap.size());
    }
    
    private void loadModels(String unigramDataFile, String bigramDataFile) throws FileNotFoundException {
        System.out.println("Loading models from file...");
        // load unigram model
        InputStream unistream = new FileInputStream(unigramDataFile);
        unigram = JsonUtils.Instance.getObject(unistream, new TypeReference<Map<String, Integer>>(){});
        // load bigram model and initialize the token map
        InputStream bistream = new FileInputStream(bigramDataFile);
        List<BigramJson> listbigrams = JsonUtils.Instance.getObject(bistream, new TypeReference<List<BigramJson>>(){});
        for (BigramJson bj : listbigrams) {
            String first = bj.getF();
            String second = bj.getS();
            int freq = bj.getC();
            // create a couple
            Couple couple = new Couple(first, second);
            // put the couple to the bigram
            bigram.put(couple, freq);
            // update the token map
            List<Couple> secondTokens = tokenMap.get(first);
            if (secondTokens == null) {
                secondTokens = new ArrayList<>();
                secondTokens.add(couple);
                tokenMap.put(first, secondTokens);
            } else {
                secondTokens.add(couple);
            }
        }
        System.out.println("tokenMap's size = " + tokenMap.size());
    }

    /**
     * Get the number of tokens in the training corpus. This value can be calculated using the unigram model. It is then
     * used to calculate probabilities P(w_i) and P(w_{i-1}, w_i).
     *
     * @return number of tokens in the training corpus.
     */
    private int getTokenCount() {
        int n = 0;
        for (String t : unigram.keySet()) {
            n += unigram.get(t);
        }
        return n;
    }

    /**
     * Output conditional probabilities P(s | f) to an XML file. This file will be used as data file for a resolver
     * <code>Resolver</code>.
     *
     * @param filename a file
     */
    private void marshalConditionalProbabilities(String filename) {
        System.out.println("Marshalling conditional probabilities...");
        // prepare a map for marshalling
        Map<String, String> map = new HashMap<>();
        System.out.println("probabilities's size = " + probabilities.size());
        String key;
        String value;
        DecimalFormat decimalFormat = new DecimalFormat("#.000");
        for (Couple c : probabilities) {
            key = c.getFirst() + "|" + c.getSecond();
            value = decimalFormat.format(c.getProb());
            map.put(key, value);
        }
        // Write the map
        String smap = JsonUtils.Instance.toJson(map);
        FileUtil.writeFileJson(filename, smap);
    }

    /**
     * Output conditional probabilities P(s | f) to a plain text file. This file will be used as data file for a
     * resolver <code>Resolver</code>.
     *
     * @param filename a file
     * @deprecated
     */
    @Deprecated
    void outputConditionalProbabilities(String filename) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufWriter = new BufferedWriter(writer);

            // write the result
            for (Couple c : probabilities) {
                bufWriter.write("(" + c.getFirst() + "|" + c.getSecond() + ")" + "\t" + c.getProb());
                bufWriter.write("\n");
            }
            // flush and close the writer
            bufWriter.flush();
            bufWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Estimate conditional probabilities and marshal the result to a file.
     */
    void buildConditionalProbabilities() {
        estimateConditionalProb();
//		estimator.outputConditionalProbabilities(IConstants.CONDITIONAL_PROBABILITIES);
        marshalConditionalProbabilities(TCommon.CONDITIONAL_PROBABILITIES);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Estimator estimator = new Estimator();
//		estimator.buildConditionalProbabilities();
        estimator.estimate();
        System.out.println("Done");
    }
}
