package logia.redis.data;

import java.util.Set;

/**
 * The Class SetRedisClass.
 * 
 * @author Paul Mai
 */
public class SetRedisClass extends KeyRedisClass {
	
	/** The value. */
	Set<String> value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Set<String> getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Set<String> value) {
		this.value = value;
	}
}
