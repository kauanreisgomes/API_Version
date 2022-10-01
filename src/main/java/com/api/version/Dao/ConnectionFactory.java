package com.api.version.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;


public class ConnectionFactory {
	//getClass().getResourceAsStream("")
	
	//JSONArray array = new JSONArray();
	private static String url = "";


	public static Connection getConnection() {
		Connection dm = null;
			
		
		url = "jdbc:mysql://localhost:3306/api?useTimezone=true&serverTimezone=America/Sao_Paulo";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			dm = DriverManager.getConnection(url, "api", "123456789");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dm;

		
		
	}

	
}
