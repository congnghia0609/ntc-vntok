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
public class Ambiguity {

    /**
     * First token
     */
    String first;
    /**
     * Second token
     */
    String second;
    /**
     * Third token
     */
    String third;
    /**
     * If <code>isFirstGroup</code> is <code>true</code>, the solution (first,second) is chosen, the method
     * <code>getSelection()</code> will return the first two tokens, otherwise the last two tokens (second,third) is
     * chosen.
     */
    boolean isFirstGroup;

    public Ambiguity(String f, String s, String t) {
        this.first = f;
        this.second = s;
        this.third = t;
        // default solution is (first,second) group.
        isFirstGroup = true;
    }

    /**
     * Update the <code>isFirstGroup</code> value.
     *
     * @param b
     */
    public void setIsFirstGroup(boolean b) {
        this.isFirstGroup = b;
    }

    /**
     * Get the selection
     *
     * @return
     */
    public boolean getIsFirstGroup() {
        return isFirstGroup;
    }

    /**
     * Get a selection.
     *
     * @return
     */
    public String[] getSelection() {
        String[] firstGroup = {first, second};
        String[] secondGroup = {second, third};
        if (isFirstGroup) {
            return firstGroup;
        } else {
            return secondGroup;
        }
    }

    @Override
    public int hashCode() {
        return 2 * first.hashCode() + 3 * second.hashCode() + 5 * third.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ambiguity)) {
            return false;
        }
        Ambiguity a = (Ambiguity) obj;
        return first.equalsIgnoreCase(a.first)
                && second.equalsIgnoreCase(a.second) && third.equalsIgnoreCase(a.third);
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + "," + third + ")";
    }
}
