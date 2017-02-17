package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.Role;
import ua.nure.parkhomenko.SummaryTask4.db.Status;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Account;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.VerifyRecaptcha;

/**
 * Login command.
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String login = null;
		String password = null;
		boolean verifyRecaptcha = false;
		String forward = Path.PAGE_ERROR_PAGE;
		
		DBManager manager = DBManager.getInstance();

		Object loginFB = request.getAttribute("loginWithFB");
		if (loginFB != null) {
			login = (String) request.getAttribute("login");
			LOG.trace("Request parameter: loging --> " + login);
			
			password = (String) request.getAttribute("password");
			LOG.trace("Request parameter: password --> " + password);
			
			verifyRecaptcha = true;
		} else {
			login = request.getParameter("login");
			LOG.trace("Request parameter: loging --> " + login);

			password = request.getParameter("password");
			LOG.trace("Request parameter: password --> " + password);			

			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
			LOG.trace("Request parameter: captcha --> " + gRecaptchaResponse);
			
			verifyRecaptcha = VerifyRecaptcha.verify(gRecaptchaResponse);
			LOG.trace("Verify captcha --> " + verifyRecaptcha);
		}

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			LOG.error("AppException: Login/password cannot be empty");
			return Path.PAGE_LOGIN + "?error=error";
		}
		
		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword()) || !verifyRecaptcha) {
			LOG.error("errorMessage: Cannot find user with such login/password OR forgot to press the captcha");
			return Path.PAGE_LOGIN + "?error=error";
		} else {

			Role userRole = Role.getRole(user);
			LOG.trace("userRole --> " + userRole);

			if (userRole == Role.ADMIN) {
				forward = Path.COMMAND_LIST_SUBSCRIBERS;
			}

			if (userRole == Role.SUBSCRIBER) {

				Account account = manager.findUserAccount(user.getId());

				if ((account.getIsBlocked() == Status.FREE) & (account.getIsBlockedByAdmin() == Status.FREE)) {
					LOG.trace("Set the session attribute: block --> " + Status.FREE);
					session.setAttribute("block", Status.FREE);
				} else {
					LOG.trace("Set the session attribute: block --> " + Status.BLOCKED);
					session.setAttribute("block", Status.BLOCKED);
				}

				forward = Path.COMMAND_LIST_TARIFFS_SERVICES;
			}
			session.setAttribute("user", user);
			LOG.trace("Set the session attribute: user --> " + user);

			session.setAttribute("userRole", userRole);
			LOG.trace("Set the session attribute: userRole --> " + userRole);

			LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());
		}
		LOG.debug("Command finished");
		return forward;
	}
}