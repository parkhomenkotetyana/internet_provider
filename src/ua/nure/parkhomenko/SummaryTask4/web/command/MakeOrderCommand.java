package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.ServicesTariffs;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Tariff;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

public class MakeOrderCommand extends Command {

	private int selectedTariffID = 0;

	private static final long serialVersionUID = 5362602959285106021L;
	
	private static final Logger LOG = Logger.getLogger(MakeOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		
		DBManager manager = DBManager.getInstance();
		List<Tariff> tariffs = manager.findTariffs();		
		LOG.trace("Found in DB: tariffs --> " + tariffs);
		
		request.setAttribute("tariffs", tariffs);
		LOG.trace("Set the session attribute: tariffs --> " + tariffs);

		String tariffID = request.getParameter("tariffID");
		LOG.trace("Request parameter: tariffID --> " + tariffID);

		String[] selectedServices = request.getParameterValues("serviceID");
		LOG.trace("Request parameter: selectedServices --> " + selectedServices);
		
		String forward = Path.PAGE_ERROR_PAGE;

		//if the form is empty
		if (tariffID == null & selectedServices == null) {
			return Path.PAGE_MAKE_ORDER;
		}
		
		//when subscriber selects tariff
		if (tariffID != null & selectedServices == null) {

			selectedTariffID = Integer.parseInt(tariffID);

			List<ServicesTariffs> servicesTariffs = manager.findServicesTariffsByTariffID(selectedTariffID);
			LOG.trace("Found in DB: servicesTariffs --> " + servicesTariffs);
			
			request.setAttribute("servicesTariffs", servicesTariffs);
			LOG.trace("Set the request attribute: servicesTariffs --> " + servicesTariffs);
			
			forward = Path.PAGE_MAKE_ORDER;
		}
		
		//when subscriber selects services
		if (selectedServices != null) {

			//get current date
			java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
			
			User user = (User) request.getSession().getAttribute("user");

			for (String serv : selectedServices) {
				manager.makeNewContaract(user.getId(), Integer.parseInt(serv), selectedTariffID, sqlDate);
			}
			
			LOG.info("New contracts were made.");
			
			forward = Path.COMMAND_VIEW_ACCOUNT;
		}

		LOG.debug("Command finished");
		return forward;
	}
}
