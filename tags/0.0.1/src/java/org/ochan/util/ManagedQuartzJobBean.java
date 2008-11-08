package org.ochan.util;

import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Specialized Quartz Job Bean that allows JMX exposure of cron expression and rescheduling of job. 
 * @author dseymore
 */
public abstract class ManagedQuartzJobBean extends QuartzJobBean {

	private static Log LOG = LogFactory.getLog(ManagedQuartzJobBean.class);

	/**
	 * Spring uses this name to build the group of triggers.
	 */
	private static final String SPRING_QUARTZ_GROUP_NAME = "DEFAULT";

	//Final so that noone can extend
	@Override
	protected final void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// We should check if we need to be rescheduled due to a saved
		// configuration
		if (getCurrentCronSetting() != null && !getCurrentCronSetting().equals(getCron())) {
			LOG.info("Rescheduleing " + getTriggerName() + "cron job to be the preferences stored value.");
			setCron(getCron());
		} else {
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
		return getPreferences().get(getTriggerName()+"cronTab", getCurrentCronSetting());
	}

	/**
	 * Sets the preferences stored cron expression, and reschedules the job.
	 * 
	 * @param cloCron
	 */
	@ManagedAttribute(description = "Cron expression for firing job", persistPolicy = "OnUpdate")
	public void setCron(String cloCron) {
		LOG.info("Setting cron expression");
		getPreferences().put(getTriggerName()+"cronTab", cloCron);
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			CronTrigger t = (CronTrigger) schedulerFactory.getScheduler().getTrigger(getTriggerName(), SPRING_QUARTZ_GROUP_NAME);
			t.setCronExpression(getCron());
			schedulerFactory.getScheduler().rescheduleJob(getTriggerName(), SPRING_QUARTZ_GROUP_NAME, t);
		} catch (Exception e) {
			LOG.error("Unable to reschedule cron trigger", e);
		}
	}

}
