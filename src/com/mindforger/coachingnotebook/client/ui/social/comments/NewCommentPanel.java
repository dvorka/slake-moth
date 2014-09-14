package com.mindforger.coachingnotebook.client.ui.social.comments;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;

public class NewCommentPanel extends VerticalPanel {
	
	private RiaContext ctx;
	private String questionId;
	private QuestionCommentsPanel questionCommentsPanel;
	private RiaMessages i18n;

	public NewCommentPanel(final String questionId, QuestionCommentsPanel questionCommentsPanel, final RiaContext ctx) {
		setStyleName("mf-newCommentPanel");
		
		this.questionId=questionId;
		this.questionCommentsPanel=questionCommentsPanel;
		this.ctx=ctx;
		this.i18n = ctx.getI18n();
		
		if(ctx.getGrowPanel().isRdWr()) {
			// for owner show just small add comment button
			final Button writeCommentButton=new Button(i18n.writeACommentDot());
			writeCommentButton.setStyleName("mf-writeACommentButton");
			writeCommentButton.setTitle(i18n.writeAComment());
			writeCommentButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					writeCommentButton.setVisible(false);
					showTextArea();
				}
			});
			add(writeCommentButton);
		} else {			
			final TextBox textBox = new TextBox();
			textBox.setStyleName("mf-writeACommentTextBox");
			textBox.setTitle(i18n.writeAComment());
			textBox.setText(i18n.writeACommentDot());
			textBox.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					textBox.setVisible(false);
					showTextArea();
				}
			});
			add(textBox);
		}
	}
	
	private void showTextArea() {
		final TextArea textArea=new TextArea();
		textArea.setStyleName("mf-writeACommentTextArea");
		textArea.setTitle(i18n.hitCtrlEnterToSubmitComment());
		textArea.setFocus(true);
		textArea.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER && event.isControlKeyDown()) {
					if(!"".equals(textArea.getText())) {
						ctx.getStatusLine().showProgress(i18n.savingYourComment());
						ctx.getService().saveQuestionComment(questionId,textArea.getText(),new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								ctx.getRia().handleServiceError(caught);
							}
							public void onSuccess(String result) {
								ctx.getStatusLine().hideStatus();
								textArea.setVisible(false);
								CommentBean commentBean=new CommentBean(
										result,
										ctx.getState().getCurrentUser(),
										questionId,
										textArea.getText(),
										new Date());
								// this is a new comment, therefore it is always added at the end - replacing existing one ;-)
								final int threadRow = questionCommentsPanel.getRowCount()-1;
								questionCommentsPanel.setWidget(threadRow, 0, new ViewCommentPanel(threadRow, questionCommentsPanel, commentBean, ctx));
								questionCommentsPanel.addNewComment();
							}
						});						
					}
				}			
			}
		});
		add(textArea);
	}
}
