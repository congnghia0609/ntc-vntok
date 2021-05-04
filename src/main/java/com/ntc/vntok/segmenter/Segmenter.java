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
package com.ntc.vntok.segmenter;

import com.ntc.vntok.graph.AdjacencyListWeightedGraph;
import com.ntc.vntok.graph.Edge;
import com.ntc.vntok.graph.IGraph;
import com.ntc.vntok.graph.IWeightedGraph;
import com.ntc.vntok.graph.Node;
import com.ntc.vntok.graph.io.GraphIO;
import com.ntc.vntok.graph.search.ShortestPathFinder;
import com.ntc.vntok.graph.util.GraphConnectivity;
import com.ntc.vntok.utils.CaseConverter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class Segmenter {

    private Logger logger = LoggerFactory.getLogger(Segmenter.class);

    private static StringNormalizer normalizer;

    /**
     * The DFA representing Vietnamese lexicon (the internal lexicon).
     */
    private static AbstractLexiconRecognizer lexiconRecognizer;

    /**
     * The external lexicon recognizer.
     */
    private static AbstractLexiconRecognizer externalLexiconRecognizer;

    /**
     * Result of the segmentation. A segmentation can have several results. Each result is represented by an array of
     * words.
     */
    private final List<String[]> result = new ArrayList<>();

    /**
     * An ambiguity resolver.
     */
    private AbstractResolver resolver = null;

    private static double MAX_EDGE_WEIGHT = 100;

    private static boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public Segmenter() {
        // create DFA lexicon recognizer
        getDFALexiconRecognizer();
        // create external lexicon recognizer
        getExternalLexiconRecognizer();
        // create a string normalizer
        normalizer = StringNormalizer.getInstance();
    }

    /**
     * Build a segmenter with an ambiguity resolver.
     *
     * @param resolver
     */
    public Segmenter(AbstractResolver resolver) {
        this();
        this.resolver = resolver;
    }

    /**
     * Build a segmenter with a properties object and an ambiguity resolver.
     *
     * @param properties
     * @param resolver
     * @throws java.io.FileNotFoundException
     */
    public Segmenter(Properties properties, AbstractResolver resolver) throws FileNotFoundException {
        // create DFA lexicon recognizer
        getDFALexiconRecognizer(properties);
        // create external lexicon recognizer
        getExternalLexiconRecognizer(properties);
        // create a string normalizer
        normalizer = StringNormalizer.getInstance(properties);
        this.resolver = resolver;
    }

    /**
     * @return The result list. Each element of the list is a possible segmentation. The list is normally contains less
     * than 4 results.
     */
    public List<String[]> getResult() {
        return result;
    }

    /**
     * A pre-processing of segmentation. If the first character of the phrase is an uppercase, then it is converted to
     * the corresponding lowercase; all syllables are assured to have correct accents. This method is called before
     * method {@link #segment(String)}
     *
     * @param phrase a phrase to segment
     * @return a phrase after pre-process
     */
    private static String normalize(String phrase) {
        // 1. change the case of the first character.
        StringBuffer s = new StringBuffer(phrase);
        char firstChar = s.charAt(0);
        char lowerChar = firstChar;
        // convert first character
        if ('A' <= firstChar && firstChar <= 'Z') {
            lowerChar = Character.toLowerCase(firstChar);
        } else if (CaseConverter.isValidUpper(firstChar)) {
            lowerChar = CaseConverter.toLower(firstChar);
        }
        s.setCharAt(0, lowerChar);
        // 2. normalize the accents of the phrase
        return normalizer.normalize(s.toString());
    }

    /**
     * @param syllables an array of syllables (a phrase)
     * @return a weighted digraph representing the phrase to be segmented. The maximum weight of edges is 1.
     */
    private IWeightedGraph makeGraph(String[] syllables) {
        int nV = syllables.length + 1;
        IWeightedGraph graph = new AdjacencyListWeightedGraph(nV, true);
        for (int i = 0; i < nV - 1; i++) {
            String word = "";
            int j = 0;
            while (j < nV - 1 - i) {
                // take the word syllables[i]..syllables[i+j]
                if (word.length() == 0) {
                    word = syllables[i];
                } else {
                    //word = word + vn.hus.nlp.fsm.IConstants.BLANK_CHARACTER + syllables[i + j];
                    word = word + ' ' + syllables[i + j];
                }
                // check to see if the word is accepted or not
                // and create corresponding edges
                if (getDFALexiconRecognizer().accept(word) || getExternalLexiconRecognizer().accept(word)) {
                    // calculate the weight of the edge (i,i+j+1)
                    double weight = (double) 1 / (j + 1);
                    // keep only two decimal digits of weight 
                    weight = Math.floor(weight * 100);
                    // insert an edge with an appropriate weight
                    graph.insert(new Edge(i, i + j + 1, weight));
                }
                j++;

            }
        }
        return graph;
    }

    /**
     * Creates an internal lexicon recognizer.
     *
     * @return the DFA lexicon recognizer in use
     */
    private AbstractLexiconRecognizer getDFALexiconRecognizer() {
        if (lexiconRecognizer == null) {
            // use the DFA lexicon recognizer
            // user can use any lexicon recognizer here.
            lexiconRecognizer = DFALexiconRecognizer.getInstance();
        }
        return lexiconRecognizer;
    }

    /**
     * Creates an internal lexicon recognizer.
     *
     * @return the DFA lexicon recognizer in use
     */
    private AbstractLexiconRecognizer getDFALexiconRecognizer(Properties properties) throws FileNotFoundException {
        if (lexiconRecognizer == null) {
            // use the DFA lexicon recognizer
            // user can use any lexicon recognizer here.
            lexiconRecognizer = DFALexiconRecognizer.getInstance(properties.getProperty("lexiconDFA"));
        }
        return lexiconRecognizer;
    }

    /**
     * Creates an external lexicon recognizer.
     *
     * @return the external lexicon recognizer
     */
    private AbstractLexiconRecognizer getExternalLexiconRecognizer() {
        if (externalLexiconRecognizer == null) {
            externalLexiconRecognizer = new ExternalLexiconRecognizer();
        }
        return externalLexiconRecognizer;
    }

    /**
     * Creates an external lexicon recognizer.
     *
     * @param properties
     * @return the external lexicon recognizer
     */
    private AbstractLexiconRecognizer getExternalLexiconRecognizer(Properties properties) throws FileNotFoundException {
        if (externalLexiconRecognizer == null) {
            externalLexiconRecognizer = new ExternalLexiconRecognizer(properties.getProperty("externalLexicon"));
        }
        return externalLexiconRecognizer;
    }

    /**
     * Try to connect an unconnected graph. If a graph is unconnected, we find all of its isolated vertices and add a
     * "fake" transition to them. A vertex is called isolated if it has not any intransition.
     *
     * @param graph a graph
     */
    private void connect(IGraph graph) {
        // no need to connect the graph if it's connected.
        if (GraphConnectivity.countComponents(graph) == 1) {
            return;
        }

        // get all isolated vertices - vertices that do not have any intransitions. 
        int[] isolatedVertices = GraphConnectivity.getIsolatedVertices(graph);
        // info for debug
        if (DEBUG) {
            System.err.println("The graph for the phrase is: ");
            GraphIO.print(graph);
            System.out.println("Isolated vertices: ");
            for (int i : isolatedVertices) {
                System.out.println(i);
            }
        }

        // There is a trick here: vertex 0 is always isolated in our linear graph since 
        // it is the initial vertex and does not have any intransition.
        // We need to check whether it has an outtransition or not (its degree is not zero),
        // if no, we connect it to the nearest vertex - vertex 1 - to get an edge with weight 1.0;
        // if yes, we do nothing. Note that since the graph represents an array of non-null syllables,
        // so the number of vertices of the graph is at least 2 and it does contain vertex 1.
        boolean zeroVertex = false;
        for (int i = 0; i < isolatedVertices.length; i++) {
            int u = isolatedVertices[i];
            if (u == 0) {
                zeroVertex = true;
                /*
				GraphDegree graphDegree = new GraphDegree(graph);
				if (graphDegree.degree(0) == 0) {
					graph.insert(new Edge(0,1,MAX_EDGE_WEIGHT));
				}
                 */
                // we always add a new edge (0,1) regardless of vertex 0 is 
                // of degree 0 or higher.
                graph.insert(new Edge(0, 1, MAX_EDGE_WEIGHT));
            } else {
                if (u != 1) {
                    // u is an internal isolated vertex, u > 0. We simply add an edge (u-1,u) 
                    // also with the maximum weight 1.0
                    graph.insert(new Edge(u - 1, u, MAX_EDGE_WEIGHT));
                } else { // u == 1
                    if (!zeroVertex) { // insert edge (0,1) only when there does not this edge
                        graph.insert(new Edge(u - 1, u, MAX_EDGE_WEIGHT));
                    }
                }
            }
        }
        // make sure that the graph is now connected:
        if (GraphConnectivity.countComponents(graph) != 1) {
            logger.info("Hmm, fail to connect the graph!");
        }
    }

    /**
     * Prepare to segment a phrase.
     *
     * @param phrase a phrase to be segmented.
     * @see #segment(String)
     * @return an array of syllables of the phrase
     */
    private String[] prepare(String phrase) {
        // clear the last result
        result.clear();
        // normalize the phrase
        phrase = Segmenter.normalize(phrase);
        // get syllables of the phrase
        String[] syllables = phrase.split("\\s+");
        return syllables;
    }

    /**
     * Build a segmentation of a phrase given a path from vertex 0 to the end vertex. The path must begin with vertex 0.
     *
     * @param syllables an array of syllables
     * @param path a path, that is an array of vertices
     * @return a segmentation.
     * @see #segment(String)
     */
    private String[] buildSegmentation(String[] syllables, int[] path) {
        String[] segmentation = new String[path.length - 1];
        int vertex = 0;
        int ii = 0;
        for (int k = 1; k < path.length; k++) {
            int nextVertex = path[k];
            String word = "";
            for (int j = vertex; j < nextVertex; j++) {
                //word += (syllables[j] + vn.hus.nlp.fsm.IConstants.BLANK_CHARACTER);
                word += (syllables[j] + ' ');
            }
            word = word.trim();
            segmentation[ii++] = word;
            vertex = nextVertex;
        }
        return segmentation;
    }

    /**
     * Segment a phrase.
     *
     * @see #normalize(String)
     * @param phrase
     * @return a list of possible segmentations.
     */
    public List<String[]> segment(String phrase) {
        // save the original phrase before normalizing it
        // objective is not to change the original words of the phrase in the 
        // result segmentations.
        String[] original = phrase.split("\\p{Space}+");
        // get syllables of the phrase
        String[] syllables = prepare(phrase);
        // create a weighted linear graph of the phrase
        IWeightedGraph graph = makeGraph(syllables);
        // get the end vertex of the linear graph
        int nV = graph.getNumberOfVertices();
        // test the connectivity between the start vertex and the end vertex of
        // the graph.
        // try to connect it if it is not connected and log the abnormal phrase out 
        if (!GraphConnectivity.isConnected(graph, 0, nV - 1)) {
//			logger.log(Level.INFO, phrase);
//			logger.log(Level.INFO, "The graph of this phrase is not connected. Try to connect it.");
            connect(graph);
        }
        // get all shortest paths from vertex 0 to the end vertex
        ShortestPathFinder pathFinder = new ShortestPathFinder(graph);
        Node[] allShortestPaths = pathFinder.getAllShortestPaths(nV - 1);
//		System.out.println("There are " + allShortestPaths.length + " segmentation(s) for the phrase."); // DEBUG
        // build segmentations corresponding to the shortest paths
        for (int i = 0; i < allShortestPaths.length; i++) {
            Node path = allShortestPaths[i];
            int[] a = path.toArray();
            // get the result on the original
            String[] segmentation = buildSegmentation(original, a);
            result.add(segmentation);
        }
        return result;
    }

    /**
     * @param segmentations a list of possible segmentations.
     * @return the most probable segmentation
     */
    public String[] resolveAmbiguity(List<String[]> segmentations) {
        return resolver.resolve(segmentations);
    }

    /**
     * Print the result of the segmentation.
     */
    public void printResult() {
        for (String[] segmentation : result) {
            for (String segmentation1 : segmentation) {
                System.out.print("[" + segmentation1 + "] ");
            }
            System.out.println();
        }
    }

    /**
     * Dispose the segmenter to save space.
     */
    public void dispose() {
        result.clear();
        lexiconRecognizer.dispose();
        externalLexiconRecognizer.dispose();
    }
}
