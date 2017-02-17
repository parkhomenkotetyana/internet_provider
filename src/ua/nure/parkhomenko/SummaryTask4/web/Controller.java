package ua.nure.parkhomenko.SummaryTask4.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.command.Command;
import ua.nure.parkhomenko.SummaryTask4.web.command.CommandContainer;

/**
 * Main servlet controller.
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 2423353715955164816L;

	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response, ActionType.GET);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response, ActionType.POST);
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response, ActionType actionType) throws IOException, ServletException {
		
		LOG.debug("Controller starts");

		// extract command name from the request
		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);
		

		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);

		// execute command and get forward address
		String forward = Path.PAGE_ERROR_PAGE;
		try {
			forward = command.execute(request, response);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}

		if (forward != null) {
			if (actionType == ActionType.GET) {
				LOG.trace("Forward to address --> " + forward);
				LOG.debug("Controller finished, now go to forward address --> " + forward);
				request.getRequestDispatcher(forward).forward(request, response);
			} else if (actionType == ActionType.POST) {
				LOG.trace("Redirect to address --> " + forward);
				LOG.debug("Controller finished, now go to redirect address --> " + forward);
				response.sendRedirect(forward);
			}			
		}
	}
}
