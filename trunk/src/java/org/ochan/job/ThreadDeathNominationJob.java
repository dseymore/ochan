package org.ochan.job;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Category;
import org.ochan.entity.Thread;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Job to manage threads growing beyond a certain cap before turning off allowing new threads to be created. 
 * @author David Seymore 
 * Mar 1, 2009
 */
@ManagedResource(description = "Mark Threads for deletion at category cap job", objectName = "Ochan:type=job,name=ThreadDeathNominationJob", logFile = "jmx.log")
public class ThreadDeathNominationJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(ThreadDeathNominationJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(ThreadDeathNominationJob.class);

	private static final int THREADS_BEFORE_NOMINATION = 10;

	private static final int THREADS_MARKED_AT_A_TIME = 1;
	
	private ThreadService threadService;
	private PostService postService;
	private CategoryService categoryService;
	
	/**
	 * @param threadService the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}
	
	/**
	 * @param postService the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * @param categoryService the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		try{
			int countBeforeNominating = Integer.valueOf(getThreadCountBeforeNominating()).intValue();
			ApplicationContext appCtx = getApplicationContext(context);
			threadService = (ThreadService)appCtx.getBean("localThreadService");
			postService = (PostService)appCtx.getBean("localPostService");
			categoryService = (CategoryService)appCtx.getBean("localCategoryService");
			
			//get the category list
			List<Category> categories = categoryService.retrieveCategories();
			for (Category cat : categories){
				//get the thread list for each category
				ThreadCriteria criteria = new ThreadService.ThreadCriteria();
				criteria.setCategory(cat.getIdentifier());
				criteria.setNotDeleted("pretty please");
				List<Thread> threads = threadService.retrieveThreads(criteria);
				//we have to get the threads because they contain the touch time.
				if (threads != null){
					for (Thread t : threads){
						t.setPosts(postService.retrieveThreadPosts(t));
					}
					//count up the number of 'non-delete-queue' threads
					if (threads != null && threads.size() > countBeforeNominating){
						//mark the the most unrecently touched ones delete worthy
						Collections.sort(threads);
						for (int i = Integer.valueOf(getNumberOfThreadsToMark()); i > 0; i--){
							//if it keeps us within the range of the max number of threads, lets kill it. 
							if (threads.size() - i > Integer.valueOf(getThreadCountBeforeNominating())){
								Thread lastThread = threads.get(threads.size() - i);
								lastThread.setDeleteDate(new Date());
								Long count = lastThread.getDeleteCount();
								if (count == null) {
									count = Long.valueOf(0);
								}
								lastThread.setDeleteCount(Long.valueOf(count.longValue() + 1));
								//update it doood!
								threadService.updateThread(lastThread);
							}
						}
					}
				}
			}
		}catch(Exception e){
			LOG.error("Things are going pretty badly...",e);
		}
	}

	/**
	 * 
	 */
	@Override
	public Preferences getPreferences() {
		return PREFERENCES;
	}

	/**
	 * 
	 */
	@Override
	public String getTriggerName() {
		return "nominateThreadTrigger";
	}

	/**
	 * @return the threadCountBeforeNominating
	 */
	@ManagedAttribute(description="The number of threads a category can get before thread deletion nomination occurs.")
	public String getThreadCountBeforeNominating() {
		return PREFERENCES.get("threadCountBeforeNominating", String.valueOf(THREADS_BEFORE_NOMINATION));
	}

	/**
	 * @param threadCountBeforeNominating the threadCountBeforeNominating to set
	 */
	@ManagedAttribute(description="The number of threads a category can get before thread deletion nomination occurs.")
	public void setThreadCountBeforeNominating(String threadCountBeforeNominating) {
		if (StringUtils.isNumeric(threadCountBeforeNominating)){
			PREFERENCES.put("threadCountBeforeNominating", threadCountBeforeNominating);
		}
	}

	/**
	 * @return the numberOfThreadsToMark
	 */
	@ManagedAttribute(description="The number of threads that can be marked each time the category is scanned.")
	public String getNumberOfThreadsToMark() {
		return PREFERENCES.get("numberOfThreadsToMark", String.valueOf(THREADS_MARKED_AT_A_TIME));
	}

	/**
	 * @param numberOfThreadsToMark the numberOfThreadsToMark to set
	 */
	@ManagedAttribute(description="The number of threads that can be marked each time the category is scanned.")
	public void setNumberOfThreadsToMark(String numberOfThreadsToMark) {
		if (StringUtils.isNumeric(numberOfThreadsToMark)){
			PREFERENCES.put("numberOfThreadsToMark", numberOfThreadsToMark);
		}
	}

	
	
}
