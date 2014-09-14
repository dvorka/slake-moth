package com.mindforger.coachingnotebook.client.ui.social.comments;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaState;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.GrowPanel;
import com.mindforger.coachingnotebook.client.ui.StatusLine;
import com.mindforger.coachingnotebook.client.ui.social.UserProfileButton;
import com.mindforger.coachingnotebook.client.ui.social.UserProfilePanel;
import com.mindforger.coachingnotebook.shared.beans.CommentBean;

public class ViewCommentPanel extends FlexTable {

	private UserProfilePanel userProfilePanel;
	private Ria ria;
	private RiaState state;
	private MindForgerServiceAsync service;
	private StatusLine statusLine;
	private GrowPanel growPanel;

	public ViewCommentPanel(
			final int threadRow, 
			final QuestionCommentsPanel questionCommentsPanel, 
			final CommentBean commentBean, 
			final RiaContext ctx) {
		this.userProfilePanel=ctx.getUserProfilePanel();
		this.ria=ctx.getRia();
		this.state=ctx.getState();
		this.service=ctx.getService();
		this.statusLine=ctx.getStatusLine();
		this.growPanel=ctx.getGrowPanel();
		
		String htmlString="<img src='"+RiaUtilities.getGravatatarUrl(commentBean.getAuthor())+"?s=32&d=identicon'>";
		HTML photoHtml = new HTML(htmlString);
		photoHtml.setStyleName("mf-commentConnectionsProfilePhoto");
		photoHtml.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userProfilePanel.refreshProfile(commentBean.getAuthor().getUserId());
				ria.showUserProfile();
			}
		});			
		setWidget(0, 0, photoHtml);
		getCellFormatter().setStyleName(0, 0, "mf-commentConnectionsProfilePhotoTd");
		getFlexCellFormatter().setRowSpan(0, 0, 2);
		
		// link to profile
		String nickname=commentBean.getAuthor().getNickname();
		setWidget(0, 1, new UserProfileButton(nickname, commentBean.getAuthor().getUserId(), "mf-commentUserButton", ctx));			

		// timestamp
		
		HTML html=new HTML(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).format(commentBean.getCreated()));
		html.setStyleName("mf-viewCommentTimestamp");
		setWidget(0, 2, html);

		// remove: comment owner OR grow owner
		if(state.getCurrentUser().getUserId().equals(commentBean.getAuthor().getUserId()) ||
				growPanel.isRdWr()) {
			Button removeCommentButton=new Button("X");
			removeCommentButton.setStyleName("mf-viewCommentRemove");
			removeCommentButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					statusLine.showProgress(ctx.getI18n().deletingComment());
					service.deleteComment(commentBean.getCommentId(),new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							ria.handleServiceError(caught);
						}
						public void onSuccess(Void result) {
							statusLine.hideStatus();
							// just hide it for now - it will disappear with the panel reload
							questionCommentsPanel.getRowFormatter().setVisible(threadRow, false);
						}
					});
				}
			});
			setWidget(0, 3, removeCommentButton);			
		}
		
		// comment
		html=new HTML(commentBean.getComment());
		html.setStyleName("mf-viewCommentComment");
		setWidget(1, 0, html);		
		getFlexCellFormatter().setColSpan(1, 0, 3);
	}
}
