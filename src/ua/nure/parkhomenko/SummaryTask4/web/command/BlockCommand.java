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
 * Block command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class BlockCommand extends Command {

	private static final long serialVersionUID = -4049891765283558312L;

	private static final Logger LOG = Logger.getLogger(BlockCommand.class);
	private static StringBuilder BLOCK_SUBSCRIBER_QUERY =  new StringBuilder("UPDATE account SET is_blocked_by_admin='BLOCKED' WHERE user_id IN ");

	public static StringBuilder getBlockSubscriberQuery() {
		return BLOCK_SUBSCRIBER_QUERY;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		String[] id = request.getParameterValues("block");
		LOG.trace("Request parameter: id to block --> " + id);

		DBManager manager = DBManager.getInstance();

		if (id != null) {
			BLOCK_SUBSCRIBER_QUERY.append("(");
			for (int i = 1; i <= id.length; i++) {
				if (i == id.length) {
					BLOCK_SUBSCRIBER_QUERY.append(id[i-1]);
					break;
				}
				BLOCK_SUBSCRIBER_QUERY.append(id[i-1] + ", ");
			}
			BLOCK_SUBSCRIBER_QUERY.append(")");
		}
		
		manager.blockSubscriber(BLOCK_SUBSCRIBER_QUERY.toString());
		
		LOG.info("Subscribers were blocked.");
		
		BLOCK_SUBSCRIBER_QUERY = new StringBuilder("UPDATE account SET is_blocked_by_admin='BLOCKED' WHERE user_id IN ");

		LOG.debug("Command finished");
		return Path.COMMAND_LIST_SUBSCRIBERS;
	}
}
