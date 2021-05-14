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
package com.ntc.vntok.graph.util;

import com.ntc.vntok.graph.AdjacencyListGraph;
import com.ntc.vntok.graph.Node;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class AdjacencyListVertexIterator implements VertexIterator {

    /**
     * The underlying graph that this iterator operates on.
     */
    private final AdjacencyListGraph graph;

    private Node next = null;

    /**
     * Construct the iterator over vertices adjacent to vertex u.
     *
     * @param g
     * @param u
     */
    public AdjacencyListVertexIterator(AdjacencyListGraph g, int u) {
        this.graph = g;

        // get the number of vertices of the graph
        int n = graph.getNumberOfVertices();
        // range checking
        new AssertionError(u < 0 || u >= n);
        next = graph.getAdj()[u];
    }

    @Override
    public int next() {
        // get the next vertex
        int v = next.getV();
        // update the next pointer
        next = next.getNext();
        return v;
    }

    @Override
    public boolean hasNext() {
        return (next != null);
    }
}
