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

import com.ntc.vntok.graph.util.AdjacencyListVertexIterator;
import com.ntc.vntok.graph.util.VertexIterator;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class AdjacencyListGraph extends Graph {

    private final Node adj[];

    /**
     * Constructor.
     *
     * @param n number of vertices of the graph.
     * @param directed <code>true/false</code>
     */
    public AdjacencyListGraph(int n, boolean directed) {
        super(n, directed);
        adj = new Node[n];
    }

    /* (non-Javadoc)
	 * @see vn.hus.graph.Graph#edge(int, int)
     */
    @Override
    public boolean edge(int u, int v) {
        VertexIterator iterator = vertexIterator(u);
        while (iterator.hasNext()) {
            int w = iterator.next();
            if (v == w) {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
	 * @see vn.hus.graph.Graph#iterator(int)
     */
    @Override
    public VertexIterator vertexIterator(int u) {
        return new AdjacencyListVertexIterator(this, u);
    }

    /* (non-Javadoc)
	 * @see vn.hus.graph.Graph#insert(vn.hus.graph.Edge)
     */
    @Override
    public void insert(Edge edge) {
        int u = edge.getU();
        int v = edge.getV();
        // add the edge (u,v)
        adj[u] = new Node(v, adj[u]);
        // add the edge (v,u) if the graph
        // is not directed
        if (!directed) {
            adj[v] = new Node(u, adj[v]);
        }
        // increase the number of edges
        cE++;
    }

    /* (non-Javadoc)
	 * @see vn.hus.graph.IGraph#remove(vn.hus.graph.Edge)
     */
    @Override
    public void remove(Edge edge) {
        //TODO
    }

    /**
     * Get the adjacency list.
     *
     * @return the adjacency list.
     */
    public Node[] getAdj() {
        return adj;
    }

    /* (non-Javadoc)
	 * @see vn.hus.graph.Graph#dispose()
     */
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
    private void dispose(Node node) {
        if (node != null) {
            dispose(node.getNext());
        }
        node = null;
    }
}
