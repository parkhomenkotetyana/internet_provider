package ua.nure.parkhomenko.SummaryTask4.web.utils;

import org.junit.Assert;
import org.junit.Test;

public class FacebookOAuthTest {

	@Test
	public void testGetAPIKey() {
		Assert.assertNotNull(FacebookOAuth.getAPIKey());
	}

	@Test
	public void testGetSecret() {
		Assert.assertNotNull(FacebookOAuth.getSecret());
	}

	@Test
	public void testGetLoginRedirectURL() {
		Assert.assertNotNull(FacebookOAuth.getLoginRedirectURL());
	}

	@Test
	public void testGetAuthURL() {
		Assert.assertNotNull(FacebookOAuth.getAuthURL("code"));
	}

}
