package com.mindforger.coachingnotebook.server;

import java.util.Date;

public class TestUtilities {

	void testToday() {       
		                                // 24h 60m 60s 1000ms 
		long oneDay=24*60*60*1000;
		
		Date date=new Date();
		//long time = date.getTime()-(1000*60*60*60*24*100000);
		long time=date.getTime()-oneDay;
		
		System.out.println(""+Utils.getPrettyTimestampHtml(time));
	}
	
	public static void main(String args[]) {
		TestUtilities t= new TestUtilities();		
		t.testToday();
	}
	
}
