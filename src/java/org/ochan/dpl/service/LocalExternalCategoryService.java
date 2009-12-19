package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.ExternalCategoryDPL;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.dpl.replication.TransactionTemplate;
import org.ochan.entity.ExternalCategory;
import org.ochan.service.ExternalCategoryService;

import com.sleepycat.persist.EntityCursor;

public class LocalExternalCategoryService implements ExternalCategoryService {

	private static final Log LOG = LogFactory.getLog(LocalExternalCategoryService.class);
	private SleepyEnvironment environment;
		
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(SleepyEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public void createCategory(String name, String description, String host) {
		try{
			final ExternalCategoryDPL dpl = new ExternalCategoryDPL();
			dpl.setHost(host);
			dpl.setLongDescription(description);
			dpl.setName(name);
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.externalCategoryByIdentifier.put(dpl);
				}
			}.run();
		}catch(Exception e){
			LOG.error("Unable to create category",e);
		}
	}

	@Override
	public void deleteCategory(final Long identifier) {
		try{
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.externalCategoryByIdentifier.delete(identifier);
				}
			}.run();
		}catch(Exception e){
			LOG.error("Unable to delete category",e);
		}
	}

	@Override
	public ExternalCategory getCategory(Long identifier) {
		try{
			return map(environment.externalCategoryByIdentifier.get(identifier));
		}catch(Exception e){
			LOG.error("Unable to get category",e);
		}
		return null;
	}

	@Override
	public List<ExternalCategory> retrieveCategories(Map<ExternalCategoryCriteria, String> criteria) {
		List<ExternalCategory> cats = new ArrayList<ExternalCategory>();
		try{
			EntityCursor<ExternalCategoryDPL> dpls = environment.externalCategoryByIdentifier.entities();
			for (ExternalCategoryDPL dpl : dpls){
				cats.add(map(dpl));
			}
			dpls.close();
		}catch(Exception e){
			LOG.error("Unable to get categories",e);
		}
		return cats;
	}

	private ExternalCategory map(ExternalCategoryDPL dpl){
		ExternalCategory cat = new ExternalCategory();
		cat.setHost(dpl.getHost());
		cat.setIdentifier(dpl.getIdentifier());
		cat.setLongDescription(dpl.getLongDescription());
		cat.setName(dpl.getName());
		return cat;
	}
}
