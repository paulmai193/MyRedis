package logia.redis.data;

import java.util.List;

/**
 * The Class ListRedisClass.
 * 
 * @author Paul Mai
 */
public class ListRedisClass extends KeyRedisClass {
	
	/** The value. */
	List<String> value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public List<String> getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(List<String> value) {
		this.value = value;
	}
}
