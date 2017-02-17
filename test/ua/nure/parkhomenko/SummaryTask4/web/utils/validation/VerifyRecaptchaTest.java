package ua.nure.parkhomenko.SummaryTask4.web.utils.validation;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class VerifyRecaptchaTest {

	@Test
	public void testVerify() throws IOException {
		boolean expected = false;
		boolean actual = VerifyRecaptcha.verify("");
		Assert.assertEquals(expected, actual);
	}
}
 