package org.platformlayer.enomapi;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openstack.utils.Io;
import org.platformlayer.http.SimpleHttpRequest;
import org.platformlayer.http.SimpleHttpRequest.SimpleHttpResponse;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class EnomApiClient {
	private static final Logger log = Logger.getLogger(EnomApiClient.class);

	final URI baseUri;
	final String username;
	final String password;

	public EnomApiClient(URI baseUri, String username, String password) {
		super();
		this.baseUri = baseUri;
		this.username = username;
		this.password = password;
	}

	CheckAvailableResponse doRequest(CheckAvailableRequest request) throws IOException {
		Map<String, String> response = doGet(request);

		return new CheckAvailableResponse(response);
	}

	Map<String, String> doGet(EnomApiRequest request) throws IOException {
		Map<String, String> parameters = Maps.newHashMap();
		request.buildParameters(parameters);
		parameters.put("uid", username);
		parameters.put("pw", password);

		StringBuilder queryString = new StringBuilder();
		for (Entry<String, String> parameter : parameters.entrySet()) {
			if (queryString.length() != 0) {
				queryString.append('&');
			}
			queryString.append(parameter.getKey());
			queryString.append('=');
			queryString.append(parameter.getValue());
		}
		URI uri = baseUri.resolve("/interface.asp?" + queryString.toString());

		SimpleHttpRequest httpRequest = SimpleHttpRequest.build("GET", uri);
		SimpleHttpResponse response = httpRequest.doRequest();

		Map<String, String> responseMap = parseResponseMap(response);
		return responseMap;
	}

	private Map<String, String> parseResponseMap(SimpleHttpResponse response) throws IOException {
		Map<String, String> responseMap = Maps.newHashMap();

		String message = Io.readAll(response.getInputStream());
		for (String line : Splitter.on('\n').split(message)) {
			line = line.trim();

			if (line.startsWith(";")) {
				continue;
			}

			int equalsIndex = line.indexOf('=');
			if (equalsIndex == -1) {
				log.warn("Could not parse response line: " + line);
				continue;
			}

			String key = line.substring(0, equalsIndex);
			String value = line.substring(equalsIndex + 1);

			responseMap.put(key, value);
		}

		return responseMap;
	}

}
