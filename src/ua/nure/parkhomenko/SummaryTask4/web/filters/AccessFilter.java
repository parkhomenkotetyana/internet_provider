package ua.nure.parkhomenko.SummaryTask4.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.Role;

/**
 * Access filter. Invokes when user wants to execute some command and gives
 * permission or forbids access. * 
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class AccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(AccessFilter.class);

	// commands access
	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> adminCommands = new ArrayList<String>();
	private static List<String> subscriberCommands = new ArrayList<String>();
	private static List<String> commonCommands = new ArrayList<String>();
	private static List<String> loginCommands = new ArrayList<String>();

	public void destroy() {
		LOG.debug("Filter destruction starts");
		// do nothing
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Filter starts");

		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessasge);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessasge);

			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		LOG.trace("Command name --> " + commandName);
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			return false;
		}
		
		Role userRole = (Role) session.getAttribute("userRole");
		if (userRole == null) {
			return loginCommands.contains(commandName);
		}
		return accessMap.get(userRole).contains(commandName) || commonCommands.contains(commandName);
	}

	public void init(FilterConfig filterfConfig) throws ServletException {
		LOG.debug("Filter initialization starts");

		// roles
		adminCommands = makeList(filterfConfig.getInitParameter("admin"));
		subscriberCommands = makeList(filterfConfig.getInitParameter("subscriber"));

		// access map
		accessMap.put(Role.ADMIN, adminCommands);
		accessMap.put(Role.SUBSCRIBER, subscriberCommands);
		LOG.trace("Access map --> " + accessMap);

		// commons
		commonCommands = makeList(filterfConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + commonCommands);
		
		// login
		loginCommands = makeList(filterfConfig.getInitParameter("login"));
		LOG.trace("Login commands --> " + loginCommands);

		LOG.debug("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> makeList(String str) {
		List<String> list = new ArrayList<String>();
		String[] commands = str.split(" ");
		for (String command : commands) {
			list.add(command);
		}
		return list;
	}
}