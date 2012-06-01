package org.platformlayer.enomapi;

import java.util.Map;

import org.apache.log4j.Logger;

public class EnomApiResponse {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(EnomApiResponse.class);

	private final Map<String, String> map;

	public EnomApiResponse(Map<String, String> responseMap) {
		this.map = responseMap;
	}

	public String find(String key) {
		return map.get(key);
	}
}
