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
package org.ochan.util;

import java.util.Date;
import java.util.prefs.Preferences;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Specialized Quartz Job Bean that allows JMX exposure of cron expression and
 * rescheduling of job.
 * 
 * @author dseymore
 */
public abstract class ManagedQuartzJobBean extends QuartzJobBean {

	private static Log LOG = LogFactory.getLog(ManagedQuartzJobBean.class);

	/**
	 * Spring uses this name to build the group of triggers.
	 */
	private static final String SPRING_QUARTZ_GROUP_NAME = "DEFAULT";
	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";
	private static Date lastRunTime;

	// Final so that noone can extend
	@Override
	protected final void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// We should check if we need to be rescheduled due to a saved
		// configuration
		if (getCurrentCronSetting() != null && !getCurrentCronSetting().equals(getCron())) {
			LOG.info("Rescheduleing " + getTriggerName() + "cron job to be the preferences stored value.");
			setCron(getCron());
		} else {
			lastRunTime = new Date();
			// Now call the specific implementation
			executeOnSchedule(context);
		}
	}

	public abstract String getTriggerName();

	public abstract Preferences getPreferences();

	public abstract void executeOnSchedule(JobExecutionContext context);

	/**
	 * Helper method to grab the SCHEDULERs impression of what the cron
	 * expression is, which, upon container startup is filled in by Spring's
	 * application context configuraiton
	 * 
	 * @return
	 */
	public String getCurrentCronSetting() {
		LOG.trace("Getting current cront setting.");
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			CronTrigger t = (CronTrigger) schedulerFactory.getScheduler().getTrigger(getTriggerName(), SPRING_QUARTZ_GROUP_NAME);
			return t.getCronExpression();
		} catch (Exception e) {
			LOG.error("Unable to get current cron trigger", e);
		}
		LOG.fatal("NULL CRON EXPRESSION!");
		return null;
	}

	/**
	 * @return Either the preferences set cron expression or the current spring
	 *         set one if not set.
	 */
	@ManagedAttribute(description = "Cron expression for firing job")
	public String getCron() {
		LOG.trace("Getting cron setting from preferences or current settings.");
		return getPreferences().get(getTriggerName() + "cronTab", getCurrentCronSetting());
	}

	/**
	 * Sets the preferences stored cron expression, and reschedules the job.
	 * 
	 * @param cloCron
	 */
	@ManagedAttribute(description = "Cron expression for firing job", persistPolicy = "OnUpdate")
	public void setCron(String cloCron) {
		LOG.info("Setting cron expression");
		getPreferences().put(getTriggerName() + "cronTab", cloCron);
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			CronTrigger t = (CronTrigger) schedulerFactory.getScheduler().getTrigger(getTriggerName(), SPRING_QUARTZ_GROUP_NAME);
			t.setCronExpression(getCron());
			schedulerFactory.getScheduler().rescheduleJob(getTriggerName(), SPRING_QUARTZ_GROUP_NAME, t);
		} catch (Exception e) {
			LOG.error("Unable to reschedule cron trigger", e);
		}
	}
	
	@ManagedOperation(description= "The last time the job was run.")
	public String lastRunTime(){
		if (lastRunTime == null){
			return "Never!";
		}
		return DateFormatUtils.ISO_DATETIME_FORMAT.format(lastRunTime);
	}

	protected ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
		ApplicationContext appCtx = null;
		appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		if (appCtx == null) {
			throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
		}
		return appCtx;
	}

}
