package ua.nure.parkhomenko.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import jdk.nashorn.internal.ir.Block;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Account;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Contract;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Service;
import ua.nure.parkhomenko.SummaryTask4.db.entity.ServicesTariffs;
import ua.nure.parkhomenko.SummaryTask4.db.entity.Tariff;
import ua.nure.parkhomenko.SummaryTask4.db.entity.User;
import ua.nure.parkhomenko.SummaryTask4.exception.DBException;
import ua.nure.parkhomenko.SummaryTask4.exception.Messages;
import ua.nure.parkhomenko.SummaryTask4.web.command.BlockCommand;

/**
 * DB manager. Works with MySQL.
 * 
 * @author Tetiana Parkhomenko
 * 
 */
public final class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DBManager instance;
	private DataSource ds;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");

			ds = (DataSource) envContext.lookup("jdbc/internet_provider");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	/**
	 * Returns a DB connection from the Pool Connections. Before using this
	 * method you must configure the Date Source and the Connections Pool in
	 * your WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return DB connection.
	 */
	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}

		return con;
	}

	/**
	 * Returns a list of Tariffs.
	 * 
	 * @return List of tariffs.
	 * @throws DBException
	 */
	public List<Tariff> findTariffs() throws DBException {
		List<Tariff> tariffs = new ArrayList<Tariff>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(Query.SQL_FIND_TARIFFS);
			while (rs.next()) {
				tariffs.add(extractTariff(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_TARIFFS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_TARIFFS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return tariffs;
	}
	
	/**
	 * Returns a list of Contracts.
	 * 
	 * @return List of contracts.
	 * @throws DBException
	 */
	public List<Contract> findContracts() throws DBException {
		List<Contract> contract = new ArrayList<Contract>();
		
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(Query.SQL_FIND_CONTRACTS);
			while (rs.next()) {
				contract.add(extractContract(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_CONTRACTS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_CONTRACTS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return contract;
	}

	/**
	 * Extracts a tariff entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a tariff entity will be extracted.
	 * @return Tariff entity
	 */
	private Tariff extractTariff(ResultSet rs) throws SQLException {
		Tariff tariff = new Tariff();
		tariff.setId(rs.getInt(Fields.ENTITY_ID));
		tariff.setName(rs.getString(Fields.TARIFF_NAME));
		return tariff;
	}

	/**
	 * Returns services and tariffs with the given tariff id.
	 * 
	 * @param tariffID
	 *            tariff id
	 * @return ServicesTariffs entity.
	 * @throws DBException
	 */
	public List<ServicesTariffs> findServicesTariffsByTariffID(int tariffID) throws DBException {
		List<ServicesTariffs> servicesTariffs = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_SERVICES_BY_TARIFFID);
			pstmt.setInt(1, tariffID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				servicesTariffs.add(extractServicesTariffs(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES_TARIFFS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES_TARIFFS, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return servicesTariffs;
	}

	/**
	 * Returns services and tariffs with the given id.
	 * 
	 * @param id
	 *            service and tariff id
	 * @return ServicesTariffs entity.
	 * @throws DBException
	 */
	public ServicesTariffs findServiceTariffById(int id) throws DBException {
		ServicesTariffs servicesTariffs = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_SERVICES_TARIFFS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				servicesTariffs = extractServicesTariffs(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES_TARIFFS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES_TARIFFS, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return servicesTariffs;
	}

	/**
	 * Returns user's account.
	 * 
	 * @param id
	 *            user id
	 * @return Account entity.
	 * @throws DBException
	 */
	public Account findUserAccount(int id) throws DBException {
		Account account = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_USER_ACCOUNT);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				account = extractAccount(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_ACCOUNT, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ACCOUNT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return account;
	}

	/**
	 * Extracts an account entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a account entity will be extracted.
	 * @return Account entity
	 */
	private Account extractAccount(ResultSet rs) throws SQLException {
		Account account = new Account();
		account.setId(rs.getInt(Fields.ACCOUNT_ID));
		User user = extractUser(rs);
		account.setUser(user);
		account.setMoney(rs.getDouble(Fields.ACCOUNT_MONEY));
		account.setIsBlocked(Status.valueOf(rs.getString(Fields.ACCOUNT_ISBLOCKED)));
		account.setIsBlockedByAdmin(Status.valueOf(rs.getString(Fields.ACCOUNT_ISBLOCKED_BY_ADMIN)));
		return account;
	}

	/**
	 * Returns list of user contracts.
	 * 
	 * @param id
	 *            user id
	 * @return list of user contracts
	 * @throws DBException
	 */
	public List<Contract> findUserContracts(int id) throws DBException {
		List<Contract> contracts = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_USER_CONTRACTS);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				contracts.add(extractContract(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_CONTRACT, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_CONTRACT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return contracts;
	}

	/**
	 * Extracts a contract entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a contract entity will be extracted.
	 * @return Contract entity
	 */
	private Contract extractContract(ResultSet rs) throws SQLException {
		Contract contract = new Contract();
		contract.setId(rs.getInt(Fields.CONTRACT_ID));
		User user = extractUser(rs);
		contract.setUser(user);
		contract.setDate(rs.getDate(Fields.CONTRACT_DATE));
		ServicesTariffs servicesTariffs = extractServicesTariffs(rs);
		contract.setServicesTariffs(servicesTariffs);
		return contract;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Finds login in table.
	 * 
	 * @param login
	 * @return true if there is the login in the table, false if not.
	 * @throws DBException
	 */
	public boolean checkLogin(String login) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_CHECK_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return false;
	}

	/**
	 * Deletes tarrif with the diven login.
	 * 
	 * @param id
	 *            Tariff id.
	 * @throws DBException
	 */
	public void deleteTariff(int id) throws DBException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_DELETE_TARIFF_FROM_CONTRACTS);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

			pstmt2 = con.prepareStatement(Query.SQL_DELETE_TARIFF_FROM_SERVICES_TARIFFS);
			pstmt2.setInt(1, id);
			pstmt2.executeUpdate();

			stmt = con.createStatement();
			stmt.executeUpdate(Query.SQL_DELETE_TARIFF_FROM_TARIFFS);

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_DELETE_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_TARIFF, ex);
		} finally {
			close(pstmt2);
			close(con, pstmt, rs);
		}
	}

	/**
	 * Updates user's login and password.
	 * 
	 * @param user
	 *            User entity.
	 * @throws DBException
	 */
	public void updateUserLoginAndPassword(User user) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_UPDATE_USER_LOGIN_PASSWORD);
			pstmt.setString(1, user.getLogin());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Blocks subscriber with the given id.
	 * 
	 * @param blockQuery
	 * @throws DBException
	 */
	public void blockSubscriber(String blockQuery) throws DBException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(blockQuery);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_BLOCK_SUBSCRIBER, ex);
			throw new DBException(Messages.ERR_CANNOT_BLOCK_SUBSCRIBER, ex);
		} finally {
			close(con, stmt, rs);
		}
	}

	/**
	 * Unblocks subscriber with the given id.
	 * 
	 * @param unblockQuery
	 * @throws DBException
	 */
	public void unblockSubscriber(String unblockQuery) throws DBException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(unblockQuery);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_UNBLOCK_SUBSCRIBER, ex);
			throw new DBException(Messages.ERR_CANNOT_UNBLOCK_SUBSCRIBER, ex);
		} finally {
			close(con, stmt, rs);
		}
	}

	/**
	 * Adds subscriber.
	 * 
	 * @param subscriber
	 * @throws DBException
	 */
	public void addSubscriber(User subscriber) throws DBException {
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtFind = null;
		PreparedStatement pstmtMakeAcc = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmtInsert = con.prepareStatement(Query.SQL_INSERT_SUBSCRIBER);

			pstmtInsert.setString(1, subscriber.getFullName());
			pstmtInsert.setDate(2, new java.sql.Date(subscriber.getDateOfBirth().getTime()));
			pstmtInsert.setString(3, subscriber.getAddress());
			pstmtInsert.setString(4, subscriber.getPassport());
			pstmtInsert.setString(5, subscriber.getLogin());
			pstmtInsert.setString(6, subscriber.getPassword());
			pstmtInsert.setInt(7, subscriber.getRoleId());
			pstmtInsert.executeUpdate();

			pstmtFind = con.prepareStatement(Query.SQL_FIND_USER_BY_LOGIN);
			pstmtFind.setString(1, subscriber.getLogin());
			rs = pstmtFind.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = Integer.parseInt(rs.getString(Fields.ENTITY_ID));
			}
			pstmtMakeAcc = con.prepareStatement(Query.SQL_MAKE_ACCOUNT);
			pstmtMakeAcc.setInt(1, id);
			pstmtMakeAcc.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_ADD_SUBSCRIBER, ex);
			throw new DBException(Messages.ERR_CANNOT_ADD_SUBSCRIBER, ex);
		} finally {
			close(pstmtFind);
			close(con, pstmtInsert, rs);
		}
	}

	/**
	 * Edits tariff.
	 * 
	 * @param servTariff
	 * @throws DBException
	 */
	public void editTariff(ServicesTariffs servTariff) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();

			pstmt = con.prepareStatement(Query.SQL_EDIT_TARIFF);
			pstmt.setString(1, servTariff.getTariff().getName());
			pstmt.setDouble(2, servTariff.getPrice());
			pstmt.setString(3, servTariff.getDescription());
			pstmt.setInt(4, servTariff.getService().getId());
			pstmt.setInt(5, servTariff.getTariff().getId());
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_EDIT_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_EDIT_TARIFF, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Adds tariff into 'tariffs' and 'services_tariffs' tables.
	 * 
	 * @param name
	 *            Tariff name.
	 * @param serviceType
	 *            Services id.
	 * @param price
	 * @param description
	 * @throws DBException
	 */
	public void addTariffWithService(String name, int serviceType, double price, String description)
			throws DBException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();

			// find tariff with this name
			int id = getTariffId(name);

			// if there is no tariff with such name
			if (id == 0) {
				addTariff(name);
				id = getTariffId(name);
			}

			pstmt = con.prepareStatement(Query.SQL_ADD_SERVICES_TARIFFS);
			pstmt.setInt(1, serviceType);
			pstmt.setInt(2, id);
			pstmt.setDouble(3, price);
			pstmt.setString(4, description);
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_ADD_TARIFF_WITH_SERVICE, ex);
			throw new DBException(Messages.ERR_CANNOT_ADD_TARIFF_WITH_SERVICE, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Adds tariff in 'tariffs' table.
	 * 
	 * @param name
	 * @throws DBException
	 */
	public void addTariff(String name) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_ADD_TARIFF_NAME);
			pstmt.setString(1, name);
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_ADD_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_ADD_TARIFF, ex);
		} finally {
			close(con, pstmt);
		}
	}

	/**
	 * Returns tariff id with the given name.
	 * 
	 * @param name
	 * @return tariff id
	 * @throws DBException
	 */
	public int getTariffId(String name) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		int id = 0;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_TARIFF_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				id = Integer.parseInt(rs.getString(Fields.ENTITY_ID));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_ADD_TARIFF, ex);
			throw new DBException(Messages.ERR_CANNOT_ADD_TARIFF, ex);
		} finally {
			close(con, pstmt, rs);
		}

		return id;
	}

	/**
	 * Pays for contracts.
	 * 
	 * @param userId
	 * @param sum
	 * @throws DBException
	 */
	public void accountPay(int userId, double sum) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_ACCOUNT_PAYMENT);

			pstmt.setDouble(1, sum);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_PAY_ACCOUNT, ex);
			throw new DBException(Messages.ERR_CANNOT_PAY_ACCOUNT, ex);
		} finally {
			close(con, pstmt);
		}
	}

	/**
	 * Makes new contract for user.
	 * 
	 * @param userId
	 *            User's id.
	 * @param serviceId
	 *            Selected service id.
	 * @param tariffId
	 *            Selected tariff id.
	 * @param date
	 * @throws DBException
	 */
	public void makeNewContaract(int userId, int serviceId, int tariffId, Date date) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_MAKE_NEW_CONTRACT);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, serviceId);
			pstmt.setInt(3, tariffId);
			pstmt.setDate(4, date);
			pstmt.executeUpdate();

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_MAKE_CONTRACT, ex);
			throw new DBException(Messages.ERR_CANNOT_MAKE_CONTRACT, ex);
		} finally {
			close(con, pstmt);
		}
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setFullName(rs.getString(Fields.USER_FULL_NAME));
		user.setDateOfBirth(rs.getDate(Fields.USER_DATE_OF_BIRTH));
		user.setAddress(rs.getString(Fields.USER_ADDRESS));
		user.setPassport(rs.getString(Fields.USER_PASSPORT));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

	/**
	 * Returns all services with tariffs.
	 * 
	 * @return List of servicesTariffs entities.
	 */
	public List<ServicesTariffs> findServicesAndTariffs() throws DBException {
		List<ServicesTariffs> servicesTariffs = new ArrayList<ServicesTariffs>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(Query.SQL_FIND_SERVICES_AND_TARIFFS);
			while (rs.next()) {
				servicesTariffs.add(extractServicesTariffs(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES_TARIFFS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES_TARIFFS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return servicesTariffs;
	}

	/**
	 * Extracts a ServicesTariffs entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a ServicesTariffs entity will be
	 *            extracted.
	 * @return ServicesTariffs entity.
	 */
	private ServicesTariffs extractServicesTariffs(ResultSet rs) throws SQLException {

		ServicesTariffs servicesTariffs = new ServicesTariffs();
		servicesTariffs.setId(rs.getInt(Fields.SERVICE_TARIFF_ID));
		Service service = new Service();
		service.setId(Integer.parseInt(rs.getString(Fields.SERVICE_ID)));
		service.setName(rs.getString(Fields.SERVICE_TARIFF_SERVICES));
		servicesTariffs.setService(service);

		Tariff tariff = new Tariff();

		tariff.setId(Integer.parseInt(rs.getString(Fields.TARIFF_ID)));
		tariff.setName(rs.getString(Fields.SERVICE_TARIFF_TARIFFS));
		servicesTariffs.setTariff(tariff);

		servicesTariffs.setPrice(rs.getDouble(Fields.SERVICE_TARIFF_PRICE));

		servicesTariffs.setDescription(rs.getString(Fields.SERVICE_TARIFF_DESC));

		return servicesTariffs;
	}

	/**
	 * Returns all services.
	 * 
	 * @return List of services.
	 */
	public List<Service> findServices() throws DBException {
		List<Service> services = new ArrayList<Service>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(Query.SQL_FIND_SERVICES);
			while (rs.next()) {
				services.add(extractService(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_SERVICES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return services;
	}

	/**
	 * Returns all subscribers.
	 * 
	 * @return List of subscribers.
	 */
	public List<User> findSubscribers(int roleId) throws DBException {
		List<User> subscribers = new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(Query.SQL_FIND_SUBSCRIBERS);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				subscribers.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_LIST_SUBSCRIBERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_LIST_SUBSCRIBERS, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return subscribers;
	}

	/**
	 * Extracts a service entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a service entity will be extracted.
	 * @return Service entity.
	 */
	private Service extractService(ResultSet rs) throws SQLException {
		Service service = new Service();
		service.setId(rs.getInt(Fields.ENTITY_ID));
		service.setName(rs.getString(Fields.SERVICE_NAME));
		return service;
	}

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 * 
	 * @param stmt
	 *            Statement to be closed.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 * 
	 * @param rs
	 *            ResultSet to be closed.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	private void close(Connection con, Statement stmt) {
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}
}