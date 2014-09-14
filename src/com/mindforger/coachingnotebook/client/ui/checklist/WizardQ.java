package com.mindforger.coachingnotebook.client.ui.checklist;

public class WizardQ {

	public static final String RATHER_YES="ry";
	public static final String RATHER_NO="rn";
	public static final String YES="y";
	public static final String NO="n";

	public static final int QUESTION_TYPE_YES_RATHER_NO=1;
	public static final int QUESTION_TYPE_STRING=2;
	public static final int QUESTION_TYPE_TRIPLE_STRING=3;
	
	private Integer id;
	private String question;
	private int questionType;
	private String hint;
	
	public WizardQ() {
		questionType=QUESTION_TYPE_YES_RATHER_NO;
	}

	public WizardQ(Integer id, String question, int questionType, String hint) {
		this.id=id;
		this.question=question;
		this.questionType=questionType;
		this.hint=hint;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
}
