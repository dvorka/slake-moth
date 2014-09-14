package com.mindforger.coachingnotebook.client;

import java.util.Map;

import com.google.gwt.core.client.GWT;
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

public class MindForgerServiceAsyncCached implements MindForgerServiceAsync {
	
	private MindForgerServiceAsync service;
	
	public MindForgerServiceAsyncCached(RiaCache cache) {
		service=GWT.create(MindForgerService.class);
	}

	public void getRiaBootImage(AsyncCallback<RiaBootImageBean> asyncCallback) {
		service.getRiaBootImage(asyncCallback);
	}

	public void saveUserSettings(UserSettingsBean userSettings, AsyncCallback<Void> asyncCallback) {
		service.saveUserSettings(userSettings, asyncCallback);
	}

	public void newGrow(AsyncCallback<String> callback) {
		service.newGrow(callback);
	}

	public void getGrows(AsyncCallback<GrowBean[]> callback) {
		service.getGrows(callback);
	}

	public void saveGrow(GrowBean bean, AsyncCallback<GrowBean> callback) {
		service.saveGrow(bean, callback);
	}

	public void getGrow(String id, AsyncCallback<GrowBean> asyncCallback) {
		service.getGrow(id, asyncCallback);
	}

	public void deleteGrow(GrowBean bean, AsyncCallback<Void> callback) {
		service.deleteGrow(bean, callback);
	}

	public void newQuestionAnswer(String growId, int order, AsyncCallback<String> asyncCallback) {
		service.newQuestionAnswer(growId, order, asyncCallback);
	}

	public void switchQuestionAnswers(String questionIdA, String questionIdB, AsyncCallback<Void> callback) {
		service.switchQuestionAnswers(questionIdA, questionIdB, callback);
	}

	public void getCheckListAnswers(String growId, String mode, AsyncCallback<CheckListAnswerBean[]> callback) {
		service.getCheckListAnswers(growId, mode, callback);
	}

	public void setCheckListAnswer(String growId, String mode, Integer questionId, String answer, AsyncCallback<Void> asyncCallback) {
		service.setCheckListAnswer(growId, mode, questionId, answer, asyncCallback);
	}

	public void getActions(AsyncCallback<QuestionAnswerBean[]> callback) {
		service.getActions(callback);
	}

	public void getLessonsLearned(AsyncCallback<QuestionAnswerBean[]> asyncCallback) {
		service.getLessonsLearned(asyncCallback);
	}

	public void getLifeVision(AsyncCallback<String> asyncCallback) {
		service.getLifeVision(asyncCallback);
	}

	public void setLifeVision(String lifeVision, AsyncCallback<Void> asyncCallback) {
		service.setLifeVision(lifeVision, asyncCallback);
	}

	public void loginCheck(AsyncCallback<UserBean> callback) {
		service.loginCheck(callback);
	}

	public void getUserLimits(AsyncCallback<UserLimitsBean> callback) {
		service.getUserLimits(callback);
	}

	public void getUserProfile(String userId, AsyncCallback<UserProfileBean> callback) {
		service.getUserProfile(userId, callback);
	}

	public void findFriend(String query, AsyncCallback<FriendBean[]> asyncCallback) {
		service.findFriend(query, asyncCallback);
	}

	public void myFriends(AsyncCallback<Map<String, FriendBean>> asyncCallback) {
		service.myFriends(asyncCallback);
	}

	public void requestFriendship(String friend, String role, AsyncCallback<Void> callback) {
		service.requestFriendship(friend, role, callback);
	}

	public void acceptFriendship(String friendId, String role, AsyncCallback<Void> asyncCallback) {
		service.acceptFriendship(friendId, role, asyncCallback);
	}

	public void rejectFriendship(String friendId, AsyncCallback<Void> asyncCallback) {
		service.rejectFriendship(friendId, asyncCallback);
	}

	public void revokeFriendship(String friendId, AsyncCallback<Void> asyncCallback) {
		service.revokeFriendship(friendId, asyncCallback);
	}

	public void getNickname(AsyncCallback<String> asyncCallback) {
		service.getNickname(asyncCallback);
	}

	public void setNickname(String nickname, AsyncCallback<Void> asyncCallback) {
		service.setNickname(nickname, asyncCallback);
	}

	public void setUserDescription(String description, AsyncCallback<Void> callback) {
		service.setUserDescription(description, callback);
	}

	public void setUserWeb(String web, AsyncCallback<Void> callback) {
		service.setUserWeb(web, callback);
	}

	public void getGrowAcl(String growId, AsyncCallback<Map<String, PermissionBean>> callback) {
		service.getGrowAcl(growId, callback);
	}

	public void setGrowAcl(String growId, PermissionBean[] acl, AsyncCallback<Void> callback) {
		service.setGrowAcl(growId, acl, callback);
	}

	public void setGrowAclToAllConnections(String growId, AsyncCallback<Void> asyncCallback) {
		service.setGrowAclToAllConnections(growId, asyncCallback);
	}

	public void getSharedGrows(AsyncCallback<GrowBean[]> asyncCallback) {
		service.getSharedGrows(asyncCallback);
	}

	public void saveQuestionComment(String questionId, String comment, AsyncCallback<String> asyncCallback) {
		service.saveQuestionComment(questionId, comment, asyncCallback);
	}

	public void getCommentsForGrow(String growId, AsyncCallback<CommentBean[]> asyncCallback) {
		service.getCommentsForGrow(growId, asyncCallback);
	}

	public void deleteComment(String commentId, AsyncCallback<Void> asyncCallback) {
		service.deleteComment(commentId, asyncCallback);
	}

	public void fts(String search, AsyncCallback<DescriptorBean[]> callback) {
		service.fts(search, callback);
	}
}
