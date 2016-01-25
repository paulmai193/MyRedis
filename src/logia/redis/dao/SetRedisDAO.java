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
		Redis redis = new Redis();
		try {
			redis.getJedis().sadd(data.getKey(), member);
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
}
