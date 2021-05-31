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
package com.ntc.vntok.graph.io;

import com.ntc.vntok.graph.AdjacencyListGraph;
import com.ntc.vntok.graph.AdjacencyListWeightedGraph;
import com.ntc.vntok.graph.AdjacencyMatrixGraph;
import com.ntc.vntok.graph.Edge;
import com.ntc.vntok.graph.IGraph;
import com.ntc.vntok.graph.IWeightedGraph;
import com.ntc.vntok.graph.util.GraphUtilities;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class GraphIO {

    /**
     * The graph is directed or not.
     */
    static final boolean DIRECTED = true;

    /**
     * Constructs a graph from an adjacency list provided by a reader.
     *
     * @param reader a reader
     * @see #scanAdjacencyList(String)
     * @return a graph
     */
    public static IGraph scanAdjacencyList(Reader reader) {
        IGraph graph = null;
        BufferedReader br = new BufferedReader(reader);
        try {
            // read the number of vertices of the graph
            // that is specified on the first line.
            int n = Integer.parseInt(br.readLine());
            // System.out.println(n);
            if (n > 0) {
                // create a graph with n vertices
                graph = new AdjacencyListGraph(n, DIRECTED);
            } else {
                System.err.println("The number of vertices of the graph must be positive.");
                System.exit(1);
            }
            // read edges of the graph
            String line = "";
            while ((line = br.readLine()) != null && line.trim().length() > 0) {
                String[] uv = line.split("\\s+");
                if (uv.length != 2) {
                    System.err.println("Bad format for data input stream!");
                    System.exit(1);
                }
                int u = Integer.parseInt(uv[0]);
                int v = Integer.parseInt(uv[1]);
                // insert a new edge to the graph
                graph.insert(new Edge(u, v));
            }
            // close the isr
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /**
     * Scans a graph from a text data file name. The data file is organised as follows:
     * <ul>
     * <li>The first line is the number of vertices of the graph</li>
     * <li>The other lines contains couples u v (separated by at least a blank char) that represent edges of the
     * graph.</li>
     * </ul>
     *
     * @param filename
     * @see #scanAdjacencyList(Reader)
     * @return an adjacency list graph.
     */
    public static IGraph scanAdjacencyList(String filename) {
        IGraph graph = null;
        try {
            InputStreamReader isr = new FileReader(filename);
            graph = scanAdjacencyList(isr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /**
     * Scans a graph from a reader.
     *
     * @see #scanAdjacencyMatrix(String)
     * @return an adjacency matrix graph.
     */
    public static IGraph scanAdjacencyMatrix(Reader reader) {
        IGraph graph = null;
        BufferedReader br = new BufferedReader(reader);
        try {
            // read the number of vertices of the graph
            // that is specified on the first line.
            int n = Integer.parseInt(br.readLine());
            // System.out.println(n);
            if (n > 0) {
                // create a graph with n vertices
                graph = new AdjacencyMatrixGraph(n, DIRECTED);
            } else {
                System.err.println("The number of vertices of the graph must be positive.");
                System.exit(1);
            }
            // read edges of the graph
            String line = "";
            int u = 0;
            while (u < n) {
                line = br.readLine();
                if (line == null || line.trim().length() == 0) {
                    System.err.println("The data is incomplete!");
                    System.exit(1);
                }
                String[] vArr = line.split("\\s+");
                if (vArr.length != n) {
                    System.out.println(vArr.length);
                    System.err.println("Bad format for data input stream!");
                    System.exit(1);
                }
                for (int v = 0; v < vArr.length; v++) {
                    int value = Integer.parseInt(vArr[v]);
                    // value > 0 or value = 0
                    if (value > 0) {
                        graph.insert(new Edge(u, v));
                    }
                }
                u++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /**
     * Scans a graph from a text data file name. The data file is organised as follows:
     * <ul>
     * <li>The first line is the number of vertices of the graph</li>
     * <li>The other lines contains a matrix representing the graph.
     * </ul>
     *
     * @param filename
     * @see #scanAdjacencyMatrix(Reader)
     * @return an adjacency list graph.
     */
    public static IGraph scanAdjacencyMatrix(String filename) {
        IGraph graph = null;
        try {
            InputStreamReader isr = new FileReader(filename);
            graph = scanAdjacencyMatrix(isr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /**
     * Print to the standard output all edges of the graph
     *
     * @param graph
     */
    public static void printEdges(IGraph graph) {
        Edge[] edges = null;
        if (graph instanceof IWeightedGraph) {
            edges = GraphUtilities.getWeightedEdges((IWeightedGraph) graph);
        } else {
            edges = GraphUtilities.getEdges(graph);
        }
        for (int i = 0; i < edges.length; i++) {
            Edge e = edges[i];
            double w = e.getWeight();
            System.out.println(e.getU() + " - " + e.getV() + " (" + w + ")");
        }
        // System.out.println("There are " + edges.length + " edges.");
    }

    /**
     * Print out a sparse graph to the standard output.
     *
     * @param graph a sparse graph
     */
    private static void printSparseGraph(IGraph graph) {
        printEdges(graph);
    }

    /**
     * Print out a dense graph to the standard output.
     *
     * @param graph
     */
    private static void printDenseGraph(IGraph graph) {
        int n = graph.getNumberOfVertices();
        System.out.print("\t");
        for (int u = 0; u < n; u++) {
            System.out.print("\t" + u);
        }
        System.out.println();
        System.out.print("\t");
        for (int u = 0; u < n; u++) {
            System.out.print("\t-");
        }
        System.out.println();
        for (int u = 0; u < n; u++) {
            System.out.print(u + "\t" + "|");
            for (int v = 0; v < n; v++) {
                int b = (graph.edge(u, v) ? 1 : 0);
                System.out.print("\t" + b);
            }
            System.out.println();
        }
    }

    /**
     * Print out a graph.
     *
     * @param graph
     */
    public static void print(IGraph graph) {
        int vC = graph.getNumberOfVertices();
        int eC = graph.getNumberOfEdges();
        System.out.println("There are " + vC + " vertices and " + eC
                + " edges.\n");
        if (graph instanceof AdjacencyListGraph
                || graph instanceof AdjacencyListWeightedGraph) {
            printSparseGraph(graph);
        } else {
            if (graph instanceof AdjacencyMatrixGraph) {
                printDenseGraph(graph);
            }
        }

        System.out.println();
    }

    /**
     * Scan a weighted graph from an input stream reader. This method is usually invoked by the method
     * {@link #scanAdjacencyListWeighted(String)}.
     *
     * @param inputStreamReader
     * @see #scanAdjacencyListWeighted(String)
     * @return an adjacency list weighted graph.
     */
    public static IWeightedGraph scanAdjacencyListWeighted(InputStreamReader inputStreamReader) {
        IWeightedGraph graph = null;
        BufferedReader br = new BufferedReader(inputStreamReader);
        try {
            // read the number of vertices of the graph
            // that is specified on the first line.
            int n = Integer.parseInt(br.readLine());
            // System.out.println(n);
            if (n > 0) {
                // create a graph with n vertices
                graph = new AdjacencyListWeightedGraph(n, DIRECTED);
            } else {
                System.err
                        .println("The number of vertices of the graph must > 0.");
                System.exit(1);
            }
            // read edges of the graph
            String line = "";
            while ((line = br.readLine()) != null && line.trim().length() > 0) {
                String[] uvw = line.split("\\s+");
                if (uvw.length != 3) {
                    System.err.println("Bad format for data input stream!");
                    System.exit(1);
                }
                int u = Integer.parseInt(uvw[0]);
                int v = Integer.parseInt(uvw[1]);
                double weight = Double.parseDouble(uvw[2]);
                // insert a new edge to the graph
                graph.insert(new Edge(u, v, weight));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    /**
     * Scan a graph from a text data file name. The data file is organised as follows:
     * <ul>
     * <li>The first line is the number of vertices of the graph</li>
     * <li>The other lines contains triples u v w (separated by at least a blank char) that represent edges (u,v) of the
     * graph and its weight (w).</li>
     * </ul>
     * See the samples directory for sample weighted graphs.
     *
     * @param filename
     * @return an adjacency list weighted graph.
     */
    public static IWeightedGraph scanAdjacencyListWeighted(String filename) {
        IWeightedGraph graph = null;
        try {
            InputStreamReader isr = new FileReader(filename);
            graph = scanAdjacencyListWeighted(isr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
