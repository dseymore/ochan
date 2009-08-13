package org.ochan.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Delete Thread Background Job", objectName = "Ochan:type=job,name=DeleteThreadJob", logFile = "jmx.log")
public class DeleteThreadJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(DeleteThreadJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(DeleteThreadJob.class);
	
	/**
	 * Default # of times a thread can be marked deleted & undeleted before it locks.
	 */
	private static final Long NUMBER_OF_DELETE_ATTEMPTS_BEFORE_LOCK = new Long(3);
	/**
	 * Default delete expiry time of 5 minutes
	 */
	private static final Long TIME_BEFORE_DELETE = Long.valueOf(300000);
	
	private ThreadService threadService;
	private PostService postService;
	
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
	 * 
	 */
	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		try{
			ApplicationContext appCtx = getApplicationContext(context);
			threadService = (ThreadService)appCtx.getBean("localThreadService");
			postService = (PostService)appCtx.getBean("localPostService");
		
			ThreadCriteria criteria = new ThreadService.ThreadCriteria();
			//just give it a value as a marker for the search
			criteria.setDeleteQueue("yes please");
			List<Thread> deleteables = threadService.retrieveThreads(criteria);
			Long now = new Date().getTime();
			if (deleteables != null){
				for (Thread t: deleteables){
					if (t.getDeleteDate() != null 
							&& t.getDeleteDate().getTime() + Long.valueOf(getDeleteWaitTime()).longValue() < now 
							&& !DeleteThreadJob.isDeleteLocked(t.getDeleteCount())){
						//if it has a delete date, and IT + the window to undo are less than now, its expired.. time to delete.
						//AND it isnt locked
						LOG.info("Deleting thread: " +  t.getIdentifier());
						List<Post> posts = postService.retrieveThreadPosts(t);
						for (Post p: posts){
							postService.deletePost(p.getIdentifier());
						}
						threadService.deleteThread(t.getIdentifier());
					}
				}
			}
		}catch(Exception e){
			LOG.fatal("Unable to get spring context for services.");
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
		return "deleteThreadTrigger";
	}
	

	/**
	 * @return the deleteWaitTime
	 */
	@ManagedAttribute(description="The time in milliseconds to wait after a thread is marked for deletion before doing the actual deletion")
	public String getDeleteWaitTime() {
		return PREFERENCES.get("deleteWaitTime", String.valueOf(TIME_BEFORE_DELETE));
	}

	/**
	 * @param deleteWaitTime the deleteWaitTime to set
	 */
	@ManagedAttribute(description="The time in milliseconds to wait after a thread is marked for deletion before doing the actual deletion", persistPolicy = "OnUpdate")
	public void setDeleteWaitTime(String deleteWaitTime) {
		try{
			Long.valueOf(deleteWaitTime);
			PREFERENCES.put("deleteWaitTime", deleteWaitTime);
		}catch(NumberFormatException nfe){
			LOG.error("Unable to assign wait time with non-numeric value:" + deleteWaitTime, nfe);
		}
	}

	/**
	 * @return the deleteMarksBeforeLock
	 */
	@ManagedAttribute(description="The number of times a thread can be marked deleted, and then marked opposite, before being locked.")
	public String getDeleteMarksBeforeLock() {
		return PREFERENCES.get("deleteMarksBeforeLock", String.valueOf(NUMBER_OF_DELETE_ATTEMPTS_BEFORE_LOCK));
	}

	/**
	 * @param deleteMarksBeforeLock the deleteMarksBeforeLock to set
	 */
	@ManagedAttribute(description="The number of times a thread can be marked deleted, and then marked opposite, before being locked.", persistPolicy = "OnUpdate")
	public void setDeleteMarksBeforeLock(String deleteMarksBeforeLock) {
		try{
			Long.valueOf(deleteMarksBeforeLock);
			PREFERENCES.put("deleteMarksBeforeLock", deleteMarksBeforeLock);
		}catch(NumberFormatException nfe){
			LOG.error("Unable to assign lock count with non-numeric value:" + deleteMarksBeforeLock, nfe);
		}
	}
	
	/**
	 * Given the current lock count of a thread, confirms or denies its lock status.
	 * @param currentCount
	 * @return
	 */
	public static boolean isDeleteLocked(Long currentCount){
		DeleteThreadJob dtj = new DeleteThreadJob();
		Long lockThreshold = Long.valueOf(dtj.getDeleteMarksBeforeLock());
		if (currentCount == null || lockThreshold == null){
			return false;
		}
		return currentCount.longValue() > lockThreshold.longValue();
	}
		
}
