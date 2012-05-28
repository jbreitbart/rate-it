package it.rate.util;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

public class MemCache {
	
	private final int CACHE_LIVE_TIME = 300; // in sec.
	private static MemCache instance = null;
	CacheFactory cacheFactory = null;
	Cache cache = null;	

	public MemCache()
	{
		try {
			Map<String, Integer> props = new HashMap<String, Integer>();
			props.put(GCacheFactory.EXPIRATION_DELTA, CACHE_LIVE_TIME);
			cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(props);
			
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static MemCache getInstance()
	{
		if (instance == null)
		{
			instance = new MemCache();
		}
		
		return instance;
	}

	
	public CacheFactory getCacheFactory() {
		return cacheFactory;
	}


	public Cache getCache() {
		return cache;
	}


}
