package logia.redis.data;

import java.util.Set;

import redis.clients.jedis.Tuple;

/**
 * The Class SortedSetRedisClass.
 * 
 * @author Paul Mai
 */
public class SortedSetRedisClass extends KeyRedisClass {
	
	/** The value. */
	Set<Tuple> value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Set<Tuple> getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Set<Tuple> value) {
		this.value = value;
	}
}
