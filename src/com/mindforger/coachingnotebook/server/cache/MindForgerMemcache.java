package com.mindforger.coachingnotebook.server.cache;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

public class MindForgerMemcache {
	private static final Logger LOG=Logger.getLogger("MindForgerMemcache");
	
	private Cache memcache;

	public MindForgerMemcache() {
        try {
        	// cluster > memcache (use of application cache makes no sense: cross cluster node, size, ...)
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            memcache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            LOG.severe("Unable to initialize MemCache!");
        }
	}

	public Object put(Object key, Object value) {
		if(memcache!=null) {
			try {
				return memcache.put(key, value);				
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}
		return null;
	}

	public Object get(Object key) {
		if(memcache!=null) {
			try {
				return memcache.get(key);				
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}
		return null;
	}

	public void remove(Object key) {
		if(memcache!=null) {
			try {
				memcache.remove(key);				
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}
	}
	
	public int getObjectCount() {
		if(memcache!=null) {
			try {
				return memcache.getCacheStatistics().getObjectCount();
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}
		return -1;
	}

	public int getCacheHits() {
		if(memcache!=null) {
			try {
				return memcache.getCacheStatistics().getCacheHits();
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}
		return -1;
	}

	public int getCacheMisses() {
		if(memcache!=null) {
			try {
				return memcache.getCacheStatistics().getCacheMisses();
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}
		return -1;
	}
	
	public void clear() {
		if(memcache!=null) {
			try {
				memcache.clear();
			} catch(Exception e) {
				LOG.log(Level.SEVERE,"Memcache error: ",e);
			}
		}		
	}
}
