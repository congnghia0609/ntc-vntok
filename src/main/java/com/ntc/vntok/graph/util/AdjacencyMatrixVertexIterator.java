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

import com.ntc.vntok.graph.AdjacencyMatrixGraph;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class AdjacencyMatrixVertexIterator implements VertexIterator {

    private final AdjacencyMatrixGraph graph;

    private final int n;

    private final int u;

    private int v = -1;

    private final boolean[][] adj;

    /**
     * Constructor.
     *
     * @param g AdjacencyMatrixGraph
     * @param u vertex u
     */
    public AdjacencyMatrixVertexIterator(AdjacencyMatrixGraph g, int u) {
        this.graph = g;
        this.u = u;
        // get the number of vertices of the graph
        n = graph.getNumberOfVertices();
        // range checking
        new AssertionError(u < 0 || u >= n);
        adj = graph.getAdj();
    }

    @Override
    public boolean hasNext() {
        // increase the current vertex v.
        v++;
        for (int i = v; i < n; i++) {
            if (adj[u][i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int next() {
        while (v < n) {
            if (adj[u][v]) {
                return v;
            } else {
                v++;
            }
        }
        return -1;
    }
}
