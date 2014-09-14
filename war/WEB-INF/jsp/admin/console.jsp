<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.mindforger.coachingnotebook.server.store.gae.GaePersistence" %>
<%@ page import="com.mindforger.coachingnotebook.server.admin.MindForgerKernel" %>
<%@ page import="com.mindforger.coachingnotebook.server.admin.AdminConsoleStatus" %>

<%
    boolean userLogged=false;
    boolean userEligible=false;

    UserService userService = UserServiceFactory.getUserService();
	userEligible=userService.isUserAdmin();
	userLogged=userService.isUserLoggedIn();
    	
    if(userLogged && userEligible) {
    	User user=userService.getCurrentUser();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>CoachingNotebook - Personal Development Tool - Admin Dashboard</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
    <link rel="alternate" type="application/rss+xml" title="RSS" href="http://www.facebook.com/feeds/page.php?id=172099806154112&format=rss20" />
    <%-- Google Analytics --%>
    <script type="text/javascript">
    
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-23166745-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
  
    </script>
	<style type="text/css">
    
    #leftMenubarContainer {
		width: 150px;
		
		position: fixed;
	  	border-collapse: collapse;
	  	margin-top: 0.3em;
	  	margin-bottom: 5em;
	  	
   <% if(MindForgerKernel.showMaintenanceMessage && AdminConsoleStatus.show) { %>
	    top: 8em;
   <% } else {
        if(MindForgerKernel.showMaintenanceMessage || AdminConsoleStatus.show) { %>
	      top: 7em;
   <%   } else { %>
		  top: 6em;	
   <%   } 
      } %>
    }
    </style>    
    
  </head>

  <body>
   <% if(MindForgerKernel.showMaintenanceMessage) { %>
     <div id="maintenanceMessage" style="width: 100%; color: <%= MindForgerKernel.maintenanceMessageFgColor %>; background-color: <%= MindForgerKernel.maintenanceMessageBgColor %>; text-align: center; font-weight: bold;">
       <%= MindForgerKernel.maintenanceMessage %>
     </div>
   <% } %>

   <% if(AdminConsoleStatus.show) { %>
     <div id="adminConsoleStatus" style="width: 100%; color: <%= AdminConsoleStatus.foregroundColor %>; background-color: <%= AdminConsoleStatus.backgroundColor %>; text-align: center; font-weight: bold;">
       <%= AdminConsoleStatus.message %>
     </div>
   <% } %>
  
   <table border="0" style="width: 100%">
   	<tbody>
   	 <tr>
   	  <td style="width:200px;"></td>
   	  <td><center><div id="errorLabelContainer"></div></center></td>
   	  <td style="width: 450px;"><div style="float: right; padding-right: 1em; padding-top: 2px;"><b><%= user.getNickname() %></b> | <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></div></td>
   	 </tr>
   	</tbody>
   </table>
        
   <hr style="background-color: #aaa; color: #aaa; border: 0; height: 1px; width: 100%"/>
    
<table class="mf-applicationPanel" width="100%">
	<tr>
		<td style="width: 185px;">
		  <span
			style="font-family: arial, sans-serif; font-weight: bolder; font-size: 25px; margin-left: 5px;">CoachingNotebook</span>
		  <span 
		    style="vertical-align: baseline; font-size: 0.9em; position: relative; top: +0.4em;"></span>
		</td>
		<td>
		<table width="100%">
			<tr>
				<td style="font-size: 2em; font-weight: bold; text-align: right; padding-right: 1em;">Admin Dashboard</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
	  <td id="leftMenubarContainer" valign="top" width="200px" style="padding-right: 15px;">
	  <div class="mf-menuButtonOn"><a href="#userManagement">User Management</a></div>
	  <div class="mf-menuButtonOff"><a href="#accessRestriction">Access Restriction</a></div>
	  <div class="mf-menuButtonOff"><a href="#showWhitelist">Show Whitelist</a></div>
	  <div class="mf-menuButtonOff"><a href="#addOnWhitelist">Add on Whitelist</a></div>
	  <!-- 
	  <div class="mf-menuButtonOff"><a href="#deleteAccount">Delete Account</a></div>
	   -->
	  <div class="mf-menuButtonOn"><a href="#statistics">Statistics</a></div>
	  <div class="mf-menuButtonOff"><a href="#report">Report</a></div>
	  <div class="mf-menuButtonOff"><a href="https://appengine.google.com/dashboard?&app_id=mind-forger" target="_blank">GAE Console</a></div>
	  <div class="mf-menuButtonOff"><a href="https://www.google.com/analytics/settings/home" target="_blank">Google Analytics</a></div>
	  <div class="mf-menuButtonOn"><a href="#application">Application</a></div>
	  <div class="mf-menuButtonOff"><a href="#maintenanceMessage">Maintenance Message</a></div>
	  <div class="mf-menuButtonOff"><a href="#applicationDisable">Disable</a></div>
	  <div class="mf-menuButtonOn"><a href="#repository">Repository</a></div>
	  <div class="mf-menuButtonOff"><a href="#repositoryDrop">Drop</a></div>
	  </td>
	  <td style="border: solid 1px #ccc; padding-left: 0.8em;" valign="top">
	  

  <a name="userManagement"></a>
  <table style="width: 100%;">
    <tbody>
      <tr>
        <td><h2>User Management</h2></td>
        <td align="right">
		    <form action="servlet" method="post" name="refreshForm">
		      <input class="mf-button" type="submit" name="action" value="Refresh"/>
		    </form>
        </td>
      </tr>
    </tbody>
  </table>
    
    <a name="accessRestriction"></a>
    <h3>Access Restrictions</h3>    
    <form action="servlet" method="post" name="howToLoginForm">
      <% if(MindForgerKernel.whitelistProtectedLogin) { %>
        <input title="Change it to free access" class="mf-button" style="background-color: #008C00;"  type="submit" name="action" value="Only Whitelist"/> users can login.
      <% } else { %>
        <input title="Change it to whitelisty only" class="mf-button" style="background-color: #B02B2C;" type="submit" name="action" value="Anybody"/> can login.
      <% } %>  
    </form>
    
    <a name="showWhitelist"></a>
    <h3>Whitelist</h3>
    <p>
      <a class='mf-button' style="color: white;" href="/_ah/admin/datastore?kind=GaeWhitelistEntryBean" target="_blank">Show</a> users on the whitelist.
    </p>
    
    <a name="addOnWhitelist"></a>
    <h3>Add on Whitelist</h3>  
	<form action="servlet" method="post" name="addOnWhitelistForm">
	  Email: <input type="text" name="email" value=""/>
             <input class="mf-button" type="submit" name="action" value="Add User"/>
	</form>
	  
    <hr/>

  <a name="statistics"></a>
  <h2>Statistics</h2>  

  <a name="report"></a>
  <h3>Report</h3>  
	<form action="servlet" method="post" name="report">
      <input class="mf-button" type="submit" name="action" value="Report"/> on number of users, GROWs, questions and checklist items. 
	</form>
	<ul>
	  <li><a href="https://appengine.google.com/datastore/explorer?submitted=1&app_id=mind-forger&show_options=yes&version_id=2.355011595544025051&viewby=gql&query=SELECT+*+FROM+GaeUserBean+ORDER+BY+loginCounter+DESC&options=Run+Query" target="_blank">Top # of logins</a></li>
	  <li><a href="https://appengine.google.com/datastore/explorer?submitted=1&app_id=mind-forger&show_options=yes&version_id=2.355011595544025051&viewby=gql&query=SELECT+*+FROM+GaeUserBean+ORDER+BY+lastLogin+DESC&options=Run+Query" target="_blank">Recently logged users</a></li>
	  <li><a href="https://appengine.google.com/datastore/explorer?submitted=1&app_id=mind-forger&show_options=yes&version_id=2.355011595544025051&viewby=gql&query=SELECT+*+FROM+GaeUserBean+ORDER+BY+registered+DESC&options=Run+Query" target="_blank">Recently registed users</a></li>
	</ul>
    <br/>

    <hr/>
	  
  <a name="repository"></a>
  <h2>Repository</h2>  
    New version deployment:
      <ol>
        <li>Export repository.</li>
        <li>Flush memcache.</li>
        <li>Drop repository.</li>
        <li>Deploy new MF application version.</li>
        <li>Import repository.</li>
      </ol>
    		 
	<a name="repositoryDrop"></a>
    <h3>Drop</h3>  
    <form action="servlet" method="post" name="deleteForm">
      <input class="mf-button" type="submit" name="action" value="Drop"/> securely <input type="text" name="dropCode" value=""/> all tables in the repository.
    </form>
    <br/>

	<a name="fulltextSearch"></a>
    <h3>Fulltext</h3>  
    <form action="servlet" method="post" name="fulltextForm">
      <input class="mf-button" type="submit" name="action" value="Fulltext Search"/> <input type="text" name="searchString" value=""/>
    </form>
    <br/>

	<a name="memcache"></a>
    <h3>Memcache</h3>  
    <form action="servlet" method="post" name="memcacheForm">
      <input class="mf-button" type="submit" name="action" value="Clear Memcache"/> and remove all entries cached.
    </form>
    <br/>


	<a name="repositoryCheckAndFix"></a>
    <h3>Check and Fix</h3>  
    <form action="servlet" method="post" name="checkAndFixForm">
      <input class="mf-button" type="submit" name="action" value="Check and Fix"/> repository.
    </form>
    <br/>

    <hr/>

  <a name="application"></a>
  <h2>Application</h2>  

  <a name="maintenanceMessage"></a>
  <h3>Maintenance Message</h3>  
	<form action="servlet" method="post" name="setMaintenanceMessage">
	  <table>
	    <tr>
	      <td>
    	    Message: 
	      </td>
	      <td>
	        <input type="text" name="maintenanceMessage" value=""/><br/>
	      </td>
	    </tr>
	    <tr>
	      <td>
    	    Foreground color: 
	      </td>
	      <td>
	        <input type="text" name="maintenanceMessageForeground" value=""/><br/>
	      </td>
	    </tr>
	    <tr>
	      <td>
    	    Background color: 
	      </td>
	      <td>
	        <input type="text" name="maintenanceMessageBackground" value=""/> (red: #B02B2C, green: #008C00, yellow: #FFFF88)<br/>
	      </td>
	    </tr>
	  </table>
      <input class="mf-button" type="submit" name="action" value="Set Maintenance Message"/>
	</form>

	<a name="applicationDisable"></a>
    <h3>Disable</h3>  
    <form action="servlet" method="post" name="disableForm">
     <% if(MindForgerKernel.isServiceDisabled) { %>
      <input class="mf-button" type="submit" name="action" value="Enable"/> the application so that anybody can use it.
     <% } else { %>
      <input class="mf-button" type="submit" name="action" value="Disable"/> the application so that only administrator can use it.
     <% } %>
    </form>
    <br/>

  <br/>
</td></tr></table>
  
       
    <center>
      <div style="color: #aaa;">  
        <br/> &copy; 2011 <a style="color: #aaa;" href="mailto:martin.dvorak@mindforger.com">Martin Dvorak</a>
      </div>
    </center>
    
    <br/>
    
  </body>
</html>

<%
    }
%>