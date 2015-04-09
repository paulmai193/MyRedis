package logia.redis.data;

import java.util.Map;

/**
 * The Class HashRedisClass.
 * 
 * @author Paul Mai
 */
public class HashRedisClass extends KeyRedisClass {
	
	/** The value. */
	Map<String, String> value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Map<String, String> getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Map<String, String> value) {
		this.value = value;
	}
}
