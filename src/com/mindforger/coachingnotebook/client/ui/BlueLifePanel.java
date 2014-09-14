package com.mindforger.coachingnotebook.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;


// TODO i18n
public class BlueLifePanel extends FlexTable {
	
	String[] BLUE_LIFE_LAWS=new String[] {
			"Physical exercise",
			"Clean teeth",
			"Cold hardening",
			"Proper language",
			"Honest behavior",
			"Good deed",
			"Cheerful enjoyment"			
	};
	
	@Deprecated
	public BlueLifePanel() {
	}

	public void init() {
		setStyleName("mf-blueLifePanel");

		int row=0;
		int column=0;

		// introduction
		String htmlString=
			"<span class='mf-hint'>Blue Life</span> is a concept that was introduced by " +
			"<a class='mf-docLink' target='_blank' title='Jaroslav Foglar was a famous Czech " +
			"author who wrote many novels about youths (partly also about Boy Scouts movement) " +
			"and their adventures in nature and dark city streets' " +
			"href='http://en.wikipedia.org/wiki/Jaroslav_Foglar'>Jaroslav Foglar</a> in " +
			"his books \"The port is calling\" and \"Chronicle of the lost footstep\". " +
			"It is the set of 7 obligations that are meant to be performed daily. " +
			"You will be rewarded by self-development and competent life. MindForger comes " +
			"with the original set of obligations just to give you an example. Feel free to " +
			"put together your own set and make your own blue life. Everyday fill what you " +
			"succeeded to do and make your life blue :-)";
		HTML html = new HTML(htmlString);
		html.setStyleName("mf-blueLifeDocIntro");
		setWidget(row,0,html);
		getFlexCellFormatter().setColSpan(row, 0, 35);
		row++;

		// TODO January 2011 > to be loaded from the server
		html = new HTML("My Blue Life in <b>January 2011</b>");
		html.setStyleName("mf-blueLifeInTheMonth");
		setWidget(row,0,html);
		getFlexCellFormatter().setColSpan(row, 0, 35);
		row++;

		// days
		// TODO determine days in month
		int monthDays=31;
		column=1;
		for (int j = 1; j <= monthDays; j++) {
			String padding="";
			if(j<10) {
				padding="&nbsp;";
			}
			html = new HTML(padding+j);
			html.setStyleName("mf-blueDayOfMonth");
			setWidget(row,column++,html);
		}
		row++;

		// blue life matrix
		// TODO load custom laws from the server for this particular user
		for (int i = 0; i < BLUE_LIFE_LAWS.length; i++) {
			column=0;
			html=new HTML(BLUE_LIFE_LAWS[i]);
			html.setStyleName("mf-blueLifeLaw");
			setWidget(row, column++, html);
			for (int j = 1; j <= monthDays; j++) {
				html=new HTML("&nbsp;&nbsp;");
				html.setStyleName("mf-blueLifeCell");
				setWidget(row,column++,html);
			}
			row++;
		}

		// TODO statics - % of blue

		// outro
		htmlString=
			"<span class='mf-hint'>!</span> Blue Life is not implemented by MindForger yet. " +
			"What you can see in here is just a mockup available for your review.";
		html = new HTML(htmlString);
		html.setStyleName("mf-blueLifeDocOutro");
		setWidget(row,0,html);
		getFlexCellFormatter().setColSpan(row, 0, 35);
		row++;
	}
}
