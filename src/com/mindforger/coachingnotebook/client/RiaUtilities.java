package com.mindforger.coachingnotebook.client;

import java.util.Date;

import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.MindForgerSettings;
import com.mindforger.coachingnotebook.shared.beans.UserBean;

public class RiaUtilities {

	public static String getGravatatarUrl(UserBean user) {
		String gravatarBaseUrl;
		if(MindForgerSettings.IMAGE_PROXY_ENABLED) {
			gravatarBaseUrl=MindForgerConstants.GRAVATAR_PROXY_BASE_URL;			
		} else {
			gravatarBaseUrl=MindForgerConstants.GRAVATAR_BASE_URL;
		}

		if(user==null) {
			Date date=new Date();
			return gravatarBaseUrl+date.hashCode();				
		} else {
			if(user.getGravatarMd5()==null) {
				return gravatarBaseUrl+user.hashCode();
			} else {
				return gravatarBaseUrl+user.getGravatarMd5();
			}
		}
	}
	
	public static String trimName(String name, int limit) {
		if(name!=null && name.length()>limit) {
			return name.substring(0,limit)+"...";
		} else {
			return name;
		}
	}
	
	public static String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;".replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
	}
	
	public static String getPrivacySafeNickname(String nickname) {
		int indexOf = nickname.indexOf("@");
		if(indexOf>-1) {
			nickname=nickname.substring(0,indexOf+1)+"...";
		}
		return nickname;
	}	
}
