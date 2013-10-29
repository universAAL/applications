package org.universAAL.AALapplication.food_shopping.service.db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.universAAL.AALapplication.food_shopping.service.db.config.Configuration;

//import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
//import org.apache.commons.dbcp.datasources.SharedPoolDataSource;

public class ConnectionManager extends ClassLoader {

	private static DataSource ds = null;
	private final static String dbDriver = Configuration.get("dbDriver");
	private final static String dbUrl = Configuration.get("dbUrl");
	private final static String dbUser = Configuration.get("dbUser");
	private final static String dbPassword = Configuration.get("dbPassword");

	
	public static synchronized Connection getConnection() throws SQLException {
		//com.mysql.jdbc.Driver d = new com.mysql.jdbc.Driver(); 
		Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		return con;
	}
	
	public static synchronized void returnConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		} catch (NullPointerException npe) {
		    npe.printStackTrace();
		}
	}

/*	
	private static SharedPoolDataSource tds;
	
	static {

		System.out.println("1. Dr " + dbDriver);
		
		try {
			com.mysql.jdbc.Driver d = new com.mysql.jdbc.Driver();
			DriverAdapterCPDS cpds = new DriverAdapterCPDS();
			cpds.setDriver("com.mysql.jdbc.Driver");
			cpds.setUrl(dbUrl);
			cpds.setUser(dbUser);
			cpds.setPassword(dbPassword);

			tds = new SharedPoolDataSource();
			tds.setConnectionPoolDataSource(cpds);
			tds.setMaxActive(10);
			//tds.setMaxWait(500);
			tds.setMaxWait(-1);
			ds = tds;
		} catch (Exception e) {
			System.out.println("SQL Driver not found! Exiting... " + e.getMessage());

		}
	}

	public static synchronized Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static synchronized void returnConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		} catch (NullPointerException npe) {
		    npe.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println("CON: " + ConnectionManager.getConnection());
	}
*/
}
