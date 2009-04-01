package org.ochan.job;

import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.je.CheckpointConfig;

/**
 * This class runs the filesystem sync & log checkpoint process of the berkeleydb database. 
 * @author David Seymore 
 * Mar 29, 2009
 */
@ManagedResource(description = "Sleepycat Persistence Checkpoint Background Job", objectName = "Ochan:type=job,name=CheckpointJob", logFile = "jmx.log")
public class SleepyCheckpointerJob extends ManagedQuartzJobBean {

	private static final Log LOG = LogFactory.getLog(SleepyCheckpointerJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(SleepyCheckpointerJob.class);
	
	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		try{
			ApplicationContext appCtx = getApplicationContext(context);
			CheckpointConfig checkpointConfig = new CheckpointConfig();
			SleepyEnvironment environment = (SleepyEnvironment)appCtx.getBean("sleepy");
			//checkpoint every 500Kb
			checkpointConfig.setKBytes(500);
			//lets run our cleaner first
			LOG.debug("Running the cleaner.");
			environment.getEnvironment().cleanLog();
			LOG.debug("Running the checkpoint.");
			environment.getEnvironment().checkpoint(checkpointConfig);
			//and lets sync it.. so that we aren't filling up memory forever.
			LOG.debug("Running the sync command.");
			environment.getEnvironment().sync();
		}catch(Exception e){
			LOG.fatal("Unable to get spring context for services.");
		}
	}

	@Override
	public Preferences getPreferences() {
		return PREFERENCES;
	}

	@Override
	public String getTriggerName() {
		return "checkpointTrigger";
	}
}
