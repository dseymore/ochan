package org.Ochan.service.remote.jmx;

import java.util.List;

import org.Ochan.entity.ExternalCategory;
import org.Ochan.service.ExternalCategoryService;
import org.Ochan.service.remote.model.RemoteCategory;
import org.Ochan.service.remote.webservice.CategoryList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
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
			StringBuffer buf = new StringBuffer();
			for (ExternalCategory xcat : externals){
				buf.append(xcat.getHost() + ": " + xcat.getName() + "\n");
			}
			return buf.toString();
		}
		
	}

}
