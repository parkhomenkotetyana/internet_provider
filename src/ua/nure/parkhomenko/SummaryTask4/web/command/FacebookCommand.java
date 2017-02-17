package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.FacebookOAuth;

/**
 * Facebook command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class FacebookCommand extends Command {

	private static final long serialVersionUID = 1922062200562356439L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {		
		return FacebookOAuth.getLoginRedirectURL();
	}
}
