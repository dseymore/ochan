package org.Ochan.service.remote.jmx;

import java.util.List;

import org.Ochan.service.ExternalCategoryService;
import org.Ochan.service.remote.model.RemoteCategory;
import org.Ochan.service.remote.webservice.CategoryList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsClientProxy;
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
	
	private CategoryList categoryListClient;
	private ExternalCategoryService externalCategoryService;

	public CategoryList getCategoryListClient() {
		return categoryListClient;
	}

	public void setCategoryListClient(CategoryList categoryListClient) {
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
		
		LOG.error("Starting: " + categoryListClient);
		try{
			List<RemoteCategory> remoteCategories = categoryListClient.getCategories();
	    	for (RemoteCategory rc : remoteCategories){
	    		externalCategoryService.createCategory(rc.getName(), "", host);
	    	}
		}catch(Exception e){
			LOG.error("Unable to call service:",e);
		}finally{
			LOG.error("ending");
		}
    	

	}

	//TODO
	public void flushHosts() {

	}

}
