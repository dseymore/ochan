/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
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
