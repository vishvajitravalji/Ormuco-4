
/*
 * Question C
At Ormuco, we want to optimize every bits of software we write.
Your goal is to write a new library that can be integrated to the Ormuco stack.
Dealing with network issues everyday, latency is our biggest problem. 
Thus, your challenge is to write a new Geo Distributed LRU (Least Recently Used) cache with time expiration.
This library will be used extensively by many of our services so it needs to meet the following criteria:

 
    1 - Simplicity. Integration needs to be dead simple.

    2 - Resilient to network failures or crashes.

    3 - Near real time replication of data across Geolocation. Writes need to be in real time.

    4 - Data consistency across regions

    5 - Locality of reference, data should almost always be available from the closest region

    6 - Flexible Schema

    7 - Cache can expire
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

/**
 *  LRU (Least Recently Used) cache with time expiration.
 *  
 *  The class makes a simple LRU cache. The maximum size and TimeToLive(TTL) of the cache can be 
 *  set through the constructor when creating a cache object. The LRU cache works on two priciples.
 *  1) If a data item is added to cache after the maximum size is reached, the cache removes the least Recently used
 *  item to make space.
 *  2) Each item has a timeToLive. Once added to cache, if the item is not accessed in its TTL, it will be removed.
 *  Every time the item is accessed, the TTL refreshes.
 *
 * @author MUSTAFA
 *
 * @param <K>
 * @param <T>
 */

public class Question_C_cache<K, T> {
	// capacity of the cache(in number of items) after which least used item is
	// deleted
	private int capacity;
	// time after which cache object will be deleted
	private long timeToLive;
	// Cache made of a MAP of key object and Value of Cache Object
	private HashMap<K, CacheObject<T>> myCache;

	/**
	 * template for a cache object
	 * 
	 * @author MUSTAFA
	 * @param <T>
	 */
	protected class CacheObject<T> {
		public long lastAccessed = System.currentTimeMillis();
		public T value;

		protected CacheObject(T value) {
			this.value = (T) value;
		}
	}

	public Question_C_cache(long TimeToLive, int maxItems) {
		myCache = new HashMap();
		// change time to live to ms and set to current cache
		this.timeToLive = TimeToLive * 1000;
		// set capacity of the cache
		this.capacity = maxItems;

		// Run a thread that clears items after their time exceeds TTL
		if (timeToLive > 0 ) {
		long TimerInterval = TimeToLive/100;
			Thread t = new Thread(new Runnable() {
				public void run() {
					// loop indefinitely and call clearCache method after a certain Timeinterval
					while (true) {
						try {
							Thread.sleep(TimerInterval * 1000);
						} catch (InterruptedException ex) {
						}
						clearChache();
					}
				}
			});

			t.setDaemon(true);
			t.start();
		}
	}

	protected void clearChache() {
		// time termination of the cache object
		long time_now = System.currentTimeMillis();
		K key = null;
		CacheObject object = null;
		ArrayList<K> expired_keys = null;
		// iterate through the cache
		Set entrySet = myCache.entrySet();
		Iterator itr = entrySet.iterator();

		while (itr.hasNext()) {

			Entry entry = (Entry) itr.next();
			key = (K) entry.getKey();
			object = (CacheObject) entry.getValue();
			expired_keys = new ArrayList<K>(this.capacity);
			// check if current time is > last accessed + time to live i.e object time has
			// expired and add item for removal
			if (time_now > object.lastAccessed + this.timeToLive) {
				expired_keys.add(key);
			}

		}

		// Remove the expired keys
		if (expired_keys != null) {
			for (K key2remove : expired_keys) {
				synchronized (myCache) {
					this.remove(key2remove);
				}
				// Thread.yield();
			}
		}
	}

	public void put(K key, T value) {
		// Lock the thread
		int size = this.size();
		synchronized (myCache) {
			if ( size < this.capacity) {
				myCache.put(key, new CacheObject<T>(value));
			} else {
				// remove the least used item
				long leastUsed_lastAccessed = System.currentTimeMillis();
				K leastUsed = null;
				// loop through Map to find least used key ( smallest last accessed)
				for (Entry<K, Question_C_cache<K, T>.CacheObject<T>> entry : myCache.entrySet()) {
					if (entry.getValue().lastAccessed < leastUsed_lastAccessed) {
						leastUsed = entry.getKey();
						leastUsed_lastAccessed = entry.getValue().lastAccessed;
					}
				}
				this.remove(leastUsed);
				synchronized (myCache) {
					myCache.put(key, new CacheObject<T>(value));
				}
			}
		}
	}

	public T get(K key) {
		CacheObject<T> data = (CacheObject<T>) myCache.get(key);
		if (data == null)
			return null;
		else {
			data.lastAccessed = System.currentTimeMillis();
			return (T) data.value;
		}
	}

	public void remove(K key) {
		// Lock the resource, remove key, release the resource
		synchronized (myCache) {
			myCache.remove(key);
		}
	}

	public int size() {
		// Lock the resource, return size, release the resource
		synchronized (myCache) {
			return myCache.size();
		}
	}
}
