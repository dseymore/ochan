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
package org.ochan.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 
 * @author David Seymore Jul 27, 2008
 */
@ManagedResource(objectName = "System:type=statistics,name=UserCounter", description = "Keeps tabs on users on this host", log = true, logFile = "jmx.log")
public class UserCounter implements HttpSessionListener {

	private static int sessions = 0;

	/**
	 * 
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		sessions++;
	}

	/**
	 * 
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if (sessions > 0) {
			sessions--;
		}
	}

	/**
	 * The number of sessions on the server may not be the actual number of
	 * current users.. session generally have a keep-alive time... so.. this is
	 * just a rough guess.
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "retrieves the current number of sessions the server is holding on to.")
	public int getSessionCount() {
		return sessions;
	}
}
