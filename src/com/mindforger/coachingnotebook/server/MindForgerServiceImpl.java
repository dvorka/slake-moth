package com.mindforger.coachingnotebook.server;

import java.util.Locale;
import java.util.Map;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mindforger.coachingnotebook.client.MindForgerService;
import com.mindforger.coachingnotebook.client.ui.security.MindForgerSecurityException;
import com.mindforger.coachingnotebook.server.admin.MindForgerKernel;
import com.mindforger.coachingnotebook.server.i10n.Messages;
import com.mindforger.coachingnotebook.server.search.Fts;
import com.mindforger.coachingnotebook.server.store.Persistence;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;
import com.mindforger.coachingnotebook.shared.beans.FriendBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.PermissionBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.RiaBootImageBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;
import com.mindforger.coachingnotebook.shared.beans.UserProfileBean;
import com.mindforger.coachingnotebook.shared.beans.UserSettingsBean;

/**
 * The server side implementation of GWT RPC service.
 */
@SuppressWarnings("serial")
public class MindForgerServiceImpl extends RemoteServiceServlet implements MindForgerService {
	private Persistence persistence;
	private UserService userService;
	private Fts fts;
	
	public MindForgerServiceImpl() {
		persistence=MindForgerKernel.getPersistence();
		fts=persistence.getFts();
		userService = UserServiceFactory.getUserService();
	}

	
	private User getGaeUser() {
		if(MindForgerKernel.isServiceDisabled) {
			if(userService.isUserAdmin() && userService.getCurrentUser()!=null) {
				return userService.getCurrentUser();
			} else {
				throw new MindForgerException(Messages.getString("error.serviceIsUndergoingMaintenance"));				
			}
		} else {
		    User user = userService.getCurrentUser();
		    if(user==null) {
		    	throw new MindForgerSecurityException(Messages.getString("error.userNotLoggedIn"));
		    }
			return user;			
		}
	}

	public RiaBootImageBean getRiaBootImage() {
		User user = getGaeUser();
		return persistence.getRiaBootImage(user.getUserId(), user.getNickname(), user.getEmail());
	}
	
	public GrowBean[] getGrows() {
		return persistence.getGrows(getGaeUser().getUserId());
	}
	
	public GrowBean getGrow(String id) {
		return persistence.getGrow(id,getGaeUser().getUserId());
	}
	
	public String newGrow() {
		return persistence.newGrow(getGaeUser().getUserId());
	}
	
	public GrowBean saveGrow(GrowBean bean) {
		GrowBean result=persistence.saveGrow(bean,getGaeUser().getUserId());
		fts.add(result, Locale.US, getGaeUser().getUserId());
		return result;
	}
	
	public void deleteGrow(GrowBean bean) {
		persistence.deleteGrow(bean,getGaeUser().getUserId());
		fts.delete(MindForgerResourceType.GROW, bean.getKey(), getGaeUser().getUserId());
	}

	/**
	 * Method called on user login.
	 * 
	 * @return false if its new user, true if user already existed.
	 */
	public UserBean loginCheck() {
	    User user = getGaeUser();
	    return persistence.loginCheck(user.getUserId(), user.getNickname(), user.getEmail());
	}

	public UserLimitsBean getUserLimits() {
	    User user = getGaeUser();
	    return persistence.getUserLimits(user.getUserId());
	}

	public FriendBean[] findFriend(String query) {
	    User user = getGaeUser();
	    return persistence.findFriend(user.getUserId(), query);
	}

	public Map<String, FriendBean> myFriends() {
	    User user = getGaeUser();
	    return persistence.getFriends(user.getUserId());
	}

	public void requestFriendship(String friend, String role) {
	    User user = getGaeUser();
	    persistence.requestFriendship(user.getUserId(), friend, role);
	}

	public void acceptFriendship(String friendId, String role) {
	    User user = getGaeUser();
	    persistence.acceptFriendship(user.getUserId(), friendId, role);
	}

	public void rejectFriendship(String friendId) {
	    User user = getGaeUser();
	    persistence.rejectFriendship(user.getUserId(), friendId);
	}

	public void revokeFriendship(String friendId) {
	    User user = getGaeUser();
	    persistence.revokeFriendship(user.getUserId(), friendId);
	}

	public String getNickname() {
	    User user = getGaeUser();
	    return persistence.getNickname(user.getUserId());
	}

	public void setNickname(String nickname) {
	    User user = getGaeUser();
	    persistence.setNickname(user.getUserId(), nickname);
	}

	public UserProfileBean getUserProfile(String profileId) {
	    User user = getGaeUser();
	    return persistence.getUserProfile(user.getUserId(), profileId);
	}

	public CheckListAnswerBean[] getCheckListAnswers(String growId, String mode) {
	    User user = getGaeUser();
	    return persistence.getCheckListAnswers(user.getUserId(), growId, mode);
	}

	public void setCheckListAnswer(String growId, String mode, Integer questionId, String answer) {
	    User user = getGaeUser();
	    persistence.saveCheckListAnswer(user.getUserId(), growId, mode, questionId, answer);
	}

	public QuestionAnswerBean[] getActions() {
	    User user = getGaeUser();
	    return persistence.getActions(user.getUserId());
	}

	public QuestionAnswerBean[] getLessonsLearned() {
	    User user = getGaeUser();
	    return persistence.getLessonsLearned(user.getUserId());
	}

	public void setLifeVision(String lifeVision) {
	    User user = getGaeUser();
	    persistence.setLifeVision(user.getUserId(), lifeVision);
	}

	public String getLifeVision() {
	    User user = getGaeUser();
	    return persistence.getLifeVision(user.getUserId());
	}

	public Map<String, PermissionBean> getGrowAcl(String growId) {
	    User user = getGaeUser();
	    return persistence.getGrowAcl(user.getUserId(), growId);
	}
	
	public void setGrowAcl(String growId, PermissionBean[] acl) {
	    User user = getGaeUser();
	    persistence.setGrowAcl(user.getUserId(), growId, acl);
	}

	public void setUserDescription(String description) {
	    User user = getGaeUser();
	    persistence.setUserDescription(user.getUserId(), description);
	}

	public void setUserWeb(String web) {
	    User user = getGaeUser();
	    persistence.setUserWeb(user.getUserId(), web);
	}

	public String saveQuestionComment(String questionId, String comment) {
	    User user = getGaeUser();
	    return persistence.saveQuestionComment(user.getUserId(), questionId, comment);
	}

	public CommentBean[] getCommentsForGrow(String growId) {
	    User user = getGaeUser();
	    return persistence.getCommentsForGrow(user.getUserId(), growId);
	}

	public void deleteComment(String commentId) {
	    User user = getGaeUser();
	    persistence.deleteComment(user.getUserId(), commentId);
	}

	public String newQuestionAnswer(String growId, int order) {
	    User user = getGaeUser();
	    return persistence.newQuestionAnswer(user.getUserId(), growId, order);
	}

	public void setGrowAclToAllConnections(String growId) {
	    User user = getGaeUser();
	    persistence.setGrowAclToAllConnections(user.getUserId(), growId);
	}

	public GrowBean[] getSharedGrows() {
	    User user = getGaeUser();
	    return persistence.getSharedGrows(user.getUserId());
	}

	public void saveUserSettings(UserSettingsBean userSettings) {
		persistence.saveUserSettings(getGaeUser().getUserId(), userSettings);
	}

	public void switchQuestionAnswers(String questionIdA, String questionIdB) {
	    User user = getGaeUser();
	    persistence.switchQuestionAnswers(user.getUserId(), questionIdA, questionIdB);
	}

	public DescriptorBean[] fts(String query) {
		return fts.find(query, getGaeUser().getUserId());
	}
}
