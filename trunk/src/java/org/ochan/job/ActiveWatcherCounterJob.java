package org.ochan.job;

import java.util.Date;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.service.remote.webservice.PostListImpl;
import org.ochan.service.remote.webservice.ThreadSupportImpl;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * This class queries the PostList & ThreadSupport restful services to see how many requests its
 * served... and then does the math to estimate the number of thread watchers
 * currently operating.
 * 
 * @author David Seymore Jul 27, 2008
 */
@ManagedResource(description = "Active Watcher Counter Service", objectName = "Ochan:type=job,name=ActiveWatcherCounter", logFile = "jmx.log")
public class ActiveWatcherCounterJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(ActiveWatcherCounterJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(ActiveWatcherCounterJob.class);

	/**
	 * the length of time between each thread watchers request
	 */
	public static long REQUEST_REST_LENGTH = 10000;
	
	private static Long lastWatcherAmount = new Long(0);
	private static Long lastSitterAmount = new Long(0);
	
	//place to store the last starting point
	private static Long lastThreadGetCount = new Long(0);
	private static Long lastMainGetCount = new Long(0);
	private static Long lastGetTime = new Date().getTime();
	
	

	// staticly held because we only care about reaching a static property
	private static PostListImpl postList = new PostListImpl();
	private static ThreadSupportImpl threadList = new ThreadSupportImpl();

	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		LOG.info("Job running");
		Long currentThreadCount = postList.getNextGetCount();
		Long currentMainCount = threadList.getNextGetCount();
		long now = new Date().getTime();
		if (lastThreadGetCount.longValue() != 0){
			//how many have there been?
			long differenceThread = currentThreadCount.longValue() - lastThreadGetCount.longValue();
			long differenceMain = currentMainCount.longValue() - lastMainGetCount.longValue();
			long timeframe = now - lastGetTime;
			long divisor = timeframe / REQUEST_REST_LENGTH;
			if (divisor == 1){
				//if its only been the time it takes for a recycle, then its just the number of gets
				//this is HIGHLY unlikely though.. and is a shitty job schedule to be running (not to mention expensive)
				lastWatcherAmount = differenceThread;
				lastSitterAmount = differenceMain;
			}else{
				lastWatcherAmount = differenceThread / divisor;
				lastSitterAmount = differenceMain / divisor;
			}
		}
		//set for next time
		lastThreadGetCount = currentThreadCount;
		lastMainGetCount = currentMainCount;
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
	public Long getLastThreadWatcherAmount() {
		return lastWatcherAmount;
	}
	
	@ManagedAttribute(description="Based on the time of the job, this rounds out a value of the number of Main Page watchers currently open.")
	public Long getLastMainPageWatcherAmount(){
		return lastSitterAmount;
	}

	
	
}
