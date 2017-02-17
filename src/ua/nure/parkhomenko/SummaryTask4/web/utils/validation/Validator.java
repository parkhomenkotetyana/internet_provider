package ua.nure.parkhomenko.SummaryTask4.web.utils.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.exception.DBException;

/**
 * Class with methods for input data validation.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class Validator {

	private static final String DATE_PATTERN = "\\d{4}\\-\\d{2}\\-\\d{2}";
	private static final String POSITIVE_NUMBER_PATTERN = "^\\d+\\.?\\d*?$";
	private static final String CARD_NUMBER_PATTERN = "\\d{16}";
	private static final String EMAIL_PATTERN = "^\\w+@[a-z]+\\.[a-z]+$";

	public static boolean validate(String... values) {

		if (checkNull(values)) {
			return false;
		}

		return true;
	}

	public static boolean addSubscriberValidator(String fullName, String dob, String address, String passport,
			String login, String password) throws DBException {

		if (checkNull(fullName, dob, address, passport, login, password)) {
			return false;
		}

		if (!validateDOB(dob)) {
			return false;
		}

		if (!validateLoginAndPassword(login, password)) {
			return false;
		}

		return true;
	}

	public static boolean addTariffValidator(String name, String price, String description) {

		if (checkNull(name, price, description)) {
			return false;
		}

		if (!price.matches(POSITIVE_NUMBER_PATTERN)) {
			return false;
		}

		return true;
	}

	public static boolean editTariffValidator(String... values) {

		if (checkNull(values)) {
			return false;
		}

		return true;
	}

	private static boolean checkNull(String... values) {
		if (values == null) {
			return true;
		} else {
			for (String value : values) {
				if (value == null) {
					return true;

				}
			}
			return false;
		}
	}

	public static boolean validateDOB(String dob) {

		Date dateOfBirth = null;

		if (!dob.matches(DATE_PATTERN)) {
			return false;
		}

		try {
			java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
			dateOfBirth = new Date(date.getTime());
		} catch (ParseException e) {
			return false;
		}

		if (dateOfBirth.after(new java.util.Date())) {
			return false;
		}

		return true;
	}

	public static boolean validateLoginAndPassword(String login, String password) throws DBException {

		if (!validateLogin(login)) {
			return false;
		}

		if (login.length() <= 3 || password.length() <= 3) {
			return false;
		}

		return true;
	}

	public static boolean validateLogin(String login) throws DBException {
		DBManager manager = DBManager.getInstance();

		boolean isExist = manager.checkLogin(login);

		if (isExist) {
			return false;
		}

		return true;
	}
	
	public static boolean accountPayValidator(String sum, String cardNumber, String code) {
		
		if (checkNull(sum, cardNumber, code)) {
			return false;
		}
		
		if (!validateSum(sum)) {
			return false;
		}
		
		if(!validateCardNumber(cardNumber)) {
			return false;
		}
		
		if(!validateCode(code)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validateSum(String sum) {
		
		if (!sum.matches(POSITIVE_NUMBER_PATTERN)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean validateCardNumber(String cardNumber) {
		
		if (!cardNumber.matches(CARD_NUMBER_PATTERN)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean validateCode(String code) {
		
		if (!code.matches(POSITIVE_NUMBER_PATTERN)) {
			return false;
		}		
		return true;
	}	
	
	public static boolean validateEmail(String login) {
		
    	if (!login.matches(EMAIL_PATTERN)) {
    		return false;
    	}
        	
    	return true;
	}	
}
