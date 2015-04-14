package logia.redis.dao;

import java.util.Map;

import logia.redis.data.SortedSetRedisClass;
import logia.redis.util.Redis;

/**
 * The Class SetRedisDAO.
 *
 * @param <T> the generic type
 * 
 * @author Paul Mai
 */
public abstract class SortedSetRedisDAO<T extends SortedSetRedisClass> extends AbstractRedisDAO<SortedSetRedisClass> {
	
	/**
	 * Instantiates a new sorted set redis dao.
	 *
	 * @param redis the redis
	 */
	public SortedSetRedisDAO(Redis redis) {
		super(redis);
	}

	/**
	 * Gets the by score.
	 *
	 * @param key the key
	 * @param minScore the min score
	 * @param maxScore the max score
	 * @return the by score
	 */
	public abstract T getByScore(String key, double minScore, double maxScore);
	
	/**
	 * Gets the by index.
	 *
	 * @param key the key
	 * @param start the start
	 * @param end the end
	 * @return the by index
	 */
	public abstract T getByIndex(String key, long start, long end);

	/**
	 * Gets the sort set member score.
	 *
	 * @param data the data
	 * @param member the member
	 * @return the sort set member score
	 */
	public Double getSortSetMemberScore(T data, String member) {
		Double value = null;
		try {
			value = redis.getJedis().zscore(data.getKey(), member);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return value;
	}
	
	/**
	 * Sets the.
	 *
	 * @param data the data
	 * @param score the score
	 * @param member the member
	 * @return true, if successful
	 */
	public boolean set(T data, double score, String member) {
		try {
			redis.getJedis().zadd(data.getKey(), score, member);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
			return false;
		}
	}
	
	/**
	 * Sets the.
	 *
	 * @param data the data
	 * @param scoremembers the scoremembers
	 * @return true, if successful
	 */
	public boolean set(T data, Map<String, Double> scoremembers) {
		try {
			redis.getJedis().zadd(data.getKey(), scoremembers);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
			return false;
		}
	}
	
	/**
	 * Del sort set member.
	 *
	 * @param data the data
	 * @param members the members
	 * @return true, if successful
	 */
	public boolean delSortSetMember(T data, String members) {
		try {
			redis.getJedis().zrem(data.getKey(), members);
			return true;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			redis.discardTransaction();
			return false;
		}
	}
}
