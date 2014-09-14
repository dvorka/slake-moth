package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;

/**
 * Click to edit; enter to view.
 */
public class GrowProgressPanel extends VerticalPanel {
	
	private TextBox editProgress;
	private HTML viewProgress;

	HorizontalPanel edit;
	HorizontalPanel view;
	
	private RiaMessages i18n;

	public GrowProgressPanel(final RiaContext ctx) {
		setStyleName("mf-progressPanel");
		
		this.i18n=ctx.getI18n();
		
		edit = new HorizontalPanel();
		edit.setStyleName("mf-progressEditPanel");
		
		editProgress = new TextBox();
	    editProgress.setStyleName("mf-progressEditField");
		editProgress.setTitle(ctx.getI18n().hitEnterForViewMode());
		editProgress.setText(ctx.getI18n().defaultProgress());
		editProgress.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER) {
					String text=editProgress.getText();
					int parseInt=0;
					if(text!=null) {
						try {
							parseInt = Integer.parseInt(text);
							if(parseInt < 0  || parseInt > 100) {
								ctx.getStatusLine().showError(ctx.getI18n().errorProgressBetweenZeroAndHundred());
								return;
							}
						} catch(Exception e) {
							ctx.getStatusLine().showError(ctx.getI18n().errorProgressBetweenZeroAndHundred());
							return;
						}
					}
					ctx.getStatusLine().hideStatus();
					toRia(""+parseInt);
					toViewMode();
					ctx.getGrowPanel().save(true);
					
					if(parseInt==100) {
						ctx.getLeftMenubar().showMyLife();
						Timer timer = new Timer() {
							public void run() {
								ctx.getStatusLine().showHelp(i18n.congratulationsToFinishingGoal()+"<br/>"+i18n.considerUpdatingWoL());
							}
						};
						timer.schedule(2000);
					}
				}
			}
		});
		edit.add(editProgress);
		HTML progressPercentHtml = new HTML("%");
		progressPercentHtml.setStyleName("mf-progressPercentHtml");
		progressPercentHtml.setTitle(ctx.getI18n().goalProgress());
		edit.add(progressPercentHtml);
		add(edit);
		
		view = new HorizontalPanel();
		view.setStyleName("mf-progressViewPanel");
		viewProgress = new HTML(ctx.getI18n().defaultProgress());
		viewProgress.setStyleName("mf-progressViewField");
		viewProgress.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(ctx.getGrowPanel().isRdWr()) {
					toEditMode();
				}
			}
		});
		view.add(viewProgress);
		add(view);
		
		toViewMode();
	}

	public void onNewGrow() {
		viewProgress.setText("0%");
		editProgress.setText("0");
	}
	
	public String fromRia() {
		return editProgress.getText();
	}
	
	public void toRia(String progress) {
		viewProgress.setText(progress+"%");
		editProgress.setText(progress);
	}
	
	public void toEditMode() {
		view.setVisible(false);
		edit.setVisible(true);		
	}
	
	public void toViewMode() {
		viewProgress.setText(editProgress.getText()+"%");
		view.setVisible(true);
		edit.setVisible(false);
	}

	public int getProgressValue() {
		try {
			return Integer.parseInt(editProgress.getText());			
		} catch(Exception e) {
			return 0;
		}
	}	
}
