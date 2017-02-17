package ua.nure.parkhomenko.SummaryTask4.exception;

/**
 * An exception that provides information on a database access error.
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public class DBException extends AppException {

	private static final long serialVersionUID = -3550446897536410392L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}