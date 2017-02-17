package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * Add subscriber form command.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class AddSubscriberFormCommand extends Command {

	private static final long serialVersionUID = 6807789770716456903L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {	
		
		String error = request.getParameter("error");
		
		if (error != null) {
			return Path.PAGE_ADD_SUBSCRIBER + "?error=error";
		}
		
		return Path.PAGE_ADD_SUBSCRIBER;
	}
}
