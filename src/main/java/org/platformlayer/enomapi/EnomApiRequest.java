package org.platformlayer.enomapi;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;

public class EnomApiRequest {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(EnomApiRequest.class);

	protected Map<String, String> parameters = Maps.newHashMap();

	public void buildParameters(Map<String, String> parameters) {
		parameters.putAll(this.parameters);
	}
}
