package com.mindforger.coachingnotebook.server.store;

import java.util.List;
import java.util.Map;

import com.google.appengine.api.users.User;
import com.mindforger.coachingnotebook.server.admin.AdminReport;
import com.mindforger.coachingnotebook.server.cache.MindForgerMemcache;
import com.mindforger.coachingnotebook.server.search.Fts;
import com.mindforger.coachingnotebook.shared.beans.CheckListAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;
import com.mindforger.coachingnotebook.shared.beans.FriendBean;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.LabelBean;
import com.mindforger.coachingnotebook.shared.beans.PermissionBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionSetsBean;
import com.mindforger.coachingnotebook.shared.beans.RiaBootImageBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;
import com.mindforger.coachingnotebook.shared.beans.UserLimitsBean;
import com.mindforger.coachingnotebook.shared.beans.UserProfileBean;
import com.mindforger.coachingnotebook.shared.beans.UserSettingsBean;

public interface Persistence {
	RiaBootImageBean getRiaBootImage(String userId, String nickname, String email);

	void saveUserSettings(String userId, UserSettingsBean userSettings);

	GrowBean[] getGrows(String userId);
	GrowBean getGrow(String id, String userId);
	String newGrow(String userId);
	GrowBean saveGrow(GrowBean grow, String userId);
	void deleteGrow(GrowBean grow, String userId);
	QuestionSetsBean getQuestionSets(String userId);

	String newQuestionAnswer(String userId, String growId, int order);
	void insertQuestionAnswerAt(String userId, String questionId, int order);
	void switchQuestionAnswers(String userId, String questionIdA, String questionIdB);
	
	QuestionAnswerBean[] getActions(String userId);
	QuestionAnswerBean[] getLessonsLearned(String userId);
	
	CheckListAnswerBean[] getCheckListAnswers(String userId, String growId, String mode);
	void saveCheckListAnswer(String userId, String growId, String mode, Integer questionId, String answer);
	
	LabelBean[] getLabels(String userId);

	User authorize();
	boolean isUserOnWhitelist(String email);
	boolean isAccountExists(String userId);
	UserBean loginCheck(String userId, String nickname, String email);
	UserLimitsBean getUserLimits(String userId);
	UserProfileBean getUserProfile(String requestorId, String userId);
			
	FriendBean[] findFriend(String userId, String query);
	Map<String,FriendBean> getFriends(String userId);
	void requestFriendship(String userId, String friend, String role);
	void acceptFriendship(String userId, String friendId, String role);
	void rejectFriendship(String userId, String friendId);
	void revokeFriendship(String userId, String friendId);
	
	String getNickname(String userId);
	void setNickname(String userId, String nickname);
	void setUserDescription(String userId, String description);	
	void setUserWeb(String userId, String web);	

	void setLifeVision(String userId, String lifeVision);
	String getLifeVision(String userId);
	
	public AdminReport adminGetReport();
	@SuppressWarnings("rawtypes")
	int adminGetTableSize(Class clazz);
	@SuppressWarnings("rawtypes")
	List adminGetTablePage(Class clazz, int from, int to, int pageSize);
	@SuppressWarnings("rawtypes")
	void adminCreateTablePage(Class clazz, List fromJson) throws Exception;
	void adminDropRepository();
	void adminSaveKernelConfig();
	void adminLoadKernelConfig();
	int adminFixQuestionsAnswerOrphans();
	int adminFindAndEmailActionsWhoseDeadlineApproaching();
	void adminSendAdminReportByEmail();
    void adminClearRepositoryImportMap();
	
	Map<String,PermissionBean> getGrowAcl(String userId, String growId);
	void setGrowAcl(String userId, String growId, PermissionBean[] acl);
	void setGrowAclToAllConnections(String userId, String growId);

	GrowBean[] getSharedGrows(String userId);
	
	String saveQuestionComment(String userId, String questionId, String comment);
	CommentBean[] getCommentsForGrow(String userId, String growId);
	void deleteComment(String userId, String commentId);
	
	MindForgerMemcache getCache();
	DescriptorBean[] fulltextSearch(String search, String userId);

	Fts getFts();
}
