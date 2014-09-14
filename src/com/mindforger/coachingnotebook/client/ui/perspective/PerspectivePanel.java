package com.mindforger.coachingnotebook.client.ui.perspective;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;

public class PerspectivePanel extends VerticalPanel {
		
	private HTML view;
	private RiaContext ria;
	private RiaMessages i18n;

	public PerspectivePanel(String perspectiveId, final RiaContext ctx) {
		setStyleName("mf-perspectivePanel");
		i18n=ctx.getI18n();
		
		this.ria=ctx;
		
		view = new HTML("<span class='mf-perspectiveRightCorner'>"+Perspective.MAP.get(perspectiveId).getLabel()+"</span>&nbsp;|");
		view.setTitle(i18n.clickToChangePerspective());
		view.setStyleName("mf-perspectivePanelView");
		view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getCheatSheet().setPerspectiveSelectionPanelVisible(true);
				ctx.getCheatSheet().showAsWelcomePage(false);
				ctx.getLeftMenubar().showCheatSheet();
			}
		});
		add(view);
	}

	public void setPerspectiveName() {
		view.setHTML(Perspective.MAP.get(ria.getState().getUserSettings().getPerspective()).getLabel());
	}
}
