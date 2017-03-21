//author: mlabib@amazon.com

package src.main.java.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class RDSConnection {

	public static Connection getConnection() {

		  Connection connection = null;

	try {

		Class.forName("org.mariadb.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:your endpoint",  "user", "password" );

	} catch (Exception e) {

		e.printStackTrace();
	}

    return connection;

   }

}
