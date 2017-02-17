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
 * Delete tariff command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class DeleteTariffCommand extends Command {

	private static final long serialVersionUID = 1242864318623663175L;
	
	private static final Logger LOG = Logger.getLogger(DeleteTariffCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		DBManager manager = DBManager.getInstance();

		String[] itemId = request.getParameterValues("itemId");
		LOG.trace("Request parameter: itemId --> " + itemId);
		
		if (itemId != null) {
			for (String id : itemId) {
				manager.deleteTariff(Integer.parseInt(id));
			}
		}
		LOG.info("Tariffs were deleted.");
		
		LOG.debug("Command finished");
		return Path.COMMAND_LIST_TARIFFS_SERVICES + "&type=delete";
	}
}
