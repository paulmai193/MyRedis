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
	 */
	public void expired(String key, int seconds) {
		Redis redis = new Redis();
		try {
			redis.getJedis().expire(key, seconds);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			redis.quitJedis();
		}
	}

	/**
	 * Persist.
	 *
	 * @param key the key
	 */
	public void persist(String key) {
		Redis redis = new Redis();
		try {
			redis.getJedis().persist(key);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			redis.quitJedis();
		}
	}

	/**
	 * Del.
	 *
	 * @param key the key
	 */
	public void del(String key) {
		Redis redis = new Redis();
		try {
			redis.getJedis().del(key);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			redis.quitJedis();
		}
	}

	/**
	 * Gets the keys.
	 *
	 * @param pattern the pattern
	 * @return the keys
	 */
	public Set<String> getKeys(String pattern) {
		Redis redis = new Redis();
		Set<String> keys = new HashSet<String>();
		try {
			keys = redis.getJedis().keys(pattern);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			redis.quitJedis();
		}
		return keys;
	}

}
