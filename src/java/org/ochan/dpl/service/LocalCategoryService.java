package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.CategoryDPL;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.replication.TransactionTemplate;
import org.ochan.entity.Category;
import org.ochan.service.CategoryService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.persist.EntityCursor;

@ManagedResource(description = "Local Category Service", objectName = "Ochan:service=local,name=LocalCategoryService", logFile = "jmx.log")
public class LocalCategoryService implements CategoryService {

	private OchanEnvironment environment;
	private static final Log LOG = LogFactory.getLog(LocalCategoryService.class);
	
	// STATS
    private static long createCount = 0;

    private static long getCount = 0;

    private static long deleteCount = 0;

    private static long lastSearchTime = 0;

    /**
     * @return the createCount
     */
    @ManagedAttribute(description = "The number of calls to create a category")
    public long getCreateCount() {
            return createCount;
    }

    /**
     * @return the getCount
     */
    @ManagedAttribute(description = "The number of calls to get a category.")
    public long getGetCount() {
            return getCount;
    }

    /**
     * @return the deleteCount
     */
    @ManagedAttribute(description = "The number of calls to delete a category.")
    public long getDeleteCount() {
            return deleteCount;
    }

    /**
     * @return the lastSearchTime
     */
    @ManagedAttribute(description = "The time in milliseconds of the last call to search for categories.")
    public long getLastSearchTime() {
            return lastSearchTime;
    }

    // END STATS

    
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(OchanEnvironment environment) {
		this.environment = environment;
	}
	
	@ManagedOperation(description="Create a new Category!")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name="name",description="The name of the category"),
            @ManagedOperationParameter(name="description",description="The description of the category"),
            @ManagedOperationParameter(name="code", description="The codeword/keyname for the category")
    })
    public void createCategory(String name, String description, String code){
		createCategory(null, name, description, code);
	}
    
	@Override
	public void createCategory(Long thisIdentifier, String name, String description, String code) {
		createCount++;
		try {
			final CategoryDPL cat = new CategoryDPL();
			cat.setIdentifier(thisIdentifier);
			cat.setName(name);
			cat.setDescription(description);
			cat.setCode(code);
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.categoryByIdentifier().put(cat);
				}
			}.run();
		} catch (Exception e) {
			LOG.error("Unable to persist category", e);
		}
	}

	@ManagedOperation(description="Delete a Category!")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name="identifier",description="The id of the category as a Long object (L at the end)")
    })
	@Override
	public void deleteCategory(final Long identifier) {
		deleteCount++;
		try{
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.categoryByIdentifier().delete(identifier);
				}
			}.run();
		}catch(Exception e){
			LOG.error("Unable to delete category.",e);
		}
	}

	@Override
	public Category getCategory(Long identifier) {
		getCount++;
		try{
			CategoryDPL catdpl = environment.categoryByIdentifier().get(identifier);
			return map(catdpl);
		}catch(Exception e){
			LOG.error("get failed",e);
		}
		return null;
	}
	
	public Category getCategoryByCode(String code){
		getCount++;
		try{
			CategoryDPL catdpl = environment.categoryByCode().get(code);
			return map(catdpl);
		}catch(Exception e){
			LOG.error("get failed",e);
		}
		return null;
	}

	@Override
	public List<Category> retrieveCategories() {
		// capture start of call
        long start = new Date().getTime();

		List<Category> categories = new ArrayList<Category>();
		try{
			EntityCursor<CategoryDPL> cursor = environment.categoryByIdentifier().entities();
			for (CategoryDPL cat: cursor){
				categories.add(map(cat));
			}
			cursor.close();
		}catch(Exception e){
			LOG.error("Unable to get categories",e);
		}
        // capture end of call
        long end = new Date().getTime();
        // compute total time
        lastSearchTime = end - start;
		return categories;
	}
	
	public Category map(CategoryDPL catdpl){
		Category cat = new Category();
		cat.setIdentifier(catdpl.getIdentifier());
		cat.setLongDescription(catdpl.getDescription());
		cat.setName(catdpl.getName());
		cat.setCode(catdpl.getCode());
		return cat;
	}

}
