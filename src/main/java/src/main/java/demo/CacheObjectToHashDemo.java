//author: mlabib@amazon.com

package src.main.java.demo;

import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import src.main.java.demo.dao.CustomerDAO;
import src.main.java.demo.dao.impl.CustomerDAOImpl;

public class CacheObjectToHashDemo {

	public static void main(String[] args) {	
		
	 CustomerDAO customerDAO = new CustomerDAOImpl();	 
	 
	 try {	
		 
		 System.out.println("\n == getting customer hash for customerID:" +Integer.valueOf(args[0])+ " ==\n");		
				
		Map<String, String> map = customerDAO.getCustomerMap(Integer.valueOf(args[0]));
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String cachedMap = gson.toJson(map);
		
		System.out.println("JSON-----------------------------------\n");
		
		System.out.println("\n"+cachedMap+"\n");
		
		System.out.println("\n---------------------------------------");
		 
	  } catch (Exception e) {
		
		    e.printStackTrace();	
	 
	  } 	   

}

}
