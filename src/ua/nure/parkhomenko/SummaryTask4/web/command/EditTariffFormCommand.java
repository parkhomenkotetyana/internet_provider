package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.ServicesTariffs;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * Edit tariff command. Returns page to edit tariff with information
 * about selected tariff. 
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class EditTariffFormCommand extends Command {

	private static final long serialVersionUID = 9150306980221219936L;
	
	private static final Logger LOG = Logger.getLogger(EditTariffFormCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		DBManager manager = DBManager.getInstance();
		
		String itemId = request.getParameter("editItemId");
		LOG.trace("Request parameter: editItemId --> " + itemId);
		
		if (itemId == null) {
			return Path.COMMAND_LIST_TARIFFS_SERVICES;
		}
		
		int editItemId = Integer.parseInt(itemId);

		ServicesTariffs serviceTariff;

		serviceTariff = manager.findServiceTariffById(editItemId);

		LOG.trace("Found in DB: serviceTariff --> " + serviceTariff);
		
		String tariffName = serviceTariff.getTariff().getName();
		double price = serviceTariff.getPrice();
		String description = serviceTariff.getDescription();

		request.setAttribute("id", editItemId);
		LOG.trace("Set the request attribute: id --> " + editItemId);
		request.setAttribute("tariffName", tariffName);
		LOG.trace("Set the request attribute: tariffName --> " + tariffName);
		request.setAttribute("price", price);
		LOG.trace("Set the request attribute: price --> " + price);
		request.setAttribute("description", description);
		LOG.trace("Set the request attribute: description --> " + description);

		LOG.debug("Command finished");
		return Path.PAGE_EDITING_TARIFF;
	}
}
