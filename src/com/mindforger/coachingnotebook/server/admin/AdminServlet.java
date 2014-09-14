package com.mindforger.coachingnotebook.server.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.mindforger.coachingnotebook.server.store.gae.GaePersistence;
import com.mindforger.coachingnotebook.shared.beans.DescriptorBean;

public class AdminServlet extends HttpServlet {
	private static final Logger log=Logger.getLogger(AdminServlet.class.getName());

    static final String ACTION_LOGIN_ANYBODY= "Only Whitelist";
    static final String ACTION_LOGIN_WHITELIST= "Anybody";
	static final String ACTION_MAINTENANCE_MESSAGE = "Set Maintenance Message";
    static final String ACTION_ADD_USER_ON_WHITELIST = "Add User";
    static final String ACTION_EXPORT = "Export";
    static final String ACTION_DELETE = "Drop";
    static final String ACTION_ENABLE= "Enable";
    static final String ACTION_DISABLE = "Disable";
    static final String ACTION_IMPORT = "Import";
    static final String ACTION_REFRESH = "Refresh";
    static final String ACTION_CHECK_AND_FIX= "Check and Fix";
    static final String ACTION_REPORT= "Report";
    static final String ACTION_CLEAR_MEMCACHE= "Clear Memcache";
    static final String ACTION_FULLTEXT_SEARCH="Fulltext Search";

	GaePersistence persistence;
    
    public AdminServlet() {
    	// TODO persistence > singleton (JSPs)
		persistence = new GaePersistence();   
		persistence.adminLoadKernelConfig();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {		
		log.log(Level.INFO, "doPost()");
		
		UserService userService = UserServiceFactory.getUserService();
    	try {
    		if(userService.isUserAdmin()) {
    			String action = request.getParameter("action");

    			if(ACTION_MAINTENANCE_MESSAGE.equals(action)) {
    				String maintenanceMessage=request.getParameter("maintenanceMessage");
    				String maintenanceMessageForeground=request.getParameter("maintenanceMessageForeground");
    				String maintenanceMessageBackground=request.getParameter("maintenanceMessageBackground");
    				
    				if(maintenanceMessage!=null && !"".equals(maintenanceMessage)) {
    					MindForgerKernel.maintenanceMessage=maintenanceMessage;
    					MindForgerKernel.showMaintenanceMessage=true;
        				if(maintenanceMessageForeground!=null && !"".equals(maintenanceMessageForeground)) {
        					MindForgerKernel.maintenanceMessageFgColor=maintenanceMessageForeground;
        				}
        				if(maintenanceMessageBackground!=null && !"".equals(maintenanceMessageBackground)) {
        					MindForgerKernel.maintenanceMessageBgColor=maintenanceMessageBackground;
        				}
    				} else {
    					MindForgerKernel.showMaintenanceMessage=false;
    				}
					AdminConsoleStatus.showInfo("Maintenance info visibility set to: "+MindForgerKernel.showMaintenanceMessage);
    				response.sendRedirect("console");
    				return;    				
    			}

    			if(ACTION_REPORT.equals(action)) {
					AdminReport report=persistence.adminGetReport();
    				
					AdminConsoleStatus.showInfo(
							"Report: "+
							report.users+" users, "+
							report.grows+" GROWs, "+
							report.questions+" questions, "+
							report.checkItems+" checkItems, total/+/- "+
							persistence.getCache().getObjectCount()+">"+
							persistence.getCache().getCacheHits()+"/"+
							persistence.getCache().getCacheMisses());
    				
    				response.sendRedirect("console");
    				return;
    			}
    			
    			if(ACTION_ADD_USER_ON_WHITELIST.equals(action)) {
    				String email=request.getParameter("email");
    				if(email!=null && !"".equals(email)) {
    					persistence.addUserOnWhitelist(email);
    					AdminConsoleStatus.showInfo("User '"+email+"' has been added on whitelist!");
    				}
    				response.sendRedirect("console");
    				return;
    			}    			
    			
    			if(ACTION_DELETE.equals(action)) {
    				String code=request.getParameter("dropCode");
    				
    				if(code!=null && "growmodel".equals(code)) {
    					persistence.adminDropRepository();
    					AdminConsoleStatus.showInfo("Repository successfuly purged!");
    				} else {
    					AdminConsoleStatus.showError("Security code didn't matched!");
    				}
    				response.sendRedirect("console");
    				return;
    			}

    			if(ACTION_FULLTEXT_SEARCH.equals(action)) {
    				String search=request.getParameter("searchString");
    				
    				if(search!=null && search.length()>0) {
    					DescriptorBean[] beans=persistence.fulltextSearch(search, userService.getCurrentUser().getUserId());
    	    			response.setContentType("text/plain; charset=utf8");
    	    			response.setCharacterEncoding("utf8");
    	    			PrintWriter writer = response.getWriter();
    	        		if(beans!=null && beans.length>0) {
    	        			StringBuffer result=new StringBuffer("Search result:\n");
    	        			for (DescriptorBean descriptorBean : beans) {
								result.append(descriptorBean.getName());
								result.append("\n");
							}
        	    			writer.append(result.toString());
    	        		}
    	    			writer.flush();
    	    			writer.close();
    				} else {
        				response.sendRedirect("console");
    				}
    				return;
    			}

    			if(ACTION_LOGIN_ANYBODY.equals(action) || ACTION_LOGIN_WHITELIST.equals(action)) {
    				MindForgerKernel.whitelistProtectedLogin=!MindForgerKernel.whitelistProtectedLogin;
					persistence.adminSaveKernelConfig();
					AdminConsoleStatus.showInfo("Login restriction has been changed to "+(MindForgerKernel.whitelistProtectedLogin?"whitelist":"free access"));
    				response.sendRedirect("console");    				
    				return;
    			}
    			
    			if(ACTION_DISABLE.equals(action) || ACTION_ENABLE.equals(action)) {
    				MindForgerKernel.isServiceDisabled=!MindForgerKernel.isServiceDisabled;
    				if(MindForgerKernel.isServiceDisabled) {
    					MindForgerKernel.maintenanceMessage="MindForger is being improved and will be back soon ;-)";
    					MindForgerKernel.maintenanceMessageFgColor="#fff";
    					MindForgerKernel.maintenanceMessageBgColor="#B02B2C";
    					MindForgerKernel.showMaintenanceMessage=true;
    				} else {
    					MindForgerKernel.showMaintenanceMessage=false;
    				}
					AdminConsoleStatus.showInfo("MindForger has been "+(MindForgerKernel.isServiceDisabled?"disabled":"enabled"));
    				response.sendRedirect("console");
    				return;
    			}
    			
    			if(ACTION_REFRESH.equals(action)) {
					MindForgerKernel.showMaintenanceMessage=true;
					AdminConsoleStatus.showInfo("Refresh done!");
    				response.sendRedirect("console");    				
    				return;
    			}

    			if(ACTION_CLEAR_MEMCACHE.equals(action)) {
    				persistence.clearMemcache();
    				persistence.adminClearRepositoryImportMap();
    				return;
    			}
    			
    			if(ACTION_CHECK_AND_FIX.equals(action)) {
					int fixes = 0;
					//fixes+=persistence.adminFixQuestionsAnswerOrphans();
					//fixes+=persistence.adminFixAddUserKeyAndSharingToGrows();
					AdminConsoleStatus.showInfo("Repository checked and fixed "+fixes+" problem(s).");
    				response.sendRedirect("console");    				
    				return;
    			}    			
    		}
    	} catch (Exception ex) {
    		throw new ServletException(ex);
    	}        			
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService userService = UserServiceFactory.getUserService();
		if(userService.isUserAdmin()) {
    		String action = ACTION_EXPORT;
    		//String action = request.getParameter("action");

    		if(ACTION_EXPORT.equals(action)) {
    			response.setContentType("text/javascript; charset=utf8");
    			response.setCharacterEncoding("utf8");
    			PrintWriter writer = response.getWriter();
        		String json="{}";
    			writer.append(json);
    			writer.flush();
    			writer.close();
    		}        	
    	}
	}
	
	private static final long serialVersionUID = 5626985725570154058L;
}
