package com.practice.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

	private static Connection conn;
	private static Statement st;
	private static ResultSet rs;

	// create connection to db
	public static void getConnection() {

		ConfigsReader.readProperties(Constants.CONFIGURATION_FILEPATH);

		try {
			conn = DriverManager.getConnection(ConfigsReader.getProperty("dbUrl"),
					ConfigsReader.getProperty("dbUserName"), ConfigsReader.getProperty("dbPassword"));
		} catch (SQLException e) {
			System.out.println("Could NOT connect to the DataBase!!");
			e.printStackTrace();
		}
	}

	// get data
	public static List<Map<String, String>> storeDataFromDB(String sqlQuery) {

		List<Map<String, String>> toReturn = new ArrayList<>();

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery);
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();

			Map<String, String> map;

			while (rs.next()) {
				map = new LinkedHashMap<>();

				for (int i = 1; i <= columnCount; i++) {

				 map.put(rsMetaData.getColumnName(i), rs.getString(i));

				}

				toReturn.add(map);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return toReturn;

	}

	// close the db connection
	public static void closeConnection() {

		try {

			if (conn != null) {
				conn.close();
			}
			
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// this method opens and closes the connection within its body
	public static List<Map<String,String>>  storeDataFromDB2(String sqlQuery) {
		
		getConnection();
		List<Map<String,String>> toReturn = storeDataFromDB(sqlQuery);
		closeConnection();
		return toReturn;
		
	}

}
