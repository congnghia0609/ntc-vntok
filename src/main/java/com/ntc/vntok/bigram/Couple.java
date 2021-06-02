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

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class Couple implements Comparable<Couple> {

    /**
     * First token
     */
    private final String first;

    /**
     * Second token
     */
    private final String second;
    /**
     * The frequency of a couple
     */
    private int freq;
    /**
     * The probability of a couple
     */
    private double prob;

    public Couple(String f, String s) {
        first = f;
        second = s;
        freq = 1;
        prob = 0;
    }

    /**
     * Get the first token.
     *
     * @return
     */
    public String getFirst() {
        return first;
    }

    /**
     * Get the second token.
     *
     * @return
     */
    public String getSecond() {
        return second;
    }

    /**
     * Return the fequency
     *
     * @return
     */
    public int getFreq() {
        return freq;
    }

    /**
     * Increase the frequency of this couple by one
     *
     * @return
     */
    public int incFreq() {
        freq += 1;
        return freq;
    }

    /**
     * Get the probability
     *
     * @return
     */
    public double getProb() {
        return prob;
    }

    /**
     * Set the probability for the couple.
     *
     * @param prob
     */
    public void setProb(double prob) {
        this.prob = prob;
    }

    /**
     * Two couples are equal if the corresponding strings are equal (ignore case).
     * @param o is object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Couple)) {
            return false;
        }
        Couple c = (Couple) o;
        return ((first.equalsIgnoreCase(c.first)) && (second
                .equalsIgnoreCase(c.second)));
    }

    /**
     * An important method for a good storage of couple inside a set. This method is used by the <code>equals()</code>
     * method to compare two couples.
     */
    @Override
    public int hashCode() {
        return 3 * first.hashCode() + 5 * second.hashCode() + 7 * freq;
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + ")" + "\t" + freq + "\t" + prob;
    }

    @Override
    public int compareTo(Couple o) {
        String fs = first + second;
        return fs.compareTo(o.getFirst() + o.getSecond());
    }
}
