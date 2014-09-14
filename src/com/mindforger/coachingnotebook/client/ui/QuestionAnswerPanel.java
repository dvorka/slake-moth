package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;
import com.mindforger.coachingnotebook.shared.beans.QuestionAnswerBean;

public class QuestionAnswerPanel extends VerticalPanel {
	
	private QuestionEditPanel edit;
	private ViewQuestionPanel view;
	private QuestionsStream questionsStream;
	private GrowTab growTab;
	private RiaContext ria;
	
	public QuestionAnswerPanel(String questionId, int questionOrder, String[] labels, String[] questions, String mode, GrowTab growTab, RiaContext ctx) {
		setWidth("100%");
		
		this.questionsStream=growTab.questionsStream;
		this.growTab=growTab;
		this.ria=ctx;
		
		edit = new QuestionEditPanel(questionId, questionOrder, labels, questions, mode, this, ctx);
		view = new ViewQuestionPanel(questionId, edit, ctx);
		edit.setViewQuestionPanel(view);
		
		add(view);
		add(edit);
	}
	
	public QuestionAnswerBean fromRia() {		
		return edit.fromRia();
	}
	
	public void toRia(QuestionAnswerBean bean) {
		edit.toRia(bean);
		view.toRia(bean.getQuestionLabel(), bean.getQuestion(), bean.getAnswer(), bean.getDeadline(), ""+bean.getProgress());
		
		toViewMode();
	}
	
	public void toEditMode() {
		if(ria.getGrowPanel().isRdWr()) {
			view.setVisible(false);
			edit.setVisible(true);			
		}
	}
	
	private void toViewMode() {
		view.setVisible(true);
		edit.setVisible(false);
	}
	
	public void selfDestroy() {
		questionsStream.removeQuestion(this);
	}
	
	public QuestionsStream.QuestionMoveCommnad moveUp() {
		return questionsStream.moveUp(this);
	}
	
	public QuestionsStream.QuestionMoveCommnad moveDown() {
		return questionsStream.moveDown(this);
	}

	public void save(boolean reloadAfterSave) {
		growTab.save(reloadAfterSave);
		
		ria.getLeftMenubar().getRecentPanel().addRow(ria.getGrowPanel().getGrowId(), edit.getQuestionId(), edit.getQuestionTitle(), "Question", MindForgerResourceType.QUESTION);		
	}

	public void addComment(CommentBean commentBean) {
		view.addComment(commentBean);
	}
	
	public int getQuestionOrder() {
		return edit.getQuestionOrder();
	}

	public void setQuestionOrder(int order) {
		edit.setQuestionOrder(order);
	}

	public String getQuestionId() {
		return edit.getQuestionId();
	}
}
