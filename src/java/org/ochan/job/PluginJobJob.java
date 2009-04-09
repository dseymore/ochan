package org.ochan.job;

import java.util.Collection;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.prefs.Preferences;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.ochan.api.PluginJob;
import org.ochan.service.ThreadService;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import xeus.jcl.JarClassLoader;

/**
 * 
 * This job handles bootstrapping plugins (jar files) that reside in the current working directory. 
 * Configuration is via json...
 * 
 * @author David Seymore Apr 5, 2009
 */
@ManagedResource(description="The job that runs the Job based Plugins.", objectName = "Ochan:type=job,name=PluginJob", logFile = "jmx.log")
public class PluginJobJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(PluginJobJob.class);
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(PluginJobJob.class);
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		Set<String> currentlyAvailable = new HashSet<String>();
		try {
			//step one is to list all the jars, and see what classes they have. 
			JarClassLoader jcl = new JarClassLoader();
			//FIXME - this shouldn't be hardcoded.. we need this to be configurable. 
			jcl.add("ochan-rss.jar"); //Load jar file  
			ServiceLoader<PluginJob> pluginJobServiceLoader = ServiceLoader.load(PluginJob.class,jcl);
			ApplicationContext appCtx = getApplicationContext(context);
			ThreadService threadService = (ThreadService)appCtx.getBean("localThreadService");
			
			
			for(PluginJob job : pluginJobServiceLoader){
				//jot down that we found this plugin still
				currentlyAvailable.add(job.getClass().getCanonicalName());
				LOG.info("Found job: " + job.getClass().getCanonicalName());
				if (PluginJobBundle.INSTANCE.containsKey(job.getClass().getCanonicalName())){
					//we already have one, lets run that.
					PluginJob storedJob = PluginJobBundle.INSTANCE.get(job.getClass().getName());
					//reset the config
					storedJob.configure(getPluginConfiguration());
					storedJob.execute();
				}else{
					PluginJobBundle.INSTANCE.put(job.getClass().getCanonicalName(), job);
					//configre
					job.configure(getPluginConfiguration());
					job.setThreadService(threadService);
					//store
					job.execute();
				}
			}
		} catch (Exception e) {
			LOG.error("Unable to detect any plugins!", e);
		}
		//now lets walk through the bundle's registered list, and remove the jobs that aren't currently availble.  (jar removed)
		Set<String> keys = PluginJobBundle.INSTANCE.keySet();
		Collection<String> toRemove = CollectionUtils.subtract(keys, currentlyAvailable);
		for(String remove: toRemove){
			LOG.info("Removing reference to plugin: " + remove);
			PluginJobBundle.INSTANCE.remove(remove);
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

	@ManagedAttribute(description="Plugin Configuration is a json string")
	public String getPluginConfiguration(){
		return PREFERENCES.get("pluginconfig","");
	}
	
	@ManagedAttribute(description="Plugin Configuration is a json string")
	public void setPluginConfiguration(String value){
		try{
			JSONObject obj = new JSONObject(value);
			AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);
			PREFERENCES.put("pluginconfig",value);
		}catch(Exception e){
			LOG.error("Configuration settings are messed up.",e);
		}
	}
	
}
