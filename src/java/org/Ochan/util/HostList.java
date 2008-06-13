package org.Ochan.util;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;



/**
 * This class kinda acts like the main dude for handling the inter-server link configuration.
 * 
 * The idea goes like this... this box knows what channels are on it... and it can also know what channels are on other boxes.
 *
 *  
 * 
 * 
 * 
 * @author seymore
 *
 */
@ManagedResource(description="Exposes operations to add and remove hosts",objectName="Ochan:utility=RemoteHostList")
public class HostList {
	
	@ManagedOperation(description="Scans a host for categories.")
	@ManagedOperationParameters(
			@ManagedOperationParameter(name="HostName",description="The full hostname")
	)
	public void addHost(String host){
		//here we would take the host, and connect to its webservice endpoint that gets the categories
		
	}
		
	
	public void flushHosts(){
		
	}
	
}
