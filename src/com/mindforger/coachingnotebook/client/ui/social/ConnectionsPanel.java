package com.mindforger.coachingnotebook.client.ui.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.LeftMenubar;
import com.mindforger.coachingnotebook.client.ui.StatusLine;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionPanel.ConnectionPanelMode;
import com.mindforger.coachingnotebook.shared.beans.FriendBean;

/**
 * Universal new connection/connections/friends/coaches/clients panel.
 */
public class ConnectionsPanel extends FlexTable {

	public static enum ConnectionsPanelMode {
		CONNECTIONS,
		NEW_CONNECTION,
		FRIENDS,
		COACHES,
		CLIENTS
	}
	
	private int connectionActionsPanelRow;
	private ConnectionActionPanel connectionActionsPanel;

	private HorizontalPanel findConnectionPanel;
	private Button findConnectionButton;
	private TextBox findConnectionTextBox;
	
	private HorizontalPanel filteringControlsPanel;
	
	private FlowPanel confirmedConnectionsPanel;
	private FlowPanel pendingConnectionsPanel;
	private FlowPanel toBeAcceptedConnectionsPanel;
	
	private RiaContext ctx;
	
	private List<ConnectionsRequestsListener> connectionRequestsListeners;
	
	private Ria ria;
	private StatusLine statusLine;
	private MindForgerServiceAsync service;
	private RiaMessages i18n;

	public ConnectionsPanel(final RiaContext ctx) {
		this.ctx=ctx;
	}
	
	public void init() {
		this.ria=ctx.getRia();
		this.statusLine=ctx.getStatusLine();
		this.service=ctx.getService();
		this.i18n=ctx.getI18n();
		
		setStyleName("mf-friendsPanel");

		connectionRequestsListeners=new ArrayList<ConnectionsRequestsListener>();

		findConnectionButton = new Button(i18n.findNewConnections(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				findConnections(ctx);
			}
		}); 
		findConnectionButton.setTitle(i18n.clickToSearchForFriends());
		findConnectionButton.setStyleName("mf-button");
		findConnectionButton.addStyleName("mf-findFriendButton");
		findConnectionTextBox = new TextBox();
		findConnectionTextBox.setStyleName("mf-findFriendTextBox");
		findConnectionTextBox.setTitle(i18n.hitEnterToSearch());
		findConnectionTextBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
					findConnections(ctx);
				}
			}
		});
		findConnectionPanel = new HorizontalPanel();
		findConnectionPanel.setStyleName("mf-findFriendPanel");
		findConnectionPanel.add(findConnectionButton);
		findConnectionPanel.add(findConnectionTextBox);
	}

	public void refreshConnections(final ConnectionsPanelMode mode, final boolean silent) {
		removeAllRows();
				
		if(ConnectionsPanelMode.NEW_CONNECTION.equals(mode)) {
			// show the connections you are NOT friend with > find new connections
			findConnections(ctx);
		} else {
			// connections listing > filtered view of existing connections
			if(!silent) {
				statusLine.showProgress(i18n.loadingYourConnections());
			}
			// load existing friends and pending requests
			service.myFriends(new AsyncCallback<Map<String, FriendBean> >() {
				public void onFailure(Throwable caught) {
					ria.handleServiceError(caught);
				}
				public void onSuccess(Map<String, FriendBean>  map) {
					if(!silent) {
						statusLine.hideStatus();						
					}

					int row=0;
					int counterToBeAcceptedConnectionRequests=0;				
					int counterConfirmedConnections=0;				
					int counterPendingConnectionRequests=0;
					connectionActionsPanelRow=row;
					addConnectionActionPanel(row++);
					addFilteringControls(row++);
					
					// panels: to be accepted/pending/confirmed
					// Other Users' Requests
					Widget toBeAcceptedDelimiter = getDelimiterPanel(i18n.usersWhoWantToConnectWithYou());
					ConnectionsPanel.this.setWidget(row++, 0, toBeAcceptedDelimiter);
					toBeAcceptedConnectionsPanel=new FlowPanel();
					ConnectionsPanel.this.setWidget(row++, 0, toBeAcceptedConnectionsPanel);
					// Your Requests
					Widget pendingDelimiter = getDelimiterPanel(i18n.yourConnectionRequests());
					ConnectionsPanel.this.setWidget(row++, 0, pendingDelimiter);
					pendingConnectionsPanel=new FlowPanel();
					ConnectionsPanel.this.setWidget(row++, 0, pendingConnectionsPanel);
					// Your Connections
					Widget confirmedDelimiter = getDelimiterPanel(i18n.yourConnections());
					ConnectionsPanel.this.setWidget(row++, 0, confirmedDelimiter);
					confirmedConnectionsPanel=new FlowPanel();
					ConnectionsPanel.this.setWidget(row++, 0, confirmedConnectionsPanel);
					
					FriendBean[] result=map.values().toArray(new FriendBean[map.size()]);
					if(result.length>0) {
						for (int i = 0; i < result.length; i++) {	
							// TODO filter them using ROLE > use filter listbox
							if(result[i].isConfirmed()) {
								counterConfirmedConnections++;
								confirmedConnectionsPanel.add(new ConnectionPanel(result[i].getFriend(), ConnectionPanelMode.CONFIRMED, ctx));
							} else {
								// action: pending/approve
								if(result[i].isRequestedByOwner()) {
									counterPendingConnectionRequests++;
									pendingConnectionsPanel.add(new ConnectionPanel(result[i].getFriend(), ConnectionPanelMode.PENDING, ctx));
								} else {
									// accept as...
									counterToBeAcceptedConnectionRequests++;									
									toBeAcceptedConnectionsPanel.add(new ConnectionPanel(result[i].getFriend(), ConnectionPanelMode.TO_BE_ACCEPTED, ctx));
								}
							}
						}
					} else {
						//if(!silent) {
						//	ria.getStatusLine().setHelp("Use <span style='color: black;'>Find Connections</span> action to add new connections...");
						//}
						
						// TODO Causes problem during boot, therefore commented out
						//ria.getLeftMenubar().showNewConnection();
						return;
					}
					
					if(counterConfirmedConnections==0) {
						confirmedDelimiter.setVisible(false);
					}
					if(counterPendingConnectionRequests==0) {
						pendingDelimiter.setVisible(false);
					}
					if(counterToBeAcceptedConnectionRequests==0) {
						toBeAcceptedDelimiter.setVisible(false);
					}
					if((counterConfirmedConnections+counterPendingConnectionRequests+counterToBeAcceptedConnectionRequests)==0) {
						filteringControlsPanel.setVisible(false);
					}
										
					// notify subscribers
					for (int j = 0; j < connectionRequestsListeners.size(); j++) {
						connectionRequestsListeners.get(j).setConnectionRequests(counterToBeAcceptedConnectionRequests);
					}
				}
			});			
		}
	}


	public void showConnectionActionPanel() {
		connectionActionsPanel.setVisible(true);
		for (int i = 1; i < getRowCount(); i++) {
			if(i!=connectionActionsPanelRow) {
				getRowFormatter().setVisible(i, false);
			}
		}
	}
	
	public void hideConnectionActionPanel() {
		connectionActionsPanel.setVisible(false);
		for (int i = 1; i < getRowCount(); i++) {
			if(i!=connectionActionsPanelRow) {
				getRowFormatter().setVisible(i, true);
			}
		}
	}
	
	private void addConnectionActionPanel(int row) {
		connectionActionsPanel = new ConnectionActionPanel(ctx);
		connectionActionsPanel.setVisible(false);
		ConnectionsPanel.this.setWidget(row, 0, connectionActionsPanel);
	}

	private void addFindFriendControls(int row) {
		setWidget(row,0,findConnectionPanel);
	}

	private void addFilteringControls(int row) {
		filteringControlsPanel = new HorizontalPanel();
		filteringControlsPanel.addStyleName("mf-friendPanelFilteringControls");
		Button button;
		
		// all
		button = new Button();
		button.setStyleName("mf-button");
		button.setText(i18n.allConnections());
		button.setTitle(i18n.allYourConnections());
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		filteringControlsPanel.add(button);
/*
		// requests
		button = new Button();
		button.setStyleName("mf-button");
		button.setText("Friends");
		button.setTitle("Your friends");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		// requests
		button = new Button();
		button.setStyleName("mf-button");
		button.setText("Coaches");
		button.setTitle("Your coaches");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		filteringControlsPanel.add(button);
		// requests
		button = new Button();
		button.setStyleName("mf-button");
		button.setText("Clients");
		button.setTitle("Clients who you coach");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		filteringControlsPanel.add(button);
		// your requests
		button = new Button();
		button.setStyleName("mf-button");
		button.setText("Your Connection Requests");
		button.setTitle("Your connection requests");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		filteringControlsPanel.add(button);
		// others requests
		button = new Button();
		button.setStyleName("mf-button");
		button.setText("Others Who Want To Connect");
		button.setTitle("Requests from other users who want to connect with you");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});
		filteringControlsPanel.add(button);
		setWidget(row,0,filteringControlsPanel);
*/		
	}
	
	private void findConnections(final RiaContext ria) {
		ria.getStatusLine().showProgress(i18n.searchingForConnections());
		ria.getService().findFriend(findConnectionTextBox.getText(), new AsyncCallback<FriendBean[]>() {
			public void onSuccess(FriendBean[] result) {
				ria.getStatusLine().hideStatus();

				ConnectionsPanel.this.removeAllRows();
				int row=0;
				addConnectionActionPanel(row++);
				addFindFriendControls(row++);
								
				FlowPanel foundConnectionsPanel=new FlowPanel();
				foundConnectionsPanel.setStyleName("mf-foundConnectionsPanel");
				ConnectionsPanel.this.setWidget(row, 0, foundConnectionsPanel);
				row++;
								
				for (int i = 0; i < result.length; i++) {
					foundConnectionsPanel.add(new ConnectionPanel(result[i].getFriend(), ConnectionPanelMode.TO_BE_ADDED, ctx));
				}
				
				hideConnectionActionPanel();
				findConnectionTextBox.setText("");
			}
			public void onFailure(Throwable caught) {
				ria.getRia().handleServiceError(caught);
				findConnectionTextBox.setText("");
			}
		});
	}
	
	public ConnectionActionPanel getConnectionActionsPanel() {
		return connectionActionsPanel;
	}

	public void addConnectionsRequestsListener(LeftMenubar leftMenubar) {
		connectionRequestsListeners.add(leftMenubar);
	}

	private Widget getDelimiterPanel(String text) {
		FlowPanel delimiterPanel=new FlowPanel();
		delimiterPanel.setStyleName("mf-friendsPanelDelimiterPanel");
		HTML html=new HTML(text);
		html.setStyleName("mf-friendsPanelDelimiterText");
		delimiterPanel.add(html);
		return delimiterPanel;
	}	
}
