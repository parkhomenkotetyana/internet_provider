package ua.nure.parkhomenko.SummaryTask4.db;

public final class Query {

	//user
	public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	public static final String SQL_CHECK_LOGIN = "SELECT * FROM users WHERE login=?";
	public static final String SQL_FIND_SUBSCRIBERS = "SELECT * FROM users WHERE role_id=?";
	public static final String SQL_INSERT_SUBSCRIBER = "INSERT INTO users (full_name, date_of_birth, address, passport, login, password, role_id) "
														+ "VALUES (?,?,?,?,?,?,?)";
	public static final String SQL_UPDATE_USER_LOGIN_PASSWORD = "UPDATE users SET login=?, password=? WHERE id=?";
	
	//services
	public static final String SQL_FIND_SERVICES = "SELECT * FROM services";
	public static final String SQL_FIND_SERVICES_BY_TARIFFID = "SELECT services_tariffs.id AS stID, services.id AS servicesID, services.name AS services, tariffs.id AS tariffsID, tariffs.name AS tariffs, services_tariffs.price, services_tariffs.description "
																+ "FROM services JOIN services_tariffs ON services_tariffs.service_id=services.id "
																+ "JOIN tariffs ON tariffs.id=services_tariffs.tariff_id "
																+ "WHERE tariff_id=?";
	
	//tariffs
	public static final String SQL_FIND_TARIFFS = "SELECT * FROM tariffs";
	public static final String SQL_ADD_TARIFF_NAME = "INSERT INTO tariffs VALUES (DEFAULT, ?)";
	public static final String SQL_EDIT_TARIFF = "UPDATE services_tariffs "
												+ "JOIN tariffs ON services_tariffs.tariff_id=tariffs.id "
												+ "SET tariffs.name=?, services_tariffs.price=?, services_tariffs.description=? "
												+ "WHERE services_tariffs.service_id=? "
												+ "AND services_tariffs.tariff_id=?";
	public static final String SQL_FIND_TARIFF_BY_NAME = "SELECT * FROM tariffs WHERE name=?";
	public static final String SQL_DELETE_TARIFF_FROM_TARIFFS = "DELETE tariffs "
																+ "FROM tariffs "
																+ "LEFT JOIN services_tariffs "
																+ "ON tariffs.id=services_tariffs.tariff_id "
																+ "WHERE services_tariffs.tariff_id IS NULL";
	
	//services_tariffs
	public static final String SQL_FIND_SERVICES_AND_TARIFFS = "SELECT services_tariffs.id AS stID, services.name AS services, services.id AS servicesID, "
																+ "tariffs.name AS tariffs, tariffs.id AS tariffsID, services_tariffs.price, services_tariffs.description "
																+ "FROM services "
																+ "JOIN services_tariffs ON services_tariffs.service_id=services.id "
																+ "JOIN tariffs ON tariffs.id=services_tariffs.tariff_id";
	public static final String SQL_FIND_SERVICES_TARIFFS_BY_ID = "SELECT services_tariffs.id AS stID, services.name AS services, services.id AS servicesID, "
																+ "tariffs.name AS tariffs, tariffs.id AS tariffsID, services_tariffs.price, services_tariffs.description "
																+ "FROM services "
																+ "JOIN services_tariffs ON services_tariffs.service_id=services.id "
																+ "JOIN tariffs ON tariffs.id=services_tariffs.tariff_id "
																+ "WHERE services_tariffs.id=?;";
	public static final String SQL_ADD_SERVICES_TARIFFS = "INSERT INTO services_tariffs VALUES (DEFAULT, ?, ?, ?, ?)";	
	public static final String SQL_DELETE_TARIFF_FROM_SERVICES_TARIFFS = "DELETE FROM services_tariffs WHERE id=?";
	
	//account
	public static final String SQL_MAKE_ACCOUNT = "INSERT INTO account VALUES (DEFAULT, ?, DEFAULT, DEFAULT, DEFAULT)";
	public static final String SQL_FIND_USER_ACCOUNT = "SELECT account.id AS accountID, user_id, money, is_blocked, is_blocked_by_admin,  users.id, full_name, passport, date_of_birth, address, login, password, role_id "
														+ "FROM account JOIN users ON users.id=account.user_id "
														+ "WHERE user_id=?";
	public static final String SQL_IS_SUBSCRIBER_BLOCKED = "SELECT isBlocked FROM account WHERE user_id=?";
	public static final String SQL_ACCOUNT_PAYMENT = "UPDATE account SET money=(money+?) WHERE user_id=?";
	public static final String SQL_BLOCK_SUBCRIBER = "UPDATE account SET is_blocked_by_admin='BLOCKED' WHERE user_id=?";
	public static final String SQL_UNBLOCK_SUBCRIBER = "UPDATE account SET is_blocked_by_admin='FREE' WHERE user_id=?";
	
	//contract
	public static final String SQL_FIND_CONTRACTS = "SELECT contracts.id AS contractID, contracts.user_id, services_tariffs.id AS stID, services.name AS services, "
													+ "services.id AS servicesID, tariffs.name AS tariffs, tariffs.id AS tariffsID, services_tariffs.price, "
													+ "services_tariffs.description, contracts.date, users.id, full_name, passport, date_of_birth, address, login, password, role_id  FROM contracts "
													+ "JOIN users ON users.id=contracts.user_id "
													+ "JOIN services_tariffs ON services_tariffs.id=service_tariff_id "
													+ "JOIN services ON services.id=services_tariffs.service_id "
													+ "JOIN tariffs ON tariffs.id= services_tariffs.tariff_id";
	
	public static final String SQL_FIND_USER_CONTRACTS = "SELECT contracts.id AS contractID, contracts.user_id, services_tariffs.id AS stID, services.name AS services, "
														+ "services.id AS servicesID, tariffs.name AS tariffs, tariffs.id AS tariffsID, services_tariffs.price, "
														+ "services_tariffs.description, contracts.date, users.id, full_name, passport, date_of_birth, address, login, password, role_id  FROM contracts "
														+ "JOIN users ON users.id=contracts.user_id "
														+ "JOIN services_tariffs ON services_tariffs.id=service_tariff_id "
														+ "JOIN services ON services.id=services_tariffs.service_id "
														+ "JOIN tariffs ON tariffs.id= services_tariffs.tariff_id "
														+ "WHERE contracts.user_id=?";
	public static final String SQL_DELETE_TARIFF_FROM_CONTRACTS = "DELETE FROM contracts WHERE service_tariff_id=?";
	public static final String SQL_MAKE_NEW_CONTRACT = "INSERT INTO contracts "
													+ "VALUES (DEFAULT, ?, (SELECT id FROM services_tariffs WHERE service_id=? AND tariff_id=?), ?)";
}
