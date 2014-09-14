package com.mindforger.coachingnotebook.server.store.gae.cache;

import java.util.logging.Logger;

import com.mindforger.coachingnotebook.server.cache.MindForgerMemcache;

public class GaeCountCache {
	private static final Logger LOG=Logger.getLogger("GaeCountCache");
	
	private static final String PREFIX="MF_COUNT_";
	
	private MindForgerMemcache memcache;		

	public GaeCountCache(MindForgerMemcache memcache) {
		this.memcache=memcache;
	}

	public void putCount(Class<?> clazz, int count) {
		if(clazz!=null && memcache!=null) {
			memcache.put(PREFIX+clazz.getName(), count);
			LOG.info("CountCache.put: "+clazz.getName()+" / "+count);			
		}
	}
	
	public Integer getCount(Class<?> clazz) {
		Integer count=null;
		if(clazz!=null && memcache!=null) {
			count=(Integer)memcache.get(PREFIX+clazz.getName());	
		}
		LOG.info("CountCache.get: "+clazz.getName()+" / "+count);
		return count;
	}
	
	public void remove(Class<?> clazz) {
		if(clazz !=null && memcache!=null) {
			memcache.remove(PREFIX+clazz.getName());
		}
	}
}
