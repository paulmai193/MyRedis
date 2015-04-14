package logia.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * The Class Redis.
 * 
 * @author Paul Mai
 */
public class Redis {

	/** The jedis factory. */
	private JedisFactory jedisFactory = JedisFactory.getInstance();
	
	/** The jedis. */
	private Jedis jedis;
	
	/** The transaction. */
	private Transaction transaction;

	/**
	 * Instantiates a new redis.
	 */
	public Redis() {
		initFromPool();
	}

	/**
	 * Inits the jedis connection from pool.
	 */
	public void initFromPool() {
		this.jedis = jedisFactory.getResource();
		this.jedis.getClient().setConnectionTimeout(60);
		this.jedis.select(JedisFactory.getDbIndex());
	}

	/**
	 * Gets the jedis connection.
	 *
	 * @return the jedis
	 */
	public Jedis getJedis() {
		return this.jedis;
	}

	/**
	 * Return jedis connection to pool.
	 */
	public void quitJedis() {
		this.jedis.close();
		this.jedis = null;
	}
	
	/**
	 * Begin transaction.
	 */
	public void beginTransaction() {
		this.transaction = this.jedis.multi();
	}
	
	/**
	 * Gets the transaction.
	 *
	 * @return the transaction
	 */
	public Transaction getTransaction() {
		return transaction;
	}
	
	/**
	 * Execute transaction.
	 */
	public void execTransaction() {
		this.transaction.exec();
	}
	
	/**
	 * Discard transaction.
	 */
	public void discardTransaction() {
		this.transaction.discard();
	}
}
