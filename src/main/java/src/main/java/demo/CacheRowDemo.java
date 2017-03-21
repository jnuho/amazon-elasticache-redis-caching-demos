//author: mlabib@amazon.com

package src.main.java.demo;

import java.sql.ResultSet;

import src.main.java.demo.dao.CustomerDAO;
import src.main.java.demo.dao.impl.CustomerDAOImpl;

public class CacheRowDemo {

	public static void main(String[] args) {	
		
	CustomerDAO customerDAO = new CustomerDAOImpl();
	 
	 try {	
		 
		 System.out.println("\n == getting resultset object for customerID:" +Integer.valueOf(args[0])+ " ==\n");
		 
		 ResultSet rs = customerDAO.getCustomerRow(Integer.valueOf(args[0]));	 
		 
		 rs.beforeFirst();
		 
		 while (rs.next()) {
			 
			    System.out.println("ROW-----------------------------------\n");
			 
				System.out.println("CUSTOMER_ID = " +  rs.getInt("CUSTOMER_ID"));
				System.out.println("FIRST_NAME = "  +  rs.getString("FIRST_NAME"));
				System.out.println("LAST_NAME = "   +  rs.getString("LAST_NAME"));
				System.out.println("GENDER = "      +  rs.getString("GENDER"));
				System.out.println("CITY = "        +  rs.getString("CITY"));
				System.out.println("STATE = "       +  rs.getString("STATE"));
				System.out.println("COUNTRY = "     +  rs.getString("COUNTRY"));
				System.out.println("EMAIL = "       +  rs.getString("EMAIL"));
				System.out.println("ADDRESS = "     +  rs.getString("ADDRESS"));
				
				System.out.println("\n---------------------------------------");
		
		 }
	 
	     
	 } catch (Exception e) {
		
		    e.printStackTrace();	 
	 
	     }
	}
}
