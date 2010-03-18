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
package org.ochan.service.proxy;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.dpl.replication.StateChangeListener;
import org.ochan.entity.Category;
import org.ochan.service.CategoryService;
import org.ochan.service.proxy.config.ShardConfiguration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 
 * @author dseymore
 * 
 */
@ManagedResource(description = "Enables you to kick off a category in a scaled instance.", objectName = "Ochan:type=proxy,name=CategoryService", logFile = "jmx.log")
public class ProxyCategoryService implements CategoryService {

	private ShardConfiguration shardConfiguration;
	private JaxWsProxyFactoryBean categoryServiceClient;
	private CategoryService localCategoryService;
	private Ehcache cache;
	private StateChangeListener stateChangeListener;

	private static final Log LOG = LogFactory.getLog(ProxyCategoryService.class);

	@ManagedOperation(description = "Create a new Category!")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "name", description = "The name of the category"), @ManagedOperationParameter(name = "description", description = "The description of the category"),
			@ManagedOperationParameter(name = "code", description = "The codeword/keyname for the category") })
	public void createCategory(String name, String description, String code) {
		createCategory(null, name, description, code);
	}

	@Override
	public void createCategory(Long thisIdentifier, String name, String description, String code) {
		if (thisIdentifier != null) {
			// hmm.. you're doing it wrong..
			LOG.fatal("No one should pass in an ID but ME!");
		}
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Long identifier = shardConfiguration.getSynchroService().getSync();
			CategoryService service = get(shardConfiguration.whichHost(identifier));
			service.createCategory(thisIdentifier, name, description, code);
		} else if (!stateChangeListener.isMaster()) {
			// replication.. we aren't the master
			LOG.debug("Sending to master node");
			CategoryService service = get(stateChangeListener.getMasterNodeName());
			service.createCategory(thisIdentifier, name, description, code);
		} else {
			localCategoryService.createCategory(null, name, description, code);
		}
	}

	@Override
	public void deleteCategory(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get(identifier);
			if (o != null || o.getObjectValue() != null) {
				cache.remove(identifier);
			}
			CategoryService service = get(shardConfiguration.whichHost(identifier));
			service.deleteCategory(identifier);
		} else if (!stateChangeListener.isMaster()) {
			// replication.. we aren't the master
			LOG.debug("Sending to master node");
			CategoryService service = get(stateChangeListener.getMasterNodeName());
			service.deleteCategory(identifier);
		} else {
			localCategoryService.deleteCategory(identifier);
		}
	}

	@Override
	public Category getCategory(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get(identifier);
			if (o != null && o.getObjectValue() != null && !o.isExpired()) {
				return (Category) o.getObjectValue();
			}
			CategoryService service = get(shardConfiguration.whichHost(identifier));
			Category cat = service.getCategory(identifier);
			cache.put(new Element(identifier, cat));
			return cat;
		} else {
			return localCategoryService.getCategory(identifier);
		}
	}

	@Override
	public Category getCategoryByCode(String code) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get(code);
			if (o != null && o.getObjectValue() != null && !o.isExpired()) {
				return (Category) o.getObjectValue();
			}
			for (String hosts : shardConfiguration.getShardHosts()) {
				CategoryService service = get(hosts);
				if (service == null) {
					LOG.error("Null Service for host: " + hosts);
				} else {
					Category cat = service.getCategoryByCode(code);
					if (cat != null) {
						cache.put(new Element(code, cat));
						return cat;
					}
				}
			}
			return null;
		} else {
			return localCategoryService.getCategoryByCode(code);
		}
	}

	@Override
	public List<Category> retrieveCategories() {
		List<Category> cats = new ArrayList<Category>();
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get("ALL");
			if (o != null && o.getObjectValue() != null && !o.isExpired()) {
				return (List<Category>) o.getObjectValue();
			}
			for (String hosts : shardConfiguration.getShardHosts()) {
				CategoryService service = get(hosts);
				if (service == null) {
					LOG.error("Null Service for host: " + hosts);
				} else {
					List<Category> catList = service.retrieveCategories();
					if (catList != null) {
						cats.addAll(catList);
					}
				}
			}
			cache.put(new Element("ALL", cats));
			return cats;
		} else {
			return localCategoryService.retrieveCategories();
		}
	}

	private synchronized CategoryService get(String host) {
		categoryServiceClient.setAddress(host + "/remote/allCategory");
		// resetting
		// categoryServiceClient.getClientFactoryBean().setClient(null);
		CategoryService client = (CategoryService) categoryServiceClient.create();
		return client;
	}

	/**
	 * @param shardConfiguration
	 *            the shardConfiguration to set
	 */
	public void setShardConfiguration(ShardConfiguration shardConfiguration) {
		this.shardConfiguration = shardConfiguration;
	}

	/**
	 * @param categoryServiceClient
	 *            the categoryServiceClient to set
	 */
	public void setCategoryServiceClient(JaxWsProxyFactoryBean categoryServiceClient) {
		this.categoryServiceClient = categoryServiceClient;
	}

	/**
	 * @param localCategoryService
	 *            the localCategoryService to set
	 */
	public void setLocalCategoryService(CategoryService localCategoryService) {
		this.localCategoryService = localCategoryService;
	}

	/**
	 * @param cache
	 *            the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	/**
	 * @param stateChangeListener
	 *            the stateChangeListener to set
	 */
	public void setStateChangeListener(StateChangeListener stateChangeListener) {
		this.stateChangeListener = stateChangeListener;
	}

	@ManagedOperation
	public void bustCache() {
		this.cache.flush();
	}

	@ManagedAttribute
	public int getCacheHitCount() {
		return this.cache.getHitCount();
	}

	@ManagedAttribute
	public int getCacheMissCountExpired() {
		return this.cache.getMissCountExpired();
	}

	@ManagedAttribute
	public int getCacheMissCountNotFound() {
		return this.cache.getMissCountNotFound();
	}

	@ManagedAttribute
	public int getCacheSize() {
		return this.cache.getSize();
	}

}
