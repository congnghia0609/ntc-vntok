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
public abstract class Graph implements IGraph {

    /**
     * The graph is directed or undirected.
     */
    protected boolean directed;
    /**
     * Number of vertices.
     */
    protected int cV;

    /**
     * Number of edges.
     */
    protected int cE;

    /**
     * Constructor.
     *
     * @param n number of vertices of the graph.
     * @param directed <code>true/false</code>
     */
    public Graph(int n, boolean directed) {
        this.directed = directed;
        cV = n;
        cE = 0;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#edge(int, int)
     */
    @Override
    public abstract boolean edge(int u, int v);

    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#getNumberOfEdges()
     */
    @Override
    public int getNumberOfEdges() {
        return cE;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#getNumberOfVertices()
     */
    @Override
    public int getNumberOfVertices() {
        return cV;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#insert(vn.hus.graph.Edge)
     */
    @Override
    public abstract void insert(Edge edge);


    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#isDirected()
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#iterator(int)
     */
    @Override
    public abstract VertexIterator vertexIterator(int u);

    /*
	 * (non-Javadoc)
	 * 
	 * @see vn.hus.graph.IGraph#remove(vn.hus.graph.Edge)
     */
    @Override
    public abstract void remove(Edge edge);

    /**
     * Dispose the graph.
     */
    protected abstract void dispose();
}
