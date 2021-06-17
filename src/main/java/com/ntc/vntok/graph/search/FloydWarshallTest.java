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

import com.ntc.vntok.graph.IGraph;
import com.ntc.vntok.graph.IWeightedGraph;
import com.ntc.vntok.graph.io.GraphIO;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class FloydWarshallTest {

    /**
     * A sample GRAPH.TXT file
     */
    public static final String INPUT_FILE = "samples/weighted/GRAPH.TXT";

    /**
     * Create a test object given an input data file.
     *
     * @param inputFilename an input data file.
     */
    public FloydWarshallTest(String inputFilename) {
        // scan the graph from a text file
        IGraph graph = GraphIO.scanAdjacencyListWeighted(inputFilename);
        // print out the graph
        GraphIO.print(graph);
        // cast to a weighted graph if it is and do the trick
        if (graph instanceof IWeightedGraph) {
            IWeightedGraph weightedGraph = (IWeightedGraph) graph;
            // create a FW object
            FloydWarshall fw = new FloydWarshall(weightedGraph);
            // run the FW algorithm on the graph to get the cost matrix
            double[][] cost = fw.algorithmFloydWarshall();
            int n = weightedGraph.getNumberOfVertices();
            // print out the cost matrix
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(cost[i][j] + "\t");
                }
                System.out.println();
            }
        } else {
            System.out.println("You don't provide me a weighted graph!");
        }

    }

    public static void main(String[] args) {
        new FloydWarshallTest(INPUT_FILE);
    }
}
