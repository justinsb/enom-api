package org.platformlayer.enomapi;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

public class CheckAvailableRequest extends EnomApiRequest {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(CheckAvailableRequest.class);

	public CheckAvailableRequest() {
		parameters.put("command", "check");
	}

	public void setDomainList(List<String> domains) {
		parameters.put("domainList", Joiner.on(',').join(domains));
	}
}
