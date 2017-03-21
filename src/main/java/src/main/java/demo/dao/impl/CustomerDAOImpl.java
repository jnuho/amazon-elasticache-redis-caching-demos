//author: mlabib@amazon.com

package src.main.java.demo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;
import src.main.java.demo.dao.CacheDAO;
import src.main.java.demo.dao.CustomerDAO;
import src.main.java.demo.utils.JedisConnection;
import src.main.java.demo.utils.RDSConnection;
import src.main.java.demo.utils.SQLQueries;
import src.main.java.demo.pojo.*;

public class CustomerDAOImpl implements CustomerDAO {

	Connection connection = RDSConnection.getConnection();
	
	Jedis jedis = JedisConnection.getConnection();
	
	CacheDAO cache = new CacheDAOImpl();
	
	SQLQueries sql = new SQLQueries();
	
	Customer customer = new Customer();
	
	Statement stmt = null;
	
	String query, name = null;

	public ResultSet getCustomerRow(int customerID) {

		query = sql.getCustomerRow + customerID;

		ResultSet rs = cache.getRow(query);

		try {

			if (rs != null)

				System.out.println("** check cache: success [HIT], found ROW for query **\n");

			else {

				stmt = connection.createStatement();

				rs = stmt.executeQuery(query);

				System.out.println("** check cache: not found [MISS], fetching ROW from database **\n");

				System.out.println("** update cache: [LAZYLOAD] cache ROW for TTL 300 seconds **\n");

				cache.setRow(query, rs, 300);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				connection.close();

			} catch (SQLException e) {

				e.printStackTrace();

			}

		}

		return rs;

	}

	public void setCustomerRow(String query, ResultSet rs) {

		cache.setRow(query, rs);

	}

	public void setCustomerRow(String query, ResultSet rs, int ttl) {

		cache.setRow(query, rs, ttl);

	}

	public Customer getCustomerObject(int customerID) {

		try {

			if (cache.getObject("object:customer:id:"+String.valueOf(customerID)) != null)

				System.out.println("** check cache: success [HIT], found Object for customerID:" +customerID+" **\n");

			else {

				System.out.println("** check cache: not found [MISS], fetching Object from database **\n");

				ResultSet rs = getCustomerRow(customerID);

				rs.beforeFirst();

				while (rs.next()) {
					
					System.out.println("** setting customer object with DB ResultSet **\n");

					Customer customer = new Customer();

					customer.setCustomerID(String.valueOf(rs.getInt("CUSTOMER_ID")));
					
					customer.setAddress(rs.getString("ADDRESS"));
					
					customer.setCity(rs.getString("CITY"));
					
					customer.setCountry(rs.getString("COUNTRY"));
					
					customer.setFirstName(rs.getString("FIRST_NAME"));
					
					customer.setLastName(rs.getString("LAST_NAME"));
					
					customer.setGender(rs.getString("GENDER"));
					
					customer.setEmail(rs.getString("EMAIL"));

					System.out.println("** update cache: [LAZYLOAD] cache Object for TTL 300 seconds **\n");
					
					cache.setObject("object:customer:id:"+customer.getCustomerID(), customer, 300);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return cache.getObject("object:customer:id:"+String.valueOf(customerID));

	}

	public void setCustomerObject(Customer customer) {

		cache.setObject("object:customer:id:"+customer.getCustomerID(),customer);

	}

	public void setCustomerObject(Customer customer, int ttl) {

		cache.setObject("object:customer:id:"+customer.getCustomerID(), customer, ttl);

	}

	public Map<String, String> getCustomerMap(int customerID) {

		Map<String, String> map = cache.getMap("hash:customer:id:"+String.valueOf(customerID));

		if (!map.isEmpty())

			System.out.println("\n** check cache: success [HIT], found HASH for customerID:" +customerID+" **\n");

		else {

			System.out.println("\n** check cache: not found [MISS] , fetching HASH from database **\n");

			customer = getCustomerObject(customerID);

			System.out.println("** update cache: [LAZYLOAD] cache HASH for TTL 300 seconds **\n");

			setCustomerMap(customer, 300);

			map = cache.getMap("hash:customer:id:"+String.valueOf(customerID));

		}

		return map;
	}

	public void setCustomerMap(Customer customer) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("customerID", customer.getCustomerID());
		
		map.put("address", customer.getAddress());
		
		map.put("city", customer.getCity());
		
		map.put("country", customer.getCountry());
		
		map.put("firstName", customer.getFirstName());
		
		map.put("lastName", customer.getFirstName());
		
		map.put("gender", customer.getGender());
		
		map.put("email", customer.getEmail());
		
		cache.setMap("hash:customer:id:"+customer.getCustomerID(), map);

	}

	public void setCustomerMap(Customer customer, int ttl) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("customerID", customer.getCustomerID());
		
		map.put("address", customer.getAddress());
		
		map.put("city", customer.getCity());
		
		map.put("country", customer.getCountry());
		
		map.put("firstName", customer.getFirstName());
		
		map.put("lastName", customer.getFirstName());
		
		map.put("gender", customer.getGender());
		
		map.put("email", customer.getEmail());

		cache.setMap("hash:customer:id:"+customer.getCustomerID(), map, ttl);

	}

}
