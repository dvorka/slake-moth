package com.mindforger.coachingnotebook.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

@RemoteServiceRelativePath("mfgwt")
public interface MindForgerService extends RemoteService {
	RiaBootImageBean getRiaBootImage();

	void saveUserSettings(UserSettingsBean userSettings);
	
	String newGrow();
	GrowBean saveGrow(GrowBean bean);
	GrowBean[] getGrows();
	GrowBean getGrow(String id);
	void deleteGrow(GrowBean bean);
	
	String newQuestionAnswer(String growId, int order);
	void switchQuestionAnswers(String questionIdA, String questionIdB);
	
	CheckListAnswerBean[] getCheckListAnswers(String growId, String mode); 
	void setCheckListAnswer(String growId, String mode, Integer questionId, String answer);
	
	UserBean loginCheck();
	UserLimitsBean getUserLimits();
	UserProfileBean getUserProfile(String userId);
	
	FriendBean[] findFriend(String query);
	Map<String, FriendBean> myFriends();
	void requestFriendship(String friend, String role);
	void acceptFriendship(String friendId, String role);
	void rejectFriendship(String friendId);
	void revokeFriendship(String friendId);
	
	String getNickname();
	void setNickname(String nickname);
	void setUserDescription(String description);
	void setUserWeb(String web);
	
	QuestionAnswerBean[] getActions();
	QuestionAnswerBean[] getLessonsLearned();
	void setLifeVision(String lifeVision);
	String getLifeVision();

	Map<String, PermissionBean> getGrowAcl(String growId);
	void setGrowAcl(String growId, PermissionBean[] acl);
	void setGrowAclToAllConnections(String growId);

	GrowBean[] getSharedGrows();
	
	String saveQuestionComment(String questionId, String comment);
	CommentBean[] getCommentsForGrow(String growId);
	void deleteComment(String commentId);
	
	DescriptorBean[] fts(String search);
}
