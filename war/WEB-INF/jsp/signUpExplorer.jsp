<%@ page 
  contentType="text/html;charset=UTF-8" 
  language="java"
  import="com.mindforger.coachingnotebook.shared.MindForgerSettings,
    com.google.appengine.api.users.User,
    com.google.appengine.api.users.UserService,
    com.google.appengine.api.users.UserServiceFactory,
    com.mindforger.coachingnotebook.server.admin.MindForgerKernel" %><%
    boolean userLogged=false;
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
    	userLogged=userService.isUserLoggedIn();
    }
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>    
    <%@ include file="/WEB-INF/jsp/include/headRegistration.jsp" %>
    <script src="/js/signUp.js"></script>
   <% if(MindForgerKernel.showMaintenanceMessage) { %>
	<style type="text/css">
      .mf-leftMenu {
	    top: 6.5em;
	  }	
	</style>
   <% } %>   		    
  </head>
  <body>
   <% if(MindForgerKernel.showMaintenanceMessage) { %>
     <div id="maintenanceMessage" style="width: 100%; color: <%= MindForgerKernel.maintenanceMessageFgColor %>; background-color: <%= MindForgerKernel.maintenanceMessageBgColor %>; text-align: center; font-weight: bold;">
       <%= MindForgerKernel.maintenanceMessage %>
     </div>
   <% } %>

<center>
<div>
<div style="padding: 0.5em; font-size: 3em;">Thank you for choosing CoachingNotebook!</div>

<table><tr><td>
<table>
  <tr>
    <td colspan="2" style="font-size: 1.3em; text-align: left;">Your login</td>
  </tr>
  <tr>
    <td colspan="2" style="text-align: left;">In order to register you will need a <b><a href="https://accounts.google.com" class="mf-link" target="_blank">Google Account</a></b>. 
    If you don't have it, please <a target="_blank" href="https://accounts.google.com/NewAccount" class="mf-button" style="color: #fff;">create</a>a new one.</td>
  </tr>
</table>

<br/>
<hr/>
<br/>
<table width="100%">
  <tr>
    <td valign="middle"><input id="iHaveReadTerms" type="checkbox" onclick="toggleAcceptButton();"/> I have read and accept the <span style="font-weight: bolder; border-bottom: dotted 1px #000; cursor: hand; cursor: pointer;" onclick="toggleTermsAndConditions()">Terms and Conditions</span>.</td>
    <td valign="middle">
<%
if(userLogged) {
%>
    <a id="createMyAccountAnchor" class="mf-button" style="display: none; color: #fff; text-decoration: none; font-family: Arial; font-size: 0.8em;" href="/registration?action=registerOrCheck&edition=explorer">Create My MindForger Account</a>
<%
} else {
%>
    <a id="createMyAccountAnchor" class="mf-button" style="display: none; color: #fff; text-decoration: none; font-family: Arial; font-size: 0.8em;" href="<%= userService.createLoginURL("/registration?action=registerOrCheck&edition=explorer") %>">Create My Account</a>
<%
}
%>    
<a title="Please accept the Terms and Conditions using checkbox above" id="createMyAccountAnchorDisabled" class="mf-button" style="color: #555; background-color: #aaa; text-decoration: none; font-family: Arial; font-size: 0.8em;">Create My Account</a>

    </td>
  </tr>
</table>

</td></tr></table>

</div>

<%@include file="/WEB-INF/jsp/include/termsAndConditions.jsp" %>

</center>
<br/>
<br/>

   </body>
</html>
