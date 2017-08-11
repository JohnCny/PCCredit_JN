package com.cardpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import com.cardpay.pbccrcReport.util.RedisPropertyUtil;

public class RedisUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	// Redis服务器IP
	private static String ADDR;

	// Redis的端口号
	private static int PORT;

	// 访问密码,若你的redis服务器没有设置密码，就不需要用密码去连接
	private static String AUTH;

	private static int MAX_ACTIVE;

	private static int MAX_IDLE;

	private static int MAX_WAIT;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			RedisPropertyUtil.reLoad();
			String ADDR = RedisPropertyUtil.getPropertyByKey("redis.host");
			int PORT = Integer.parseInt(RedisPropertyUtil.getPropertyByKey("redis.port"));
		  //String AUTH = RedisPropertyUtil.getPropertyByKey("redis.pass");//服务器上如果有设置密码则打开
			String MAX_IDLE = RedisPropertyUtil.getPropertyByKey("redis.maxIdle");
			String MAX_ACTIVE = RedisPropertyUtil.getPropertyByKey("redis.maxActive");
			String MAX_WAIT = RedisPropertyUtil.getPropertyByKey("redis.maxWait");
			
			logger.info("*******************************ip地址:"+ADDR);
			logger.info("*******************************port端口:"+PORT);
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(Integer.parseInt(MAX_ACTIVE));
			config.setMaxIdle(Integer.parseInt(MAX_IDLE));
			config.setMaxWait(Integer.parseInt(MAX_WAIT));
			config.setTestOnBorrow(true);
			
			//jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);//服务器上如果有设置密码则打开
			jedisPool = new JedisPool(config, ADDR, PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
				return jedis;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
		    if (null != jedis) {
		    	returnResource(jedis);
		    }
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * @desc 测试读取redis.properties
	 * @time 2017年8月1日15:27:32
	 * @author songchen
	 */
	/*public static void main(String[] args) {
		try {
			RedisPropertyUtil.reLoad();
			String ADDR = RedisPropertyUtil.getPropertyByKey("redis.host");
			int PORT = Integer.parseInt(RedisPropertyUtil.getPropertyByKey("redis.port"));
			//String AUTH = RedisPropertyUtil.getPropertyByKey("redis.pass");
			String MAX_IDLE = RedisPropertyUtil.getPropertyByKey("redis.maxIdle");
			String MAX_ACTIVE = RedisPropertyUtil.getPropertyByKey("redis.maxActive");
			String MAX_WAIT = RedisPropertyUtil.getPropertyByKey("redis.maxWait");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}