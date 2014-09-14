package com.mindforger.coachingnotebook.client.ui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;

public class QuestionsStream extends FlexTable {
	
	private ArrayList<QuestionAnswerPanel> model;
	
	static class QuestionMoveCommnad {
		public boolean doSwitch;
		public String questionIdA;
		public String questionIdB;
		
		public QuestionMoveCommnad() {
			doSwitch=false;
		}

		public QuestionMoveCommnad(String questionIdA, String questionIdB) {
			doSwitch=true;
			this.questionIdA=questionIdA;
			this.questionIdB=questionIdB;
		}
	}
	
	public QuestionsStream() {
	    setStyleName("mf-questionsStream");
	    model=new ArrayList<QuestionAnswerPanel>();
	}

	void insertQuestion(QuestionAnswerPanel questionAnswerPanel, int row) {
		super.insertRow(row);
		setWidget(row, 0, questionAnswerPanel);
		// model
		model.add(row, questionAnswerPanel);
	}

	public void removeQuestion(QuestionAnswerPanel questionAnswerPanel) {
		int indexOf = model.indexOf(questionAnswerPanel);
		GWT.log("Delete question from the stream: #"+indexOf);
		if(indexOf>=0) {
			super.removeRow(indexOf);
			model.remove(indexOf);
		}
	}
	
	@Override
	public void removeAllRows() {
		super.removeAllRows();
		model.clear();
	}
	
	@Override
	public int insertRow(int row) {
		throw new RuntimeException("Use of insert row forbidden!");
	}

	public QuestionMoveCommnad moveUp(QuestionAnswerPanel questionAnswerPanel) {
		if(model.size()<=1) {
			return new QuestionMoveCommnad();
		}
		int questionA = model.indexOf(questionAnswerPanel);
		int questionB = 0;
		GWT.log("Move up question: #"+questionA);
		if(questionA==0) {
			questionB=model.size()-1;
		} else {
			if(questionA>0) {
				questionB=questionA-1;
			}			
		}
		switchWidgetsAndUpdateQuestionBeanOrder(questionA, questionB);
		
		return new QuestionMoveCommnad(
				model.get(questionA).getQuestionId(), 
				model.get(questionB).getQuestionId());
	}
	
	public QuestionMoveCommnad moveDown(QuestionAnswerPanel questionAnswerPanel) {
		if(model.size()<=1) {
			return new QuestionMoveCommnad();
		}
		int questionB = model.indexOf(questionAnswerPanel);
		int questionA = 0;
		GWT.log("Move up question: #"+questionB);
		if(questionB==model.size()-1) {
			questionA=0;
		} else {
			if(questionB<model.size()-1) {
				questionA=questionB+1;
			}			
		}
		switchWidgetsAndUpdateQuestionBeanOrder(questionA, questionB);
		return new QuestionMoveCommnad(
				model.get(questionA).getQuestionId(), 
				model.get(questionB).getQuestionId());
	}

	/**
	 * Convention: 
	 *   This method is called in a way that question A suppose to be at the top of question B after switch.
	 *   In other words, parameters are ordered and this method just changes order variables.
	 *   This convention might be used to fix the order variables if they are the same for both questions.
	 */	
	private void switchWidgetsAndUpdateQuestionBeanOrder(int questionA, int questionB) {
		// widgets
		QuestionAnswerPanel a=model.get(questionA);
		QuestionAnswerPanel b = model.get(questionB);
		model.set(questionA, b);
		model.set(questionB, a);
		setWidget(questionB, 0, a);
		setWidget(questionA, 0, b);
		// order
		int orderA = a.getQuestionOrder();
		int orderB = b.getQuestionOrder();
		if(orderA==orderB) {
			orderB--;
		}
		a.setQuestionOrder(orderB);
		b.setQuestionOrder(orderA);
	}

	public int getHeadQuestionOrder() {
		if(model.size()>0) {
			QuestionAnswerPanel questionAnswerPanel = model.get(0);
			return questionAnswerPanel.getQuestionOrder();
		}
		return 0;
	}
}
