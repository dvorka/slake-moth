<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/openid.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="/css/welcome.css" />
</head>
<body>

  <center style="padding-top: 50px;">
<form class="openid" method="post" action="/_ah/login_required"> 
<table style="width: 600px;">
  <tr>
    <td style="vertical-align: center; width: 300px;">
      <span style="font-size: 1.5em; margin-bottom: 1em;">Log in to <span style="font-weight: bolder;">CoachingNotebook</span> with</span>
    </td>
    <td>
      <img src="/images/openid/logo-openid.png" border="0"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="border-bottom: dotted 1px #000;"/> 
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <span>&nbsp;<br/>Please click your account provider:</span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
	  <div><ul class="providers"> 
	  <li class="openid" title="OpenID"><img src="/images/openid/openidW.png" alt="icon" /> 
	  <span><strong>http://{your-openid-url}</strong></span></li> 
	  <li class="direct" title="Google"> 
			<img src="/images/openid/googleW.png" alt="icon" /><span>https://www.google.com/accounts/o8/id</span></li> 
	  <li class="direct" title="Yahoo"> 
			<img src="/images/openid/yahooW.png" alt="icon" /><span>http://yahoo.com/</span></li> 
	  <li class="username" title="AOL screen name"> 
			<img src="/images/openid/aolW.png" alt="icon" /><span>http://openid.aol.com/<strong>username</strong></span></li> 
	  <li class="username" title="MyOpenID user name"> 
			<img src="/images/openid/myopenid.png" alt="icon" /><span>http://<strong>username</strong>.myopenid.com/</span></li> 
	  <li class="username" title="Flickr user name"> 
			<img src="/images/openid/flickr.png" alt="icon" /><span>http://flickr.com/<strong>username</strong>/</span></li> 
	  <li class="username" title="Technorati user name"> 
			<img src="/images/openid/technorati.png" alt="icon" /><span>http://technorati.com/people/technorati/<strong>username</strong>/</span></li> 
	  <li class="username" title="Wordpress blog name"> 
			<img src="/images/openid/wordpress.png" alt="icon" /><span>http://<strong>username</strong>.wordpress.com</span></li> 
	  <li class="username" title="Blogger blog name"> 
			<img src="/images/openid/blogger.png" alt="icon" /><span>http://<strong>username</strong>.blogspot.com/</span></li> 
	  <li class="username" title="LiveJournal blog name"> 
			<img src="/images/openid/livejournal.png" alt="icon" /><span>http://<strong>username</strong>.livejournal.com</span></li> 
	  <li class="username" title="ClaimID user name"> 
			<img src="/images/openid/claimid.png" alt="icon" /><span>http://claimid.com/<strong>username</strong></span></li> 
	  <li class="username" title="Vidoop user name"> 
			<img src="/images/openid/vidoop.png" alt="icon" /><span>http://<strong>username</strong>.myvidoop.com/</span></li> 
	  <li class="username" title="Verisign user name"> 
			<img src="/images/openid/verisign.png" alt="icon" /><span>http://<strong>username</strong>.pip.verisignlabs.com/</span></li> 
	  </ul></div> 
    </td>
  </tr>
  <tr>
    <td colspan="2">
	  <fieldset> 
	  <label for="openid_username">Enter your <span>Provider user name</span></label> 
	  <div><span></span><input type="text" name="openid_username" /><span></span> 
	  <input type="submit" value="Login" /></div> 
	  </fieldset> 
	  <fieldset> 
	  <label for="openid_identifier">Enter your username:</label> 
	  <div><input type="text" name="openid_identifier" /> 
	  <input type="submit" value="Login" class="mf-button"/></div> 
	  </fieldset> 
    </td>
  </tr>
</table>
</form>


<div style="height: 3em;"></div>


<a style="border-bottom: dotted 1px #000; cursor: hand; cursor: pointer;" onclick="javascript:document.getElementById('openidProblems').style.display='block';">Problems logging in with OpenID?</a> | 
<a style="border-bottom: dotted 1px #000;" href="http://openid.net/get-an-openid/" target="_blank">Get OpenID</a>

<div id="openidProblems" style="display: none;" onclick="javascript:document.getElementById('openidProblems').style.display='none';">
<h2>OpenID</h2>
<div style="text-align: justify; width: 600px;">
<a style="border-bottom: dotted 1px #000; cursor: hand; cursor: pointer;" href="http://openid.net/">OpenID</a> provides a way to share the same username and password among many websites. OpenID consists of providers which give you the user accounts that can be reused on other sites and relying parties that have websites that accept OpenID accounts. CoachingNotebook is a relying party. Examples of OpenID providers whose accounts can be used include Yahoo! and Blogger.
</div>

<h2>Supported Providers</h2>
The officially supported OpenID providers that are used by CoachingNotebook are listed below:

<ul style="text-align: left; width: 20em;">
  <li>Google*</li>
  <li>Yahoo</li>
  <li>AOL</li>
  <li>myOpenID</li>
  <li>Flickr</li>
  <li>Technorati*</li>
  <li>WordPress*</li>
  <li>Blogger*</li>
  <li>LiveJournal*</li>
  <li>ClaimID</li>
  <li>myVidoop</li>
  <li>Verisign</li>
</ul>
* Please log in first to the provider e.g. Google or Technorati.
</div>

</center>
           
           
           
<%-- http://jvance.com/pages/JQueryOpenIDPlugin.xhtml --%>
<script type="text/javascript" src="/js/jquery/1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/openid/jquery.openid.js"></script>
<script type="text/javascript">  $(function() { $("form.openid:eq(0)").openid(); });</script>

</body>
</html>
