/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package org.ochan.job;

import java.io.File;
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
import org.ochan.util.LoggingConfiguration;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.StringUtils;

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
		//there is a weird issue where the logging in the jar classloader stuff
		//runs under log4j level not defined by the logging configuration dude. 
		//BUT, if we run it again.. it'll fix it.. 
		new LoggingConfiguration().contextInitialized(null);
		
		ThreadService threadService = null;
		try{
			ApplicationContext appCtx = getApplicationContext(context);
			threadService = (ThreadService)appCtx.getBean("proxyThreadService");
		}catch(Exception e){
			LOG.fatal("Unable to grab the thread service.",e);
		}
		
		if (threadService != null){
			Set<String> currentlyAvailable = new HashSet<String>();
			//taking current working directory to scan
			File currentDirectory = new File(System.getProperty("user.dir"));
			//files are there
			if (currentDirectory.isDirectory() && currentDirectory.listFiles().length > 0){
				for (File f : currentDirectory.listFiles()){
					//the file is not a directory, and can be read, and ends with .jar. 
					if (!f.isDirectory() && f.isFile() && f.canRead() && StringUtils.endsWithIgnoreCase(f.getName(), "jar")){
						LOG.info("Scanning file : " + f.getName());
						try {
							//Load jar file
							JarClassLoader jcl = new JarClassLoader(); 
							jcl.add(f.getName());   
							ServiceLoader<PluginJob> pluginJobServiceLoader = ServiceLoader.load(PluginJob.class,jcl);
							//go through the plugins it has in it... yes, it can have more than 1.
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
									LOG.warn("Registring new Job: " + job.getClass().getCanonicalName());
									PluginJobBundle.INSTANCE.put(job.getClass().getCanonicalName(), job);
									//configre
									job.configure(getPluginConfiguration());
									job.setThreadService(threadService);
									//store
									job.execute();
								}
							}
						} catch (NullPointerException npe){
							LOG.info("No service loader information in here.",npe);
						} catch (Exception e) {
							LOG.error("Unable to detect any plugins!", e);
						}
					}
				}
			}
			//now lets walk through the bundle's registered list, and remove the jobs that aren't currently availble.  (jar removed)
			Set<String> keys = PluginJobBundle.INSTANCE.keySet();
			Collection<String> toRemove = CollectionUtils.subtract(keys, currentlyAvailable);
			for(String remove: toRemove){
				LOG.warn("Removing plugin: " + remove);
				PluginJobBundle.INSTANCE.remove(remove);
			}
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
