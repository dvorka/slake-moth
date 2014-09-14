package com.mindforger.coachingnotebook.client.ui.social;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionActionPanel.ConnectionActionPanelMode;
import com.mindforger.coachingnotebook.shared.beans.UserBean;

public class ConnectionPanel extends VerticalPanel {
	public static enum ConnectionPanelMode {
		VIEW,
		TO_BE_ACCEPTED,
		TO_BE_ADDED,
		PENDING,
		CONFIRMED,
	};

	private Ria ria;
	private UserProfilePanel userProfilePanel;
	private ConnectionsPanel connectionsPanel;
	private RiaMessages i18n;
	
	public ConnectionPanel(final UserBean user, ConnectionPanelMode mode, RiaContext ctx) {
		this.userProfilePanel=ctx.getUserProfilePanel();
		this.ria=ctx.getRia();
		i18n = ctx.getI18n();
		this.connectionsPanel=ctx.getConnectionsPanel();
		
		setStyleName("mf-connectionsProfilePanel");

		HTML html, photoHtml;
		
		// gravatar
		String htmlString="<img border='0' src='"+RiaUtilities.getGravatatarUrl(user)+"?s=75&d=identicon'>";
		photoHtml = new HTML(htmlString);
		photoHtml.setStyleName("mf-connectionsProfilePhoto");
		if(!mode.equals(ConnectionPanelMode.TO_BE_ADDED)) {
			photoHtml.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					userProfilePanel.refreshProfile(user.getUserId());
					ria.showUserProfile();
				}
			});			
		}
		add(photoHtml);
		
		// link to profile
		String nickname=user.getNickname();
		if(!mode.equals(ConnectionPanelMode.CONFIRMED)) {
			nickname=RiaUtilities.getPrivacySafeNickname(user.getNickname());
		}
		if(mode.equals(ConnectionPanelMode.CONFIRMED) || mode.equals(ConnectionPanelMode.VIEW)) {
			UserProfileButton button=new UserProfileButton(nickname, user.getUserId(), "mf-showUserProfileConfirmed", ctx);
			add(button);			
		} else {
			html = new HTML(nickname);
			html.setStyleName("mf-addFriendName");
			add(html);						
		}
		
		// role
		if(mode.equals(ConnectionPanelMode.CONFIRMED)) {
			html = new HTML(user.getRole());
			html.setStyleName("mf-connectionsProfilePanelRole");
			add(html);
		}
		
		// status
		if(mode.equals(ConnectionPanelMode.PENDING)) {
			html=new HTML(i18n.waitingForApproval());
			html.setStyleName("mf-pendingFriend");
			html.setTitle(i18n.yourFriendRequestWasNotAcceptedYet());
			add(html);
		} else {
			if(mode.equals(ConnectionPanelMode.TO_BE_ACCEPTED)) {
				html=new HTML(i18n.connectionRequest());
				html.setTitle(connectionRequestFromOtherUserIsWaiting());
				html.setStyleName("mf-pendingFriend");
				add(html);
			}
		}

		// add as/revoke button
		if(mode.equals(ConnectionPanelMode.TO_BE_ADDED)) {
			final ClickHandler openConnectionActionHandler = new ClickHandler() {
				public void onClick(ClickEvent event) {
					connectionsPanel.getConnectionActionsPanel().refresh(
							user,
							ConnectionActionPanelMode.ADD_AS_CONNECTION);
					connectionsPanel.showConnectionActionPanel();
				}
			};
			photoHtml.addClickHandler(openConnectionActionHandler);
			Button openConnectionActions=new Button(i18n.add(), openConnectionActionHandler);
			openConnectionActions.setStyleName("mf-button");
			add(openConnectionActions);
		} else {
			if(mode.equals(ConnectionPanelMode.TO_BE_ACCEPTED)) {
				final ClickHandler openConnectionActionHandler = new ClickHandler() {
					public void onClick(ClickEvent event) {
						connectionsPanel.getConnectionActionsPanel().refresh(
								user,
								ConnectionActionPanelMode.ACCEPT_CONNECTION);
						// TODO another show for connections listing
						connectionsPanel.showConnectionActionPanel();
					}
				};
				photoHtml.addClickHandler(openConnectionActionHandler);
				HorizontalPanel acceptRejectPanel=new HorizontalPanel();
				Button openConnectionActions=new Button(i18n.accept(), openConnectionActionHandler);
				openConnectionActions.setStyleName("mf-button");
				acceptRejectPanel.add(openConnectionActions);
				Button rejectButton=new Button(i18n.reject(), openConnectionActionHandler);
				rejectButton.setStyleName("mf-button");
				acceptRejectPanel.add(rejectButton);
				add(acceptRejectPanel);
			} else  {
				if(mode.equals(ConnectionPanelMode.CONFIRMED)) {
					RevokeFriendButton button=new RevokeFriendButton(ctx);
					button.setTitle(i18n.revoke());
					button.setText(i18n.revokeTheConnection());
					button.setFriendId(user.getUserId());
					button.init();
					add(button);
				} else {
					if(mode.equals(ConnectionPanelMode.PENDING)) {
						RevokeFriendButton button=new RevokeFriendButton(ctx);
						button.setTitle(i18n.cancel());
						button.setText(i18n.cancelYourPendingRequest());
						button.setFriendId(user.getUserId());
						button.init();
						add(button);
					}							
				}				
			}			
		}		
	}

	private String connectionRequestFromOtherUserIsWaiting() {
		// TODO Auto-generated method stub
		return null;
	}
}
