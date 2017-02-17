package ua.nure.parkhomenko.SummaryTask4.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DBManagerTest {
	
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	@BeforeClass
	public static void beforeClass() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/internet_provider", "root", "L74Hzoxi3V");
			stmt = con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("ClassNotFoundException | SQLException");
		}
	}
	
	@Test
	public void testFindTariffs() throws SQLException {		
		rs = stmt.executeQuery(Query.SQL_FIND_TARIFFS);
		Assert.assertNotNull(rs.next());
	}
	
	@Test
	public void testFindServices() throws SQLException {		
		rs = stmt.executeQuery(Query.SQL_FIND_SERVICES);
		Assert.assertNotNull(rs.next());
	}
	
	@Test
	public void testFindServicesTariffs() throws SQLException {		
		rs = stmt.executeQuery(Query.SQL_FIND_SERVICES_AND_TARIFFS);
		Assert.assertNotNull(rs.next());
	}
	
	@Test
	public void testFindSubcribers() throws SQLException {		
		rs = stmt.executeQuery(Query.SQL_FIND_SUBSCRIBERS);
		Assert.assertNotNull(rs.next());
	}

}
