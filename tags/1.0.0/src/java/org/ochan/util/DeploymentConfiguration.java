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

import java.util.prefs.Preferences;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "System:type=config,name=DeploymentConfiguration", description = "Deployment management", log = true, logFile = "jmx.log")
public class DeploymentConfiguration {

	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(DeploymentConfiguration.class);

	static final String DEFAULT_HOSTNAME = "localhost";
	static final String DEFAULT_PORT = "8080";
	static final String DEFAULT_TITLE = "Ochan (http://code.google.com/p/ochan)";
	static final String POST_LIMIT = "-1";
	static final String THREAD_LIMIT = "-1";
	static final String UNDER_BUMP = "2";
	static final String DO_PINGBACK = "true";

	/**
	 * @return the hostname
	 */
	@ManagedAttribute(description = "The hostname the server is reachable at")
	public String getHostname() {
		return PREFERENCES.get("host", DEFAULT_HOSTNAME);
	}

	/**
	 * @param hostname
	 *            the hostname to set
	 */
	@ManagedAttribute(description = "The hostname the server is reachable at")
	public void setHostname(String hostname) {
		PREFERENCES.put("host", hostname);
	}

	/**
	 * @return the port
	 */
	@ManagedAttribute(description = "The port the server is reachable at")
	public String getPort() {
		return PREFERENCES.get("port", DEFAULT_PORT);
	}

	/**
	 * @param port
	 *            the port to set
	 */
	@ManagedAttribute(description = "The port the server is reachable at")
	public void setPort(String port) {
		PREFERENCES.put("port", port);
	}

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The title of the application deployment")
	public String getTitle() {
		return PREFERENCES.get("title", DEFAULT_TITLE);
	}

	/**
	 * 
	 * @param title
	 */
	@ManagedAttribute(description = "The title of the application deployment")
	public void setTitle(String title) {
		PREFERENCES.put("title", title);
	}

	/**
	 * Helper method for all the places that will invariable call this
	 * statically.
	 * 
	 * @return
	 */
	public static String getSystemTitle() {
		DeploymentConfiguration deploymentConfiguration = new DeploymentConfiguration();
		return deploymentConfiguration.getTitle();
	}

	/**
	 * @return the postLimit
	 */
	@ManagedAttribute(description = "The maximum number of posts in a thread before disabling posting")
	public String getPostLimit() {
		return PREFERENCES.get("postLimit", POST_LIMIT);
	}

	/**
	 * @param postLimit
	 *            the postLimit to set
	 */
	@ManagedAttribute(description = "The maximum number of posts in a thread before disabling posting")
	public void setPostLimit(String postLimit) {
		Long.valueOf(postLimit);
		PREFERENCES.put("postLimit", postLimit);
	}

	/**
	 * @return the threadLimit
	 */
	@ManagedAttribute(description = "The maximum number of threads in a category before disabling posting")
	public String getThreadLimit() {
		return PREFERENCES.get("threadLimit", THREAD_LIMIT);
	}

	@ManagedAttribute(description = "The number of posts to show under the thread in the category view after the starting post..")
	public void setUnderBumpCount(String postCount) {
		Long.valueOf(postCount);
		PREFERENCES.put("underbump", postCount);
	}

	@ManagedAttribute(description = "The number of posts to show under the thread in the category view after the starting post..")
	public String getUnderBumpCount() {
		return PREFERENCES.get("underbump", UNDER_BUMP);
	}

	public Long getUnderBumpCountLong() {
		return Long.valueOf(this.getUnderBumpCount());
	}

	/**
	 * @param threadLimit
	 *            the threadLimit to set
	 */
	@ManagedAttribute(description = "The maximum number of threads in a category before disabling posting")
	public void setThreadLimit(String threadLimit) {
		Long.valueOf(threadLimit);
		PREFERENCES.put("threadLimit", threadLimit);
	}

	/**
	 * @return the performPingBackTracking
	 */
	@ManagedAttribute(description = "true or false, pings google analytics to track the number of deployments. ")
	public String getPerformPingBackTracking() {
		return PREFERENCES.get("performPingBackTracking", DO_PINGBACK);
	}

	/**
	 * @param performPingBackTracking
	 *            the performPingBackTracking to set
	 */
	@ManagedAttribute(description = "true or false, pings google analytics to track the number of deployments. ")
	public void setPerformPingBackTracking(String performPingBackTracking) {
		PREFERENCES.put("performPingBackTracking", performPingBackTracking);
	}

	/**
	 * Helper method to test whether to enforce post limit
	 * 
	 * @param count
	 * @return
	 */
	public static boolean enforcePostLimit(int count) {
		DeploymentConfiguration dc = new DeploymentConfiguration();
		if (!"-1".equals(dc.getPostLimit()) && Long.valueOf(dc.getPostLimit()).longValue() <= count) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to test whether to enforce thread limit
	 * 
	 * @param count
	 * @return
	 */
	public static boolean enforceThreadLimit(int count) {
		DeploymentConfiguration dc = new DeploymentConfiguration();
		if (!"-1".equals(dc.getThreadLimit()) && Long.valueOf(dc.getThreadLimit()).longValue() <= count) {
			return true;
		}
		return false;
	}

}
