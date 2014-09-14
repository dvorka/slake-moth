package com.mindforger.coachingnotebook.server.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.mindforger.coachingnotebook.client.ui.security.MindForgerSecurityException;
import com.mindforger.coachingnotebook.server.store.Persistence;
import com.mindforger.coachingnotebook.server.store.beans.BackupAttachmentBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupCheckListAnswerBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupFriendBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupGrowBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupLifeVisionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupPermissionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionAnswerBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupQuestionBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupUserSettingsBean;
import com.mindforger.coachingnotebook.server.store.beans.BackupWhitelistEntryBean;
import com.mindforger.coachingnotebook.server.store.gae.GaePersistence;
import com.mindforger.coachingnotebook.shared.MindForgerConstants;
import com.mindforger.coachingnotebook.shared.beans.GrowBean;

/**
 * MF URI Space:
 * 
 * http://mindforger.com/rest/
 *                         proxy/                           
 *                           charts                          GET
 *                           gravatar                        GET
 *                         user/                            *GET / JSon        get information about the user
 *                           grows/                         *GET / JSon        list of GROWs
 *                                                          *POST/ JSon        create GROW as JSon
 *                             {grow key}
 *                         repository/                       GET / JSon        admin: get whole repository in single stream
 *                           check-list-answers/             GET / JSon        admin: get RestCollectionBean
 *                                                           POST/ JSon        admin: post JSon feed - creates all entries
 *                             1                             GET / JSon        admin: get page of entries
 *                             ...
 *                           grows/
 *                           ...
 *                           question-answers/
 *                             1
 *                             ...
 */
public class RestServlet extends HttpServlet {
	private static final Logger log=Logger.getLogger(RestServlet.class.getName());

	private static final String CONTENT_TYPE_JSON = "text/javascript; charset=utf8";
	private static final String ENCODING_UTF_8 = "utf8";
	
    private UserService userService;
	private Persistence persistence;
	private Gson gson;
	private Cache cache;
		
	@SuppressWarnings("rawtypes")
	private HashMap<String, Class> repositoryPath2backupClass;
	
	enum Representations {ATOM, GRAPHVIZ, JSON};
	
	public RestServlet() {
		log.log(Level.INFO, "REST servlet instantiated");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init() {
		log.log(Level.INFO, "REST servlet initialized");

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            log.severe("Unable to initialize MemCache!");
        }
        
		this.userService = UserServiceFactory.getUserService();
		this.persistence = new GaePersistence();
		
		this.gson = new Gson();		
		
		repositoryPath2backupClass=new HashMap<String, Class>();
		repositoryPath2backupClass.put("check-list-answers", BackupCheckListAnswerBean.class);
		repositoryPath2backupClass.put("grows", BackupGrowBean.class);
		repositoryPath2backupClass.put("grow-question-answers", BackupQuestionAnswerBean.class);
		repositoryPath2backupClass.put("grow-questions", BackupQuestionBean.class);
		repositoryPath2backupClass.put("users", BackupUserBean.class);
		repositoryPath2backupClass.put("whitelist-entries", BackupWhitelistEntryBean.class);
		repositoryPath2backupClass.put("life-visions", BackupLifeVisionBean.class);
		repositoryPath2backupClass.put("friends", BackupFriendBean.class);
		repositoryPath2backupClass.put("permissions", BackupPermissionBean.class);
		repositoryPath2backupClass.put("question-comments", BackupQuestionBean.class);
		repositoryPath2backupClass.put("user-settings", BackupUserSettingsBean.class);
		repositoryPath2backupClass.put("note-attachments", BackupAttachmentBean.class);
	}
	
	@Override
	public void destroy() {
		log.log(Level.INFO, "REST servlet destroyed");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		log.log(Level.INFO, "POST "+url);
		
		if(url!=null) {
			String[] split = url.split("/");

			// http://host:port/rest/repository
			if("repository".equals(split[2])) {
				if(!userService.isUserAdmin()) {
					response.sendError(HttpURLConnection.HTTP_FORBIDDEN, "Your are not allowed to access this resource.");
				}
				
				Class clazz = repositoryPath2backupClass.get(split[3]);
				Class arrayClazz=null;
				try {
					arrayClazz = Class.forName("[L"+clazz.getName()+";"); 
					log.log(Level.INFO, " Class: "+arrayClazz.getCanonicalName());
				} catch(Exception e) {
					log.log(Level.SEVERE,e.getMessage(),e);
				}
				
				if(split[3]!=null && clazz!=null) {
					InputStreamReader inputStreamReader=null;
					try {
						inputStreamReader = new InputStreamReader(request.getInputStream(),"UTF-8");						
						Object json = gson.fromJson(inputStreamReader, arrayClazz);
						
						List beanList=new ArrayList();
						if(json!=null && Array.getLength(json)>0) {
							for (int i = 0; i < Array.getLength(json); i++) {
								beanList.add(Array.get(json, i));
							}
						}
						persistence.adminCreateTablePage(clazz, beanList);
					} catch(Exception e) {
						log.log(Level.SEVERE,e.getMessage(),e);
						response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Unable to read input!");
					} finally  {
						if(inputStreamReader!=null) {
							inputStreamReader.close();
						}
					}
					response.setStatus(HttpURLConnection.HTTP_CREATED);
					return;
				}
			}
		}
		
		response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);								
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		log.log(Level.INFO, "GET "+url);

        User user;
		
		if(url!=null) {
			String[] split = url.split("/");
			
			if(split.length>1 && "rest".equals(split[1])) {
				
				// http://host:port/rest/proxy
				if(split.length>2 && "proxy".equals(split[2])) {
					if("charts".equals(split[3])) {
						user = userService.getCurrentUser();
				        if (user != null) {
				        	if(persistence.isAccountExists(user.getUserId())) {
				        		String encode = URLEncoder.encode("|","UTF-8");
								String stringUrl=MindForgerConstants.GOOGLE_CHARTS_BASE_URL+"?"+request.getQueryString().replace("|", encode);
				        		log.log(Level.INFO, "  Proxying: "+stringUrl);
				        		
				        		response.setContentType("image/png");
				        		
				        		// use GAE URL fetch
			                    proxyUsingStream(response, stringUrl);
				                return;
				        	}
				        }
					} else {
						if(split.length>3 && "gravatar".equals(split[3])) {
							user = userService.getCurrentUser();
					        if (user != null) {
					        	if(persistence.isAccountExists(user.getUserId())) {
					        		String md5="123456789";
					        		if(split.length>4) {
					        			md5=split[4];
					        		}
									String stringUrl=MindForgerConstants.GRAVATAR_BASE_URL+md5+"?"+request.getQueryString();
					        		log.log(Level.INFO, "  Proxying: "+stringUrl);
					        		
					        		response.setContentType("image/png");
					        		
					        		// use GAE URL fetch
				                    proxyUsingStream(response, stringUrl);
					                return;
					        	}
					        }
						}						
					}
				}							

				// http://host:port/rest/user/grows/{grow ID}
				if(split.length>4 && "user".equals(split[2]) &&
						"grows".equals(split[3]) &&
						split[4]!=null) {
					try {
						String growId=split[4];
						try {
							user = persistence.authorize();
						} catch(MindForgerSecurityException e) {
	    	        		response.sendError(HttpURLConnection.HTTP_FORBIDDEN, e.getMessage());
	    	        		return;
						}

						GrowBean growBean = persistence.getGrow(growId,user.getUserId());

						String json = gson.toJson(growBean);
						response.setContentType(CONTENT_TYPE_JSON);

						response.setCharacterEncoding(ENCODING_UTF_8);
						PrintWriter writer = response.getWriter();
						writer.append(json);
						writer.flush();
						writer.close();
						response.setStatus(HttpURLConnection.HTTP_OK);						
					} catch(MindForgerSecurityException e) {
    	        		response.sendError(HttpURLConnection.HTTP_FORBIDDEN, "Your are not allowed to access resources of other users.");
    	        		return;
					}
					return;
			    }
			}
			response.sendError(HttpURLConnection.HTTP_NOT_FOUND, "Resource not found!");
		}		
	}

	private void proxyUsingStream(HttpServletResponse response, String stringUrl)
			throws MalformedURLException, IOException {
		
		// check cache first
		byte[] cacheValue;
		BufferedInputStream in=null;
		BufferedOutputStream out=null;
		
		if(cache!=null && (cacheValue=(byte[])cache.get(stringUrl))!=null) {
			log.log(Level.INFO, "  Loading chart from memcache...");
			try {
				out = new BufferedOutputStream(response.getOutputStream());									
			    out.write(cacheValue, 0, cacheValue.length);								
			} finally {
				if(out!=null) {
					out.flush();
					out.close();
				}
			}
		} else {
			try {
				// limit input length - avoid proxying large objects
				final int LIMIT=65536;
				byte[] buffer = new byte[LIMIT];
			    URL proxiedUrl = new URL(stringUrl);
			    
			    // load image from charts.google.com
				in = new BufferedInputStream(proxiedUrl.openStream());
				out = new BufferedOutputStream(response.getOutputStream());									
				int read=0;
				int offset=0;
				int total=0;
				int available=0;
				if((available=in.available())>0) {
					if((available+total)>LIMIT) {
						response.sendError(HttpURLConnection.HTTP_NOT_FOUND, "Resource too big for proxy > "+LIMIT);
						return;
					}
					read = in.read(buffer,offset,available);
					log.log(Level.INFO, "  >> offset/read: "+offset+"/"+read);
					total+=read;
					offset+=read;
				}
				// write image as response
				out.write(buffer, 0, total);
				
				// and store it also to MEMCACHE
				cacheValue=Arrays.copyOf(buffer, read);
				if(cache!=null) {
					log.log(Level.INFO, "  Memcaching image...");
					cache.put(stringUrl, cacheValue);
				}
			} finally {
				if(in!=null) {
					in.close();
				}
				if(out!=null) {
					out.flush();
					out.close();
				}
			}			
		}		
	}
	
	private static final long serialVersionUID = 7687718114239426764L;
}
