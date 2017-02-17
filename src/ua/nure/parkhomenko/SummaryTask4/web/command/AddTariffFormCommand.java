package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Service;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * Add tariff form command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class AddTariffFormCommand extends Command{

	private static final long serialVersionUID = 8019722139830558531L;
	
	private static final Logger LOG = Logger.getLogger(AddTariffFormCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");
		List<Service> services = new ArrayList<Service>();
		
		DBManager manager = DBManager.getInstance();
		
		services = manager.findServices();
		LOG.trace("Found in DB: services --> " + services);
		
		request.setAttribute("services", services);
		LOG.trace("Set the request attribute: services --> " + services);		
		
		String error = request.getParameter("error");
		
		if (error != null) {
			return Path.PAGE_ADD_TARIFF + "?error=error";
		}	
		
		LOG.debug("Command finished");
		return Path.PAGE_ADD_TARIFF;
	}	
}
