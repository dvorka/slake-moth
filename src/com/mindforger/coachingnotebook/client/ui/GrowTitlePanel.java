package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

/**
 * Click to edit; enter to view.
 */
public class GrowTitlePanel extends VerticalPanel {
	
	private TextBox editTitle;
	private HTML viewTitle;

	HorizontalPanel edit;
	HorizontalPanel view;

	HTML viewDescription;
	TextArea editDescription;
	
	private RiaContext ria;
	
	public GrowTitlePanel(final RiaContext ria) {
		setStyleName("mf-goalTitlePanel");
		
		this.ria=ria;
		
		edit = new HorizontalPanel();
		edit.setStyleName("mf-goalTitleEditPanel");
		
		editTitle = new TextBox();
		editTitle.setTitle(ria.getI18n().hitEnterForViewMode());
	    editTitle.setStyleName("mf-goalTitleEditField");
		editTitle.setText(ria.getI18n().defaultGoalName());
		editTitle.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
					viewTitle.setText(editTitle.getText());
					toViewTitleMode();
					ria.getGrowPanel().save(true);						
				}
			}
		});
		edit.add(editTitle);
		add(edit);
		
		view = new HorizontalPanel();
		view.setStyleName("mf-goalTitlePanel");
		viewTitle = new HTML(ria.getI18n().defaultGoalName());
		viewTitle.setStyleName("mf-goalTitleViewField");
		viewTitle.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//viewTitle.setText(editTitle.getText());
				toEditTitleMode();
			}
		});
		view.add(viewTitle);
		add(view);
				
		viewDescription = new HTML(ria.getI18n().defaultGoalDescription());
		viewDescription.setStyleName("mf-goalDescriptionView");
		viewDescription.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//viewDescription.setText(editDescription.getText());
				toEditDescriptionMode();
			}
		});
		add(viewDescription);
		editDescription = new TextArea();
		editDescription.setTitle(ria.getI18n().hitCtrlEnterToFinishEdit());
		editDescription.setStyleName("mf-goalDescriptionEdit");
		editDescription.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER && event.isControlKeyDown()) {
					String text = editDescription.getText();
					if(text==null || "".equals(text)) {
						text="...";
					}
					viewDescription.setText(text);
					toViewDescriptionMode();
					ria.getGrowPanel().save(true);						
				}			
			}
		});
		add(editDescription);
		
		toViewTitleMode();
		toViewDescriptionMode();		
	}

	public void fromRia(GrowBean growBean) {
		growBean.setName(editTitle.getText());
		growBean.setDescription(editDescription.getText());
	}
		
	public void toRia(String title, String description) {
		toViewTitleMode();
		toViewDescriptionMode();
		
		editTitle.setText(title);
		viewTitle.setText(title);
		if(description==null || "".equals(description)) {
			description=ria.getI18n().defaultGoalDescription();
		}
		editDescription.setText(description);
		viewDescription.setText(description);
	}
	
	public void toEditTitleMode() {
		if(ria.getGrowPanel().isRdWr()) {
			view.setVisible(false);
			edit.setVisible(true);
		}
	}
	public void toViewTitleMode() {
		viewTitle.setText(editTitle.getText());
		view.setVisible(true);
		edit.setVisible(false);
	}
	
	public void toEditDescriptionMode() {
		if(ria.getGrowPanel().isRdWr()) {
			viewDescription.setVisible(false);
			editDescription.setVisible(true);
		}
	}
	
	public void toViewDescriptionMode() {
		viewDescription.setText(editDescription.getText());
		viewDescription.setVisible(true);
		editDescription.setVisible(false);
	}	
}
