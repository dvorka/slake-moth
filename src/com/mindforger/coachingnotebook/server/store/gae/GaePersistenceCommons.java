package com.mindforger.coachingnotebook.server.store.gae;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mindforger.coachingnotebook.server.QuestionsRepository;
import com.mindforger.coachingnotebook.server.cache.MindForgerMemcache;
import com.mindforger.coachingnotebook.server.search.Fts;
import com.mindforger.coachingnotebook.server.search.dummy.DummyFts;
import com.mindforger.coachingnotebook.server.search.gae.GaeFts;
import com.mindforger.coachingnotebook.server.security.SecurityAccountExistenceCache;
import com.mindforger.coachingnotebook.server.security.SecurityOwnerCache;
import com.mindforger.coachingnotebook.server.store.beans.BackupAttachmentBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCheckListAnswerBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCommentBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupFriendBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupGrowBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupLifeVisionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupPermissionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionAnswerBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserSettingsBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupWhitelistEntryBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeAttachmentBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeCheckListAnswerBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeCommentBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeFriendBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeGrowBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeLifeVisionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaePermissionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionAnswerBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeUserBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeUserSettingsBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeWhitelistEntryBean;
import com.mindforger.coachingnotebook.server.store.gae.cache.GaeCountCache;
import com.mindforger.coachingnotebook.server.store.gae.cache.GaeCursorsCache;
import com.mindforger.coachingnotebook.shared.MindForgerSettings;
import com.mindforger.coachingnotebook.shared.comparators.ComparatorGrowModified;

public class GaePersistenceCommons {
	private static final Logger LOG=Logger.getLogger("GaePersistenceCommons");
	
	static final PersistenceManagerFactory persistenceManagerFactory;
	static final QuestionsRepository questionsRepository;
	static MindForgerMemcache memcache;
	static final SecurityOwnerCache ownerCache;
	static final SecurityAccountExistenceCache accountExistenceCache;
	static final GaeCursorsCache tablePageCursorsCache;
	static final GaeCountCache countCache;
	static final Fts fts;

	static {
		memcache=new MindForgerMemcache();
		accountExistenceCache=new SecurityAccountExistenceCache(memcache);
		ownerCache=new SecurityOwnerCache(memcache);
		tablePageCursorsCache=new GaeCursorsCache(memcache);
		countCache=new GaeCountCache(memcache);
        
		questionsRepository=new QuestionsRepository();        
		persistenceManagerFactory=JDOHelper.getPersistenceManagerFactory("transactions-optional");		
		
		if(MindForgerSettings.FTS_ENABLED) {
			fts=new GaeFts();
		} else {
			fts=new DummyFts();
		}
	}
	
	public Fts getFts() {
		return fts;
	}
	
	/**
	 *  For each request to the big table, new persistence manager MUST be created.
	 *  It can be reused only if it is used on the same entity group.
	 *  Once it is not needed, it must be released with close() method.
	 */
	static synchronized PersistenceManager getPm() {
		return persistenceManagerFactory.getPersistenceManager();
	}

	GaeGrowModifiedComparator gaeGrowModifiedComparator;
	ComparatorGrowModified growModifiedComparator;
	UserService userService;
	HashMap<Class<?>,Class<?>> persistence2PojoMap;
	HashMap<Class<?>,Class<?>> pojo2PersistenceMap;

	GaePersistenceCommons() {
		gaeGrowModifiedComparator = new GaeGrowModifiedComparator();
		growModifiedComparator = new ComparatorGrowModified();
		userService = UserServiceFactory.getUserService();

		persistence2PojoMap=new HashMap<Class<?>, Class<?>>(); 
		persistence2PojoMap.put(BackupCheckListAnswerBean.class, GaeCheckListAnswerBean.class);
		persistence2PojoMap.put(BackupGrowBean.class, GaeGrowBean.class);
		persistence2PojoMap.put(BackupQuestionAnswerBean.class, GaeQuestionAnswerBean.class);
		persistence2PojoMap.put(BackupQuestionBean.class, GaeQuestionBean.class);
		persistence2PojoMap.put(BackupUserBean.class, GaeUserBean.class);
		persistence2PojoMap.put(BackupWhitelistEntryBean.class, GaeWhitelistEntryBean.class);
		persistence2PojoMap.put(BackupLifeVisionBean.class, GaeLifeVisionBean.class);
		persistence2PojoMap.put(BackupFriendBean.class, GaeFriendBean.class);
		persistence2PojoMap.put(BackupPermissionBean.class, GaePermissionBean.class);
		persistence2PojoMap.put(BackupCommentBean.class, GaeCommentBean.class);
		persistence2PojoMap.put(BackupUserSettingsBean.class, GaeUserSettingsBean.class);
		persistence2PojoMap.put(BackupAttachmentBean.class, GaeAttachmentBean.class);
		
		pojo2PersistenceMap=new HashMap<Class<?>, Class<?>>();
		Iterator<Class<?>> iterator = persistence2PojoMap.values().iterator();
		while (iterator.hasNext()) {
			Class<?> backupBeanClass = (Class<?>)iterator.next();
			pojo2PersistenceMap.put(persistence2PojoMap.get(backupBeanClass), backupBeanClass);
		}				
	}
	
	/*
	 * Security: 
	 *   userId used in query is set by trusted code
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	GaeUserBean getUserById(String inUserId) {
		PersistenceManager pm = getPm();
		try {
			// "SELECT FROM " + GaeUserBean.class.getName() + " WHERE userId == '"+userId+"'";
			Query query = pm.newQuery(GaeUserBean.class);
			query.setFilter("userId == inUserId");
			query.declareParameters("String inUserId");
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeUserBean> usersList=(List<GaeUserBean>)query.execute(inUserId);
			LOG.log(Level.INFO,"  Result: "+usersList.size());
			if(usersList!=null && usersList.size()>0) {
				accountExistenceCache.putAccount(inUserId, usersList.get(0).getKey());
				return usersList.get(0);
			}
		} finally {
			pm.close();
		}
		return null;
	}	
	
	/*
	 * Security: 
	 *   userId set by trusted code; delegated to getUserById
	 * Limits:
	 *   N/A
	 */
	Key getKeyForUserId(String userId) {
		Key userKey=null;
		if((userKey=accountExistenceCache.getAccount(userId))!=null) {
			return userKey;
		} else {
			GaeUserBean userBean = getUserById(userId);
			if(userBean!=null) {
				return userBean.getKey();
			} else {
				return null;
			}			
		}
	}
	
	/*
	 * Security: 
	 *   N/A
	 * Limits:
	 *   N/A
	 */
	GaeUserBean getUserBeanByKey(String userKey) {
		if(userKey!=null) {
			PersistenceManager pm = getPm();
			try {
				GaeUserBean bean = pm.getObjectById(GaeUserBean.class, userKey);
				if(bean!=null) {
					return bean;
				}
			} finally {
				pm.close();
			}			
		}
		return null;
	}
	
	/*
	 * Security: 
	 *   N/A
	 * Limits:
	 *   N/A
	 */
	GaeUserBean getUserBeanByKey(Key userKey) {
		if(userKey!=null) {
			PersistenceManager pm = getPm();
			try {
				GaeUserBean bean = pm.getObjectById(GaeUserBean.class, userKey);
				if(bean!=null) {
					return bean;
				}
			} finally {
				pm.close();
			}			
		}
		return null;
	}		
	
	public MindForgerMemcache getCache() {
		return memcache;
	}

	public void clearMemcache() {
		memcache.clear();
	}	
}
