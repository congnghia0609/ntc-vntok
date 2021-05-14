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

import com.ntc.vntok.graph.util.AdjacencyListEdgeIterator;
import com.ntc.vntok.graph.util.AdjacencyListVertexIterator;
import com.ntc.vntok.graph.util.EdgeIterator;
import com.ntc.vntok.graph.util.VertexIterator;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class AdjacencyListWeightedGraph extends WeightedGraph {

    private final EdgeNode adj[];

    /**
     * Constructor.
     *
     * @param n number of vertices of the graph.
     * @param directed <code>true/false</code>
     */
    public AdjacencyListWeightedGraph(int n, boolean directed) {
        super(n, directed);
        adj = new EdgeNode[n];
    }

    @Override
    public boolean edge(int u, int v) {
        EdgeIterator iterator = edgeIterator(u);
        while (iterator.hasNext()) {
            Edge e = iterator.next();
            if (v == e.getV()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EdgeIterator edgeIterator(int u) {
        return new AdjacencyListEdgeIterator(this, u);
    }

    @Override
    public void insert(Edge edge) {
        int u = edge.getU();
        int v = edge.getV();
        // add the edge (u,v)
        adj[u] = new EdgeNode(edge, adj[u]);
        // add the edge (v,u) if the graph
        // is not directed
        if (!directed) {
            adj[v] = new EdgeNode(edge, adj[v]);
        }
        // increase the number of edges
        cE++;
    }

    @Override
    public void remove(Edge edge) {
        // TODO
    }

    /**
     * Get the adjacency list.
     *
     * @return the adjacency list.
     */
    public EdgeNode[] getAdj() {
        return adj;
    }

    @Override
    public Edge getEdge(int u, int v) {
        EdgeIterator iterator = edgeIterator(u);
        while (iterator.hasNext()) {
            Edge e = iterator.next();
            if (v == e.getV()) {
                return e;
            }
        }
        return null;
    }

    @Override
    public VertexIterator vertexIterator(int u) {
        // build the graph2 from graph
        int nV = getNumberOfVertices();
        AdjacencyListGraph graph2 = new AdjacencyListGraph(nV, isDirected());
        // copy the edges of graph to graph2
        for (int v = 0; v < nV; v++) {
            EdgeIterator edgeIterator = edgeIterator(v);
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                graph2.insert(edge);
            }
        }
        // return the vertex iterator of graph2
        return new AdjacencyListVertexIterator(graph2, u);
    }

    @Override
    protected void dispose() {
        // delete the array of linked-list.
        for (int u = 0; u < adj.length; u++) {
            dispose(adj[u]);
        }
    }

    /**
     * Dispose a LIFO linked list headed by a node.
     *
     * @param node the top node of the list.
     */
    private void dispose(EdgeNode node) {
        if (node != null) {
            dispose(node.getNext());
        }
        node = null;
    }
}
