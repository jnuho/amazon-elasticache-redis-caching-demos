//author: mlabib@amazon.com

package src.main.java.demo.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;
import redis.clients.jedis.Jedis;
import src.main.java.demo.dao.CacheDAO;
import src.main.java.demo.pojo.Customer;
import src.main.java.demo.utils.JedisConnection;

public class CacheDAOImpl implements CacheDAO {

	static Jedis jedis = JedisConnection.getConnection();

	private CachedRowSet cachedRowSet;

	public void setRow(String query, ResultSet rs) {

		try {

			cachedRowSet = new CachedRowSetImpl();

			if (rs != null) {

				cachedRowSet.populate(rs, 1);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				ObjectOutput out = null;

				out = new ObjectOutputStream(bos);

				out.writeObject(cachedRowSet);

				byte[] redisRSValue = bos.toByteArray();

				jedis.set(query.getBytes(), redisRSValue);

			}

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

	}

	public void setRow(String key, ResultSet rs, int ttl) {

		try {

			cachedRowSet = new CachedRowSetImpl();

			if (rs != null) {

				cachedRowSet.populate(rs, 1);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				ObjectOutput out = new ObjectOutputStream(bos);

				out.writeObject(cachedRowSet);

				byte[] redisRSValue = bos.toByteArray();

				jedis.set(key.getBytes(), redisRSValue);

				jedis.expire(key.getBytes(), ttl);

			}

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

	}

	public ResultSet getRow(String key) {

		byte[] redisResultSet = null;

		redisResultSet = jedis.get(key.getBytes());

		ResultSet rs = null;

		if (redisResultSet != null) {

			try {

				cachedRowSet = new CachedRowSetImpl();

				ByteArrayInputStream bis = new ByteArrayInputStream(redisResultSet);

				ObjectInput in = new ObjectInputStream(bis);

				cachedRowSet.populate((CachedRowSet) in.readObject());

				rs = cachedRowSet;

			} catch (Exception e) {
				
				e.printStackTrace();

			} finally {

				jedis.close();
			}

		}

		return rs;

	}

	public void setObject(String key, Object object) {
		
		Customer customer = (Customer) object;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		ObjectOutput out = null;

		try {

			out = new ObjectOutputStream(bos);
			
			out.writeObject(customer);
			
			out.flush();
			
			byte[] objectValue = bos.toByteArray();

			jedis.set(key.getBytes(), objectValue);

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

	};

	public void setObject(String key, Object object, int ttl) {
		
		Customer customer = (Customer) object;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		ObjectOutput out = null;

		try {

			out = new ObjectOutputStream(bos);
			
			out.writeObject(customer);
			
			out.flush();
			
			byte[] objectValue = bos.toByteArray();

			jedis.set(key.getBytes(), objectValue);

			jedis.expire(key.getBytes(), ttl);
	
		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

	};

	public Customer getObject(String key) {

		Customer customer = null;

		byte[] redisObject = null;

		redisObject = jedis.get(key.getBytes());

		if (redisObject != null) {

			try {

				ByteArrayInputStream in = new ByteArrayInputStream(redisObject);

				ObjectInputStream is = new ObjectInputStream(in);

				customer = (Customer) is.readObject();

			} catch (Exception e) {
				
				e.printStackTrace();

			} finally {

				jedis.close();
			}

		}

		return customer;

	};

	public Map<String, String> getMap(String key) {

		Map<String, String> map = new HashMap<String, String>();

		try {

			map = jedis.hgetAll(key);

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

		return map;
	}

	public void setMap(String key, Map<String, String> map) {

		try {

			jedis.hmset(key, map);

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

	}

	public void setMap(String key, Map<String, String> map, int ttl) {

		try {

			jedis.hmset(key, map);
			
			jedis.expire(key, ttl);

		} catch (Exception e) {
			
			e.printStackTrace();

		} finally {

			jedis.close();
		}

	}

}