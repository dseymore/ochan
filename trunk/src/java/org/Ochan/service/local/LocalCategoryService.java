package org.Ochan.service.local;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.management.Notification;

import org.Ochan.dao.CategoryDAO;
import org.Ochan.entity.Category;
import org.Ochan.service.CategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

@ManagedResource(description = "Local Category Service", objectName = "Ochan:service=local,name=LocalCategoryService", logFile = "jmx.log")
public class LocalCategoryService implements CategoryService, NotificationPublisherAware {

	private static final Log LOG = LogFactory.getLog(LocalCategoryService.class);

	private NotificationPublisher publisher;
	
	private CategoryDAO categoryDAO;

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
	
	public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.publisher = notificationPublisher;
    }

	/**
	 * @return the categoryDAO
	 */
	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	/**
	 * @param categoryDAO
	 *            the categoryDAO to set
	 */
	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public void createCategory(String name, String description) {
		createCount++;
		publisher.sendNotification(new Notification("Create",this,createCount, "Creating a new category named: " + name));
		if (LOG.isDebugEnabled()) {
			LOG.debug("about to create category with name:" + name + " and description: " + description);
		}
		Category cat = new Category();
		cat.setLongDescription(description);
		cat.setName(name);
		categoryDAO.saveCategory(cat);

	}

	public void deleteCategory(Long identifier) {
		// TODO Auto-generated method stub
		deleteCount++;
	}

	public Category getCategory(Long identifier) {
		getCount++;
		if (LOG.isDebugEnabled()) {
			LOG.debug("about to retrieve category: " + identifier);
		}
		return categoryDAO.getCategory(identifier);
	}

	public List<Category> retrieveCategories(Map<CategoryCriteria, String> criteria) {
		// capture start of call
		long start = new Date().getTime();

		if (criteria == null || criteria.isEmpty()) {
			return categoryDAO.getAllCategories();
		}
		
		List<Category> cats = categoryDAO.retrieveCategories(criteria);
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;

		return cats;
	}
	
	

}
