package org.platformlayer.enomapi;

import java.util.Map;

import org.apache.log4j.Logger;

public class CheckAvailableResponse extends EnomApiResponse {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(CheckAvailableResponse.class);

	public CheckAvailableResponse(Map<String, String> response) {
		super(response);
	}

	public boolean isAvailable(String domain) {
		for (int i = 1;; i++) {
			String key = "Domain" + i;
			String domainName = find(key);
			if (domainName == null) {
				break;
			}

			if (!domainName.equals(domain)) {
				continue;
			}

			String code = find("RRPCode" + i);

			if (code.equals("210")) {
				return true;
			} else if (code.equals("211")) {
				return false;
			} else {
				throw new IllegalStateException("Unknown response code: " + code);
			}
		}

		throw new IllegalArgumentException("Domain not found in response: " + domain);
	}
}
