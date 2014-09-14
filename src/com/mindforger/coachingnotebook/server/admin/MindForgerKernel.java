package com.mindforger.coachingnotebook.server.admin;

import com.mindforger.coachingnotebook.server.i10n.Messages;
import com.mindforger.coachingnotebook.server.store.Persistence;
import com.mindforger.coachingnotebook.server.store.gae.GaePersistence;

/**
 * MF kernel holds the service status and is used to synchronize admin console, service and persistence.
 */
public class MindForgerKernel {

	// login restriction: anybody OR whitelist members
	public static boolean whitelistProtectedLogin=true;
	
	// service disabled for maintenance
	public static boolean isServiceDisabled=false; 
	
	// maintenance
	public static boolean showMaintenanceMessage=false;
	public static String maintenanceMessage=Messages.getString("kernel.noScheduledMaintenance");
	public static String maintenanceMessageFgColor="#fff";
	public static String maintenanceMessageBgColor="#B02B2C";

	private static final Persistence persistence;
	static {
		persistence=new GaePersistence();
	}

	public static final Persistence getPersistence() {
		return persistence;
	}
	
}
