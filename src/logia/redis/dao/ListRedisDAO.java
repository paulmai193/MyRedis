package logia.redis.dao;

import logia.redis.data.ListRedisClass;
import logia.redis.util.Redis;

/**
 * The Class ListRedisDAO.
 *
 * @param <T> the generic type
 * 
 * @author Paul Mai
 */
public abstract class ListRedisDAO<T extends ListRedisClass> extends AbstractRedisDAO<ListRedisClass> {

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @param start the start index
	 * @param end the end index
	 * @return the t
	 */
	public abstract T get(String key, long start, long end);

	/**
	 * Sets the.
	 *
	 * @param data the data
	 * @param index the index
	 * @param element the element
	 * @param strings
	 * @return true, if successful
	 */
	public boolean set(T data, String element) {
		Redis redis = new Redis();
		try {
			redis.getJedis().lpush(data.getKey(), element);
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
	 * Del list element.
	 *
	 * @param data the data
	 * @param count the count.<br>
	 *        * count > 0: Remove elements equal to value moving from head to tail;<br>
	 *        * count < 0: Remove elements equal to value moving from tail to head;<br>
	 *        * count = 0: Remove all elements equal to value.
	 * @param element the element
	 * @return true, if successful
	 */
	public boolean delListElement(T data, long count, String element) {
		Redis redis = new Redis();
		try {
			redis.getJedis().lrem(data.getKey(), count, element);
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
