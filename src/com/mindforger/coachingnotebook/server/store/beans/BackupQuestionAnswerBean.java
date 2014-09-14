package com.mindforger.coachingnotebook.server.store.beans;

import java.util.Date;

import com.mindforger.coachingnotebook.server.store.BackupBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class BackupQuestionAnswerBean implements BackupBean {

	private String key;
	private String growType;
	private String questionLabel;
	private String question;
	private String answer;
	private String growId;
	private String ownerId;
	private Date deadline;
	private int progress;
	private String category;
	private int order;
	private int depth;
	
	public BackupQuestionAnswerBean() {
		growType=QuestionAnswerBean.G_PART;
	}
			
	public BackupQuestionAnswerBean(String growType) {
		this.growType=growType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getGrowKey() {
		return growId;
	}

	public void setGrowKey(String growId) {
		this.growId = growId;
	}

	public String getGrowType() {
		return growType;
	}

	public void setGrowType(String growType) {
		this.growType = growType;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getGrowId() {
		return growId;
	}

	public void setGrowId(String growId) {
		this.growId = growId;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}	
}
