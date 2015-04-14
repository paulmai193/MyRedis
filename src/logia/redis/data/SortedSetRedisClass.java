package logia.redis.data;

import java.util.HashMap;
import java.util.Map;
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
	
	/**
	 * Gets the values as map.
	 *
	 * @return the values as map
	 */
	public Map<String, Double> getValuesAsMap() {
		Map<String, Double> mapValues = new HashMap<String, Double>();
		for (Tuple tuple : value) {
			mapValues.put(tuple.getElement(), tuple.getScore());
		}
		return mapValues;
	}
}
