package logia.redis.dao;

import java.util.HashSet;
import java.util.Set;

import logia.redis.data.KeyRedisClass;
import logia.redis.util.Redis;

import org.apache.log4j.Logger;

/**
 * The Class AbstractRedisDAO.
 *
 * @param <T> the generic type
 * @param <V> the value type
 * 
 * @author Paul Mai
 */
abstract class AbstractRedisDAO<T extends KeyRedisClass> {

	/** The logger. */
	protected final Logger LOGGER = Logger.getLogger(getClass());

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
		Redis redis = new Redis();
		try {
			redis.getJedis().expire(key, seconds);
			return true;
		}
		catch (Exception e) {
			this.LOGGER.error(e.getMessage(), e);
			return false;
		}
		finally {
			redis.quitJedis();
		}
	}

	/**
	 * Persist.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean persist(String key) {
		Redis redis = new Redis();
		try {
			redis.getJedis().persist(key);
			return true;
		}
		catch (Exception e) {
			this.LOGGER.error(e.getMessage(), e);
			return false;
		}
		finally {
			redis.quitJedis();
		}
	}

	/**
	 * Del.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean del(String key) {
		Redis redis = new Redis();
		try {
			redis.getJedis().del(key);
			return true;
		}
		catch (Exception e) {
			this.LOGGER.error(e.getMessage(), e);
			return false;
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
			this.LOGGER.error(e.getMessage(), e);
		}
		finally {
			redis.quitJedis();
		}
		return keys;
	}

}
