package com.mindforger.coachingnotebook.server.servlets;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mindforger.coachingnotebook.server.store.gae.GaePersistence;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.MindForgerSettings;

public class RegistrationServlet extends HttpServlet {
	private static final Logger log=Logger.getLogger("RegistrationServlet");

	private static final String CREATE_ACCOUNT = "registerOrCheck";

	GaePersistence persistence;
	UserService userService;
    
    public RegistrationServlet() {
		persistence = new GaePersistence();    	
		persistence.adminLoadKernelConfig();
		userService = UserServiceFactory.getUserService();
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {		
    	log.log(Level.INFO, "doGet()");

    	if(userService.isUserLoggedIn()) {
    		try {
    			String action = request.getParameter("action");

    			if(CREATE_ACCOUNT.equals(action)) {
    				String edition=request.getParameter("edition");
    				User user = userService.getCurrentUser();
    				persistence.loginCheck(user.getUserId(),user.getNickname(),user.getEmail());
    				log.log(Level.INFO, "User "+user.getEmail()+" signed to "+edition+"!");
    				response.sendRedirect("/");
    				return;    				
    			}
    		} catch (Exception e) {
    			log.log(Level.SEVERE,"Unable to handle user registration!",e);
    			throw new ServletException(e);
    		}
    	} else {
    		// redirect user to Google login
    		try {
    			if(MindForgerSettings.OPENID_ENABLED) {
    				response.sendRedirect(userService.createLoginURL(MindForgerConstants.MIND_FORGER_OPENID_LOGIN_PAGE_PATH));    				    				
    			} else {
    				response.sendRedirect(userService.createLoginURL(MindForgerConstants.MIND_FORGER_BASE_URL));    				
    			}
    			return;    				
    		} catch (Exception e) {
    			log.log(Level.SEVERE,"Unable to handle user registration!",e);
    			throw new ServletException(e);
    		}
    	}
		log.log(Level.INFO,"No dispatch: "+request.getRequestURL());
    }

	private static final long serialVersionUID = -7479442044754182223L;
}
