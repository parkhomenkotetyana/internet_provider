package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.Role;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * List subscribers command.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class ListSubscribersCommand extends Command {

	private static final long serialVersionUID = 5697709917313599659L;

	private static final Logger LOG = Logger.getLogger(ListSubscribersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		// get subscribers list
		List<User> subscribers = DBManager.getInstance().findSubscribers(Role.SUBSCRIBER.ordinal());
		LOG.trace("Found in DB: subscribers --> " + subscribers);

		// put subscribers list to the request
		request.setAttribute("subscribers", subscribers);
		LOG.trace("Set the request attribute: subscribers --> " + subscribers);

		LOG.debug("Command finished");
		return Path.PAGE_LIST_SUBSCRIBERS;
	}

}
