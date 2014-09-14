package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.ui.perspective.PerspectiveSelectionPanel;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;

public class CheatSheetPanel extends VerticalPanel implements MindForgerConstants {
	
	private HTML welcomeTitle;
	private PerspectiveSelectionPanel perspectiveSelectionPanel;
	private RiaContext ctx;
	private RiaMessages i18n;

	public CheatSheetPanel(RiaContext ctx) {
		this.ctx=ctx;
		this.i18n=ctx.getI18n();
	}
	
	// TODO i18n
	@Deprecated
	public void init() {
		setStyleName("mf-cheatSheet");

		HTML html;
		
		welcomeTitle= new HTML(i18n.welcomeToMindForger());
		welcomeTitle.setStyleName("mf-welcomeTitle");
		add(welcomeTitle);			

		// TODO 5 recent goals
		html = new HTML("Recent Goals"); // TODO i18n
		html.setStyleName("mf-cheatSheetSettings");
		//add(html);

		// TODO 5 actions and 5 connections > later (computationally expensive)
		html = new HTML("Recent Actions | Recent Connections"); // TODO i18n
		html.setStyleName("mf-cheatSheetSettings");
		//add(html);
		
		perspectiveSelectionPanel = new PerspectiveSelectionPanel(ctx);
		html = new HTML("<span class='mf-hint'>1. Perspective</span> Choose the perspective that suits your needs. You may change it anytime later."); // TODO
		html.setTitle("Click to select the perspective");
		html.setStyleName("mf-perspectiveSelectionTitle");
		html.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(perspectiveSelectionPanel.isVisible()) {
					perspectiveSelectionPanel.setVisible(false);					
				} else {
					perspectiveSelectionPanel.setVisible(true);
				}
			}
		});
		add(html);
		
		perspectiveSelectionPanel.setVisible(false);
		add(perspectiveSelectionPanel);
		
		html = new HTML("<span class='mf-hint' title='Meet your friends, coach and clients'>2. Social Network</span> Connect with your coach, friends, mentors and clients.");
		html.setTitle("Click to find new connections");
		html.setStyleName("mf-cheatSheetSocialNetworking");
		html.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getLeftMenubar().showNewConnection();
			}
		});
		add(html);
				
		html = new HTML("<span class='mf-hint'>3. Documentation</span> Check <a class='mf-docLink' href='http://www.mindforger.com/documentation/' target='blank'>MindForger documentation</a>.");
		html.setTitle("Study documentation");
		html.setStyleName("mf-cheatSheetReadHelp");
		add(html);

		html = new HTML("<span class='mf-hint'>4. Get Started</span> Click the diagram below to start...");
		html.setStyleName("mf-cheatSheetReadHelp");
		add(html);
		
		String title="Click to start";
		html=new HTML("<img src='./images/goal-lifecycle.png' title='"+title+"'/>");
		html.setStyleName("mf-welcomeImageContainer");
		html.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getState().inPerspective(new String[]{PERSPECTIVE_PROBLEM_SOLVER})) {
					ctx.getLeftMenubar().createNewGrow(null);
				} else {
					if(ctx.getState().inPerspective(new String[]{PERSPECTIVE_COACH,
							PERSPECTIVE_HR})) {
						ctx.getLeftMenubar().showConnections();
					} else {
						ctx.getLeftMenubar().showMyLife();						
					}					
				}
			}
		});
		add(html);
		
		// TODO Settings
		html = new HTML("<span class='mf-hint'>5. Customization </span> Set up MindForger...");
		html.setStyleName("mf-cheatSheetSettings");
		// TODO settings panel: emailing preferences, L&F theme
		//add(html);
				
		
		html = new HTML("<span class='mf-hint mf-hintGreen'>... follow green color</span>");
		html.setStyleName("mf-cheatSheetGreenColor");
		add(html);		
	}

	public void setPerspectiveSelectionPanelVisible(boolean show) {
		perspectiveSelectionPanel.setVisible(show);
	}
	
	public void showAsWelcomePage(boolean show) {
		if(show) {
			welcomeTitle.setVisible(show);			
		} else {
			welcomeTitle.setVisible(false);
		}
	}
	
	public void refresh() {
		perspectiveSelectionPanel.refresh();
	}
}
