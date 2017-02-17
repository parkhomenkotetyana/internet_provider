package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.ServicesTariffs;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * List tariffs-services command.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class ListTariffsServicesCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(ListTariffsServicesCommand.class);

	private List<ServicesTariffs> tariffsServices;

	private static Comparator<ServicesTariffs> compareByPrice = new CompareByPrice();

	private static Comparator<ServicesTariffs> compareByNameAZ = new CompareByNameAZ();

	private static Comparator<ServicesTariffs> compareByNameZA = new CompareByNameZA();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");		

		tariffsServices = DBManager.getInstance().findServicesAndTariffs();
		LOG.trace("Found in DB: tariffsServices --> " + tariffsServices);

		request.setAttribute("tariffsServices", tariffsServices);
		LOG.trace("Set the request attribute: tariffsServices --> " + tariffsServices);
		
		String sort = request.getParameter("sort");
		LOG.trace("Request parameter: sort --> " + sort);
		
		String type = request.getParameter("type");
		LOG.trace("Request parameter: type --> " + type);

		if ("delete".equals(type)) {
			return Path.PAGE_LIST_TARIFFS_SERVICES_DELETE;
		}

		if ("edit".equals(type)) {
			return Path.PAGE_LIST_TARIFFS_SERVICES_EDIT;
		}

		if (sort != null)
			switch (sort) {

			case "price":
				Collections.sort(tariffsServices, compareByPrice);
				break;
			case "nameAZ":
				Collections.sort(tariffsServices, compareByNameAZ);
				break;
			case "nameZA":
				Collections.sort(tariffsServices, compareByNameZA);
				break;
			default:
				break;
			}
		
		LOG.debug("Command finished");
		return Path.PAGE_LIST_TARIFFS_SERVICES;
	}

	private static class CompareByPrice implements Comparator<ServicesTariffs>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(ServicesTariffs o1, ServicesTariffs o2) {
			return Double.compare(o1.getPrice(), (o2.getPrice()));
		}
	}

	private static class CompareByNameAZ implements Comparator<ServicesTariffs>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(ServicesTariffs o1, ServicesTariffs o2) {
			return o1.getTariff().getName().compareToIgnoreCase(o2.getTariff().getName());
		}
	}

	private static class CompareByNameZA implements Comparator<ServicesTariffs>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(ServicesTariffs o1, ServicesTariffs o2) {
			return o2.getTariff().getName().compareToIgnoreCase(o1.getTariff().getName());
		}
	}
}
