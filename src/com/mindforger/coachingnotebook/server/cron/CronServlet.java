package com.mindforger.coachingnotebook.server.cron;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mindforger.coachingnotebook.server.store.Persistence;
import com.mindforger.coachingnotebook.server.store.gae.GaePersistence;

public class CronServlet extends HttpServlet {
	private static final Logger log=Logger.getLogger(CronServlet.class.getName());

	private Persistence persistence;
	
	public CronServlet() {
		log.log(Level.INFO, "Cron servlet instantiated");
	}

	@Override
	public void init() {
		log.log(Level.INFO, "Cron servlet initialized");

		persistence = new GaePersistence();
	}

	@Override
	public void destroy() {
		log.log(Level.INFO, "Cron servlet destroyed");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.log(Level.SEVERE, 
				"Cron job secured just using configuration - the admin check in the code doesn't work as there is " +
				"no current user that could be obtained from UserService.");
		
		int emails = persistence.adminFindAndEmailActionsWhoseDeadlineApproaching();
		persistence.adminSendAdminReportByEmail();
				
		response.setStatus(200);
		response.setContentType("text/plain");
		response.getWriter().println("Successfuly notified "+emails+" users!");						
	}

	private static final long serialVersionUID = -463206922379980258L;
}
