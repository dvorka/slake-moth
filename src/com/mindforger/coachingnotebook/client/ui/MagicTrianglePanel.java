package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mindforger.coachingnotebook.client.RiaContext;
import com.mindforger.coachingnotebook.client.RiaMessages;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

public class MagicTrianglePanel extends VerticalPanel {
		
	Button fastCheapButton;
	Button fastGoodButton;
	Button goodCheapButton;

	boolean editMode;
	
	Button result;
	
	String documentationBackup;
	String documentation;
	HTML goalTabDocumentation;
	private RiaMessages i18n;
	
	public MagicTrianglePanel(final HTML goalTabDocumentation, final RiaContext ctx) {
		setStyleName("mf-magicTriangle");
		
		this.i18n=ctx.getI18n();
		
		this.goalTabDocumentation=goalTabDocumentation;
		this.documentationBackup=goalTabDocumentation.getHTML();
		this.documentation=i18n.magicTriangleDocumentation();
		
		result=goodCheapButton=new Button("");
		goodCheapButton.setTitle(i18n.goodAndCheapStrategy());
		goodCheapButton.setStyleName("mf-magicTriangleGC");
		fastCheapButton=new Button("");
		fastCheapButton.setTitle(i18n.fastAndCheapStrategy());
		fastCheapButton.setStyleName("mf-magicTriangleFC");
		fastGoodButton=new Button("");
		fastGoodButton.setTitle(i18n.goodAndFastStrategy());
		fastGoodButton.setStyleName("mf-magicTriangleFG");
		
		add(fastGoodButton);
		add(goodCheapButton);
		add(fastCheapButton);
		
		editMode=false;
		fastCheapButton.setVisible(false);
		fastGoodButton.setVisible(false);
		
		fastCheapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!ctx.getGrowPanel().isRdWr()) {
					return;
				}
				boolean othersVisibility;
				if(editMode) {
					// I'm selected
					editMode=false;
					othersVisibility=false;
					result=fastCheapButton;
					goalTabDocumentation.setHTML(documentationBackup);
				} else {
					editMode=true;
					othersVisibility=true;
					goalTabDocumentation.setHTML(documentation);
				}
				fastGoodButton.setVisible(othersVisibility);
				goodCheapButton.setVisible(othersVisibility);
				if(!editMode) {
					ctx.getGrowPanel().save(true);					
				}
			}
		});
		fastGoodButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!ctx.getGrowPanel().isRdWr()) {
					return;
				}
				boolean othersVisibility;
				if(editMode) {
					// I'm selected
					editMode=false;
					othersVisibility=false;
					result=fastGoodButton;
					goalTabDocumentation.setHTML(documentationBackup);
				} else {
					editMode=true;
					othersVisibility=true;
					goalTabDocumentation.setHTML(documentation);
				}
				fastCheapButton.setVisible(othersVisibility);
				goodCheapButton.setVisible(othersVisibility);
				if(!editMode) {
					ctx.getGrowPanel().save(true);					
				}
			}
		});
		goodCheapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!ctx.getGrowPanel().isRdWr()) {
					return;
				}
				boolean othersVisibility;
				if(editMode) {
					// I'm selected
					editMode=false;
					othersVisibility=false;
					result=goodCheapButton;
					goalTabDocumentation.setHTML(documentationBackup);
				} else {
					editMode=true;
					othersVisibility=true;
					goalTabDocumentation.setHTML(documentation);
				}
				fastGoodButton.setVisible(othersVisibility);
				fastCheapButton.setVisible(othersVisibility);
				if(!editMode) {
					ctx.getGrowPanel().save(true);					
				}
			}
		});
	}	
	
	public String getStrategy() {
		if(fastCheapButton.isVisible()) {
			return GrowBean.STRATEGY_FAST_CHEAP;
		} else {
			if(fastGoodButton.isVisible()) {
				return GrowBean.STRATEGY_FAST_GOOD;
			} else {
				return GrowBean.STRATEGY_GOOD_CHEAP;
			}
		}
	}
	
	public void setStrategy(String strategy) {
		if(GrowBean.STRATEGY_FAST_CHEAP.equals(strategy)) {
			fastCheapButton.setVisible(true);
			fastGoodButton.setVisible(false);
			goodCheapButton.setVisible(false);
		} else {
			if(GrowBean.STRATEGY_FAST_GOOD.equals(strategy)) {
				goodCheapButton.setVisible(false);				
				fastCheapButton.setVisible(false);
				fastGoodButton.setVisible(true);
			} else {
				goodCheapButton.setVisible(true);								
				fastCheapButton.setVisible(false);
				fastGoodButton.setVisible(false);
			}
		}
	}
}
