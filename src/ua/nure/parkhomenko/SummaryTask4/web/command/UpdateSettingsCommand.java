package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.Validator;

/**
 * Update settings command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class UpdateSettingsCommand extends Command {

	private static final long serialVersionUID = 5087006202650006210L;
	
	private static final Logger LOG = Logger.getLogger(UpdateSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		 
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		
		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> " + password);
		
		String language = request.getParameter("language");
		LOG.trace("Request parameter: language --> " + language);
		
		//if user change only language or nothing
		if (login.equals(user.getLogin()) & password.equals(user.getPassword())) {			
			session.setAttribute("language", language);	
			LOG.trace("Set the session attribute: language --> " + language);
			return Path.COMMAND_VIEW_SETTINGS;			
		}
		
		if (login.equals(user.getLogin())) {
			if (password == null || password.isEmpty() || password.length() <= 3) {
				LOG.error("Invalid password");
				return Path.COMMAND_VIEW_SETTINGS;
			}
		} else {
			
			boolean valid = Validator.validateLoginAndPassword(login, password);

			if (!valid) {				
				LOG.error("Invalid data.");
				return Path.COMMAND_VIEW_SETTINGS + "&error=error";
			}			
		}
		
		DBManager manager = DBManager.getInstance();		
		
		user.setLogin(login);
		user.setPassword(password);
		manager.updateUserLoginAndPassword(user);		
		LOG.info("Login and password were updated.");

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);
		
		session.setAttribute("language", language);
		LOG.trace("Set the session attribute: language --> " + language);
		
		LOG.debug("Command finished");
		return Path.COMMAND_VIEW_SETTINGS;
	}
}
