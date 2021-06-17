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

import com.ntc.vntok.graph.util.AdjacencyMatrixVertexIterator;
import com.ntc.vntok.graph.util.VertexIterator;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class AdjacencyMatrixGraph extends Graph {

    private final boolean adj[][];

    /**
     * @param n a number vertex
     * @param directed is directed graph
     */
    public AdjacencyMatrixGraph(int n, boolean directed) {
        super(n, directed);
        adj = new boolean[n][n];
    }

    @Override
    public void insert(Edge edge) {
        int u = edge.getU();
        int v = edge.getV();
        if (!adj[u][v]) {
            cE++;
        }
        adj[u][v] = true;
        if (!directed) {
            adj[v][u] = true;
        }
    }

    @Override
    public VertexIterator vertexIterator(int u) {
        return new AdjacencyMatrixVertexIterator(this, u);
    }

    @Override
    public void remove(Edge edge) {
        int u = edge.getU();
        int v = edge.getV();
        if (adj[u][v]) {
            cE--;
        }
        adj[u][v] = false;
        if (!directed) {
            adj[v][u] = false;
        }
    }

    @Override
    public boolean edge(int u, int v) {
        return adj[u][v];
    }

    /**
     * Get the adjacency matrix that represents the graph.
     *
     * @return the adjacency matrix that represents the graph.
     */
    public boolean[][] getAdj() {
        return adj;
    }

    @Override
    protected void dispose() {
    }
}
