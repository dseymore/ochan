package org.ochan.service.local;

import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.service.AnnouncementService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Service to interface with the announcement
 * @author David Seymore 
 * Nov 18, 2008
 */
@ManagedResource(description = "Local Announcement Service", objectName = "Ochan:service=local,name=LocalAnnouncementService", logFile = "jmx.log")
public class LocalAnnouncementService implements AnnouncementService {

	private static final Log LOG = LogFactory.getLog(LocalAnnouncementService.class);
	
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(LocalAnnouncementService.class);
	
	private static final String DEFAULT_ANNOUNCEMENT = "<h1>Welcome to Ochan!</h1>";
	
	@ManagedAttribute(description="Retrieve the current announcement.")
	@Override
	public String getAnnouncement() {
		return PREFERENCES.get("announcement", DEFAULT_ANNOUNCEMENT);
	}
	
	@ManagedAttribute(description="Retrieve the current announcement.", persistPolicy = "OnUpdate")
	@Override
	public void setAnnouncement(String announcement) {
		PREFERENCES.put("announcement", announcement);
	}

}
