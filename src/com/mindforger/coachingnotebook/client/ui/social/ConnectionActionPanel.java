package com.mindforger.coachingnotebook.client.ui.social;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.shared.beans.FriendBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;

public class ConnectionActionPanel extends FlexTable {
	private RiaContext ctx;
	
	private ConnectionsPanel connectionsPanel;

	private RiaMessages i18n;

	public static enum ConnectionActionPanelMode {
		ADD_AS_CONNECTION, 
		ACCEPT_CONNECTION
	}
	
	public ConnectionActionPanel(RiaContext ctx) {
		this.ctx=ctx;
		i18n = ctx.getI18n();
		this.connectionsPanel=ctx.getConnectionsPanel();
		
		setStyleName("mf-connectionActionPanel");
	}	
	public void refresh(
			final UserBean userBean, 
			final ConnectionActionPanelMode mode) {
		removeAllRows();
		
		// photo
		String htmlString="<img src='"+RiaUtilities.getGravatatarUrl(userBean)+"?s=75&d=identicon'>";
		HTML html = new HTML(htmlString);
		html.setStyleName("mf-connectionsActionPanelPhoto");
		setWidget(0,0,html);
		
		// question
		if(mode.equals(ConnectionActionPanelMode.ADD_AS_CONNECTION)) {
			html=new HTML(i18n.doYouReallyWantToAddXAsConnection(userBean.getNickname()));
		} else {
			if(mode.equals(ConnectionActionPanelMode.ACCEPT_CONNECTION)) {
				html=new HTML(i18n.doYouReallyWantToAcceptXAsConnection(userBean.getNickname()));
			}
		}
		html.setStyleName("mf-connectionsActionPanelQuestion");
		setWidget(0,1,html);
		
		// actions
		HorizontalPanel actionsPanel=new HorizontalPanel();		
		actionsPanel.setStyleName("mf-connectionsActionPanelActions");
		if(mode.equals(ConnectionActionPanelMode.ADD_AS_CONNECTION)) {
			html=new HTML(i18n.addAs()+"&nbsp;");
			html.setStyleName("mf-friendRequestsText");
			actionsPanel.add(html);
			actionsPanel.add(new AddAsConnectionButton(userBean.getUserId(), FriendBean.ROLE_FRIEND, ctx));
			actionsPanel.add(new AddAsConnectionButton(userBean.getUserId(), FriendBean.ROLE_COACH, ctx));
			actionsPanel.add(new AddAsConnectionButton(userBean.getUserId(), FriendBean.ROLE_CLIENT, ctx));
			html = new HTML("&nbsp;"+i18n.or()+"&nbsp;");
			html.setStyleName("mf-friendRequestsText");
			actionsPanel.add(html);
			final Button cancelButton = new Button(i18n.cancel(), new ClickHandler() {
				public void onClick(ClickEvent event) {
					connectionsPanel.hideConnectionActionPanel();
				}
			});
			cancelButton.setStyleName("mf-button");
			actionsPanel.add(cancelButton);		
		} else {
			if(mode.equals(ConnectionActionPanelMode.ACCEPT_CONNECTION)) {
				html=new HTML(i18n.acceptAs()+"&nbsp;");
				html.setStyleName("mf-friendRequestsText");
				actionsPanel.add(html);
				actionsPanel.add(new AcceptFriendButton(userBean.getUserId(), FriendBean.ROLE_FRIEND, ctx));
				actionsPanel.add(new AcceptFriendButton(userBean.getUserId(), FriendBean.ROLE_COACH, ctx));
				actionsPanel.add(new AcceptFriendButton(userBean.getUserId(), FriendBean.ROLE_CLIENT, ctx));
				html = new HTML("&nbsp;"+i18n.or()+"&nbsp;");
				html.setStyleName("mf-friendRequestsText");
				actionsPanel.add(html);
				final Button cancelButton = new Button(i18n.cancel(), new ClickHandler() {
					public void onClick(ClickEvent event) {
						// TODO change
						connectionsPanel.hideConnectionActionPanel();
					}
				});
				cancelButton.setStyleName("mf-button");
				actionsPanel.add(cancelButton);						
			}
		}
		setWidget(1,1,actionsPanel);

		setVisible(true);
	}
}
