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

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class EdgeNode {

    private final Edge e;
    private final EdgeNode next;

    /**
     * Default constructor.
     */
    public EdgeNode() {
        e = null;
        next = null;
    }

    /**
     * Constructor an edge node given an edge and next edge node.
     *
     * @param e an edge
     * @param next next edge node
     */
    public EdgeNode(Edge e, EdgeNode next) {
        this.e = e;
        this.next = next;
    }

    /**
     * Get the next edge node.
     *
     * @return the next edge node.
     */
    public EdgeNode getNext() {
        return next;
    }

    /**
     * Get the edge in the current node.
     *
     * @return the edge.
     */
    public Edge getEdge() {
        return e;
    }
}
