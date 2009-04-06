package org.ochan.job;

import java.util.ServiceLoader;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.api.PluginJob;
import org.ochan.service.ThreadService;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @author David Seymore Apr 5, 2009
 */
public class PluginJobJob extends ManagedQuartzJobBean implements StatefulJob{

	private static final Log LOG = LogFactory.getLog(PluginJobJob.class);
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(PluginJobJob.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		ServiceLoader<PluginJob> pluginJobServiceLoader = ServiceLoader.load(PluginJob.class);
		try {
			ApplicationContext appCtx = getApplicationContext(context);
			ThreadService threadService = (ThreadService)appCtx.getBean("localThreadService");
			
			for(PluginJob job : pluginJobServiceLoader){
				LOG.info("Found job: " + job.getClass().getCanonicalName());
				job.setThreadService(threadService);
				job.execute();
			}
		} catch (Exception e) {
			LOG.error("Unable to detect any plugins!", e);
		}
	}

	@Override
	public Preferences getPreferences() {
		return PREFERENCES;
	}

	@Override
	public String getTriggerName() {
		return "PluginJobJobTrigger";
	}

}
