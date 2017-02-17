package ua.nure.parkhomenko.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;

/**
 * View settings command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class ViewSettingsCommand extends Command {

	private static final long serialVersionUID = 4550685633220092690L;

	private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		request.setAttribute("login", user.getLogin());
		request.setAttribute("password", user.getPassword());

		Object locales = request.getServletContext().getAttribute("locales");
		LOG.trace("Found in Servlet Context: locales --> " + locales);

		request.setAttribute("locales", locales);
		LOG.trace("Set the request attribute: locales --> " + locales);

		LOG.debug("Command finished");
		
		String error = request.getParameter("error");		
		if (error != null) {
			return Path.PAGE_SETTINGS + "?error=error";
		}
		
		return Path.PAGE_SETTINGS;
	}
}
