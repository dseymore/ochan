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
package org.ochan.service.remote.jmx;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.entity.ExternalCategory;
import org.ochan.service.ExternalCategoryService;
import org.ochan.service.remote.model.RemoteCategory;
import org.ochan.service.remote.webservice.CategoryList;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * This class kinda acts like the main dude for handling the inter-server link
 * configuration.
 * 
 * The idea goes like this... this box knows what channels are on it... and it
 * can also know what channels are on other boxes.
 * 
 * 
 * 
 * 
 * 
 * @author seymore
 * 
 */
@ManagedResource(description = "Exposes operations to add and remove hosts", objectName = "Ochan:utility=RemoteHostList")
public class HostList {

	private static final Log LOG = LogFactory.getLog(HostList.class);
	
	private JaxWsProxyFactoryBean categoryListClient;
	private ExternalCategoryService externalCategoryService;


	public JaxWsProxyFactoryBean getCategoryListClient() {
		return categoryListClient;
	}

	public void setCategoryListClient(JaxWsProxyFactoryBean categoryListClient) {
		this.categoryListClient = categoryListClient;
	}

	public ExternalCategoryService getExternalCategoryService() {
		return externalCategoryService;
	}

	public void setExternalCategoryService(
			ExternalCategoryService externalCategoryService) {
		this.externalCategoryService = externalCategoryService;
	}

	@ManagedOperation(description = "Scans a host for categories.")
	@ManagedOperationParameters(@ManagedOperationParameter(name = "HostName", description = "The full hostname"))
	public void addHost(String host) {
		// here we would take the host, and connect to its webservice endpoint
		// that gets the categories
		LOG.info("About to connect to host: " + host);
		
		categoryListClient.setAddress(host + "/remote/category");
		CategoryList client = (CategoryList)categoryListClient.create();
		try{
			List<RemoteCategory> remoteCategories = client.getCategories();
	    	for (RemoteCategory rc : remoteCategories){
	    		externalCategoryService.createCategory(rc.getName(), "", host);
	    	}	    	
		}catch(Exception e){
			LOG.error("Unable to call service:",e);
		}
		

	}

	@ManagedOperation(description = "Removes all external hosts listings")
	public void flushHosts() {
		List<ExternalCategory> externals = externalCategoryService.retrieveCategories(null);
		for (ExternalCategory xcat : externals){
			LOG.debug("Deleting: " + xcat);
			externalCategoryService.deleteCategory(xcat.getIdentifier());
		}
	}
	
	@ManagedAttribute(description="Returns a list of all hosts")
	@ManagedOperation(description="Returns a list of all hosts")
	public String getExternalHosts(){
		List<ExternalCategory> externals = externalCategoryService.retrieveCategories(null);
		if (externals == null){
			return "";
		}else{
			StringBuilder buf = new StringBuilder();
			for (ExternalCategory xcat : externals){
				buf.append(xcat.getHost() + ": " + xcat.getName() + "\n");
			}
			return buf.toString();
		}
		
	}

}
