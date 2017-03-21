//author: mlabib@amazon.com

package src.main.java.demo.utils;

import redis.clients.jedis.Jedis;

public class JedisConnection {


	public static Jedis getConnection() {

		Jedis jedis = new Jedis("your-endpoint", 6379);

		return jedis;


	}

}
