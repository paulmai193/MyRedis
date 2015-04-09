package logia.redis.data;

/**
 * The Class StringRedisClass.
 *
 * @param <V> the value type
 * @author Paul Mai
 */
public class StringRedisClass extends KeyRedisClass {
	
	/** The value. */
	String value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
