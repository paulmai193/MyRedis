package logia.redis.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * A factory for creating Jedis objects.
 * 
 * @author Paul Mai
 */
public class JedisFactory {

	/** The Constant instance. */
	private static final JedisFactory instance = new JedisFactory();

	/** The pool. */
	private static JedisPool          pool;

	/** The host. */
	private String                    host;

	/** The port. */
	private int                       port;

	/** The max connection. */
	private int                       maxConnection;

	/** The selected database index. */
	private int                       dbIndex;

	/** The time out. */
	private int                       timeOut;

	/**
	 * Gets the single instance of JedisFactory.
	 *
	 * @return single instance of JedisFactory
	 */
	public final static JedisFactory getInstance() {
		return instance;
	}

	/**
	 * Gets the selected database index.
	 *
	 * @return the db index
	 */
	public int getDbIndex() {
		return dbIndex;
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		if (pool == null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Connect.
	 *
	 * @param host the host
	 * @param port the port
	 * @param maxConnection the max connection
	 * @param index the DB index
	 * @param timeout the timeout
	 */
	public void connect(String host, int port, int maxConnection, int index, int timeout) {
		this.host = host;
		this.port = port;
		this.maxConnection = maxConnection;
		this.dbIndex = index;
		this.timeOut = timeout;

		// Create and set a JedisPoolConfig
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

		// Maximum active connections to Redis instance
		poolConfig.setMaxTotal(maxConnection);

		// Tests whether connection is dead when connection
		// retrieval method is called
		poolConfig.setTestOnBorrow(true);

		/* Some extra configuration */
		// Tests whether connection is dead when returning a
		// connection to the pool
		poolConfig.setTestOnReturn(true);

		// Number of connections to Redis that just sit there
		// and do nothing
		poolConfig.setMaxIdle(5);

		// Minimum number of idle connections to Redis
		// These can be seen as always open and ready to serve
		poolConfig.setMinIdle(1);

		// Tests whether connections are dead during idle periods
		poolConfig.setTestWhileIdle(true);

		// Maximum number of connections to test in each idle check
		poolConfig.setNumTestsPerEvictionRun(10);

		// Idle connection checking period
		poolConfig.setTimeBetweenEvictionRunsMillis(60000);

		// Create the jedisPool
		pool = new JedisPool(poolConfig, host, port, timeout, null, index, null);

		System.out.println("Jedis pool connected on host: " + this.host + ", port: " + this.port);
	}

	/**
	 * Release.
	 */
	public void release() {
		pool.destroy();
		pool = null;
		System.out.println("Jedis pool disconnected");
	}

	/**
	 * Gets the resource.
	 *
	 * @return the resource
	 */
	public Jedis getResource() {
		if (!isConnected()) {
			connect(host, port, maxConnection, dbIndex, timeOut);
		}
		return pool.getResource();
	}
}
