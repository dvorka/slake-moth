package com.mindforger.coachingnotebook.server.security;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.mindforger.coachingnotebook.server.cache.MindForgerMemcache;

public class SecurityAccountExistenceCache {
	private static final Logger LOG=Logger.getLogger("SecurityAccountExistenceCache");		
	
	private static final String PREFIX="MF_ACCOUNT_";
	
	private MindForgerMemcache memcache; 
	
	public SecurityAccountExistenceCache(MindForgerMemcache memcache) {
		this.memcache=memcache;
	}

	public void putAccount(String userId, Key userKey) {
		memcache.put(PREFIX+userId, userKey);
		LOG.info("AccountExistenceCache.put: "+userId);
	}

	public Key getAccount(String userId) {
		return (Key)memcache.get(PREFIX+userId);
	}
}
