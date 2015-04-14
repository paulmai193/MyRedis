package logia.redis.dao;

import java.util.HashSet;
import java.util.Set;

import logia.redis.data.KeyRedisClass;
import logia.redis.util.Redis;

/**
 * The Class AbstractRedisDAO.
 *
 * @param <T> the generic type
 * @param <V> the value type
 * 
 * @author Paul Mai
 */
abstract class AbstractRedisDAO<T extends KeyRedisClass> {

	/** The redis. */
	Redis redis;
	
	public AbstractRedisDAO(Redis redis) {
		this.redis = redis;
	}
	
	/**
	 * Gets the prefix key.
	 *
	 * @return the prefix key
	 */
	public abstract String getPrefixKey();

	/**
	 * Expired.
	 *
	 * @param key the key
	 * @param seconds the seconds
	 * @return true, if successful
	 */
	public boolean expired(String key, int seconds) {
		try {
			redis.getTransaction().expire(key, seconds);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
		}
		return false;
	}

	/**
	 * Persist.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean persist(String key) {
		try {
			redis.getTransaction().persist(key);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
		}
		return false;
	}

	/**
	 * Del.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean del(String key) {
		try {
			redis.getTransaction().del(key);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
		}
		return false;
	}

	/**
	 * Gets the keys.
	 *
	 * @param pattern the pattern
	 * @return the keys
	 */
	public Set<String> getKeys(String pattern) {
		Set<String> keys = new HashSet<String>();
		try {
			keys = redis.getJedis().keys(pattern);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return keys;
	}

}
