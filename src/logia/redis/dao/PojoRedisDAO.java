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
		Redis redis = new Redis();
		byte[] arr = null;
		try {
			if (key != null && key.trim().length() > 0) {
				arr = redis.getJedis().get(key.getBytes());
			}
		}
		catch (Exception e) {
			this.LOGGER.error(e.getMessage(), e);
		}
		finally {
			redis.quitJedis();
		}
		return arr;
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
				return (V) SerializationUtils.deserialize(data);
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
	 */
	private synchronized boolean setByteArray(String key, byte[] data) {
		if (key != null && key.trim().length() > 0 && data != null) {
			Redis redis = new Redis();
			try {
				redis.getJedis().set(key.getBytes(), data);
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
	protected synchronized boolean setObject(String key, V object) {
		if (key != null && key.trim().length() > 0 && object != null) {
			byte[] data = SerializationUtils.serialize((Serializable) object);
			return setByteArray(key, data);
		}
		return false;
	}
}
