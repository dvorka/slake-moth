package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.client.RiaUtilities;
import com.mindforger.coachingnotebook.client.ui.perspective.PerspectivePanel;

public class RightCornerPanel extends FlexTable {
	
	native String getAppEngineLogoutUrl() /*-{
	  return $wnd.getAppEngineLogoutUrl(); // $wnd is a JSNI synonym for 'window'
	}-*/;
	
	private RiaContext ctx;
	
	private NicknamePanel nicknamePanel;
	private PerspectivePanel perspectivePanel;
	private RiaMessages i18n;
		
	public RightCornerPanel(RiaContext ctx) {
		this.ctx=ctx;
	}
	
	public void init() {
		i18n = ctx.getI18n();
		
		setStyleName("mf-rightCornerPanel");
		
		HTML html;
		int column=0;
		
		// gravatar
		String htmlString=
			"<a target='_blank' href='http://en.gravatar.com/' title='" +
			i18n.changeYourPhotoOnGravatar()+
			"'>"+
			"<img border='0' src='"+
			RiaUtilities.getGravatatarUrl(ctx.getState().getCurrentUser())+"?s=20&d=identicon'>"+
			"</a>";
		html = new HTML();
		html.setStyleName("mf-rcGravatar");
		html.setHTML(htmlString);
		setWidget(0, column++, html);

		// nickname
		nicknamePanel = new NicknamePanel(ctx.getState().getCurrentUser().getNickname(), "mf-nicknameView", false, ctx);
		setWidget(0, column++, nicknamePanel);
		
		// as
		html = new HTML();
		html.setStyleName("mf-rcAs");
		html.setHTML("&nbsp;"+i18n.as()+"&nbsp;");
		setWidget(0, column++, html);
		
		// perspective
		perspectivePanel = new PerspectivePanel(ctx.getState().getUserSettings().getPerspective(), ctx);
		setWidget(0, column++, perspectivePanel);

		// settings
		html = new HTML();
		html.setStyleName("mf-settingsRightCorner");
		html.setTitle("Open Settings and Cheat Sheet");
		html.setHTML("<img class='mf-settingsRightCornerImg' src='./images/settings-black-padding.png'>");
		html.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getCheatSheet().setPerspectiveSelectionPanelVisible(true);
				ctx.getCheatSheet().showAsWelcomePage(false);
				ctx.getLeftMenubar().showCheatSheet();
			}
		});
		setWidget(0, column++, html);

		// as
		html = new HTML();
		html.setStyleName("mf-rcSignOut");
		html.setHTML(
				"|&nbsp;"+
				"<a href='http://www.mindforger.com/documentation/' target='_blank'>"+i18n.help()+"</a>&nbsp;|&nbsp;"+
				"<a href='"+getAppEngineLogoutUrl()+"'>"+i18n.sign()+"&nbsp;"+i18n.out()+"</a>"
				);
		setWidget(0, column++, html);
	}

	public void setNickname(String nickname) {
		nicknamePanel.refreshNickname(nickname);
	}

	public void setPerspectiveName() {
		perspectivePanel.setPerspectiveName();
	}
}
