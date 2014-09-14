package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;

public class GrowWolLabelPanel extends VerticalPanel {
	
	private ListBox edit;
	private HTML view;

	public GrowWolLabelPanel(final RiaContext ria) {
		setStyleName("mf-growWolLabel");

		edit = new ListBox();
		setTitle(ria.getI18n().changeValueOrLetItLooseFocus());
		edit.setStyleName("mf-growWolLabelEdit");
		for (int i = 0; i < LifeDesignerPanel.WHEEL_OF_LIFE_LABELS.length; i++) {
			edit.addItem(
					LifeDesignerPanel.WHEEL_OF_LIFE_LABELS[i], 
					LifeDesignerPanel.WHEEL_OF_LIFE_VALUES[i]);			
		}
		edit.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				handleBlurAndChange(ria);
			}
		});
		edit.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				handleBlurAndChange(ria);
			}
		});
		add(edit);
		
		view = new HTML(ria.getI18n().defaultProgress());
		view.setText(edit.getItemText(edit.getSelectedIndex()));
		view.setTitle(ria.getI18n().clickToChange());
		view.setStyleName("mf-growWolLabelView");
		view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				toEditMode();
			}
		});
		add(view);
		
		toViewMode();
	}
	
	public String fromRia() {
		return edit.getItemText(edit.getSelectedIndex());
	}
	
	public void toRia(String value) {
		// TODO editProgress.setItemText(progress);
		view.setText(edit.getItemText(edit.getSelectedIndex()));
	}
	
	public void toEditMode() {
		view.setVisible(false);
		edit.setVisible(true);		
	}
	
	public void toViewMode() {
		view.setVisible(true);
		edit.setVisible(false);
	}

	private void handleBlurAndChange(final RiaContext ria) {
		view.setText(edit.getItemText(edit.getSelectedIndex()));
		ria.getGrowPanel().save(false);
		toViewMode();
	}
}
