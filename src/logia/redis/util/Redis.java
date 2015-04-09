package logia.redis.util;

import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * The Class Redis.
 * 
 * @author Paul Mai
 */
public class Redis {

	/** The jedis factory. */
	private JedisFactory jedisFactory = JedisFactory.getInstance();
	
	/** The jedis. */
	private Jedis jedis;

	/**
	 * Instantiates a new redis.
	 */
	public Redis() {
		initFromPool();
	}

	/**
	 * Inits the jedis connection from pool.
	 */
	public void initFromPool() {
		this.jedis = jedisFactory.getResource();
		this.jedis.getClient().setConnectionTimeout(60);
		this.jedis.select(JedisFactory.getDbIndex());
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the byte[]
	 */
	private synchronized byte[] get(String key) {
		try {
			if (key != null && key.trim().length() > 0) {
				return this.jedis.get(key.getBytes());
			}
		} catch (JedisConnectionException e) {
			this.jedis.close();
			initFromPool();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Gets the object from redis database by key.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @return the object
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T> T getObject(String key) {
		if (key != null && key.trim().length() > 0) {
			byte[] data = get(key);
			if (data != null) {
				try {
					return (T) SerializationUtils.deserialize(data);
				} catch (Exception e) {
					System.err.println("Error when Deserialize object with key: "
							+ key);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Sets the.
	 *
	 * @param key the key
	 * @param data the data
	 */
	private synchronized void set(String key, byte[] data) {
		try {
			if (key != null && key.trim().length() > 0 && data != null) {
				this.jedis.set(key.getBytes(), data);
			}
		} catch (JedisConnectionException e) {
			this.jedis.close();
			initFromPool();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Sets the object to redis database by key.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param object the object
	 */
	public synchronized <T> void set(String key, T object) {
		if (key != null && key.trim().length() > 0 && object != null) {
			try {
				byte[] data = SerializationUtils
						.serialize((Serializable) object);
				set(key, data);
			} catch (Exception e) {
				System.err.println("Error when Serialize object with key: " + key);
			}
		}
	}

	/**
	 * Gets the jedis connection.
	 *
	 * @return the jedis
	 */
	public Jedis getJedis() {
		return this.jedis;
	}

	/**
	 * Return jedis connection to pool.
	 */
	public void quitJedis() {
		this.jedis.close();
		this.jedis = null;
	}
}
