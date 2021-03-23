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
package com.ntc.vntok.graph.search;

import com.ntc.vntok.graph.Edge;
import com.ntc.vntok.graph.IGraph;
import com.ntc.vntok.graph.IWeightedGraph;
import com.ntc.vntok.graph.util.EdgeIterator;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class FloydWarshall {

    /**
     * The weighted graph we operate on.
     */
    private IWeightedGraph graph;

    /**
     * The cost matrix.
     */
    private double[][] cost;

    /**
     * The number of vertices of the graph
     */
    private int n;

    /**
     * Copy constructor.
     *
     * @param graph a weighted graph
     */
    public FloydWarshall(IWeightedGraph graph) {
        // initialize the graph
        //
        this.graph = graph;
    }

    /**
     * Get the graph.
     *
     * @return the graph
     */
    public IGraph getGraph() {
        return graph;
    }

    /**
     * Initialize the cost matrix.
     */
    public void initialize() {
        // initialize the cost matrix 
        //
        // get the number of vertices of the graph
        n = graph.getNumberOfVertices();
        // create the cost matrix
        cost = new double[n][n];
        int i, j;
        // initialize the cost matrix
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                cost[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        // initialize elements on the diagonal of the matrix 
        //
        for (i = 0; i < n; i++) {
            cost[i][i] = 0;
        }

        // for each vertex i, we iterate through all the edges (i,j) that go out from i
        // and update the element (i,j) of the cost matrix 
        for (i = 0; i < n; i++) {
            EdgeIterator edgeIterator = graph.edgeIterator(i);
            while (edgeIterator.hasNext()) {
                Edge edge = edgeIterator.next();
                cost[i][edge.getV()] = edge.getWeight();
            }
        }
    }

    /**
     * Get the cost matrix.
     *
     * @return the cost matrix
     */
    public double[][] getCost() {
        return cost;
    }

    /**
     * Implementation of the Floyd-Warshall algorithm.
     *
     * @return the cost matrix.
     */
    public double[][] algorithmFloydWarshall() {
        // first, initialize the cost matrix:
        //
        initialize();
        // 
        int i, j, k;
        // then implement the FW algorithm.
        for (k = 0; k < n; k++) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k][j]);
                }
            }
        }
        // return the cost matrix.
        return cost;
    }
}
