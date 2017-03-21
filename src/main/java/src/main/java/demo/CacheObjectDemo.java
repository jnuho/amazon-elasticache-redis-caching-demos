//author: mlabib@amazon.com

package src.main.java.demo;

import src.main.java.demo.dao.CustomerDAO;
import src.main.java.demo.dao.impl.CustomerDAOImpl;
import src.main.java.demo.pojo.Customer;

public class CacheObjectDemo {

  public static void main(String[] args) {	
		
	 CustomerDAO customerDAO = new CustomerDAOImpl();
	 
	 try {	
		 
		System.out.println("\n == getting customer object for customerID:" +Integer.valueOf(args[0])+ " ==\n");			
		 
		Customer customer  = customerDAO.getCustomerObject(Integer.valueOf(args[0])); 
		
		System.out.println("OBJECT-----------------------------------\n");
		
		System.out.println("CustomerID = "+ customer.getCustomerID());					
		System.out.println("Address = "+ customer.getAddress());
		System.out.println("City = "+ customer.getCity());
		System.out.println("Country = "+ customer.getCountry());
		System.out.println("FirstName = "+ customer.getFirstName());
		System.out.println("LastName = "+ customer.getFirstName());
		System.out.println("Gender = "+ customer.getGender());
		System.out.println("Email = "+ customer.getEmail());
		
		System.out.println("\n---------------------------------------");
		 
	  } catch (Exception e) {
		
	       e.printStackTrace();	 
	 
	  } 	   

  }

}
