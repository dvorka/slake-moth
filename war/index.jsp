<%@ page contentType="text/html;charset=UTF-8" 
  language="java"
  import="java.util.List,
  javax.jdo.PersistenceManager,
  com.google.appengine.api.users.User,
  com.google.appengine.api.users.UserService,
  com.google.appengine.api.users.UserServiceFactory,
  com.mindforger.coachingnotebook.server.store.Persistence,
  com.mindforger.coachingnotebook.server.store.gae.GaePersistence,
  com.mindforger.coachingnotebook.server.admin.MindForgerKernel,
  com.mindforger.coachingnotebook.shared.skins.Skin,
  com.mindforger.coachingnotebook.shared.MindForgerSettings" 
%><%
    boolean userLogged=false;
    boolean userOnWhitelist=false;
    boolean userHasAccount=false;

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
    	userLogged=userService.isUserLoggedIn();
    	// whitelist check
    	Persistence persistence=MindForgerKernel.getPersistence();
    	if(persistence.isUserOnWhitelist(user.getEmail())) {
			userOnWhitelist=true;
            // check whether user has the account
    		if(persistence.isAccountExists(user.getUserId())) {
    			userHasAccount=true; 		
    		}			
    	}       	
    }
    
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%-- 
- RIA begin ------------------------------------------------------------------------------------------------------- --%><%
  // take it from the most positive result and fallback to the homepage with all the links
  if(userLogged && userHasAccount) {
  // RIA
%>
<html>
  <head>    
    <%@ include file="/WEB-INF/jsp/include/headRia.jsp" %>
	<script type="text/javascript">
		function getAppEngineLogoutUrl() { 
			return "<%= userService.createLogoutURL(request.getRequestURI()) %>";
           }
           
        function mfKeyHandler(e) {
        	if (!e) {
        		e = window.event;
        	}
        	console.log(e.keyCode);
        	console.log(String.fromCharCode(e.keyCode));
        	if (event.altKey == 1) {
				if("S" == String.fromCharCode(e.keyCode)) {
					document.getElementById('mf-searchTextBox').focus();
					document.getElementById('mf-searchTextBox').select();
				}
				if("G" == String.fromCharCode(e.keyCode)) {
					alert("GOAL");
				}        		
				if("Q" == String.fromCharCode(e.keyCode)) {
					alert("GOAL");
				}        		
        	}
        }
        
        window.onload = function() { 
        	document.onkeydown = mfKeyHandler; 
        } 
	</script> 
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
    <%-- OPTIONAL: include this if you want history support --%>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position: absolute; width:0; height:0; border:0"></iframe>
    <%-- RECOMMENDED if your web app will not function without JavaScript enabled --%>
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled in order for this application to display correctly.
      </div>
    </noscript>

   <table border="0" style="width: 100%">
   	<tbody>
   	 <tr>
   	  <td style="width: 100px; display: none;"><%-- <span>Coacher</span> | <a href="http://mind-raider.appspot.com">Smart Notebook</a> --%></td>
   	  <td><div id="errorLabelContainer"><span id="mfLoadingStatusId" class="mf-statusInfo">Loading <%= Skin.name %>...</span></div></td>
   	  <td id="rightCornerContainer"></td>
   	 </tr>
   	</tbody>
   </table>
     
   <hr id="mfTopHrId" style="display: none; background-color: #aaa; color: #aaa; border: 0; height: 1px; width: 100%"/>
    
<table class="mf-applicationPanel">
	<tr>
		<td id="mfLogoTdId" style="display: none;" width="190px">
          <span style="font-family: arial, sans-serif; font-weight: bolder; font-size: 25px; margin-left: 5px;"><%= Skin.name %></span>
		  <span style="vertical-align: baseline; font-size: 0.9em; position: relative; top: +0.4em;"><%-- &alpha; --%></span>
		  <%-- DIV below is used to ensure that left menu will be wide enough - even in case that the browser window is narrow --%>
		  <div style="width: 178px;"></div>
		</td>
		<td>
		<table width="100%">
			<tr>
				<td id="searchContainer"></td>
				<td id="pageTitleContainer"></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
	  <td id="leftMenubarContainer" class="mf-leftMenubarContainer" valign="top">
	  </td>
	  <td valign="top">
          <table class="mf-bottomContainers" style="boder-collapse: collapse; width: 100%">
            <tbody>
              <tr>        
				<td valign="top" id="friendsContainer" style="display: none;"></td>				
				<td valign="top" id="userProfileContainer" style="display: none;"></td>
				<td valign="top" id="blueLifeContainer" style="display: none;"></td>
				<td valign="top" id="searchResultsContainer" style="display: none;"></td>
				<td valign="top" id="cheatsheetContainer" style="display: none;"></td>
				<td valign="top" id="dashboardContainer" style="display: none;"></td>
				<td valign="top" id="importanceUrgencyChartContainer" style="display: none;"></td>
				<td valign="top" id="goalsTableContainer" style="display: none;"></td>
				<td valign="top" id="sharedGoalsTableContainer" style="display: none;"></td>
				<td valign="top" id="actionsTableContainer" style="display: none;"></td>
				<td valign="top" id="lessonsLearnedTableContainer" style="display: none;"></td>
				<td valign="top" id="growTabsContainer" style="display: none;"></td>
				<td valign="top" id="myLifeContainer" style="display: none;"></td>
				<td valign="top" id="mfLoadingPanelId" style="t-align: center; padding: 2em;">
				<img src="/images/goal-lifecycle.png">
				</img></td>
              </tr>
            </tbody>
          </table>
      </td>
	</tr>
</table>
        
    <center>
      <br/>
      <div id="creditsDiv" 
           style="width: 35em; display: none; font-style: italic; text-align: justify; border: solid 1px #ccc; background-color: white; cursor: hand; cursor: pointer; margin-bottom: 2em;" 
           onclick="javascript:document.getElementById('creditsDiv').style.display='none';">
        <pre>
   Credits  

     Written by Martin Dvorak

   Acknowledgements to reviewers
   
     Tanna Boran Corona, Christine Prelaz,
     Cindy Schulson and Lorna Levy
      
   Special thanks
     
     Mari, Floex, Gryga and Jan Mirus

   Platform, Services and 3rd party libraries
   
     Google App Engine
     Google Chart API
     Google Gson
     Google Web Toolkit
     Gravatar
     Jakarta Commons FileUpload
     Jakarta Commons IO
     jQuery
     
     Eclipse
     Wink Tutorial Maker
       </pre>
     </div>

    <%@ include file="/WEB-INF/jsp/include/privacyPolicy.jsp" %>

     <div style="color: #777; width: 99%;">
       <div id="mfFootnoteId" style="display: none; float: right;">
         <a style="color: #777;" href='mailto:yourfriend@example.com?subject=Invitation to CoachingNotebook&body=I just found CoachingNotebook which is an online life designer, problem solver and coaching notebook.%0A%0ATry it at http://web.mindforger.com' title="Invitate your friends, clients and coaches to MindForger">Invite Friends</a> | 
         <a style="color: #777;" href='mailto:feedback@mindforger.com?subject=CoachingNotebook Testimonial&body=If CoachingNotebook have helped you, I would be thankful to receive your testimonial. When you add a testimonial, you are providing your peers with an honest assessment of MindForger and helping them understand how they can benefit from it. Thank you for your time!%0A%0AYour Name:%0A...%0A%0AYour Web:%0A...%0A%0ATestimonial Details:%0A...%0A%0A' title="Send a testimonial">Add Testimonial</a> |
         <%-- <a style="color: #777;" href="mailto:info@mindforger.com">Contact</a> | --%> 
         <a style="color: #777;" href="mailto:feedback@mindforger.com?subject=[CoachingNotebook] Feedback" title="Provide a feedback">Feedback</a> |
         <a style="color: #777;" href="mailto:feedback@mindforger.com?subject=[CoachingNotebook] Feature Request" title="Request a feature">Request&nbsp;Feature</a> | 
         <%-- <a style="color: #777;" href="mailto:info@mindforger.com?subject=Bug Report">Bug&nbsp;Report</a> | --%>
         <a style="font-weight: normal; cursor: pointer;" onclick="javascript:document.getElementById('creditsDiv').style.display='block';document.getElementById('privacyPolicyDiv').style.display='none';" title="Credits and acknowledgements">Credits</a> |
         <a style="font-weight: normal; cursor: pointer;" onclick="javascript:document.getElementById('privacyPolicyDiv').style.display='block';document.getElementById('creditsDiv').style.display='none';" title="Privacy Policy">Privacy</a>
         <%-- <a href="">Privacy</a> --%>
       </div>
       <div style="float: left; margin-top: 0.3em;"><%-- &copy; <a style="color: #777;" href="mailto:martin.dvorak@mindforger.com?subject=[CoachingNotebook]">Martin Dvorak</a> 2011 --%></div>
     </div>
    </center>
    
    <br/>
    
  </body>
</html>
<%-- RIA end ------------------------------------------------------------------------------------------------------- --%>

<%
    } else {
    	// most positive option didn't matched (logged in & has account & on whitelist) > elaborate others
%><%-- 
- WELCOME PAGE begin ------------------------------------------------------------------------------------------------------- --%>
<html>
  <head>
    <%@ include file="/WEB-INF/jsp/include/headWelcome.jsp" %>
  </head>

  <body>
   <% if(MindForgerKernel.showMaintenanceMessage) { %>
     <div id="maintenanceMessage" style="width: 100%; color: <%= MindForgerKernel.maintenanceMessageFgColor %>; background-color: <%= MindForgerKernel.maintenanceMessageBgColor %>; text-align: center; font-weight: bold;">
       <%= MindForgerKernel.maintenanceMessage %>
     </div>
   <% } %>

   <center id="welcomepage" style="display: block;">
   <table width="850px" height="50px">
    <tbody>
     <tr>
       <td align="left" valign="top"><a href="http://www.mindforger.com"><img src="/images/welcome-logo.png" border="0"/></a></td>
       <td>
       </td>
       <td align="right">
       <% if(!userLogged) { %>
         Already a CoachingNotebook user?&nbsp;&nbsp;<a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="signInButton">Sign In</a>
       <% } else { %>
         <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="signOutButton">Sign Out</a>
       <% } %>
       </td>
     </tr>
    </tbody>
   </table>

   <table style="text-align: left; width: 850px; height: 564px;" background="/images/miky-1024.jpg">
    <tbody>
     <tr style="height: 14em;">
       <td valign="bottom"><img style="margin-left: 1em;" src="/images/grow.png" border="0"/></td>       
     </tr>
     <tr style="height: 4.5em;">
       <td>
         <span style="padding-left: 10.5em; color: white;">with</span><br/>
         <span style="padding-left: 3em; color: white; font-size: 2em; font-weight: normal; font-family: arial;"><%= Skin.name %></span>
       </td>
     </tr>
     <tr style="height: 150px;">
       <td valign="top">
         <a href="http://www.youtube.com/watch?v=m3g5vnELDxY" rel="vidbox 800 600"><img onmouseover="this.src='./images/youtube-thumbnail-hover.png'" onmouseout="this.src='./images/youtube-thumbnail.png'" style="margin-left: 3.2em;margin-top: 1.5em; border: 1px;" src="./images/youtube-thumbnail.png" alt="MindForger trailer on YouTube" width="250" height="180"/></a>           
       </td>
     </tr>
     <tr>
       <td align="right" valign="top">
       <% 
	   if(userLogged) {
	       if(userOnWhitelist && !userHasAccount) {
               // user is logged in and on whitelist, BUT doesn't have account yet...
               %>
               <a class="mf-signUpButtonA" href="./editions"><span class="mf-signUpButtonSpan"><span style="font-weight: bolder;">Sign Up</span> for a Free Account &gt;</span></a>
               <%
           }
           // if(!userOnWhitelist && userHasAccount) ... cannot occur
           // if(userOnWhitelist && userHasAccount) ... handled above
           // if(!userOnWhitelist && !userHasAccount) ... cannot access MF > don't show anything
	   } else {
		   // user not logged in > I don't know whether he is on the whitelist
           %>
           <a class="mf-signUpButtonA" href="./editions"><span class="mf-signUpButtonSpan"><span style="font-weight: bolder;">Sign Up</span> for a Free Account &gt;</span></a>
           <%
	   }
       %>
       </td>
     </tr>
     <tr><td><%-- do not remove this line --%></td></tr>
    </tbody>
   </table>

   <br/>

   <table style="width: 870px">
     <tr>
       <td class="mf-purposeAndTutorialTitle">Solve&nbsp;Problems</td>
       <td class="mf-purposeAndTutorialTitle">&nbsp;</td>
       <td class="mf-purposeAndTutorialTitle">Achieve&nbsp;Goals</td>
     </tr>
     <tr>
       <td valign="top" style="padding-left: 0.5em; padding-right: 0.5em; text-align: justify;">Do want to start a new business, find a new partner for life, change your career, run sub 3 hour marathon or lose 25 pounds? Any tough problem? Need to make up your mind? <b>Problem Solver</b> will help you to find the solution.</td> 
       <td valign="top" style="padding-left: 0.5em; padding-right: 0.5em; border-right: solid #ccc 1px; border-left: solid #ccc 1px; text-align: justify;"></td> 
       <td valign="top" style="padding-left: 0.5em; text-align: justify;">How do you track progress on your goals between sessions with your life, busines or executive coach? Are you a professional coach? How do you interact with your clients? Connect and share with your <b>social network</b> on CoachingNotebook!</td> 
     </tr>
   </table>

   <br/>

   <table border="0" style="margin-bottom: 0.5em;">
     <tbody>
       <tr>
       <td>
         Follow <a href="http://www.mindforger.com" style="border-bottom:1px dotted #000;"><%= Skin.name %></a> on
       </td>
       <td>
         <a href="https://twitter.com/mindforger" target="_blank" title="@mindforger on Twitter" style="border-bottom:1px dotted #000;">Twitter</a>, 
       </td>
       <td>
         <a href="http://www.facebook.com/pages/MindForger/172099806154112" target="_blank" title="CoachingNotebook on Facebook" style="border-bottom:1px dotted #000;">Facebook</a>,
       </td>
       <td>
         <a href="http://www.youtube.com/user/MindForgerChannel/videos" target="_blank" title="CoachingNotebook on YouTube" style="border-bottom:1px dotted #000;">YouTube</a>,
       </td>
       <td>
         <a href="https://plus.google.com/100232165710311926388/posts" target="_blank" title="CoachingNotebook on Google+" style="border-bottom:1px dotted #000;">Google+</a> and          
       </td>
       <td>
         <a href="https://groups.google.com/group/mindforger" target="_blank" title="CoachingNotebook group on Google Groups" style="border-bottom:1px dotted #000;">Google Groups</a>          
       </td>
       </tr>
     </tbody>
   </table>

  <br/>

   <table style="width: 870px">
     <tr>
       <td class="mf-purposeAndTutorialTitle">Getting Started</td>
       <td class="mf-purposeAndTutorialTitle">G.R.O.W. Model</td>
       <td class="mf-purposeAndTutorialTitle">S.M.A.R.T. Goals</td>
     </tr>
     <tr>
       <td><a href="http://www.youtube.com/watch?v=nx01gt50w_c" target="_blank" rel="vidbox 800 600"><img 
       		onmouseover="this.src='./images/video-tutorial-getting-started-hover.png'" onmouseout="this.src='./images/video-tutorial-getting-started.png'" border="0" title="Launch Getting Started tutorial..." style="width: 15em; height: 8em; margin-left: 1.2em;" src="/images/video-tutorial-getting-started.png"/></a></td>
       <td style="border-right: solid #ccc 1px; border-left: solid #ccc 1px;"><a href="http://www.youtube.com/watch?v=Yc2KmVgo3Sk" target="_blank" rel="vidbox 800 600"><img 
       		onmouseover="this.src='./images/video-tutorial-grow-model-hover.png'" onmouseout="this.src='./images/video-tutorial-grow-model.png'" border="0" title="Launch G.R.O.W. Model tutorial..." style="width: 15em; height: 12em; margin-left: 1.2em;" src="/images/video-tutorial-grow-model.png"/></a></td>
       <td><a href="http://www.youtube.com/watch?v=-nhK3UGH8OU" target="_blank" rel="vidbox 800 600"><img 
       		onmouseover="this.src='./images/video-tutorial-smart-goals-hover.png'" onmouseout="this.src='./images/video-tutorial-smart-goals.png'" border="0" title="Launch S.M.A.R.T. Goals tutorial..." style="width: 15em; height: 12em; margin-left: 1.2em;" src="/images/video-tutorial-smart-goals.png"/></a></td>
     </tr>
     <tr>
       <td colspan="3"><br/></td>
     </tr>
     <tr>
       <td class="mf-purposeAndTutorialTitle">S.W.O.T. Analysis</td>
       <td class="mf-purposeAndTutorialTitle">Eisenhower Matrix</td>
       <td class="mf-purposeAndTutorialTitle">Wheel of Life</td>
     </tr>
     <tr>
       <td><a href="http://www.youtube.com/watch?v=8aqoT3kj06Y" target="_blank" rel="vidbox 800 600"><img 
       		onmouseover="this.src='./images/video-tutorial-swot-analysis-hover.png'" onmouseout="this.src='./images/video-tutorial-swot-analysis.png'" border="0" title="Launch S.W.O.T. Analysis tutorial..." style="width: 15em; height: 12em; margin-left: 1.2em;" src="/images/video-tutorial-swot-analysis.png"/></a></td>
       <td style="border-right: solid #ccc 1px; border-left: solid #ccc 1px;"><a href="http://www.youtube.com/watch?v=99InKq9n1yA" target="_blank" rel="vidbox 800 600"><img 
       		onmouseover="this.src='./images/video-tutorial-eisenhower-matrix-hover.png'" onmouseout="this.src='./images/video-tutorial-eisenhower-matrix.png'" border="0" title="Launch Eisenhower Matrix tutorial..." style="width: 15em; height: 12em; margin-left: 1.2em;" src="/images/video-tutorial-eisenhower-matrix.png"/></a></td>
       <td><a href="http://www.youtube.com/watch?v=AomKPm8Lhkg" target="_blank" rel="vidbox 800 600"><img 
       		onmouseover="this.src='./images/video-tutorial-wheel-of-life-hover.png'" onmouseout="this.src='./images/video-tutorial-wheel-of-life.png'" border="0" title="Launch Wheel of Life tutorial..." style="width: 15em; height: 12em; margin-left: 1.2em;" src="/images/video-tutorial-wheel-of-life.png"/></a></td>
     </tr>
   </table>
   
   <div style="margin-top: 2em; margin-bottom: 1em; width: 870px"/>
 
    <table style="width: 870px;">
     <tr>
       <td style="width: 33%; text-align: center; font-size: x-large; padding: 0.3em;">
       	 <a href="./editions.html" class="mf-link">Features</a>
       </td>
       <td style="width: 66%; text-align: left; font-size: normal;">
         <a href="./editions.html">Detailed overview of CoachingNotebook features.</a>
       </td>
     </tr>
     <tr>
       <td style="width: 33%; text-align: center; font-size: x-large; padding: 0.3em;">
       	 <a href="./documentation/index.html" class="mf-link">Documentation</a>
       </td>
       <td style="width: 66%; text-align: left; font-size: normal;">
         <a href="./documentation/index.html">Learn all about CoachingNotebook features, motivation and its theoretical background.</a>
       </td>
     </tr>
   </table> 
 
   <div id="aboutDiv" 
        style="width: 30em; display: none; font-style: italic; text-align: justify; cursor: hand; cursor: pointer;" 
        onclick="javascript:document.getElementById('aboutDiv').style.display='none';">
   CoachingNotebook is offered in the hope that it will be useful. It is free, but comes with no warranty. Account limits
   might be extended on your request. Your feedback, suggestion and constructive criticism is highly appreciated.
   <br/>
   <br/>
   </div>

   <%@ include file="/WEB-INF/jsp/include/privacyPolicy.jsp" %>

   <div style="font-size: normal; color: #777; width: 850px;">
     <%--
     <div style="float: left; margin-top: 0.3em;">&copy; Martin Dvorak 2011</div>
      --%>
     <div style="text-align: right;">
       <a class="mf-welcomeFootnote" style="padding-bottom: 30px;" onclick="javascript:document.getElementById('aboutDiv').style.display='block';document.getElementById('privacyPolicyDiv').style.display='none';">About</a> | 
       <a class="mf-welcomeFootnote" onclick="javascript:document.getElementById('privacyPolicyDiv').style.display='block';document.getElementById('aboutDiv').style.display='none';">Privacy</a> |
       <a class="mf-welcomeFootnote" href="mailto:info@mindforger.com">Contact</a>
       <%-- | Terms of Use | Privacy  --%>
     </div>
   </div>
   
   <br/>
      
   </center>
    
  </body>
</html>

<%
    }
%>

