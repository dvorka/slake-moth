package com.mindforger.coachingnotebook.client.ui.social;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mindforger.coachingnotebook.client.Ria;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaState;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.DashboardPanel;
import com.mindforger.coachingnotebook.client.ui.GrowsTableToGrowButton;
import com.mindforger.coachingnotebook.client.ui.NicknamePanel;
import com.mindforger.coachingnotebook.client.ui.StatusLine;
import com.mindforger.coachingnotebook.client.ui.social.ConnectionPanel.ConnectionPanelMode;
import com.mindforger.coachingnotebook.shared.MindForgerResourceType;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;
import com.mindforger.coachingnotebook.shared.beans.UserBean;
import com.mindforger.coachingnotebook.shared.beans.UserProfileBean;

// TODO add more information about user: wheel of life, total goals; Member since; logins; 					
public class UserProfilePanel extends VerticalPanel {
	private RiaContext ctx;
	
	private String gravatarComLink;
	private static final String NO_URL="http://www.mindforger.com";

	private StatusLine statusLine;
	private MindForgerServiceAsync service;
	private RiaState state;
	private RiaMessages i18n;
	private Ria ria;
	
	private FlexTable topUserInfoPanel;
	
	private HTML viewDescription;
	private TextArea editDescription;
	private TextBox editWeb;
	private HTML viewWeb;
	
	private FlexTable sharedGrowsPanel;
	private FlowPanel friendPanel;
	private HTML friendPanelTitle;
	
	public UserProfilePanel(RiaContext ctx) {
		this.ctx=ctx;
	}
	
	public void init() {
		this.statusLine=ctx.getStatusLine();
		this.service=ctx.getService();
		this.state=ctx.getState();
		this.i18n=ctx.getI18n();
		this.ria=ctx.getRia();
		
		gravatarComLink="<a target='_blank' href='http://en.gravatar.com/' title='" +
				i18n.changeYourPhotoOnGravatar()+
				"'>";
		
		setStyleName("mf-userProfile");

		topUserInfoPanel = new FlexTable();
		topUserInfoPanel.setStyleName("mf-userProfileTop");
		add(topUserInfoPanel);
		
		sharedGrowsPanel = new FlexTable();
		sharedGrowsPanel.setStyleName("mf-userProfileGrows");
		add(sharedGrowsPanel);
		
		friendPanelTitle = new HTML();
		friendPanelTitle.setStyleName("mf-userProfileFriendsTitle");
		add(friendPanelTitle);				
		friendPanel = new FlowPanel();
		friendPanel.setStyleName("mf-userProfileFriends");
		add(friendPanel);		
	}

	public void refreshProfile(final String userId) {		
		topUserInfoPanel.removeAllRows();
		friendPanel.clear();
		sharedGrowsPanel.removeAllRows();
		
		setVisible(false);
		statusLine.showProgress(i18n.loadingUserProfile());
		service.getUserProfile(userId, new AsyncCallback<UserProfileBean>() {
			public void onSuccess(UserProfileBean result) {
				if(result!=null) {
					boolean isOwner=userId.equals(state.getCurrentUser().getUserId());
										
					if(isOwner) {
						List<DescriptorBean> ownerGrowBeans=new ArrayList<DescriptorBean>();
						GrowBean[] growBeans = state.getGrowBeans();
						if(growBeans!=null && growBeans.length>0) {
							for (int row = 0; row < growBeans.length; row++) {
								if(growBeans[row].getSharedTo()!=null && 
										!GrowBean.SHARING_OPTION_VALUE_NOBODY.equals(growBeans[row].getSharedTo())) {
									DescriptorBean b=new DescriptorBean();
									b.setType(MindForgerResourceType.GROW);
									b.setId(growBeans[row].getKey());
									b.setName(growBeans[row].getName());
									b.setDescription(growBeans[row].getDescription());
									b.setModified(growBeans[row].getModified());
									ownerGrowBeans.add(b);
								}
							}																						
						}
						result.setGrowDescriptors(ownerGrowBeans.toArray(new DescriptorBean[ownerGrowBeans.size()]));
					} 
					
					// left
					FlexTable left=new FlexTable();
					left.setStyleName("mf-userProfileRightLeft");
					topUserInfoPanel.setWidget(0, 0, left);					
					String htmlString=
						(isOwner?gravatarComLink:"") +
						"<img src='"+RiaUtilities.getGravatatarUrl(result.getUser())+"?s=75&d=identicon'>" +
						(isOwner?"</a>":"");
					HTML html=new HTML(htmlString);
					left.setWidget(0, 0, html);
					// friends / goals
					htmlString=
						"<span title='"+i18n.goals()+"'>"+
						(result.getGrowDescriptors()==null||result.getGrowDescriptors().length==0?"0":result.getGrowDescriptors().length)+"</span>" +
						" / " +
						"<span title='"+i18n.friends()+"'>"+
						(result.getFriends()==null||result.getFriends().length==0?"0":result.getFriends().length)+"</span>";
					html=new HTML(htmlString);
					html.setStyleName("mf-userProfileFriendsGrows");
					left.setWidget(1, 0, html);					


					// right
					
					
					FlexTable right=new FlexTable();
					right.setStyleName("mf-userProfileRightPanel");
					topUserInfoPanel.setWidget(0, 1, right);
					int rrow=0;
					// nickname
					Widget widget;
					if(isOwner) {
						widget=new NicknamePanel(
								result.getUser().getNickname(),
								"mf-userProfileNicknameEditable",
								true,
								ctx);
					} else {
						widget=new HTML(
						"<div class='mf-userProfileNickname'>"+
						 result.getUser().getNickname()+
						 "</div>");
					}
					right.setWidget(rrow++, 0, widget);
					// description
					VerticalPanel descriptionPanel=new VerticalPanel();
					descriptionPanel.setStyleName("mf-userProfileDescriptionPanel");
					viewDescription = new HTML();
					if(isOwner) {
						viewDescription.setTitle(i18n.clickToShareSomeInfoAboutYou());						
					} else {
						viewDescription.setTitle(i18n.moreAbout()+" "+result.getUser().getNickname()+"...");												
					}
					viewDescription.setStyleName("mf-userProfileDescriptionView");
					if(isOwner) {
						viewDescription.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								toEditDescriptionAndWebMode();
							}
						});						
					}
					descriptionPanel.add(viewDescription);
					editDescription = new TextArea();
					editDescription.setTitle(i18n.hitCtrlEnterToFinishEdit());
					editDescription.setStyleName("mf-userProfileDescriptionEdit");
					String userDescription=(result.getUser().getDescription()==null||"".equals(result.getUser().getDescription())?"...":result.getUser().getDescription());
					editDescription.setText(userDescription);
					editDescription.addKeyUpHandler(new KeyUpHandler() {
						public void onKeyUp(KeyUpEvent event) {
							if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER && event.isControlKeyDown()) {
								toViewDescriptionAndWebMode();
								// save
								saveDescriptionAndWeb();
							}			
						}
					});
					descriptionPanel.add(editDescription);
					right.setWidget(rrow++, 0, descriptionPanel);
					// web url
					String userWeb=(result.getUser().getWeb()==null||"".equals(result.getUser().getWeb())?NO_URL:result.getUser().getWeb());
					editWeb = new TextBox();
					editWeb.setVisible(false);
					editWeb.setTitle(i18n.hitEnterForViewMode());
				    editWeb.setStyleName("mf-userProfileWebEdit");
					editWeb.setText(userWeb);
					editWeb.addKeyUpHandler(new KeyUpHandler() {
						public void onKeyUp(KeyUpEvent event) {
							if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
								toViewDescriptionAndWebMode();
								// save
								saveDescriptionAndWeb();
							}
						}
					});
					right.setWidget(rrow++, 0, editWeb);
					viewWeb = new HTML();
					viewWeb.setVisible(true);
					viewWeb.setStyleName("mf-userProfileWebView");
					right.setWidget(rrow++, 0, viewWeb);
					
					toViewDescriptionAndWebMode();
										
					// grows
					DescriptorBean[] growDescriptors = result.getGrowDescriptors();
					if(growDescriptors!=null && growDescriptors.length>0) {
						sharedGrowsPanel.setVisible(true);
						html= new HTML(growDescriptors.length+" "+i18n.sharedGoal()+(growDescriptors.length>1?"s":""));
						html.setStyleName("mf-userProfileGrowsTitle");
						sharedGrowsPanel.setWidget(0,0, html);
						
						for (int row = 0; row < growDescriptors.length; row++) {
							// TODO normal grows table I already have elsewhere
							String growName = growDescriptors[row].getName();
							String growDescription = growDescriptors[row].getDescription();
							String growId = growDescriptors[row].getId();
							GrowsTableToGrowButton button=new GrowsTableToGrowButton(
									RiaUtilities.trimName(growName,DashboardPanel.LIMIT_NAME_LENGTH),
									growDescription,
									growId,
									"mf-growsTableGoalButton",
									GrowsTableToGrowButton.FIRST_TAB,
									ctx);
							sharedGrowsPanel.setWidget(row+1, 0, button);
							//rightSharedGrows.setWidget(row, 1, new HTML("20%"));
							//rightSharedGrows.setWidget(row, 2, new HTML("20.7.2005"));
							}							
					} else {
						sharedGrowsPanel.setVisible(false);
					}
					
					// friends
					UserBean[] friendNicknames = result.getFriends();
					if(friendNicknames!=null && friendNicknames.length>0) {
						friendPanelTitle.setText(friendNicknames.length+" "+i18n.connection()+(friendNicknames.length>1?"s":""));
						friendPanelTitle.setVisible(true);
						friendPanel.setVisible(true);
												
						for (int i = 0; i < friendNicknames.length; i++) {
							ConnectionPanel connectionPanel = new ConnectionPanel( result.getFriends()[i], ConnectionPanelMode.VIEW, ctx);
							friendPanel.add(connectionPanel);
						}
					} else {
						friendPanel.setVisible(false);						
						friendPanelTitle.setVisible(false);
					}
					
					statusLine.setVisible(false);
					UserProfilePanel.this.setVisible(true);
				}
			}
			public void onFailure(Throwable caught) {
				ria.handleServiceError(caught);
			}
		});
	}
	
	void saveDescriptionAndWeb() {
		statusLine.showProgress(i18n.savingYourDescription());
		service.setUserDescription(editDescription.getText(), new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				statusLine.hideStatus();
			}
			public void onFailure(Throwable caught) {
				ria.handleServiceError(caught);
			}
		});
		statusLine.showProgress(i18n.savingYourUrl());
		service.setUserWeb(editWeb.getText(), new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				statusLine.hideStatus();
			}
			public void onFailure(Throwable caught) {
				ria.handleServiceError(caught);
			}
		});
	}
	
	void toEditDescriptionAndWebMode() {
		viewDescription.setVisible(false);
		editDescription.setVisible(true);
		viewWeb.setVisible(false);
		editWeb.setVisible(true);
	}
	
	void toViewDescriptionAndWebMode() {
		setViewDescription(editDescription.getText());
		viewDescription.setVisible(true);
		editDescription.setVisible(false);
		setViewWeb(editWeb.getText());
		viewWeb.setVisible(true);
		editWeb.setVisible(false);
	}

	private void setViewDescription(String description) {
		if(description==null || "".equals(description)) {
			description="...";
		}
		viewDescription.setText(description);		
	}

	private void setViewWeb(String www) {
		String href;;
		href=www=RiaUtilities.escapeHtml(www);
		if(NO_URL.equals(www)) {
			href="";
		}
		viewWeb.setHTML("<a class='mf-userProfileWebViewLink' title='"+i18n.webUrl()+"' target='_blank' href='"+href+"'>"+www+"</a>");		
	}
}
