package org.ochan.job;

import java.util.Date;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.service.remote.webservice.PostListImpl;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * This class queries the PostList restful service to see how many requests its
 * served... and then does the math to estimate the number of thread watchers
 * currently operating.
 * 
 * @author David Seymore Jul 27, 2008
 */
@ManagedResource(description = "Active Thread Watcher Service", objectName = "Ochan:type=job,name=ActiveThreadWatcher", logFile = "jmx.log")
public class ActiveThreadCounterJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(ActiveThreadCounterJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(ActiveThreadCounterJob.class);

	/**
	 * the length of time between each thread watchers request
	 */
	public static long REQUEST_REST_LENGTH = 10000;
	
	private static Long lastWatcherAmount = new Long(0);
	
	//place to store the last starting point
	private static Long lastGetCount = new Long(0);
	private static Long lastGetTime = new Date().getTime();
	
	

	// staticly held because we only care about reaching a static property
	private static PostListImpl postList = new PostListImpl();

	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		LOG.info("Job running");
		Long currentCount = postList.getNextGetCount();
		long now = new Date().getTime();
		if (lastGetCount.longValue() != 0){
			//how many have there been?
			long difference = currentCount.longValue() - lastGetCount.longValue();
			long timeframe = now - lastGetTime;
			long divisor = timeframe / REQUEST_REST_LENGTH;
			if (divisor == 1){
				//if its only been the time it takes for a recycle, then its just the number of gets
				//this is HIGHLY unlikely though.. and is a shitty job schedule to be running (not to mention expensive)
				lastWatcherAmount = difference;
			}else{
				lastWatcherAmount = difference / divisor;
			}
		}
		//set for next time
		lastGetCount = currentCount;
		lastGetTime = now;
	}

	@Override
	public Preferences getPreferences() {
		return PREFERENCES;
	}

	@Override
	public String getTriggerName() {
		return "activeThreadTrigger";
	}

	/**
	 * @return the lastWatcherAmount
	 */
	@ManagedAttribute(description="Based on the time of the job, this rounds out a value of the number of thread watchers currently open.")
	public Long getLastWatcherAmount() {
		return lastWatcherAmount;
	}

	
	
}
