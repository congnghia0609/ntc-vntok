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

/**
 *
 * @author nghiatc
 * @since Mar 23, 2021
 */
public interface VertexIterator {

    /**
     * Get the next vertex of the iteration. Note that this method must be called after checking with
     * {@link #hasNext()}.
     *
     * @return <code>-1</code> or the next vertex index.
     */
    public int next();

    /**
     * Returns <tt>true</tt> if the iteration has more vertices.
     *
     * @return <code>true/false</code>
     */
    public boolean hasNext();
}
