package org.platformlayer.enomapi;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class EnomApiClientTest {

	public EnomApiClient buildClient() {
		String uri = System.getProperties().getProperty("enom.url");
		String username = System.getProperties().getProperty("enom.username");
		String password = System.getProperties().getProperty("enom.password");

		URI baseUri = URI.create(uri);
		return new EnomApiClient(baseUri, username, password);
	}

	@Test
	public void test() throws IOException {
		EnomApiClient client = buildClient();

		CheckAvailableRequest request = new CheckAvailableRequest();

		String availableDomain = "random12347987389789345.com";
		String unavailableDomain = "enom.com";

		List<String> domains = Lists.newArrayList();
		domains.add(availableDomain);
		domains.add(unavailableDomain);

		request.setDomainList(domains);

		CheckAvailableResponse response = client.doRequest(request);

		Assert.assertEquals(response.isAvailable(availableDomain), true);
		Assert.assertEquals(response.isAvailable(unavailableDomain), false);

		try {
			response.isAvailable("notchecked");
			Assert.fail("Expected exception");
		} catch (IllegalArgumentException e) {
			// Expected
		}
	}
}
