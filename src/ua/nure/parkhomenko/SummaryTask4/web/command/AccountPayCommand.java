package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.parkhomenko.SummaryTask4.Path;
import ua.nure.parkhomenko.SummaryTask4.db.DBManager;
import ua.nure.parkhomenko.SummaryTask4.db.Status;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Account;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.AppException;
import ua.nure.parkhomenko.SummaryTask4.web.utils.SendMail;
import ua.nure.parkhomenko.SummaryTask4.web.utils.validation.Validator;

/**
 * Account pay command.
 * 
 * @author Tetiana Parkhomenko
 *
 */
public class AccountPayCommand extends Command {

	private static final long serialVersionUID = 618566868110668359L;

	private static final Logger LOG = Logger.getLogger(AccountPayCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		HttpSession session = request.getSession();

		DBManager manager = DBManager.getInstance();

		User user = (User) session.getAttribute("user");

		String sumParam = request.getParameter("sum");
		LOG.trace("Request parameter: sumParam --> " + sumParam);
		
		String cardNumberParam = request.getParameter("cardNumber");
		LOG.trace("Request parameter: cardNumberParam --> " + cardNumberParam);
		
		String codeParam = request.getParameter("code");
		LOG.trace("Request parameter: codeParam -->  " + codeParam);

		boolean valid = Validator.accountPayValidator(sumParam, cardNumberParam, codeParam);

		if (!valid) {
			LOG.error("Not all fields are properly filled");
			return Path.COMMAND_VIEW_ACCOUNT;
		}

		double sum = Double.parseDouble(sumParam);

		manager.accountPay(user.getId(), sum);		
		LOG.trace("Account is paid");
		
		boolean isEmail = Validator.validate(user.getLogin());		
		if (isEmail) {
			
			String to = user.getLogin();
			String subject = "Receipt";
			String message = "Your balance was replenished with " + sum + " hrivnas! \n\n Internet provider.";

			try {
				SendMail.sendMail(to, subject, message);
			} catch (MessagingException e) {
				LOG.error("Error while sending email.");
			}
			LOG.trace("Receipt is send");
		}
		
		Account account = manager.findUserAccount(user.getId());
		LOG.trace("Found in DB: account --> " + account);
		
		if ((account.getIsBlocked() == Status.FREE) & (account.getIsBlockedByAdmin() == Status.FREE)) {
			LOG.trace("Set the session attribute: block --> " + Status.FREE);
			session.setAttribute("block", Status.FREE);
		} else {
			LOG.trace("Set the session attribute: block --> " + Status.BLOCKED);
			session.setAttribute("block", Status.BLOCKED);
		}

		LOG.debug("Command finished");
		return Path.COMMAND_VIEW_ACCOUNT;
	}
}
