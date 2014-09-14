<%@ page contentType="text/html;charset=UTF-8" 
  language="java"
  import="com.mindforger.coachingnotebook.shared.MindForgerSettings,com.google.appengine.api.users.UserService,com.google.appengine.api.users.UserServiceFactory,com.mindforger.coachingnotebook.server.admin.MindForgerKernel" %><%UserService userService = UserServiceFactory.getUserService();%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>    
   <%@ include file="/WEB-INF/jsp/include/headRegistration.jsp" %>
   <%
   	if(MindForgerKernel.showMaintenanceMessage) {
   %>
	<style type="text/css">
      .mf-leftMenu {
	    top: 6.5em;
	  }	
	</style>
   <%
   	}
   %>   		    
  </head>

  <body>
   <%
   	if(MindForgerKernel.showMaintenanceMessage) {
   %>
     <div id="maintenanceMessage" style="width: 100%; color: <%=MindForgerKernel.maintenanceMessageFgColor%>; background-color: <%=MindForgerKernel.maintenanceMessageBgColor%>; text-align: center; font-weight: bold;">
       <%=MindForgerKernel.maintenanceMessage%>
     </div>
   <%
   	}
   %>

<center>
<div id="editionSelection">
<%
	if(MindForgerSettings.BILLING_ENABLED) {
%>
  
<div style="padding: 0.5em; font-size: 3em;">Which edition is right for you?</div>

<br/>
<br/>

<%
} else 
%>

   <%@ include file="/WEB-INF/jsp/include/freeFeaturesOverview.jsp" %>

   <br/>
   <br/>
<table>
 <tr>
  <td valign="top">
  </td>
 </tr>
</table>

</div>
</center>
   
   </body>
</html>
