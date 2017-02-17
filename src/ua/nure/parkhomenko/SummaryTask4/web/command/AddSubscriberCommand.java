package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.Role;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.Validator;

/**
 * Add subscriber command.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class AddSubscriberCommand extends Command {

	private static final long serialVersionUID = -5402965005545980299L;

	private static final Logger LOG = Logger.getLogger(AddSubscriberCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		DBManager manager = DBManager.getInstance();

		String fullName = request.getParameter("fullName");
		LOG.trace("Request parameter: fullName --> " + fullName);

		String dob = request.getParameter("dob");
		LOG.trace("Request parameter: dob --> " + dob);

		Date dateOfBirth = null;
		String address = request.getParameter("address");
		LOG.trace("Request parameter: address --> " + address);

		String passport = request.getParameter("passport");
		LOG.trace("Request parameter: passport --> " + passport);

		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);

		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> " + password);

		String forward = Path.PAGE_ERROR_PAGE;

		boolean valid = Validator.addSubscriberValidator(fullName, dob, address, passport, login, password);

		if (valid) {
			try {
				java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
				dateOfBirth = new Date(date.getTime());
			} catch (ParseException e) {
				LOG.error("Invalid date format");
				new AppException("Invalid date format");
			}

			User subsrciber = new User(fullName, dateOfBirth, address, passport, login, password,
					Role.SUBSCRIBER.ordinal());

			manager.addSubscriber(subsrciber);
			LOG.trace("Subscriber is added." + subsrciber);
			forward = Path.COMMAND_LIST_SUBSCRIBERS;

		} else {
			LOG.error("Not all fields are properly filled");
			return Path.COMMAND_ADD_SUBSCRIBER_FORM + "&error=error";
		}

		LOG.debug("Command finished");
		return forward;
	}
}
