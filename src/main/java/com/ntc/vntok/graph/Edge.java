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

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public class Edge {

    private final int u;
    private final int v;
    private double weight;

    /**
     * Constructor.
     *
     * @param u vertex u
     * @param v vertex v
     */
    public Edge(int u, int v) {
        this.u = u;
        this.v = v;
        this.weight = 0;
    }

    /**
     * Constructor
     *
     * @param u vertex u
     * @param v vertex v
     * @param weight Edge weight
     */
    public Edge(int u, int v, double weight) {
        this(u, v);
        this.weight = weight;
    }

    /**
     * Get the source vertex.
     *
     * @return source vertex
     */
    public int getU() {
        return u;
    }

    /**
     * Get the target vertex.
     *
     * @return the target vertex.
     */
    public int getV() {
        return v;
    }

    /**
     * Get the weight of the edge.
     *
     * @return the weight.
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge e = (Edge) obj;
            return (u == e.getU() && v == e.getV());
        }
        return false;
    }

    @Override
    public String toString() {
        return getU() + " - " + getV() + ": " + getWeight();
    }

    public int compareTo(Edge o) {
        double diff = getWeight() - o.getWeight();
        if (diff == 0) {
            return 0;
        } else if (diff > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
