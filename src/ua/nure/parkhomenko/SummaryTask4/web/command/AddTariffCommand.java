package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.Validator;

/**
 * Add tariff command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class AddTariffCommand extends Command {

	private static final long serialVersionUID = -5413030268195775343L;
	
	private static final Logger LOG = Logger.getLogger(AddTariffCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();

		String name = request.getParameter("name");
		LOG.trace("Request parameter: name --> " + name);
		
		String serviceType = request.getParameter("serviceType");
		LOG.trace("Request parameter: serviceType --> " + serviceType);
		
		String price = request.getParameter("price");
		LOG.trace("Request parameter: price --> " + price);
		
		String description = request.getParameter("description");
		LOG.trace("Request parameter: description --> " + description);

		boolean valid = Validator.addTariffValidator(name, price, description);

		if (!valid) {
			LOG.error("Invalid data");
			return Path.COMMAND_ADD_TARIFF_FORM + "&error=error";
		}

		int serviceT = Integer.parseInt(serviceType);
		double dprice = Double.parseDouble(price);
		
		manager.addTariffWithService(name, serviceT, dprice, description);	
		LOG.info("Tariff was added.");
		
		LOG.debug("Command finished");

		return Path.COMMAND_LIST_TARIFFS_SERVICES;
	}
}
