package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.mindforger.coachingnotebook.client.MindForgerServiceAsync;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;

// TODO this will be combination of suggest from titles (I will search them myself as user types)
//      and FTS, that will be run after enter is hit
public class SearchPanel extends HorizontalPanel {

	private Button searchButton;
	private TextBox searchField;
	
	private RiaContext ctx;
	private RiaMessages i18n;
	private MindForgerServiceAsync service;
    
	public SearchPanel(RiaContext ctx) {
		this.ctx=ctx;
		this.i18n = ctx.getI18n();
		this.service = ctx.getService();
		
		setStyleName("mf-searchPanel");
		
		// TODO search button before edit line

		searchButton = new Button(i18n.search());
		searchButton.setStyleName("mf-button mf-searchButton");
		add(searchButton);

		searchField = new TextBox();
		searchField.setStyleName("mf-searchTextBox");
		searchField.getElement().setId("mf-searchTextBox");
		add(searchField);
		searchField.setFocus(true);
		searchField.selectAll();

		class MyHandler implements ClickHandler, KeyUpHandler {
			public void onClick(ClickEvent event) {
				search();
			}
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					search();
				}
			}
		};

		MyHandler handler = new MyHandler();
		searchButton.addClickHandler(handler);
		searchField.addKeyUpHandler(handler);			
	}
	
	private void search() {
		service.fts(searchField.getText(), new AsyncCallback<DescriptorBean[]>() {
			@Override
			public void onSuccess(DescriptorBean[] result) {
				SearchResultsPanel searchResultsPanel = ctx.getSearchResultsPanel();
				searchResultsPanel.refresh(result);
				ctx.getRia().showSearchResults();
			}
			@Override
			public void onFailure(Throwable caught) {
				ctx.getRia().handleServiceError(caught);
			}
		});
	}
}