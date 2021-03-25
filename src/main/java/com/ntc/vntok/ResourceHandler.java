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

package com.ntc.vntok;

import java.util.ResourceBundle;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class ResourceHandler {
    /**
	 * Get a resource value
	 * @param key a key of resource
	 * @return value of resource
	 */
	public static String get(String key) {
		return resource.getString(key);
	}
	
	/**
	 * The ressource bundle of the package
	 */
	static final ResourceBundle resource = ResourceBundle.getBundle("vntok");
}
