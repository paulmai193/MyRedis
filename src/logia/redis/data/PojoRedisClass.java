package logia.redis.data;

/**
 * The Class PojoRedisClass.
 *
 * @param <V> the value type
 * @author Paul Mai
 */
public class PojoRedisClass<V> extends KeyRedisClass {
	
	/** The value. */
	V value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(V value) {
		this.value = value;
	}
}
