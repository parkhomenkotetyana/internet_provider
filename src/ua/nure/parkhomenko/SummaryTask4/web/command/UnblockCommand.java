package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * Unblock command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class UnblockCommand extends Command {

	private static final long serialVersionUID = -235751242324546264L;

	private static final Logger LOG = Logger.getLogger(UnblockCommand.class);
	
	private static StringBuilder UNBLOCK_SUBSCRIBER_QUERY =  new StringBuilder("UPDATE account SET is_blocked_by_admin='FREE' WHERE user_id IN ");

	public static StringBuilder getUnblockSubscriberQuery() {
		return UNBLOCK_SUBSCRIBER_QUERY;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		String[] id = request.getParameterValues("unblock");
		LOG.trace("Request parameter: unblock --> " + id);

		DBManager manager = DBManager.getInstance();
		if (id != null) {
			UNBLOCK_SUBSCRIBER_QUERY.append("(");
			for (int i = 1; i <= id.length; i++) {
				if (i == id.length) {
					UNBLOCK_SUBSCRIBER_QUERY.append(id[i-1]);
					break;
				}
				UNBLOCK_SUBSCRIBER_QUERY.append(id[i-1] + ", ");
			}
			UNBLOCK_SUBSCRIBER_QUERY.append(")");
		}
		
		manager.unblockSubscriber(UNBLOCK_SUBSCRIBER_QUERY.toString());
		
		LOG.info("Subscribers were unblocked.");
		
		UNBLOCK_SUBSCRIBER_QUERY = new StringBuilder("UPDATE account SET is_blocked_by_admin='FREE' WHERE user_id IN ");

		LOG.debug("Command finished");
		return Path.COMMAND_LIST_SUBSCRIBERS;
	}
}
