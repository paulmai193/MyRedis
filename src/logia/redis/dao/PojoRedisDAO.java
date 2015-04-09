package logia.redis.dao;

import logia.redis.data.PojoRedisClass;


/**
 * The Class PojoRedisDAO.
 *
 * @param <V> the value type
 * @param <T> the generic type
 * 
 * @author Paul Mai
 */
public abstract class PojoRedisDAO<V, T extends PojoRedisClass<V>> extends AbstractRedisDAO<PojoRedisClass<V>> {
	
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
	public abstract boolean set(T data);
}
