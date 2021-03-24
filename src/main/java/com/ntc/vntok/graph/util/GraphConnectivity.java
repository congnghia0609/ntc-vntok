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
import com.ntc.vntok.graph.search.GraphDFS;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class GraphConnectivity {

    /**
     * Get the number of connected components of the graph. We use the DFS algorithm to visit all connected components.
     *
     * @param graph
     * @return the number of components of the graph
     */
    public static int countComponents(IGraph graph) {
        GraphDFS graphDFS = new GraphDFS(graph);
        return graphDFS.components();
    }

    /**
     * Check the connectivity between two given vertices.
     *
     * @param graph
     * @param u
     * @param v
     * @return <code>true</code> or <code>false</code>
     */
    public static boolean isConnected(IGraph graph, int u, int v) {
        // search the graph with u is the start vertex.
        GraphDFS graphDFS = new GraphDFS(graph, u);
        // test to see if u and v is in the same connected
        // component or not.
        int[] componentId = graphDFS.getComponentId();
        return (componentId[u] == componentId[v]);
    }

    /**
     * Get all isolated vertices of a graph.
     *
     * @param graph
     * @return An array of isolated vertices. A vertex is called isolated if it does not have an intransition.
     */
    public static int[] getIsolatedVertices(IGraph graph) {
        int nV = graph.getNumberOfVertices();
        int[] vertices = new int[nV];

        int n = 0;
        for (int u = 0; u < nV; u++) {
            // Is u isolated?
            boolean isolated = true;
            for (int v = 0; v < nV; v++) {
                if (graph.edge(v, u)) {
                    isolated = false;
                }
            }
            if (isolated) {
                vertices[n++] = u;
            }
        }
        int[] isolatedVertices = new int[n];
        for (int i = 0; i < n; i++) {
            isolatedVertices[i] = vertices[i];
        }
        return isolatedVertices;
    }
}
