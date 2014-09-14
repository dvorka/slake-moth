package com.mindforger.coachingnotebook.server.security;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mindforger.coachingnotebook.server.cache.MindForgerMemcache;

public class SecurityOwnerCache {
	private static final Logger LOG=Logger.getLogger("SecurityOwnerCache");

	private static final String PREFIX="MF_OWNER_";
	
	private MindForgerMemcache memcache;		
		
	public SecurityOwnerCache(MindForgerMemcache memcache) {
		this.memcache=memcache;
	}

	public void putOwner(String entityId, String owner) {
		memcache.put(PREFIX+entityId, owner);
		LOG.info("OwnerCache.put: "+entityId+" / "+owner);
	}

	public void putOwner(Key entityId, String owner) {
		this.putOwner(PREFIX+KeyFactory.keyToString(entityId), owner);
	}
	
	public String getOwner(String entityId) {
		return (String)memcache.get(PREFIX+entityId);
	}
}
