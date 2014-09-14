package com.mindforger.coachingnotebook.server.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

public class LoginRequiredServlet extends HttpServlet {
	private static final Logger log=Logger.getLogger("LoginRequiredServlet");
	
	public static final String LOGIN_PAGE_PATH="/_ah/login_required";
	private static final String PARAMETER_OPEN_ID_URL = "openid_identifier";
	private static final String PARAMETER_CONTINUE = "continue";
	 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getUserPrincipal() != null) {
			response.sendRedirect("/");						
		} else {
			log.log(Level.INFO,"Redirecting to OpenID login page...");
			response.sendRedirect("/login-openid");			
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String openidUrl = request.getParameter(PARAMETER_OPEN_ID_URL);		
		if(openidUrl==null || openidUrl.isEmpty()) {
			// TODO fallback to gmail
			openidUrl="gmail.com";
		}
		log.log(Level.INFO,"OpenID login handler called: "+openidUrl);
		
		Set<String> attributes = new HashSet<String>();
		attributes.add("email");
		String loginUrl = UserServiceFactory.getUserService().createLoginURL(
				getDestinationUrl(request),
				null,
				openidUrl,
				attributes);

		log.log(Level.INFO,"Redirecting to login URL: "+loginUrl);
		response.sendRedirect(loginUrl);
	}

	private String getDestinationUrl(HttpServletRequest request) {
		String destinationURL = request.getParameter(PARAMETER_CONTINUE);
		if (destinationURL==null || destinationURL.isEmpty()) {
			destinationURL = "/";            	 
		}
		return destinationURL;
	}
	
	private static final long serialVersionUID = -6135133279758151303L;
}
