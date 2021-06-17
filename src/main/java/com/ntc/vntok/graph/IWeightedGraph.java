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

import com.ntc.vntok.graph.util.EdgeIterator;

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public interface IWeightedGraph extends IGraph {

    /**
     * Get the edge determined by two vertices.
     *
     * @param u vertex u
     * @param v vertex v
     * @return the edge or <tt>null</tt> if there does not a such edge.
     */
    public Edge getEdge(int u, int v);

    /**
     * Get an iterator of edges that go out from a vertex
     *
     * @param u a vertex
     * @return an iterator of edges.
     */
    public EdgeIterator edgeIterator(int u);
}
