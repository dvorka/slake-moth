package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.verifiers.FieldVerifier.Field;

/**
 * Click to edit; hit ENTER to view.
 */
public class NicknamePanel extends VerticalPanel {
	public static final int MAX_NICKNAME_LENGTH=30;
	
	private String nickname;
	private String viewCssStyle;
	private boolean updateRightCorner;
	
	private TextBox edit;
	private HTML view;
	
	private RiaMessages i18n;
	private MindForgerServiceAsync service;
	private StatusLine statusLine;
	private Ria ria;
	private RightCornerPanel rightCornerPanel;
	
	public NicknamePanel(String nickname, String viewCssStyle, boolean updateRightCorner, RiaContext ctx) {
		this.statusLine=ctx.getStatusLine();
		this.i18n=ctx.getI18n();
		this.service=ctx.getService();
		this.ria=ctx.getRia();
		this.rightCornerPanel=ctx.getRightCornerPanel();

		setStyleName("mf-nicknamePanel");
		
		if(nickname==null) {
			statusLine.showProgress(i18n.loadingNickname());
			service.getNickname(new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					ria.handleServiceError(caught);
				}
				public void onSuccess(String nickname) {
					statusLine.hideStatus();
					initializePanel(nickname);
				}
			});							
		} else {			
			initializePanel(nickname);
		}
	}
	
	private void initializePanel(String nickname) {
		this.nickname=nickname;
		
		edit = new TextBox();
		edit.setTitle(i18n.hitEnterForViewMode());
	    edit.setStyleName("mf-nicknameEdit");
		edit.setText(nickname);
		edit.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
					String newNickname=edit.getText();
					if(newNickname==null || "".equals(newNickname)) {
						statusLine.showError(i18n.yourNicknameCannotBeEmpty());
					} else {
						if(!ria.verifyField(Field.USER_NAME, newNickname, i18n.nickname())) {
							return;
						}
					}
					setNickname(newNickname);
					view.setText(getNickname());
					toViewTitleMode();
					
					service.setNickname(getNickname(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							ria.handleServiceError(caught);
						}
						public void onSuccess(Void noresult) {
							statusLine.hideStatus();
							if(updateRightCorner) {
								rightCornerPanel.setNickname(getNickname());								
							}
						}
					});				
				}
			}
		});
		add(edit);
		
		view = new HTML(nickname);
		view.setTitle(i18n.clickToChangeYourNickname());
		view.setStyleName(viewCssStyle);
		view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.setText(edit.getText());
				view.setVisible(false);
				edit.setVisible(true);
			}
		});
		add(view);
						
		toViewTitleMode();		
	}

	public void toEditTitleMode() {
		view.setVisible(false);
		edit.setVisible(true);		
	}
	
	public void toViewTitleMode() {
		view.setText(edit.getText());
		view.setVisible(true);
		edit.setVisible(false);
	}

	private String getNickname() {
		return nickname;
	}

	private void setNickname(String nickname) {
		this.nickname=nickname;
	}
	
	public void refreshNickname(String nickname) {
		setNickname(nickname);
		edit.setText(nickname);
		toViewTitleMode();
	}
}
