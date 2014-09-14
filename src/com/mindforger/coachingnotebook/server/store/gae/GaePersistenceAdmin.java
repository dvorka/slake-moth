package com.mindforger.coachingnotebook.server.store.gae;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.datanucleus.store.appengine.query.JDOCursorHelper;

import com.google.appengine.api.datastore.Cursor;
import com.mindforger.coachingnotebook.server.Utils;
import com.mindforger.coachingnotebook.server.admin.AdminReport;
import com.mindforger.coachingnotebook.server.admin.MindForgerKernel;
import com.mindforger.coachingnotebook.server.email.EmailBean;
import com.mindforger.coachingnotebook.server.email.Emailing;
import com.mindforger.coachingnotebook.server.store.BackupBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupAttachmentBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCheckListAnswerBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCommentBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupFriendBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupGrowBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupPermissionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionAnswerBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeAttachmentBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeCheckListAnswerBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeCommentBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeFriendBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeGrowBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeImportKeyMapBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeLifeVisionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeMindForgerKernelBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaePermissionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionAnswerBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeQuestionBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeStatusUpdateBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeUserBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeUserSettingsBean;
import com.mindforger.coachingnotebook.server.store.gae.beans.GaeWhitelistEntryBean;
import com.mindforger.coachingnotebook.shared.MindForgerSettings;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class GaePersistenceAdmin extends GaePersistenceSecurity {
	private static final Logger LOG=Logger.getLogger("GaePersistenceAdmin");

	public GaePersistenceAdmin() {
		super();		
	}

	/*
	 * Security:
	 *   only admin is allowed to perform this operation
	 * Limits:
	 *   N/A
	 */
	public AdminReport adminGetReport() {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		
		AdminReport result=new AdminReport();
		result.setUsers(adminGetTableSize(BackupUserBean.class));
		result.setGrows(adminGetTableSize(BackupGrowBean.class));
		result.setQuestions(adminGetTableSize(BackupQuestionAnswerBean.class));
		result.setCheckItems(adminGetTableSize(BackupCheckListAnswerBean.class));

		return result;
	}
	
	/*
	 * Security:
	 *   only admin can invoke this method
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("rawtypes")
	public int adminGetTableSize(Class clazz) {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}

		final Class<?> gaeClass = persistence2PojoMap.get(clazz);
		if(gaeClass!=null) {
			Integer count = countCache.getCount(gaeClass);
			if(count!=null) {
				return count;
			} else {
				PersistenceManager pm = getPm();
				try {
					// "SELECT count(this) FROM " + gaeClass.getName();
					Query query=pm.newQuery(gaeClass);
					query.setResult("count(this)");
					count = (Integer)query.execute();
					countCache.putCount(gaeClass, count);
					return count;
				} finally {
					pm.close();
				}			
			}
		}
		return -1;
	}
		
	/**
	 * This method is meant to be used by administrator only - it keeps 
	 * state by using the cursor. The cursor is reset when page starting 
	 * from index 0 is requested. In other words it will not return
	 * consistent results if there will be no concurrent users.
	 */
	/*
	 * Security:
	 *   only admin can use this method
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List adminGetTablePage(Class clazz, int from, int to, int pageSize) {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		
		clazz=persistence2PojoMap.get(clazz);
		
		List<?> queryResultList=null;
		PersistenceManager pm = getPm();
		List result=null;
		try {
			Query queryStatement = pm.newQuery(clazz);
			if(from==0) {
				queryStatement.setRange(from, to);
			} else {
				if(from>0) {
					Cursor cursor = Cursor.fromWebSafeString(tablePageCursorsCache.getCursor(clazz));
			        Map<String, Object> extensionMap = new HashMap<String, Object>();
			        extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
			        queryStatement.setExtensions(extensionMap);
			        queryStatement.setRange(0, pageSize);
				}
			}
			queryResultList = (List<?>)queryStatement.execute();
			
			Cursor cursor = JDOCursorHelper.getCursor(queryResultList);
			//log.log(Level.INFO,"adminGetTablePage() class/cursor "+clazz+"/"+cursor); 
			tablePageCursorsCache.putCursor(clazz, cursor.toWebSafeString());
	        
			if(queryResultList!=null && queryResultList.size()>0) {
				result=new ArrayList<GaeBackupTranscoder<?>>();
				for (int i = 0; i < queryResultList.size(); i++) {
					result.add(((GaeBackupTranscoder)queryResultList.get(i)).toBackup());
				}
			}
		} finally {
			pm.close();
		}
		return result;
	}
	
	public void adminClearRepositoryImportMap() {
		PersistenceManager pm;
		Query query;
		pm = getPm();
		try {
			query = pm.newQuery(GaeImportKeyMapBean.class);
			query.deletePersistentAll(query);		
		} finally {
			pm.close();
		}			
	}
	
	private String adminRepositoryImportMapGetNewKey(String inOldKey) {
		PersistenceManager pm=getPm();
		try {
			Query query=pm.newQuery(GaeImportKeyMapBean.class);
			query.setFilter("oldKey == inOldKey");			
			query.declareParameters("String inOldKey");
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeImportKeyMapBean> entriesList=(List<GaeImportKeyMapBean>)query.execute(inOldKey);
			LOG.log(Level.INFO,"  OLD > NEW key result size: "+entriesList.size());
			if(entriesList!=null && entriesList.size()>0 && entriesList.get(0)!=null) {
				return entriesList.get(0).getNewKey();
			}
		} finally {
			pm.close();
		}
		LOG.log(Level.INFO,"  OLD > NEW key: "+inOldKey+" > NULL xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		return null;
	}

	/*
	 * Security
	 *   only admin can use this method;
	 * Limits
	 *   N/A
	 */
	/**
	 * Import of a JSon feed with raw table.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void adminCreateTablePage(Class clazz, List fromJson) throws Exception {
		LOG.log(Level.INFO,"adminCreateTablePage() "+clazz.getCanonicalName());
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}

		// from backup to GAE Class
		clazz=persistence2PojoMap.get(clazz);

		List<String> oldKeys=new ArrayList<String>();
		
		if(fromJson!=null && fromJson.size()>0) {
			List gaeBeans=new ArrayList();
			for (int i = 0; i < fromJson.size(); i++) {
				if(MindForgerSettings.IMPORT_FROM_ANYWHERE) {
					/*
					 * Key map:
					 * 
					 *   User
					 *     xxx
					 *   UserSettings
					 *     xxx
					 *   LifeVision
					 *     xxx
					 *   Question
					 *     xxx
					 *   WhitelistEntry
					 *     
					 *   Grow
					 *     > User
w					 *   
					 *   Friend
					 *     > User
					 *   CheckListAnswer
					 *     xxx
					 *   QuestionAnswer
					 *     > Grow
					 *      
					 *   Attachment
					 *     > Grow
					 *   Comment
					 *     > User
					 *     > Grow
					 *     > Question
					 *   Permission
					 *     > User
					 *     > any resource
					 */					
					// fix backup bean
					BackupBean backupBean = (BackupBean)fromJson.get(i);
					oldKeys.add(backupBean.getKey());
					backupBean.setKey(null);
					
					if(backupBean instanceof BackupGrowBean) {
						((BackupGrowBean)backupBean).setOwnerKey(adminRepositoryImportMapGetNewKey(((BackupGrowBean)backupBean).getOwnerKey()));
					}
					
					if(backupBean instanceof BackupFriendBean) {
						((BackupFriendBean)backupBean).setOwnerKey(adminRepositoryImportMapGetNewKey(((BackupFriendBean)backupBean).getOwnerKey()));
						((BackupFriendBean)backupBean).setFriendKey(adminRepositoryImportMapGetNewKey(((BackupFriendBean)backupBean).getFriendKey()));
					}
					if(backupBean instanceof BackupCheckListAnswerBean) {
						// grow key is fake
					}
					if(backupBean instanceof BackupQuestionAnswerBean) {
						((BackupQuestionAnswerBean)backupBean).setGrowId(adminRepositoryImportMapGetNewKey(((BackupQuestionAnswerBean)backupBean).getGrowId()));
					}
					
					if(backupBean instanceof BackupAttachmentBean) {
						if(((BackupAttachmentBean)backupBean).getGrowKey()!=null) {
							((BackupAttachmentBean)backupBean).setGrowKey(adminRepositoryImportMapGetNewKey(((BackupAttachmentBean)backupBean).getGrowKey()));							
						}
						if(((BackupAttachmentBean)backupBean).getNoteKey()!=null) {
							((BackupAttachmentBean)backupBean).setNoteKey(adminRepositoryImportMapGetNewKey(((BackupAttachmentBean)backupBean).getNoteKey()));							
						}
						if(((BackupAttachmentBean)backupBean).getTargetGrowKey()!=null) {
							((BackupAttachmentBean)backupBean).setTargetGrowKey(adminRepositoryImportMapGetNewKey(((BackupAttachmentBean)backupBean).getTargetGrowKey()));							
						}
					}
					if(backupBean instanceof BackupCommentBean) {
						((BackupCommentBean)backupBean).setGrowKey(adminRepositoryImportMapGetNewKey(((BackupCommentBean)backupBean).getGrowKey()));
						((BackupCommentBean)backupBean).setQuestionKey(adminRepositoryImportMapGetNewKey(((BackupCommentBean)backupBean).getQuestionKey()));
						((BackupCommentBean)backupBean).setAuthorUserKey(adminRepositoryImportMapGetNewKey(((BackupCommentBean)backupBean).getAuthorUserKey()));
					}
					if(backupBean instanceof BackupPermissionBean) {
						((BackupPermissionBean)backupBean).setSubjectKey(adminRepositoryImportMapGetNewKey(((BackupPermissionBean)backupBean).getSubjectKey()));
						((BackupPermissionBean)backupBean).setUserKey(adminRepositoryImportMapGetNewKey(((BackupPermissionBean)backupBean).getUserKey()));
					}					
				}				
				
				Object newInstance = clazz.newInstance();
				((GaeBackupTranscoder)newInstance).fromBackup(fromJson.get(i));
				gaeBeans.add(newInstance);					
			}
			
			Collection result;
			
			PersistenceManager pm = getPm();
			try {
				result = pm.makePersistentAll(gaeBeans);
				countCache.remove(clazz);				
			} finally {
				pm.close();
			}			
		}
	}	
	
	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("rawtypes")
	public void adminDropRepository() {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		
		Class[] collectionsToDelete=new Class[] {
				GaeCheckListAnswerBean.class,
				GaeWhitelistEntryBean.class,
				GaeGrowBean.class,
				GaeQuestionBean.class,
				GaeQuestionAnswerBean.class,
				GaeUserBean.class,
				GaeLifeVisionBean.class,
				GaeFriendBean.class,
				GaePermissionBean.class,
				GaeCommentBean.class,
				GaeUserSettingsBean.class,
				GaeAttachmentBean.class,
				GaeStatusUpdateBean.class,
				
				GaeImportKeyMapBean.class
		};
		
		PersistenceManager pm;
		Query query;
		for (int i = 0; i < collectionsToDelete.length; i++) {
			pm = getPm();
			try {
				query = pm.newQuery(collectionsToDelete[i]);
				query.deletePersistentAll(query);		
			} finally {
				pm.close();
			}			
		}
	}
	
	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public void adminSaveKernelConfig() {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		PersistenceManager pm = getPm();
		try {
			// "SELECT FROM " + GaeMindForgerKernelBean.class.getName();
			Query query = pm.newQuery(GaeMindForgerKernelBean.class);
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeMindForgerKernelBean> kernelList=(List<GaeMindForgerKernelBean>)query.execute();
			LOG.log(Level.INFO,"  Result: "+kernelList.size());
			GaeMindForgerKernelBean gaeKernelBean;
			if(kernelList!=null && kernelList.size()>0) {
				gaeKernelBean = kernelList.get(0);
			} else {
				gaeKernelBean=new GaeMindForgerKernelBean();
			}
			gaeKernelBean.setWhitelistProtectedLogin(MindForgerKernel.whitelistProtectedLogin);
			pm.makePersistent(gaeKernelBean);
			countCache.putCount(GaeMindForgerKernelBean.class, 1);
		} finally {
			pm.close();
		}
	}

	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public void adminLoadKernelConfig() {
		PersistenceManager pm = getPm();
		try {
			// "SELECT FROM " + GaeMindForgerKernelBean.class.getName();
			Query query=pm.newQuery(GaeMindForgerKernelBean.class);
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeMindForgerKernelBean> kernelList = (List<GaeMindForgerKernelBean>)query.execute();
			LOG.log(Level.INFO,"  Result: "+kernelList.size());
			if(kernelList!=null && kernelList.size()>0) {
				MindForgerKernel.whitelistProtectedLogin=kernelList.get(0).isWhitelistProtectedLogin();
			}
		} finally {
			pm.close();
		}
	}
	
	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public int adminFixAddUserKeyAndSharingToGrows() {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		
		int fixes=0;
		
		List<GaeGrowBean> gaeResult=null;
		PersistenceManager pm = getPm();
		try {
			// "SELECT FROM " + GaeGrowBean.class.getName();
			Query query=pm.newQuery(GaeGrowBean.class);
			LOG.log(Level.INFO,"Query: "+query);
			gaeResult = (List<GaeGrowBean>)query.execute();
			LOG.log(Level.INFO,"  Grows to fix: "+gaeResult.size());			
		} finally {
			pm.close();
		}
		
		if(gaeResult!=null) {
			for(GaeGrowBean gaeGrowBean:gaeResult) {
				if(gaeGrowBean.getOwnerKey()==null) {
					LOG.log(Level.INFO,"Fixing grow: "+gaeGrowBean.getKey()+"   "+gaeGrowBean.getName());
					GaeUserBean userById = getUserById(gaeGrowBean.getOwnerId());
					gaeGrowBean.setOwnerKey(Utils.keyToString(userById.getKey()));
					fixes++;
				}
				if(gaeGrowBean.getSharedTo()==null) {
					gaeGrowBean.setSharedTo(GrowBean.SHARING_OPTION_VALUE_NOBODY);
				}
			}

			pm = getPm();
			try {
				pm.makePersistentAll(gaeResult);
				countCache.remove(GaeGrowBean.class);				
			} finally {
				pm.close();
			}
		}
		
		return fixes;
	}
	
	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public int adminFixQuestionsAnswerOrphans() {
		if(!userService.isUserAdmin()) {
			throw new RuntimeException("You must have admin priviledges to perform this operation!");
		}
		int fixesCount=0;
		// find questionAnswers beans that have no GROW associated
		PersistenceManager pm=getPm();
		try {
			// "SELECT FROM " + GaeQuestionAnswerBean.class.getName();
			Query query=pm.newQuery(GaeQuestionAnswerBean.class);
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeQuestionAnswerBean> oldQuestions = (List<GaeQuestionAnswerBean>)query.execute();
			LOG.log(Level.INFO,"  Questions: "+oldQuestions.size());		
			
			// find questions that are in datastore, but not present among new questions and delete them
			List<GaeQuestionAnswerBean> gaeQuestionsToDelete=new ArrayList<GaeQuestionAnswerBean>();
			if(!oldQuestions.isEmpty()) {
				for (int i = 0; i < oldQuestions.size(); i++) {
					GaeQuestionAnswerBean gaeQuestionBean = oldQuestions.get(i);
					try {
						pm.getObjectById(GaeGrowBean.class, gaeQuestionBean.getGrowKey());						
					} catch(JDOObjectNotFoundException e) {
						LOG.log(Level.INFO,"Orphan questions to be deleted: "+gaeQuestionBean.getKey().toString()+" > "+gaeQuestionBean.getQuestionLabel()+" > "+gaeQuestionBean.getQuestion());
						gaeQuestionsToDelete.add(gaeQuestionBean);
					}
				}
			}
			fixesCount=gaeQuestionsToDelete.size();
			pm.deletePersistentAll(gaeQuestionsToDelete);
			countCache.remove(GaeQuestionAnswerBean.class);
		} finally {
			pm.close();
		}
		return fixesCount;
	}
	
	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	public void adminSendAdminReportByEmail() {
		LOG.log(Level.INFO,"adminSendAdminReportByEmail()");
		
		AdminReport report = adminGetReport();
		Emailing.adminReport(report);
	}
	
	/*
	 * Security:
	 *   can be used by admin only
	 * Limits:
	 *   N/A
	 */
	@SuppressWarnings("unchecked")
	public int adminFindAndEmailActionsWhoseDeadlineApproaching() {
		LOG.log(Level.INFO,"adminFindAndEmailActionsWhoseDeadlineApproaching()");

		Calendar today = Calendar.getInstance();
		Calendar future = Calendar.getInstance();
		future.add(Calendar.DATE, 2); 
		Calendar deadline = Calendar.getInstance();
		
		List<GaeQuestionAnswerBean> notify1DayToGo=new ArrayList<GaeQuestionAnswerBean>();
		PersistenceManager pm=getPm();
		try {
			// "SELECT FROM " + GaeQuestionAnswerBean.class.getName() + " WHERE growType == 'W' && questionLabel == 'Action' && progress < 100";
			Query query=pm.newQuery(GaeQuestionAnswerBean.class);
			query.setFilter("growType == 'W' && questionLabel == 'Action' && progress < 100");
			LOG.log(Level.INFO,"Query: "+query);
			List<GaeQuestionAnswerBean> actions = (List<GaeQuestionAnswerBean>)query.execute();
			LOG.log(Level.INFO,"  Found "+actions.size()+" actions - filtering out by deadline...");
			for (GaeQuestionAnswerBean action : actions) {
				if((action.getDeadline())!=null) {
					deadline.setTime(action.getDeadline());
					LOG.log(Level.INFO,"    Deadline/today/future:\n  "+deadline+"\n  "+today+"\n  "+future);
					if(today.before(deadline) && future.after(deadline)) {
						LOG.log(Level.INFO,"    Scheduling email: "+action.getQuestion());
						notify1DayToGo.add(action);
					}
				}
			}
		} finally {
			pm.close();
		}
		
		if(notify1DayToGo.size()>0) {
			List<EmailBean> emails=new ArrayList<EmailBean>();
			for(GaeQuestionAnswerBean action1Day : notify1DayToGo) {
				// TODO optimize this - would be possible to get all users using single select; do it in one transaction (getUserByID would take array of IDs)?
				GaeUserBean userBean = getUserById(action1Day.getOwnerId());
				if(userBean!=null) {
					EmailBean email=Emailing.emailActionWhoseDeadlineIsApproaching(
							userBean.getEmail().getEmail(),
							userBean.getNickname(),
							action1Day.getQuestion());
					emails.add(email);
				}
			}
			Emailing.sendEmails(emails.toArray(new EmailBean[emails.size()]));
			return notify1DayToGo.size();
		}
		return 0;
	}	
}
