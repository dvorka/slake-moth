package com.mindforger.coachingnotebook.client.ui.perspective;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;

public class PerspectiveSelectionPanel extends FlexTable {
	
	private RiaContext ria;
	private RiaMessages i18n;

	public PerspectiveSelectionPanel(final RiaContext ria) {
		setStyleName("mf-perspectiveSelectionPanel");
		this.ria=ria;
		i18n=ria.getI18n();
		refresh();
	}
	
	// TODO find a name to use for this method across UI
	public void refresh() {
		HTML html;
		SetPerspectiveButton button;
				
		for (int i = 0; i < Perspective.LIST.length; i++) {
			PerspectiveDescriptor perspective = Perspective.LIST[i];

			button = new SetPerspectiveButton(perspective.getLabel(), perspective.getId(), ria);
			setWidget(i, 0, button);

			html = new HTML(perspective.getDescription());
			html.setTitle(i18n.setPerspectiveTo()+" "+perspective.getLabel());
			html.addClickHandler(new SetPerspectiveClickHandler(perspective.getId(), ria));
			html.setStyleName("mf-perspectiveSelectionDescription");
			setWidget(i, 1, html);
		}
		
	}
}
