package ua.nure.parkhomenko.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.<br/>
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("updateSettings", new UpdateSettingsCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("listTariffsServices", new ListTariffsServicesCommand());
		commands.put("facebook", new FacebookCommand());
		commands.put("fboauth", new OAuthCommand());

		// client commands
		commands.put("accountPay", new AccountPayCommand());
		commands.put("downloadPDF", new DownloadPDFCommand());
		commands.put("makeOrder", new MakeOrderCommand());
		commands.put("viewAccount", new ViewAccountCommand());

		// admin commands/
		commands.put("listSubscribers", new ListSubscribersCommand());
		commands.put("addSubscriber", new AddSubscriberCommand());
		commands.put("addSubscriberForm", new AddSubscriberFormCommand());
		commands.put("addTariff", new AddTariffCommand());
		commands.put("addTariffForm", new AddTariffFormCommand());
		commands.put("deleteTariff", new DeleteTariffCommand());
		commands.put("editTariff", new EditTariffCommand());
		commands.put("editTariffForm", new EditTariffFormCommand());
		commands.put("block", new BlockCommand());
		commands.put("unblock", new UnblockCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}
}