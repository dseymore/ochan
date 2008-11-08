package org.ochan.util;

import java.util.prefs.Preferences;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "System:type=config,name=DeploymentConfiguration", description = "Deployment management", log = true, logFile = "jmx.log")
public class DeploymentConfiguration {

	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(DeploymentConfiguration.class); 
	
	static final String DEFAULT_HOSTNAME = "localhost";
	static final String DEFAULT_PORT = "8080";
	
	
	/**
	 * @return the hostname
	 */
	@ManagedAttribute(description="The hostname the server is reachable at")
	public String getHostname() {
		return PREFERENCES.get("host", DEFAULT_HOSTNAME);
	}
	/**
	 * @param hostname the hostname to set
	 */
	@ManagedAttribute(description="The hostname the server is reachable at")
	public void setHostname(String hostname) {
		PREFERENCES.put("host", hostname);
	}
	/**
	 * @return the port
	 */
	@ManagedAttribute(description="The port the server is reachable at")
	public String getPort() {
		return PREFERENCES.get("port",DEFAULT_PORT);
	}
	
	/**
	 * @param port the port to set
	 */
	@ManagedAttribute(description="The port the server is reachable at")
	public void setPort(String port) {
		PREFERENCES.put("port", port);
	}
	
	
	
}
