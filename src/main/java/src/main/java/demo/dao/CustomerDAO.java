//author: mlabib@amazon.com

package src.main.java.demo.dao;

import java.sql.ResultSet;
import java.util.Map;

import src.main.java.demo.pojo.Customer;

public interface CustomerDAO {	
	
	public void setCustomerRow(String query, ResultSet rs);
	public void setCustomerRow(String query, ResultSet rs, int ttl);
	
	public ResultSet getCustomerRow(int customerID);
	
	public void setCustomerMap(Customer customer);
	public void setCustomerMap(Customer customer, int ttl);	
	
	public Map<String, String> getCustomerMap(int customerID);
	
	public void setCustomerObject(Customer customer);	
	public void setCustomerObject(Customer customer, int ttl);	
	
	public Customer getCustomerObject(int customerID);	

}
