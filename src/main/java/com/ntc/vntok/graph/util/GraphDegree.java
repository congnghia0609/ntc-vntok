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

import com.ntc.vntok.graph.IGraph;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class GraphDegree {

    private final IGraph graph;
    private final int[] deg;

    /**
     * Constructor.
     *
     * @param g
     */
    public GraphDegree(IGraph g) {
        this.graph = g;
        int n = graph.getNumberOfVertices();
        deg = new int[n];
        for (int u = 0; u < n; u++) {
            deg[u] = 0;
            VertexIterator iterator = graph.vertexIterator(u);
            while (iterator.hasNext()) {
                iterator.next();
                deg[u]++;
            }
        }
    }

    /**
     * Get the degree of a vertex.
     *
     * @param u
     * @return the degree of a vertex
     */
    public int degree(int u) {
        return deg[u];
    }

    /**
     * Print degrees of all the vertices.
     */
    public void printDegrees() {
        int n = graph.getNumberOfVertices();
        for (int u = 0; u < n; u++) {
            int d = degree(u);
            // for testing only:
            System.out.println("deg(" + u + ") = " + d);
        }

    }
}
