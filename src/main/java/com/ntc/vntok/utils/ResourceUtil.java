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

package com.ntc.vntok.utils;

import java.io.InputStream;

/**
 *
 * @author nghiatc
 * @since Apr 30, 2021
 */
public class ResourceUtil {
    public static ClassLoader loader = ResourceUtil.class.getClassLoader();
    
    public static InputStream getResourceAsStream(String name) {
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(name);
        } else {
            in = ClassLoader.getSystemResourceAsStream(name);
        }
        return in;
    }
}
