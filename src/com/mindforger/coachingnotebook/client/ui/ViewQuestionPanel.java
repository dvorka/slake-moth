package com.mindforger.coachingnotebook.client.ui;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.social.comments.QuestionCommentsPanel;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;

public class ViewQuestionPanel extends VerticalPanel {

	private String label;
	private String question;
	private String answer;
	private QuestionCommentsPanel questionCommentsPanel;

	HTML questionHtml;
	HTML answerHtml;
	HTML deadlineAndProgressHtml;
	Date deadline;
	String progress;
	
    DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
    
	private RiaMessages i18n;
	
	public ViewQuestionPanel(String questionId, VerticalPanel edit, RiaContext ctx) {
		i18n = ctx.getI18n();
		
		setStyleName("mf-viewQuestionAnswer");
		
		ToEditModeClickHandler editClickHandler = new ToEditModeClickHandler(edit, this, ctx);
		FoldClickHandler foldClickHandler = new FoldClickHandler(this);
		
		questionHtml = new HTML(label+": "+question);
		questionHtml.setStyleName("mf-viewQuestionAnswerQ");
		questionHtml.setTitle(i18n.clickQuestionLabelToFoldUnfoldAndEdit());
		questionHtml.addClickHandler(foldClickHandler);
		add(questionHtml);
		
		answerHtml = new HTML(answer);
		answerHtml.setStyleName("mf-viewQuestionAnswerA");
		answerHtml.setTitle(i18n.clickQuestionLabelToFoldUnfoldAndEdit());
		answerHtml.addClickHandler(editClickHandler);
		add(answerHtml);

		deadlineAndProgressHtml = new HTML(getDeadlineProgressHtml());
		deadlineAndProgressHtml.setStyleName("mf-deadlineAndProgressView");
		deadlineAndProgressHtml.addClickHandler(editClickHandler);
		add(deadlineAndProgressHtml);
		
		this.questionCommentsPanel = new QuestionCommentsPanel(questionId, ctx);
		add(questionCommentsPanel);		
	}

	private String getDeadlineProgressHtml() {
		return 
				(deadline!=null&&!deadline.equals("")?"<b>"+i18n.deadline()+"</b>: "+dateFormat.format(deadline):"")+
				(progress!=null&&!progress.equals("")&&!progress.equals("0")?"&nbsp;&nbsp;&nbsp;<b>"+i18n.progress()+"</b>: "+progress+"%":"");
	}
	
	public void toRia(String label, String question, String answer, Date deadline, String progress) {
		this.label=(label==null||"".equals(label)?"...":label);
		this.question=(question==null||"".equals(question)?"...":question);
		this.answer=(answer==null||"".equals(answer)?"...":answer);
		this.deadline=deadline;
		this.progress=progress;
		
		// TODO i18n vs. drop down ID
		if("Question".equals(label)) {
			label="";
		}

		questionHtml.setText(this.label+": "+this.question);
		answerHtml.setText(this.answer);
		
		// TODO i18n vs. drop down ID
		if("Action".equals(this.label) || "Task".equals(this.label)) {
			if(!"".equals(deadlineAndProgressHtml)) {
				deadlineAndProgressHtml.setHTML(getDeadlineProgressHtml());			
				deadlineAndProgressHtml.setVisible(true);
			} else {
				deadlineAndProgressHtml.setVisible(false);
			}			
		}
		
		answerHtml.setVisible(false);
		questionCommentsPanel.setVisible(false);
	}

	public void addComment(CommentBean commentBean) {
		questionCommentsPanel.addComment(commentBean);
	}
	
	public void toggleAnswerVisibility() {
		if(answerHtml.isVisible()) {
			answerHtml.setVisible(false);
			questionCommentsPanel.setVisible(false);
		} else {
			answerHtml.setVisible(true);
			questionCommentsPanel.setVisible(true);
		}		
	}
}

@Deprecated
// TODO standalone class starting with grown name
class ToEditModeClickHandler implements ClickHandler {
	
	private VerticalPanel edit;
	private VerticalPanel view;
	private RiaContext ria;
	
	public ToEditModeClickHandler(VerticalPanel edit, VerticalPanel view, RiaContext ria) {
		this.edit=edit;
		this.view=view;
		this.ria=ria;
	}
	
	public void onClick(ClickEvent event) {
		if(ria.getGrowPanel().isRdWr()) {
			view.setVisible(false);
			edit.setVisible(true);				
		}
	}
}

class FoldClickHandler implements ClickHandler {
	
	private ViewQuestionPanel view;
	
	public FoldClickHandler(ViewQuestionPanel view) {
		this.view=view;
	}
	
	public void onClick(ClickEvent event) {
		view.toggleAnswerVisibility();
	}
}

