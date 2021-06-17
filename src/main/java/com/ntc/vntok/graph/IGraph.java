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

import com.ntc.vntok.graph.util.VertexIterator;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public interface IGraph {

    /**
     * Get the number of vertices of the graph.
     *
     * @return the number of vertices of the graph.
     */
    int getNumberOfVertices();

    /**
     * Get the number of edges of the graph.
     *
     * @return the number of edges of the graph.
     */
    int getNumberOfEdges();

    /**
     * Check to see if the graph is directed or undirected.
     *
     * @return <code>true</code> if the graph is directed, <code>false</code> if it is not.
     */
    boolean isDirected();

    /**
     * Insert an edge to the graph
     *
     * @param edge Edge
     */
    void insert(Edge edge);

    /**
     * Remove an edge from the graph.
     *
     * @param edge Edge
     */
    void remove(Edge edge);

    /**
     * Check to see the existence of the edge (u,v).
     *
     * @param u vertex u
     * @param v vertex v
     * @return <code>true/false</code>
     */
    boolean edge(int u, int v);

    /**
     * Get an iterator for processing the vertices adjacent to a vertex.
     *
     * @param u a vertex
     * @return an iterator of vertices adjacent to <code>u</code>.
     */
    VertexIterator vertexIterator(int u);
}
