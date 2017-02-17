package ua.nure.parkhomenko.SummaryTask4.web.utils.validation;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.nure.parkhomenko.SummaryTask4.exception.DBException;

public class ValidatorTest {

	static Validator validator;
	static Logger LOG = Logger.getLogger(ValidatorTest.class);

	@BeforeClass
	public static void validatorsTest() {
		validator = new Validator();		
	}

	@Test
	public void testValidate() {
		boolean expected = false;
		boolean actual = Validator.validate(new String[]{"Java", null});
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testValidate2() {
		boolean expected = true;
		boolean actual = Validator.validate(new String[]{"Java", "not null"});
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testAddSubscriberValidator() throws DBException {
		boolean expected = false;
		boolean actual = Validator.addSubscriberValidator("Petrov Petr Petrovich", "2022-01-01", "Kharkiv", "PP123", "petka", "123");
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testAddTariffValidator() {
		boolean expected = false;
		boolean actual = Validator.addTariffValidator("Tariff", "-200", "Super cool");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testAddTariffValidator2() {
		boolean expected = true;
		boolean actual = Validator.addTariffValidator("Tariff", "200", "Super cool");
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testEditTariffValidator() {
		boolean expected = false;
		boolean actual = Validator.editTariffValidator("Tariff", null);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testEditTariffValidator2() {
		boolean expected = true;
		boolean actual = Validator.editTariffValidator("Tariff", "parameter");
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateDOB() {
		boolean expected = false;
		boolean actual = Validator.validateDOB("3017-01-02");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testValidateDOB2() {
		boolean expected = true;
		boolean actual = Validator.validateDOB("2017-01-02");
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testAccountPayValidator() {
		boolean expected = false;
		boolean actual = Validator.accountPayValidator("-20", "1234567898765432", "123");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testAccountPayValidator2() {
		boolean expected = true;
		boolean actual = Validator.accountPayValidator("20", "1234567898765432", "123");
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testValidateSum() throws DBException {
		boolean expected = false;
		boolean actual = Validator.validateSum("-50");
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testValidateSum2() throws DBException {
		boolean expected = true;
		boolean actual = Validator.validateSum("50");		
		Assert.assertEquals(expected, actual);
	}

	@Test 
	public void testValidateEmail() {
		boolean expected = false;
		boolean actual = Validator.validateEmail("@mailru");
		Assert.assertEquals(expected, actual);
	}
	
	@Test 
	public void testValidateEmail2() {
		boolean expected = true;
		boolean actual = Validator.validateEmail("normal@mail.ru");
		Assert.assertEquals(expected, actual);
	}
}
