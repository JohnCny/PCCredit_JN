package com.cardpay.pccredit.creditEvaluation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import org.json.*;

/*
 * This class has already packaged a DataBase connector,
 * via it, you can connect to the DataBase more
 * friendly. Hope you enjoy it.
 */
public class DatabaseConnector {
	private String fullPathFileName;
	private String fileContent;
	
	// structural method
	public DatabaseConnector() {
		// getting dataBase configuration from the configuration file
		fullPathFileName = "./configuration.json";
		
		File file = new File(fullPathFileName);
		Scanner scanner = null;
		StringBuilder buffer = new StringBuilder();
		try {
			scanner = new Scanner(file, "utf-8");
			while (scanner.hasNext()) {
				buffer.append(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} finally {
			if(scanner != null) {
				scanner.close();
			}
		}
		fileContent = buffer.toString();
	}
	
	// get a DataBase operator, INSERT & DELETE & UPDATE
	public int getDBOperator(String sql) throws Exception {
		int result = 0;
		
		// create a dataBase connector
		Connection conn = null;
		JSONObject configuration = new JSONObject(fileContent);
		String url = configuration.getJSONObject("dataBaseConnector").get("host").toString();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("mySql operate fault!");
		} finally {
			conn.close();
		}
		
		return result;
	}
	
	// get a DataBase operator, SELECT
	public ResultSet getSelectOperator(String sql) throws Exception {
		// create a dataBase connector
		Connection conn = null;
		ResultSet rs = null;
		JSONObject configuration = new JSONObject(fileContent);
		String url = configuration.getJSONObject("dataBaseConnector").get("host").toString();		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("mySql operate fault!");
		} finally {
			conn.close();
		}
		
		return rs;
	}
}
