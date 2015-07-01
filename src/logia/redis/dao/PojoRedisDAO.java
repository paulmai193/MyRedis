package logia.redis.dao;

import java.io.Serializable;

import logia.redis.data.PojoRedisClass;
import logia.redis.util.Redis;

import org.apache.commons.lang3.SerializationUtils;

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
	 * Instantiates a new pojo redis dao.
	 *
	 * @param redis the redis
	 */
	public PojoRedisDAO(Redis redis) {
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
	 * @return true, if successful
	 */
	public abstract boolean set(T data);

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the byte[]
	 */
	private synchronized byte[] getByteArray(String key) {
		try {
			if (key != null && key.trim().length() > 0) {
				return this.redis.getJedis().get(key.getBytes());
			}
		}
		catch (Exception e) {
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
	protected synchronized V getObject(String key) {
		if (key != null && key.trim().length() > 0) {
			byte[] data = getByteArray(key);
			if (data != null) {
				try {
					return (V) SerializationUtils.deserialize(data);
				}
				catch (Exception e) {
					System.err.println("Error when Deserialize object with key: " + key);
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
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	private synchronized boolean setByteArray(String key, byte[] data) throws Exception {
		if (key != null && key.trim().length() > 0 && data != null) {
			this.redis.getTransaction().set(key.getBytes(), data);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Sets the object to redis database by key.
	 *
	 * @param key the key
	 * @param object the object
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	protected synchronized boolean setObject(String key, V object) throws Exception {
		if (key != null && key.trim().length() > 0 && object != null) {
			byte[] data = SerializationUtils.serialize((Serializable) object);
			return setByteArray(key, data);
		}
		return false;
	}
}
