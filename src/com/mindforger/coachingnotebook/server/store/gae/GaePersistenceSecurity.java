package com.mindforger.coachingnotebook.server.store.gae;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.mindforger.coachingnotebook.client.ui.security.MindForgerSecurityException;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.admin.MindForgerKernel;
import com.mindforger.coachingnotebook.server.i10n.Messages;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeCommentBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeFriendBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeGrowBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaePermissionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeUserBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeWhitelistEntryBean;
import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.FriendBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.PermissionBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;
import com.mindforger.coachingnotebook.shared.security.SecurityLimits;


public class GaePersistenceSecurity extends GaePersistenceCommons {
	private static final Logger LOG=Logger.getLogger("GaePersistenceSecurity");
	
	public GaePersistenceSecurity() {
		super();
	}
		
	/*
	 * Security:
	 *   no protection
	 * Limits:
	 *   N/A
	 */
	public boolean isUserOnWhitelist(String email) {
		PersistenceManager pm = getPm();
		boolean result=false;
		try {
			// "SELECT FROM " + GaeWhitelistEntryBean.class.getName() + " WHERE emailAsString == '"+email+"'";
			Query query=pm.newQuery(GaeWhitelistEntryBean.class);
			query.setFilter("emailAsString == email");
			query.declareParameters("String email");
			LOG.log(Level.INFO,"Query: "+query);
			@SuppressWarnings("unchecked")
			List<GaeUserBean> usersList = (List<GaeUserBean>)query.execute(email);
			LOG.log(Level.INFO,"  Whitelist matches: "+usersList.size());
			if(usersList!=null && usersList.size()>0) {
				result=true;
			}
		} finally {
			pm.close();
		}
		
		LOG.log(Level.INFO,""+MindForgerKernel.whitelistProtectedLogin);
		LOG.log(Level.INFO,""+result);
		if(!MindForgerKernel.whitelistProtectedLogin && !result) {
			// in case of free access add users to whitelist automatically
			internalAddUserOnWhitelist(email);
			result=true;
		}
			
		return result;
	}
	
	/*
	 * Security:
	 *   no protection
	 * Limits:
	 *   N/A
	 */	
	public User authorize() {
	    User user = userService.getCurrentUser();
	    if (user != null) {
	    	boolean userLoggedIn=userService.isUserLoggedIn();
	    	if(userLoggedIn) {
	    		if(isAccountExists(user.getUserId())) {
	    			return user;
	    		} else {
					throw new MindForgerSecurityException(Messages.getString("error.registerFirst"));
	    		}	
	    	} else {
				throw new MindForgerSecurityException(Messages.getString("error.loginFirst"));
	    	}
	    }
		throw new MindForgerSecurityException(Messages.getString("error.notAuthorizedResource"));
	}
	
	/*
	 * Security:
	 *   only admin can use this method
	 * Limits:
	 *   N/A
	 */
	public void addUserOnWhitelist(String email) {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		if(!isUserOnWhitelist(email) && email!=null) {
			internalAddUserOnWhitelist(email);
		}
	}

	/*
	 * Security:
	 *   only admin can use this method
	 * Limits:
	 *   N/A
	 */
	private void internalAddUserOnWhitelist(String email) {
		PersistenceManager pm = getPm();
		GaeWhitelistEntryBean whitelistEntry=new GaeWhitelistEntryBean();
		whitelistEntry.setEmailAsString(email);
		try {
			pm.makePersistent(whitelistEntry);
			countCache.remove(GaeWhitelistEntryBean.class);				
		} finally {
			pm.close();
		}
	}
			
	/*
	 * Security:
	 *   read method; user ID set by trusted code; user ID must be on the whitelist/account exist
	 * Limits:
	 *   N/A
	 */
	/**
	 * Method called after user accepts the license and then again on each login.
	 * 
	 * @return false if it is new user.
	 */
	public UserBean loginCheck(String inUserId, String nickname, String email) {
		LOG.log(Level.INFO,"loginCheck()");

		if(!isUserOnWhitelist(email)) {
			throw new RuntimeException("User '"+email+"' is not on whitelist!");
		}
				
		PersistenceManager pm = getPm();
		boolean isNewUser=false;
		UserBean result;
		Key userKey=null;
		try {
			// "SELECT FROM " + GaeUserBean.class.getName() + " WHERE userId == '"+userId+"'";
			Query query=pm.newQuery(GaeUserBean.class);
			query.setFilter("userId == inUserId");
			query.declareParameters("String inUserId");
			LOG.log(Level.INFO,"Query: "+query);
			@SuppressWarnings("unchecked")
			List<GaeUserBean> usersList = (List<GaeUserBean>)query.execute(inUserId);
			LOG.log(Level.INFO,"  Result: "+usersList.size());
			GaeUserBean user;
			if(usersList==null || usersList.size()==0) {
				isNewUser=true;
				user=new GaeUserBean();
				user.setRegistered(new Date());
				user.setUserId(inUserId);
				user.setNickname(nickname);
				user.setEmail((email==null?null:new Email(email)));
				user.setLimitGrows(SecurityLimits.LIMIT_GOALS);
				user.setLimitQuestionsPerGrow(SecurityLimits.LIMIT_QUESTIONS_PER_GOAL);
				user.setPreviousLogin(new Date());
			} else {
				user=usersList.get(0);
			}
			if(user.getGravatarMd5()==null) {
				String lowercaseEmail = user.getEmail().getEmail().toLowerCase().trim();
				LOG.log(Level.INFO,"Lowercase email for MD5: '"+lowercaseEmail+"'");
				user.setGravatarMd5(Utils.md5Hex(lowercaseEmail));				
			}
			user.setPreviousLogin(user.getLastLogin());
			user.setLastLogin(new Date());
			user.setLoginCounter(user.getLoginCounter()+1);
			user=pm.makePersistent(user);
			
			userKey=user.getKey();
			
			result=user.toPojo();
		} finally {
			pm.close();
		}
		if(isNewUser) {
			countCache.remove(GaeUserBean.class);
			accountExistenceCache.putAccount(inUserId, userKey);
		}
		
		
		// initialize new account with: default set of questions, default settings, ...
		if(isNewUser) {
	        // make persistent all questions - DISABLED FOR NOW (space complexity reasons)
			//			ArrayList<GaeQuestionBean> predefinedQuestions=new ArrayList<GaeQuestionBean>();
			//			for (int i = 0; i < QuestionsRepository.g.length; i++) {
			//				predefinedQuestions.add(new GaeQuestionBean(QuestionsRepository.g[i],QuestionAnswerBean.G_PART, userId));
			//			}
			//			for (int i = 0; i < QuestionsRepository.r.length; i++) {
			//				predefinedQuestions.add(new GaeQuestionBean(QuestionsRepository.r[i],QuestionAnswerBean.R_PART, userId));
			//			}
			//			for (int i = 0; i < QuestionsRepository.o.length; i++) {
			//				predefinedQuestions.add(new GaeQuestionBean(QuestionsRepository.o[i],QuestionAnswerBean.O_PART, userId));
			//			}
			//			for (int i = 0; i < QuestionsRepository.w.length; i++) {
			//				predefinedQuestions.add(new GaeQuestionBean(QuestionsRepository.w[i],QuestionAnswerBean.W_PART, userId));
			//			}
			//			for (int i = 0; i < QuestionsRepository.i.length; i++) {
			//				predefinedQuestions.add(new GaeQuestionBean(QuestionsRepository.i[i],QuestionAnswerBean.I_PART, userId));
			//			}
			//			pm = getPm();
			//			try {
			//				log.log(Level.INFO,"Saving default questions: "+predefinedQuestions.size());
			//				pm.makePersistentAll(predefinedQuestions);
			//              countCache.remove(GaeQuestionBean.class);				
			//			} finally {
			//				pm.close();
			//			}			
		}
		return result;
	}
	
	/*
	 * Security:
	 *   ?
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public boolean isAccountExists(String checkedUserId) {
		LOG.log(Level.INFO,"isAccountExists()");
		
		// cache
		if(accountExistenceCache.getAccount(checkedUserId)!=null) {
			return true;
		}
		
		List<GaeUserBean> usersList=null;
		PersistenceManager pm = getPm();
		try {
			// "SELECT FROM " + GaeUserBean.class.getName() + " WHERE userId == '"+checkedUserId+"'";
			Query query=pm.newQuery(GaeUserBean.class);
			query.setFilter("userId == checkedUserId");
			query.declareParameters("String checkedUserId");
			LOG.log(Level.INFO,"Query: "+query);
			usersList = (List<GaeUserBean>)query.execute(checkedUserId);
			LOG.log(Level.INFO,"  Does account exist: "+usersList.size());
			if(usersList==null || usersList.size()==0) {
				return false;
			} else {
				accountExistenceCache.putAccount(checkedUserId,usersList.get(0).getKey());
				return true;
			}
		} finally {
			pm.close();			
		}
	}
	
	/*
	 * Security:
	 *   user ID set by trusted code; read only method
	 * Limits:
	 *   N/A
	 */
	public UserLimitsBean getUserLimits(String inUserId) {
		PersistenceManager pm = getPm();
		UserLimitsBean result=null;
		try {
			// "SELECT FROM " + GaeUserBean.class.getName() + " WHERE userId == '"+userId+"'";
			Query query=pm.newQuery(GaeUserBean.class);
			query.setFilter("userId == inUserId");
			query.declareParameters("String inUserId");
			@SuppressWarnings("unchecked")
			List<GaeUserBean> usersList=(List<GaeUserBean>)query.execute(inUserId); 
			if(usersList!=null && usersList.size()>0) {
				GaeUserBean user=usersList.get(0);
				result=user.toUserLimitBean();
			}
		} finally {
			pm.close();
		}
		return result;
	}
			
	/*
	 * Security:
	 *   private method used to check limits
	 */
	void checkGrowOwnership(String userId, String growId) {
		String owner = ownerCache.getOwner(growId);
		if(owner==null) {
			Key key = Utils.stringToKey(growId);
			PersistenceManager pm=getPm();
			try {
				GaeGrowBean gaeResult = pm.getObjectById(GaeGrowBean.class, key);
				ownerCache.putOwner(growId, gaeResult.getOwnerId());
				if(userId.equals(gaeResult.getOwnerId())) {
					return;
				}
			} finally {
				pm.close();
			}			
		} else {
			if(userId.equals(owner)) {
				return;
			}			
		}
		throw new MindForgerSecurityException(Messages.getString("error.insufficientModifyGrow"));
	}
	
	
	/*
	 * Security:
	 *   private method used to check limits
	 */
	String securityCheckGrowLimit(String userId, UserLimitsBean userLimits) {
		PersistenceManager pm = getPm();
		try {
			Query query=pm.newQuery(GaeGrowBean.class);
			query.setFilter("ownerId == userId");
			query.declareParameters("String userId");
			query.setResult("count(this)");
			int existingGrows = (Integer)query.execute(userId);
			if(existingGrows>=userLimits.getLimitGrows()) {
				return UserLimitsBean.LIMIT_EXCEEDED+existingGrows+"/"+userLimits.getLimitGrows();
			}
		} finally {
			pm.close();
		}
		return null;
	}
	
	/*
	 * Security:
	 *   private method used to check limits
	 */
	void securityCheckAnswerCommentsLimit(String inAuthorUserKey, String inQuestionKey) {
		PersistenceManager pm = getPm();
		try {
			Query query=pm.newQuery(GaeCommentBean.class);
			query.setFilter("authorUserKey == inAuthorUserKey && questionKey == inQuestionKey");
			query.declareParameters("String inAuthorUserKey, String inQuestionKey");
			query.setResult("count(this)");
			int existingGrows = (Integer)query.execute(inAuthorUserKey, inQuestionKey);
			if(existingGrows>=SecurityLimits.LIMIT_COMMENTS_PER_QUESTION) {
				throw new MindForgerSecurityException(Messages.getString("error.commentsPerQuestionLimitExceeded", new String[]{""+SecurityLimits.LIMIT_COMMENTS_PER_QUESTION}));
			}
		} finally {
			pm.close();
		}
	}
		
	/*
	 * Security:
	 *   private method used to check limits
	 */
	String securityCheckFriendRequestLimit(String userId) {
		PersistenceManager pm;
		pm = getPm();
		try {
			Query query=pm.newQuery(GaeFriendBean.class);
			query.setFilter("ownerId == userId");
			query.declareParameters("String userId");
			query.setResult("count(this)");
			int count = (Integer)query.execute(userId);
			if(count>=SecurityLimits.LIMIT_FRIEND_REQUESTS) {
				throw new MindForgerSecurityException(Messages.getString("error.friendRequestLimitExceeded", new String[]{""+SecurityLimits.LIMIT_FRIEND_REQUESTS}));
			}
		} finally {
			pm.close();
		}
		return null;
	}

	/*
	 * Security: 
	 *   userId set by trusted code, userId used to query to storage
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public Map<String, PermissionBean> getGrowAcl(String ownerId, String growId) {
		List<GaePermissionBean> permissionList=null;
		PersistenceManager pm = getPm();
		try {
			Query query=pm.newQuery(GaePermissionBean.class);
			query.declareParameters("String growId");
			query.setFilter("subjectKey == growId");
			LOG.log(Level.INFO,"Query: "+query);
			permissionList = (List<GaePermissionBean>)query.execute(growId);
			LOG.log(Level.INFO,"  Result: "+permissionList.size());
		} finally {
			pm.close();
		}
		Map<String, PermissionBean> result=new HashMap<String, PermissionBean>();
		if(permissionList!=null && permissionList.size()>0) {
			pm = getPm();
			try {
				for (int i = 0; i < permissionList.size(); i++) {
					GaeUserBean gaeUserBean=pm.getObjectById(GaeUserBean.class, permissionList.get(i).getUserKey());
					UserBean userBean=gaeUserBean.toPojo();
					GaePermissionBean gaePermissionBean = permissionList.get(i);
					PermissionBean permissionBean = new PermissionBean(
							gaePermissionBean.getSubjectKey(),
							gaePermissionBean.getSubjectOwnerId(),
							userBean, 
							gaePermissionBean.getPermission());
					result.put(permissionBean.getUser().getUserId(), permissionBean);
				}
			} finally {
				pm.close();
			}
		}
		
		// for each friend there must be a permission
		Map<String, FriendBean> friends = getFriends(ownerId, true);
		Iterator<String> iterator = friends.keySet().iterator();
		while (iterator.hasNext()) {
			String friendId=(String)iterator.next();
			if(!result.containsKey(friendId)) {
				result.put(friendId, 
						new PermissionBean(
								growId,
								ownerId,
								friends.get(friendId).getFriend(),
								PermissionBean.PERMISSION_NO));																	
			}
		}		
		
		return result;
	}
		
	/*
	 * Security: 
	 *   N/A
	 * Limits:
	 *   N/A
	 */
	boolean securityIsGrowVisibleToUser(GrowBean bean, String owner, String userId) {
		if(bean==null || userId==null || owner==null) {
			return false;
		}
		if(owner.equals(userId)) {
			return true;
		} else {
			if(bean.getSharedTo()==null || bean.getSharedTo().equals(GrowBean.SHARING_OPTION_VALUE_NOBODY)) {
				return false;
			} else {
				if(bean.getSharedTo().equals(GrowBean.SHARING_OPTION_VALUE_EVERYBODY)) {
					return true;
				} else {
					if(bean.getSharedTo().equals(GrowBean.SHARING_OPTION_VALUE_ALL_CONNECTIONS)) {
						Map<String, FriendBean> friends = getFriends(owner);
						if(friends.containsKey(userId)) {
							return true;
						}
					} else {
						if(bean.getSharedTo().equals(GrowBean.SHARING_OPTION_VALUE_SELECTED_CONNECTIONS)) {
							Map<String, PermissionBean> acl = getGrowAcl(owner, bean.getKey());
							PermissionBean permissionBean = acl.get(userId);
							if(permissionBean!=null && permissionBean.getPermission()>=PermissionBean.PERMISSION_READ) {
								return true;
							}
						}						
					}					
				}
			}
		}
		return false;
	}	
	
	/*
	 * Security:
	 *   user ID set by trusted code
	 * Limits:
	 *   N/A
	 */
	GrowBean hasPermissionGrow(String growKey, String userId, PersistenceManager pm) {
		LOG.log(Level.INFO,"hasPermission() "+growKey+" "+userId);
		
		if(CheckListAnswerBean.FAKE_GROW_ID_WHEEL_OF_LIFE.equals(growKey)) {
			return null;
		}
		
		GaeGrowBean gaeResult;
		GrowBean result;
		gaeResult = pm.getObjectById(GaeGrowBean.class, KeyFactory.stringToKey(growKey));
		result = gaeResult.toPojo();
		
		if(!GrowBean.SHARING_OPTION_VALUE_EVERYBODY.equals(gaeResult.getSharedTo()) && !userId.equals(gaeResult.getOwnerId())) {
			Map<String, PermissionBean> growAcl = getGrowAcl(gaeResult.getOwnerId(),growKey);
			PermissionBean permission= growAcl.get(userId);
			if(permission==null || permission.getPermission()<PermissionBean.PERMISSION_READ) {
				throw new MindForgerSecurityException(Messages.getString("error.insufficientViewGrow"));
			}				
		}
		return result;
	}
	
	/*
	 * Security:
	 *   just delegate
	 * Limits:
	 *   N/A
	 */
	public Map<String, FriendBean> getFriends(String userId) {
		return getFriends(userId, false);
	}
	
	/**
	 * Relation 'friendship' is symmetric i.e. user A's friends are both his requests and others requests.
	 */
	/*
	 * Security:
	 *   userId set by trusted code; read only method
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	Map<String, FriendBean> getFriends(String userId, boolean onlyConfirmed) {
		Map<String, FriendBean> result=new HashMap<String, FriendBean>();
		PersistenceManager pm=getPm();
				
		// TODO paging of friends - now trimmed to first 100
		try {
			List<GaeFriendBean> allFriends=new ArrayList<GaeFriendBean>();
			//  "SELECT FROM " + GaeFriendBean.class.getName() + " WHERE ownerId == '"+userId+"'";
			Query query = pm.newQuery(GaeFriendBean.class);
			query.setFilter("ownerId == userId");
			query.declareParameters("String userId");
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeFriendBean> friends=(List<GaeFriendBean>)query.execute(userId);
			allFriends.addAll(friends);
			
			// "SELECT FROM " + GaeFriendBean.class.getName() + " WHERE friendId == '"+userId+"'";;
			query = pm.newQuery(GaeFriendBean.class);
			query.setFilter("friendId == userId");
			query.declareParameters("String userId");
			LOG.log(Level.INFO,"Query: "+query);
			friends=(List<GaeFriendBean>)query.execute(userId);
			allFriends.addAll(friends);
			
			for (int i = 0; i < allFriends.size(); i++) {
				GaeUserBean ownerUserBean=getUserBeanByKey(allFriends.get(i).getOwnerKey());
				GaeUserBean friendUserBean=getUserBeanByKey(allFriends.get(i).getFriendKey());

				FriendBean friendBean = new FriendBean();
				friendBean.setConfirmed(allFriends.get(i).getConfirmed());
				
				if(onlyConfirmed && !friendBean.isConfirmed()) {
					continue;
				}
				
				// solve symetricity: switch user & friend
				if(userId.equals(allFriends.get(i).getFriendId())) {
					friendBean.setRequestedByOwner(false);
					friendBean.setOwner(friendUserBean.toPojo());
					friendBean.setFriend(ownerUserBean.toPojo());
				} else {
					friendBean.setRequestedByOwner(true);
					friendBean.setFriend(friendUserBean.toPojo());
					friendBean.setOwner(ownerUserBean.toPojo());
				}
				
				result.put(friendBean.getFriend().getUserId(), friendBean);
			}
		} finally {
			pm.close();
		}
		
		return result;
	}	
}
