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
import com.ntc.vntok.graph.AdjacencyMatrixGraph;
import com.ntc.vntok.graph.Edge;
import com.ntc.vntok.graph.IGraph;
import com.ntc.vntok.graph.IWeightedGraph;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class GraphUtilities {

    /**
     * Extract edges of a graph.
     *
     * @param graph a graph
     * @return an array of edges of the graph.
     */
    public static Edge[] getEdges(IGraph graph) {
        Edge[] edges = new Edge[graph.getNumberOfEdges()];
        int e = 0;
        for (int u = 0; u < graph.getNumberOfVertices(); u++) {
            // get all vertices adjacent to u
            VertexIterator iterator = graph.vertexIterator(u);
            while (iterator.hasNext()) {
                int v = iterator.next();
                // create an edge (u,v)
                // we don't count for a loop edge of type (u,u)
                if (graph.isDirected() || u < v) {
                    edges[e++] = new Edge(u, v);
                }
            }
        }
        return edges;
    }

    /**
     * Extract edges of a weighted graph.
     *
     * @param graph a weighted graph
     * @return an array of edges.
     */
    public static Edge[] getWeightedEdges(IWeightedGraph graph) {
        Edge[] edges = new Edge[graph.getNumberOfEdges()];
        int e = 0;
        for (int u = 0; u < graph.getNumberOfVertices(); u++) {
            // get all edges adjacent to u
            EdgeIterator iterator = graph.edgeIterator(u);
            while (iterator.hasNext()) {
                Edge edge = iterator.next();
                if (graph.isDirected() || u < edge.getV()) {
                    edges[e++] = edge;
                }
            }
        }
        return edges;
    }

    /**
     * Copy a graph.
     *
     * @param g a graph
     * @param dense the returned graph is a dense one or not.
     * @return a dense graph that is implemented by an adjacency matrix graph or a adjacency list graph.
     * @see AdjacencyMatrixGraph
     * @see AdjacencyListGraph
     */
    public static IGraph copy(IGraph g, boolean dense) {
        int n = g.getNumberOfVertices();
        // create an appropriate graph
        IGraph graph = null;
        if (dense) {
            graph = new AdjacencyMatrixGraph(n, g.isDirected());
        } else {
            graph = new AdjacencyListGraph(n, g.isDirected());
        }
        // fill its edges
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (g.edge(u, v)) {
                    graph.insert(new Edge(u, v));
                }
            }
        }
        return graph;
    }

    /**
     * Get the transitive closure of a graph.
     *
     * @param g a graph.
     * @return the transitive closure of <code>g</code>.
     */
    public static IGraph getTransitiveClosure(IGraph g) {
        // copy the original graph
        IGraph transitiveClosure = GraphUtilities.copy(g, true);
        int n = g.getNumberOfVertices();
        // add dummy loop edges
        for (int u = 0; u < n; u++) {
            transitiveClosure.insert(new Edge(u, u));
        }
        // the Warhall's algorithm to compute the transitive closure
        for (int v = 0; v < n; v++) {
            for (int u = 0; u < n; u++) {
                if (transitiveClosure.edge(u, v)) {
                    for (int w = 0; w < n; w++) {
                        if (transitiveClosure.edge(v, w)) {
                            transitiveClosure.insert(new Edge(u, w));
                        }
                    }
                }
            }
        }
        return transitiveClosure;
    }

    /**
     * Checks the projectivity of a graph. A graph is projective if for all edges (u,v), forall k
     * there exists a path from u to k, that is (u,k) is an edge of the transitive closure of g.
     * @param g a graph
     * @return <code>true</code> or <code>false</code>
     */
    public static boolean isProjective(IGraph g) {
        // get the transitive closure of g 
        IGraph tcg = getTransitiveClosure(g);
        Edge[] edges = GraphUtilities.getEdges(g);
        for (Edge e : edges) {
            int u = e.getU();
            int v = e.getV();
            for (int k = Math.min(u, v); k < Math.max(u, v); k++) {
                if (!tcg.edge(u, k)) {
                    System.err.println("(u,k,v) = (" + u + "," + k + "," + v + ")");
                    return false;
                }
            }
        }
        return true;
    }
}
