package logia.redis.dao;

import logia.redis.data.StringRedisClass;
import logia.redis.util.Redis;

/**
 * The Class PojoRedisDAO.
 *
 * @param <V> the value type
 * @param <T> the generic type
 * 
 * @author Paul Mai
 */
public abstract class StringRedisDAO<T extends StringRedisClass> extends AbstractRedisDAO<StringRedisClass> {
	
	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the t
	 */
	public abstract T get(String key);	

	/**
	 * Sets the.
	 *
	 * @param data the data
	 * @return true, if successful
	 */
	public boolean set(T data) {
		Redis redis = new Redis();
		try {
			redis.getJedis().set(data.getKey(), data.getValue());
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
