package com.mindforger.coachingnotebook.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

public interface MindForgerServiceAsync {
	void getRiaBootImage(AsyncCallback<RiaBootImageBean> asyncCallback);

	void saveUserSettings(UserSettingsBean userSettings, AsyncCallback<Void> asyncCallback);
	
	void newGrow(AsyncCallback<String> callback);	
	void getGrows(AsyncCallback<GrowBean[]> callback);	
	void saveGrow(GrowBean bean, AsyncCallback<GrowBean> callback);
	void getGrow(String id, AsyncCallback<GrowBean> asyncCallback);
	void deleteGrow(GrowBean bean, AsyncCallback<Void> callback);

	void newQuestionAnswer(String growId, int order, AsyncCallback<String> asyncCallback);
	void switchQuestionAnswers(String questionIdA, String questionIdB, AsyncCallback<Void> callback);
	
	void getCheckListAnswers(String growId, String mode, AsyncCallback<CheckListAnswerBean[]> callback);
	void setCheckListAnswer(String growId, String mode, Integer questionId, String answer, AsyncCallback<Void> asyncCallback);
	
	void getActions(AsyncCallback<QuestionAnswerBean[]> callback);
	void getLessonsLearned(AsyncCallback<QuestionAnswerBean[]> asyncCallback);

	void getLifeVision(AsyncCallback<String> asyncCallback);
	void setLifeVision(String lifeVision, AsyncCallback<Void> asyncCallback);
	
	void loginCheck(AsyncCallback<UserBean> callback);
	void getUserLimits(AsyncCallback<UserLimitsBean> callback);	
	void getUserProfile(String userId, AsyncCallback<UserProfileBean> callback);

	void findFriend(String query, AsyncCallback<FriendBean[]> asyncCallback);
	void myFriends(AsyncCallback<Map<String, FriendBean>> asyncCallback);
	void requestFriendship(String friend, String role, AsyncCallback<Void> callback);
	void acceptFriendship(String friendId, String role, AsyncCallback<Void> asyncCallback);
	void rejectFriendship(String friendId, AsyncCallback<Void> asyncCallback); 
	void revokeFriendship(String friendId, AsyncCallback<Void> asyncCallback);
	
	void getNickname(AsyncCallback<String> asyncCallback);
	void setNickname(String nickname, AsyncCallback<Void> asyncCallback);
	void setUserDescription(String description, AsyncCallback<Void> callback);
	void setUserWeb(String web, AsyncCallback<Void> callback);
	
	void getGrowAcl(String growId, AsyncCallback<Map<String, PermissionBean>> callback);
	void setGrowAcl(String growId, PermissionBean[] acl, AsyncCallback<Void> callback);
	void setGrowAclToAllConnections(String growId, AsyncCallback<Void> asyncCallback);
	
	void getSharedGrows(AsyncCallback<GrowBean[]> asyncCallback);
	
	void saveQuestionComment(String questionId, String comment, AsyncCallback<String> asyncCallback);
	void getCommentsForGrow(String growId, AsyncCallback<CommentBean[]> asyncCallback);
	void deleteComment(String commentId, AsyncCallback<Void> asyncCallback);

	void fts(String search, AsyncCallback<DescriptorBean[]> callback);
}