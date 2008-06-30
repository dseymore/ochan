package org.Ochan.config;

import java.util.prefs.Preferences;
/**
 * Settings bean for the current host address
 * @author David Seymore 
 * Jun 29, 2008
 */
public class HostSettings {

	private static Preferences PREFERENCES = Preferences.userNodeForPackage(HostSettings.class);
	
	private static String DEFAULT_ADDRESS = "http://localhost:8080";
	
	/**
	 * @return the address
	 */
	public static String getAddress() {
		return PREFERENCES.get("address", DEFAULT_ADDRESS);
	}

	/**
	 * @param address the address to set
	 */
	public static void setAddress(String address) {
		PREFERENCES.put("address", address);
	}
	
	
	
	
	
}
