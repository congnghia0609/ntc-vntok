/*
 * Copyright 2015 nghiatc.
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
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *
 * @author nghiatc
 * @since Sep 11, 2015
 */
public class JsonUtils {
	private final ObjectMapper objMapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public JsonUtils() {}

	public static JsonUtils Instance = new JsonUtils();

	public JsonUtils(Map<SerializationFeature, Boolean> config) {
		for(SerializationFeature feature : config.keySet()) {
			objMapper.configure(feature, config.get(feature));
		}
		objMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public <T> List<T> getList(String json){
		try {
			return objMapper.readValue(json, new TypeReference<List<T>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public <T> List<T> getList(InputStream inputStream){
		try {
			return objMapper.readValue(inputStream, new TypeReference<List<T>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, String> getMap(String json){
		try {
			return objMapper.readValue(json, new TypeReference<Map<String, String>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, String> getMap(InputStream inpuStream){
		try {
			return objMapper.readValue(inpuStream, new TypeReference<Map<String, String>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, String> getMap(byte[] data){
		try {
			return objMapper.readValue(data, new TypeReference<Map<String, String>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, Integer> getMapInteger(String json){
		try {
			return objMapper.readValue(json, new TypeReference<Map<String, Integer>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, Object> getMapObject(String json){
		try {
			return objMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, Object> getMapObject(InputStream inpuStream){
		try {
			return objMapper.readValue(inpuStream, new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Map<String, Object> getMapObject(byte[] data){
		try {
			return objMapper.readValue(data, new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public <T> T getObject(Class<T> type ,String json){
		try {
			return objMapper.readValue(json, type);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public <T> T getObject(Class<T> type ,byte[] data){
		try {
			return objMapper.readValue(data, type);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public <T> T getObject(String json, TypeReference<T> type){
		try {
			return objMapper.readValue(json, type);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public String toJson(Object obj){
		try {
			return objMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public byte[] toByteJson(Object obj){
		try {
			return objMapper.writeValueAsBytes(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}

