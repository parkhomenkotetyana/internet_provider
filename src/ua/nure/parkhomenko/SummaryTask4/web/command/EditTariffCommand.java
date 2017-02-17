package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.ServicesTariffs;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Tariff;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.Validator;

/**
 * Edit tariff command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class EditTariffCommand extends Command {

	private static final long serialVersionUID = -5933048089536858790L;

	private static final Logger LOG = Logger.getLogger(EditTariffCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		String serviceTariffId = request.getParameter("serviceTariffId");
		LOG.trace("Request parameter: serviceTariffId --> " + serviceTariffId);

		String tariffName = request.getParameter("tariffName");
		LOG.trace("Request parameter: tariffName --> " + tariffName);

		String price = request.getParameter("price");
		LOG.trace("Request parameter: price --> " + price);

		String description = request.getParameter("description");
		LOG.trace("Request parameter: description --> " + description);

		DBManager manager = DBManager.getInstance();

		ServicesTariffs serviceTariff = manager.findServiceTariffById(Integer.parseInt(serviceTariffId));
		LOG.trace("Found in DB: serviceTariff --> " + serviceTariff);

		boolean valid = Validator.addTariffValidator(tariffName, price, description);

		if (!valid) {
			LOG.error("Invalid data!");
			return Path.COMMAND_LIST_TARIFFS_SERVICES;
		}

		Tariff tariff = new Tariff();
		tariff.setId(serviceTariff.getTariff().getId());
		tariff.setName(tariffName);

		serviceTariff.setPrice(Double.parseDouble(price));
		serviceTariff.setDescription(description);

		if (!tariffName.equals(serviceTariff.getTariff().getName())) {
			serviceTariff.setTariff(tariff);

			int id = manager.getTariffId(tariffName);
			LOG.trace("Found in DB: tariffId --> " + id);

			if (id == 0) {
				manager.editTariff(serviceTariff);
			} else {
				//errorMessage = "Invalid tariff name!";
			}
		} else {
			serviceTariff.setTariff(tariff);
			manager.editTariff(serviceTariff);
		}

		LOG.info("Tariff was edited.");
		
		LOG.debug("Command finished");
		return Path.COMMAND_LIST_TARIFFS_SERVICES;
	}
}
