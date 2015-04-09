package logia.redis.dao;

import logia.redis.data.HashRedisClass;
import logia.redis.util.Redis;

/**
 * The Class HashRedisDAO.
 *
 * @param <T> the generic type
 * 
 * @author Paul Mai
 */
public abstract class HashRedisDAO<T extends HashRedisClass> extends AbstractRedisDAO<HashRedisClass> {
	
	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the t
	 */
	public abstract T get(String key);	

	/**
	 * Gets the hash field.
	 *
	 * @param data the data
	 * @param field the field
	 * @return the hash field
	 */
	public String getHashField(T data, String field) {
		Redis redis = new Redis();
		String value = null;
		try {
			value = redis.getJedis().hget(data.getKey(), field);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			redis.quitJedis();
		}
		return value;
	}
	
	/**
	 * Sets the.
	 *
	 * @param data the data
	 * @return true, if successful
	 */
	public boolean set(T data) {
		Redis redis = new Redis();
		try {
			redis.getJedis().hmset(data.getKey(), data.getValue());
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		finally {
			redis.quitJedis();
		}
	}
	
	/**
	 * Del hash field.
	 *
	 * @param data the data
	 * @param field the field
	 * @return true, if successful
	 */
	public boolean delHashField(T data, String field) {
		Redis redis = new Redis();
		try {
			redis.getJedis().hdel(data.getKey(), field);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		finally {
			redis.quitJedis();
		}
	}
}
