package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;

public class LifeVisionTextArea extends VerticalPanel {

	private RiaContext ctx;
	
	private TextArea edit;
	private HTML view;

	private String defaultLifeVisionText;

	private RiaMessages i18n;
	
	public LifeVisionTextArea(final int myRow, final RiaContext ctx) {
		this.ctx=ctx;
		this.i18n=ctx.getI18n();
		
		defaultLifeVisionText=i18n.defaultLifeVisionText();
		
		setStyleName("mf-lifeVision");

		// watermark label
		HTML html=new HTML(i18n.lifeVision());
		html.setStyleName("mf-lifeVisionWatermark");
		add(html);
		
		edit = new TextArea();
		edit.setTitle(ctx.getI18n().hitCtrlEnterToFinishEdit());
		edit.setStyleName("mf-lifeVisionEdit");
		edit.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER && event.isControlKeyDown()) {
					save();
					toViewMode();
					ctx.getLifeDesignerPanel().showAllRows();				
				}			
			}
		});
		edit.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				ctx.getLifeDesignerPanel().showAllRows();				
			}
		});
		add(edit);
		
		view = new HTML(ctx.getI18n().defaultProgress());
		view.setText(edit.getText());
		view.setTitle(ctx.getI18n().clickToChange());
		view.setStyleName("mf-lifeVisionView");
		view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ctx.getLifeDesignerPanel().hideAllRows(new Integer[]{myRow-1, myRow});
				toEditMode();
			}
		});
		add(view);
		
		toViewMode();
	}
	
	public void save() {
		ctx.getStatusLine().showProgress(i18n.savingLifeVision());
		ctx.getService().setLifeVision(edit.getText(), new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				ctx.getStatusLine().hideStatus();
			}
			public void onFailure(Throwable caught) {
				ctx.getRia().handleServiceError(caught);
			}
		});
	}
	
	public void load() {
		ctx.getStatusLine().showProgress(i18n.loadingLifeVision());
		ctx.getService().getLifeVision(new AsyncCallback<String>() {
			public void onSuccess(String lifeVision) {
				ctx.getStatusLine().hideStatus();
				edit.setText(lifeVision);
				view.setText(lifeVision);
				if(lifeVision==null || lifeVision.isEmpty()) {
					toEditMode();
				} else {
					toViewMode();
				}				
			}
			public void onFailure(Throwable caught) {
				ctx.getRia().handleServiceError(caught);
			}
		});
	}
	
	public String getText() {
		return edit.getText();
	}
	
	public void toEditMode() {
		if(edit.getText()==null || edit.getText().isEmpty()) {
			edit.setText(defaultLifeVisionText);
		}
		view.setVisible(false);		
		edit.setVisible(true);
		edit.setFocus(true);
	}
	
	public void toViewMode() {
		if(edit.getText()==null || edit.getText().isEmpty()) {
			view.setText(defaultLifeVisionText);
		} else {
			view.setText(edit.getText());			
		}
		view.setVisible(true);
		edit.setVisible(false);
	}
}
