package com.mindforger.coachingnotebook.server.store.gae.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.mindforger.coachingnotebook.server.cache.MindForgerMemcache;

/**
 * Ensures cross cluster synchronization of cursors. Local map ensures that even if the memcache
 * evicts the value, it will be still available (at least at this node). The persistence of the cursor
 * was not implemented as it is used only for export which is a sequence of requests. Therefore the probability
 * that the value will be evicted is very low.
 */
public class GaeCursorsCache {
	private static final Logger LOG=Logger.getLogger("GaeCursorsCache");
	
	private static final String PREFIX="MF_CURSOR_";

	private Map<Class<?>, String> map;	
	private MindForgerMemcache memcache;		

	public GaeCursorsCache(MindForgerMemcache memcache) {
		this.memcache=memcache;
		this.map=new HashMap<Class<?>, String>();
	}

	public void putCursor(Class<?> clazz, String owner) {
		map.put(clazz, owner);
		
		if(clazz!=null && memcache!=null) {
			memcache.put(PREFIX+clazz.getName(), owner);
			LOG.info("CursorCache.put: "+clazz.getName()+" / "+owner);			
		}
	}
	
	public String getCursor(Class<?> clazz) {
		if(clazz!=null && memcache!=null) {
			return (String)memcache.get(PREFIX+clazz.getName());				
		}
		return map.get(clazz);
	}
}
