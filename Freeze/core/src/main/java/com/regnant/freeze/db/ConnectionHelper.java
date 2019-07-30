package com.regnant.freeze.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
	private String url;
	  private static ConnectionHelper instance;
	  private ConnectionHelper()
	  {
	    try {
	      Class.forName("org.postgresql.Driver").newInstance();
	      url = "jdbc:postgresql://localhost:5432/mydb";
	      System.out.println("inside connection1");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	  public static Connection getConnection() throws SQLException {
//	    if (instance == null) {
//	      instance = new ConnectionHelper();
//	    }
	    try {
		      System.out.println("inside connection2");
		      Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "2019");
		      
	      return connection;
	    }
	    catch (SQLException e) {
	      throw e;
	    }
	  }
	  public static void close(Connection connection)
	  {
	    try {
	      if (connection != null) {
	      connection.close();
	   }
	  }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }

}
