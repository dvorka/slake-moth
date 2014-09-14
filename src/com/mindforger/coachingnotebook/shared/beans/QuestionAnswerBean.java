package com.mindforger.coachingnotebook.shared.beans;

import java.io.Serializable;
import java.util.Date;

public class QuestionAnswerBean implements Serializable {

	public static final String CSS_DEADLINE_DEFAULT="mf-actionDeadline";
	
	public static final String G_PART = "G";
	public static final String R_PART = "R";
	public static final String O_PART = "O";
	public static final String W_PART = "W";
	public static final String I_PART = "I";
		
	private String key;
	private String growId;
	private String growType;
	
	private String questionLabel;
	private String question;
	private String answer;
	
	private Date deadline;
	private int progress;
	private String category;
	
	private String deadlineCssClass;
	
	private int order;
	private int depth;
	
	public QuestionAnswerBean() {
		deadlineCssClass=CSS_DEADLINE_DEFAULT;
	}

	public QuestionAnswerBean(String growType) {
		this();
		this.growType=growType;
	}
	
	public QuestionAnswerBean(String questionLabel, String question, String answer, String growType) {
		this();
		this.questionLabel=questionLabel;
		this.question=question;
		this.answer=answer;
		this.growType=growType;
	}
		
	public QuestionAnswerBean(String questionLabel, String question, String answer, String growType, Date deadline, int progress) {
		this(questionLabel,question,answer,growType);
		this.deadline=deadline;
		this.progress=progress;
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
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public void setGrowMode(String growType) {
		this.growType= growType;
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

	public void setDeadlineCssClass(String deadlineCssClass) {
		this.deadlineCssClass=deadlineCssClass;
	}

	public String getDeadlineCssClass() {
		if(deadlineCssClass==null) {
			deadlineCssClass=CSS_DEADLINE_DEFAULT;
		}
		return deadlineCssClass;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	private static final long serialVersionUID = -1709503153102838297L;
}
