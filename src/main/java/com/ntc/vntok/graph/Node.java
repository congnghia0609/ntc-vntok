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
package com.ntc.vntok.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class Node {

    private final int v;
    private final Node next;

    /**
     * Default constructor.
     */
    public Node() {
        v = -1;
        next = null;
    }

    /**
     * Construct a node.
     *
     * @param v current vertex data of the node.
     * @param next the next node.
     */
    public Node(int v, Node next) {
        this.v = v;
        this.next = next;
    }

    /**
     * Get next node.
     *
     * @return the next node.
     */
    public Node getNext() {
        return next;
    }

    /**
     * Get the vertex.
     *
     * @return the vertex
     */
    public int getV() {
        return v;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String s = "";
        Node node = this;
        while (node.getV() != -1) {
            s = s + node.getV();
            node = node.getNext();
            if (node.getV() != -1) {
                s += "->";
            }
        }
        return s;
    }

    /**
     * Convert the list to an array of integers.
     *
     * @return an array of integers
     */
    public int[] toArray() {
        List<Integer> list = new ArrayList<Integer>();

        Node node = this;
        while (node.getV() != -1) {
            list.add(node.getV());
            node = node.getNext();
        }
        int[] a = new int[list.size()];
        for (int i = 0; i < a.length; i++) {
            a[i] = list.get(i).intValue();
        }
        return a;
    }
}
