package com.mindforger.coachingnotebook.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Utils {
	
    private static final int CREATED_TODAY = 1;
    private static final int CREATED_6_DAYS = 2;
    private static final int CREATED_THIS_YEAR = 4;
    private static final int CREATED = 8;
                                       // sec+min+hour+day+6days
    private static final long SIX_DAYS = 1000 * 60 * 60 * 24 * 6;

    public static boolean isSameDay(Date d1, Date d2) {
    	Calendar c1=Calendar.getInstance();
    	c1.setTime(d1);
    	Calendar c2=Calendar.getInstance();
    	c2.setTime(d2);
    	return isSameDay(c1,c2);
    }
    
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
    	return 
    	   cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	       cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
	       cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Get string to be rendered for creation time (today, 6 days, this year,
     * another year).
     *
     * @param created
     * @return
     */
    public static String getPrettyTimestampHtml(long created) {
        int type = CREATED;

        Date date = new Date(created);
        Calendar dateCalendar=Calendar.getInstance();
        dateCalendar.setTime(date);
        
        Date today = new Date(System.currentTimeMillis());
        Calendar todayCalendar=Calendar.getInstance();
        todayCalendar.setTime(today);
        
        SimpleDateFormat simpleDateFormat;

        if (isSameDay(date, today)) {
            type = CREATED_TODAY;
        } else {
            if (System.currentTimeMillis() - created < SIX_DAYS) {
                type = CREATED_6_DAYS;
            } else {
                //if (date.getYear() == today.getYear()) {
                if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
                    type = CREATED_THIS_YEAR;
                }
            }
        }

        String backgroundColor = null, text = null;
        switch (type) {
        case CREATED:
            backgroundColor = "bbbbbb";
            simpleDateFormat = new SimpleDateFormat("yyyy");
            text = simpleDateFormat.format(date);
            break;
        case CREATED_6_DAYS:
            backgroundColor = "555555";
            simpleDateFormat = new SimpleDateFormat("EEEEEEEEE", new Locale("en", "US"));
            text = simpleDateFormat.format(date);
            break;
        case CREATED_THIS_YEAR:
            backgroundColor = "888888";
            simpleDateFormat = new SimpleDateFormat("MMMMMMMMM dd", new Locale("en", "US"));
            text = simpleDateFormat.format(date);
            break;
        case CREATED_TODAY:
            backgroundColor = "000000";
            simpleDateFormat = new SimpleDateFormat("HH:mm");
            text = simpleDateFormat.format(date);
            break;
        }

        // note that long timestamp is added to the HTML - it is convenient for parsing/sorting/processing
        return "<div style='background-color: #" + backgroundColor + "; color: #ffffff; text-align: center; white-space: pre;'>" +
        		"&nbsp;&nbsp;" + text + "&nbsp;&nbsp;" +
                "</div>";
    }	
    
	public static String getCssStyleNameForActionDeadline(Date deadlineDate) {
		if(deadlineDate==null) {
			return "mf-actionDeadline";
		}
		
		Calendar deadline=Calendar.getInstance();
		deadline.setTime(deadlineDate);

		Calendar today=Calendar.getInstance();
		today.setTime(new Date());
		
		// missed
		if(today.after(deadline)) {
			return "mf-actionDeadlineMissed";
		}
		
		// today
		if((deadline.get(Calendar.DATE) == today.get(Calendar.DATE)) &&
	       (deadline.get(Calendar.YEAR) == today.get(Calendar.YEAR)) &&
	       (deadline.get(Calendar.MONTH) == today.get(Calendar.MONTH))) {
			return "mf-actionDeadlineToday";
	    }				

		Calendar calendar = new GregorianCalendar();
		
		// tomorrow
		calendar.setTime(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		if(deadline.before(calendar)) {
			return "mf-actionDeadlineTomorrow";			
		}

		// week from now
		calendar.setTime(calendar.getTime());
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		if(deadline.before(calendar)) {
			return "mf-actionDeadlineWeek";			
		}
		
		// later
		return "mf-actionDeadlineLater";
	}
    
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	public static String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;".replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
	}

	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));        
		}
		return sb.toString();
	}

	public static String md5Hex (String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex (md.digest(message.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	public static String keyToString(Key key) {
    	return (key==null?null:KeyFactory.keyToString(key));
	}

	public static Key stringToKey(String key) {
    	return (key==null?null:KeyFactory.stringToKey(key));
	}

	public static String checkAndEncodeQueryString(String riaQuery) {
		if(riaQuery==null || "".equals(riaQuery)) {
			return "";
		} else {
			if(riaQuery.length()>50) {
				riaQuery=riaQuery.substring(0,50);
			}
			String result="";
			for (int i = 0; i < riaQuery.length(); i++) {
				char c = riaQuery.charAt(i);
				if(Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
					result=result+c;
				}
			}
			return result;
		}
	}
	
	public static boolean isContainWhitespace(String s) {
        if (s == null) {
        	return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (Character.isSpaceChar(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }	
	
	public static Date getDateOnly() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
	    try {
			return sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			throw new MindForgerException("Unable to get parse date!",e);
		}			
	}
}
