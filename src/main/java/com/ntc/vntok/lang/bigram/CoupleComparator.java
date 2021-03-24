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
package com.ntc.vntok.lang.bigram;

import java.util.Comparator;

/**
 *
 * @author nghiatc
 * @since Mar 24, 2021
 */
public class CoupleComparator implements Comparator<Couple> {

    @Override
    public int compare(Couple c1, Couple c2) {
        return (c1.getFirst() + c1.getSecond()).compareTo(c2.getFirst() + c2.getSecond());
    }
}
