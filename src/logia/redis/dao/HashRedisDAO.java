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
	 * Instantiates a new hash redis dao.
	 *
	 * @param redis the redis
	 */
	public HashRedisDAO(Redis redis) {
		super(redis);
	}

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
		String value = null;
		try {
			value = redis.getJedis().hget(data.getKey(), field);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
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
		try {
			redis.getTransaction().hmset(data.getKey(), data.getValue());
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
			return false;
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
		try {
			redis.getTransaction().hdel(data.getKey(), field);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
			return false;
		}
		finally {
			redis.quitJedis();
		}
	}
}
