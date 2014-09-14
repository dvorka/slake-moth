package com.mindforger.coachingnotebook.server.email;

import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.utils.SystemProperty;
import com.mindforger.coachingnotebook.server.admin.AdminReport;
import com.mindforger.coachingnotebook.server.i10n.Messages;

public class Emailing {
	private static final Logger log=Logger.getLogger(Emailing.class.getName());
	
	public static EmailBean emailActionWhoseDeadlineIsApproaching(String toEmail, String toNickname, String questionName) {
		EmailBean email=new EmailBean();
		email.toEmail=toEmail;
		email.toNickname=toNickname;
		email.subject = Messages.getString("mail.deadlineApproachingSubject",new String[]{questionName});
		email.body = Messages.getString("mail.deadlineApproachingBody",new String[]{toNickname,questionName});
		return email;
	}	

	public static void friendshipConfirmed(String ownerNickname, String ownerEmail, String friendNickname, String role) {
		EmailBean emailBean = new EmailBean();
		emailBean.toNickname=ownerNickname;
		emailBean.toEmail=ownerEmail;
		emailBean.subject=Messages.getString("mail.friendshipConfirmedSubject",new String[]{friendNickname, role});
		emailBean.body=Messages.getString("mail.friendshipConfirmedBody",new String[]{ownerNickname, friendNickname, role});
		Emailing.sendEmail(emailBean);				
	}
	
	public static void notifyOwnerThatSomebodyCommentedQuestion(String ownerNickname, String ownerEmail, String commentAuthorNickname, String growName, String comment) {
		EmailBean emailBean = new EmailBean();
		emailBean.toEmail=ownerEmail;
		emailBean.toNickname=ownerNickname;
		emailBean.subject=Messages.getString("mail.commentNotificationSubject",new String[]{commentAuthorNickname, growName});
		emailBean.body=Messages.getString("mail.commentNotificationBody",new String[]{ownerNickname,commentAuthorNickname,growName,(comment==null?"":comment)});
		Emailing.sendEmail(emailBean);
	}
	
	public static void requestFriendship(String role, String friendEmail, String friendNickname, String userNickname) {
		EmailBean email=new EmailBean();
		email.toNickname=role;
		email.toEmail=friendEmail;
		email.subject=Messages.getString("mail.requestFriendshipSubject", new String[]{userNickname, role});			
		email.body=Messages.getString("mail.requestFriendshipBody", new String[]{friendNickname, userNickname, role});
		Emailing.sendEmail(email);
	}

	public static void adminReport(AdminReport report) {
		Calendar today = Calendar.getInstance();
		String subject="[MindForger] Admin Report "+today.get(Calendar.YEAR)+"/"+today.get(Calendar.MONTH)+"/"+today.get(Calendar.DAY_OF_MONTH);
		String body = 
			"Summary of existing entities:\n" +
			"\n"+
			"Users          : "+report.getUsers()+"\n"+
			"Goals          : "+report.getGrows()+"\n"+
			"Questions      : "+report.getQuestions()+"\n"+
			"Checklist items: "+report.getCheckItems()+"\n"+
			"\n"+
			"Thanks,\n"+
			"    MindForger\n\n";						
		Emailing.sendEmail(new EmailBean("martin.dvorak@mindforger.com", "Martin Dvorak", subject, body));
	}
	
	public static void sendEmails(EmailBean[] emails) {
		if(emails!=null) {
			for (EmailBean email : emails) {
				sendEmail(email);
			}
		}
	}
	
	public static void sendEmail(EmailBean email) {
		// TODO send email asynchronously - task queue
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
        	String applicationId=SystemProperty.applicationId.get();
        	log.info("Sending email from "+applicationId+" to "+email.toEmail+" with subject '"+email.subject+"'");
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("noreply@"+applicationId+".appspotmail.com", "MindForger"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.toEmail, email.toNickname));
            msg.setSubject(email.subject);
            msg.setText(email.body);
            Transport.send(msg);
        } catch (Exception e) {
            log.severe("Unable to send email to "+email.toEmail+": "+e.getMessage());
        }		
	}
}
