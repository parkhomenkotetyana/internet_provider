package ua.nure.parkhomenko.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.nure.parkhomenko.SummaryTask4.db.DBManagerTest;
import ua.nure.parkhomenko.SummaryTask4.web.utils.FacebookOAuthTest;
import ua.nure.parkhomenko.SummaryTask4.web.utils.SendMailTest;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.ValidatorTest;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.VerifyRecaptchaTest;

@RunWith(Suite.class)
@SuiteClasses({ DatetimeTagTest.class, PathTest.class, DBManagerTest.class, FacebookOAuthTest.class, SendMailTest.class,
		ValidatorTest.class, VerifyRecaptchaTest.class })
public class AllTests {

}
