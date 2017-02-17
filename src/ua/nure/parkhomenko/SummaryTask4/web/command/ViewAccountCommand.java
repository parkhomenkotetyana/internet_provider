package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Account;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Contract;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;

/**
 * View account command
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class ViewAccountCommand extends Command{

	private static final long serialVersionUID = 1653789540258880818L;
	
	private static final Logger LOG = Logger.getLogger(ViewAccountCommand.class);
	
	private static Comparator<Contract> compareByDate = new CompareByDate();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		Account account = manager.findUserAccount(user.getId());
		LOG.trace("Found in DB: account --> " + account);
		
		List<Contract> contracts = manager.findUserContracts(user.getId());
		LOG.trace("Found in DB: contracts --> " + contracts);
		
		Collections.sort(contracts, compareByDate);
		request.setAttribute("contracts", contracts);
		LOG.trace("Set the request attribute: contracts --> " + contracts);
		
		double money = account.getMoney();
		
		request.setAttribute("money", money);
		LOG.trace("Set the request attribute: money --> " + money);
		
		LOG.debug("Command finished");	
		return Path.PAGE_ACCOUNT;
	}
	
	private static class CompareByDate implements Comparator<Contract>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(Contract o1, Contract o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}
}
