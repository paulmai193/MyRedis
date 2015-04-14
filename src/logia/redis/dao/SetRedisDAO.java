package logia.redis.dao;

import logia.redis.data.SetRedisClass;
import logia.redis.util.Redis;

/**
 * The Class SetRedisDAO.
 *
 * @param <T> the generic type
 * 
 * @author Paul Mai
 */
public abstract class SetRedisDAO<T extends SetRedisClass> extends AbstractRedisDAO<SetRedisClass> {
	
	/**
	 * Instantiates a new sets the redis dao.
	 *
	 * @param redis the redis
	 */
	public SetRedisDAO(Redis redis) {
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
	 * Sets the.
	 *
	 * @param data the data
	 * @param member the member
	 * @return true, if successful
	 */
	public boolean set(T data, String member) {
		try {
			redis.getTransaction().sadd(data.getKey(), member);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
			return false;
		}
	}
}
