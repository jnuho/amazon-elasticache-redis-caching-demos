//author: mlabib@amazon.com

package src.main.java.demo.dao;

import java.sql.ResultSet;
import java.util.Map;

import src.main.java.demo.pojo.Customer;

public interface CacheDAO {	
	
	public void setRow(String key, ResultSet rs);
	public void setRow(String key, ResultSet rs, int ttl);
	
	public ResultSet getRow(String key);		
	
	public void setObject(String key, Object object);	
	public void setObject(String key, Object object, int ttl);
	
	public Customer getObject(String key);
	
	public void setMap(String key, Map<String, String> map);
	public void setMap(String key, Map<String, String> map, int ttl);
	
	public Map<String, String> getMap(String key);
	
}
