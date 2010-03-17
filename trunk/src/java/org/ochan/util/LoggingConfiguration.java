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

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.prefs.Preferences;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.net.SocketAppender;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Utility class for modifying the log4j log file, and threshold at runtime.
 * Also, this implements a servlet context listener so that values set at last
 * runtime are set again upon restart of the application. (with some delay I'm
 * sure).
 * 
 * @author dseymore
 */
@ManagedResource(objectName = "System:type=config,name=LoggingConfiguration", description = "Logging management", log = true, logFile = "jmx.log")
public class LoggingConfiguration implements ServletContextListener {

	/**
	 * Friendly little commons log ^_^
	 */
	private static final Log LOG = LogFactory.getLog(LoggingConfiguration.class);

	/**
	 * Preferences store
	 */
	private transient final Preferences preferences = Preferences.userNodeForPackage(LoggingConfiguration.class);

	/**
	 * The log4j.properties configured name of the rolling log file appender
	 */
	public static final String ROLLING_LOG_FILE_CONFIGURED_NAME = "LOGFILE";

	/**
	 * Directly uses the Log4j API for moving the log file at runtime.
	 * 
	 * @param absoluteFilePath
	 */
	@ManagedOperation(description = "Changes the file of the rolling logger, pass in the absolute WRITEABLE path")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="absolutePath", description="Log4j will start using the logfile passed in. The user running the code must have write access to the path.")
	})
	public void changeLogFile(final String absoluteFilePath) {
		if (LOG.isInfoEnabled()) {
			LOG.info("about to move the log file to:" + absoluteFilePath);
		}
		// we HAVE to use our implementation logger here, Commons-logging has
		// specific code preventing runtime changes
		((RollingFileAppender) Logger.getRootLogger().getAppender(ROLLING_LOG_FILE_CONFIGURED_NAME)).setFile(absoluteFilePath);
		((RollingFileAppender) Logger.getRootLogger().getAppender(ROLLING_LOG_FILE_CONFIGURED_NAME)).activateOptions(); // reset!
		// save our value for next time we start the application
		setLogFile(absoluteFilePath);
		if (LOG.isInfoEnabled()) {
			LOG.info("Log file successfully moved to:" + absoluteFilePath);
		}
	}

	@ManagedOperation(description = "Changes the length of log files, and the maximum number to store. numberOfFiles is an INTEGER, sizeOfFiles is a STRING (You can specify the value "
			+ "with the suffixes \"KB\", \"MB\" or \"GB\" so that the integer is "
			+ "interpreted being expressed respectively in kilobytes, megabytes"
			+ "or gigabytes. For example, the value \"10KB\" will be interpreted" + "as 10240.)")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name = "Number of log files", description = "Log4j will store this many log files before it starts deleting."),
		@ManagedOperationParameter(name = "Size of log file", description = "Log4j will keep using one file until it reaches the size passed in, before moving to a new file.")
		})
	public void changeLogDepth(
			final String numberOfFiles,
			final String sizeOfFiles) {
		try {
			LOG.info("Attempting to set the maximum number of files to backup to: " + numberOfFiles.trim());
			final int num = Integer.valueOf(numberOfFiles.trim());
			((RollingFileAppender) Logger.getRootLogger().getAppender(ROLLING_LOG_FILE_CONFIGURED_NAME)).setMaxBackupIndex(num);
			LOG.info("Attempting to set the maximum size of a log file to : " + sizeOfFiles.trim());
			((RollingFileAppender) Logger.getRootLogger().getAppender(ROLLING_LOG_FILE_CONFIGURED_NAME)).setMaxFileSize(sizeOfFiles.trim());
			((RollingFileAppender) Logger.getRootLogger().getAppender(ROLLING_LOG_FILE_CONFIGURED_NAME)).activateOptions(); // reset!
			// save the passed in values for after reboot
			setNumberOfBackups(numberOfFiles);
			setSizeOfBackups(sizeOfFiles);
		} catch (NumberFormatException nfe) {
			LOG.error("Unable to accept your numberOfFiles input (" + numberOfFiles + ") as it doesn't parse to an INTEGER.. aborting");
		}

	}

	/**
	 * Directly usses the Log4j API for modifying the log threshold of the log
	 * file output.
	 * 
	 * @param level
	 *            one of these options (DEBUG, INFO, WARN, ERROR, FATAL) (From
	 *            greatest to least amount of logs)
	 */
	@ManagedOperation(description = "Changes the threshold of the log file, pass in one of these options (DEBUG, INFO, WARN, ERROR, FATAL) (From greatest to least amount of logs)")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="Level", description="Log4j level, from most to lease verbose: (DEBUG, INFO, WARN, ERROR, FATAL)")
	})
	public void changeLogLevel(final String level) {
		Level setTo = null;
		if ("debug".equalsIgnoreCase(level)) {
			LOG.info("Changing log level to debug.");
			setTo = Level.DEBUG;
		} else if ("info".equalsIgnoreCase(level)) {
			LOG.info("Changing log level to info.");
			setTo = Level.INFO;
		} else if ("warn".equalsIgnoreCase(level)) {
			LOG.info("Changing log level to warn.");
			setTo = Level.WARN;
		} else if ("error".equalsIgnoreCase(level)) {
			LOG.info("Changing log level to error.");
			setTo = Level.ERROR;
		} else if ("fatal".equalsIgnoreCase(level)) {
			LOG.info("Changing log level to fatal.");
			setTo = Level.FATAL;
		} else if ("trace".equalsIgnoreCase(level)) {
			LOG.info("Changing log level to trace.");
			setTo = Level.TRACE;
		}

		if (setTo == null) {
			LOG.fatal("You passed in an argument to change log threshold that didn't match an expected value.");
		} else {
			Enumeration enumer = Logger.getRootLogger().getAllAppenders();
			while(enumer.hasMoreElements()){
				Object next = enumer.nextElement();
				if (next instanceof AppenderSkeleton){
					AppenderSkeleton appen = (AppenderSkeleton)next;
					appen.setThreshold(setTo);
					appen.activateOptions();
					// saving into preferences
					setLevel(level);
				}
			}
			Logger.getRootLogger().setLevel(setTo);
		}
	}

	/**
	 * 
	 * @param ipAddress
	 * @param port
	 */
	@ManagedOperation(description = "Adds a remote socket appender.")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="ipAddress", description="The IP Address of the socket client."),
		@ManagedOperationParameter(name="port", description="The Port number for the client.")
	})
	public void addRemoteLogger(final String ipAddress, final String port) {
		Logger log = Logger.getRootLogger();
		SocketAppender sa = new SocketAppender();
		// making it unique by using the date
		Date d = new Date();
		sa.setName(d.getTime() + ipAddress + port);
		sa.setRemoteHost(ipAddress);
		try {
			sa.setPort(Integer.parseInt(port));
		} catch (Exception e) {
			LOG.error("Unable to figure out the port you typed.. defaulting to 4445.");
			sa.setPort(4445);
		}
		LOG.debug("Adding Socket Appender to: " + ipAddress + ":" + sa.getPort());
		log.addAppender(sa);
		sa.activateOptions();
	}
	
	/**
	 * Returns a list of remote loggers 
	 * @return
	 */
	@ManagedOperation(description = "Gets a list of the remote logger names")
	public List<String> fetchRemoteLoggerNames() {
		final List<String> resultList = new ArrayList<String>();
		
		final Logger log = Logger.getRootLogger();
		for (final Appender appender : (List<Appender>) EnumerationUtils.toList(log.getAllAppenders())) {
			if (appender instanceof SocketAppender) {
		        resultList.add(appender.getName());
			}
		} //end for
		
		return resultList;
	} //end getRemoteLoggerNames()
	
	/**
	 * Removes the remote logger by name. 
	 * @param name
	 */
	@ManagedOperation(description = "Removes a remote socket appender.")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="name", description="Name of the appender to remove")
	})
	public void removeRemoteLogger(final String name) {
		final Logger log = Logger.getRootLogger();
		final Appender appender = log.getAppender(name);
		if (appender == null) {
			throw new IllegalArgumentException(name + " does not exist");
		}
	    if (!(appender instanceof SocketAppender)) {
	    	throw new IllegalArgumentException(name + " does not correspond to a SocketAppender");
	    }
	    log.warn("Removing appender: " + appender.getName());
	    log.removeAppender(appender);
	} //end removeRemoteLogger(String)

	/**
	 * Nothing here
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(final ServletContextEvent arg0) {
		// nothing to do here
		LOG.trace("Just got told the web context is going down.");
	}

	/**
	 * Resets Log4j values to previously defined runtime values
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(final ServletContextEvent arg0) {
		LOG.info("Container starting, attempting to reset non-configuration settings for logging");
		if (getLevel() != null) {
			LOG.info("Level has been defined at runtime, resetting to that value.");
			changeLogLevel(getLevel());
		}
		if (getLogFile() != null) {
			LOG.info("Log file has been defined at runtime, resetting to that value.");
			changeLogFile(getLogFile());
		}
		if (getNumberOfBackups() != null && getSizeOfBackups() != null) {
			LOG.info("Log size & number of backups have been defined at runtime, resetting to those values.");
			changeLogDepth(getNumberOfBackups(), getSizeOfBackups());
		}
	}

	/**
	 * Returns the preferences backed level value if it exists, null otherwise
	 * 
	 * @return
	 */
	public String getLevel() {
		return preferences.get("level", null);
	}

	/**
	 * Sets the preferences backed level
	 * 
	 * @param level
	 */
	public void setLevel(final String level) {
		preferences.put("level", level);
	}

	/**
	 * Returns the preferences backed log file location if it exists, null
	 * otherwise
	 * 
	 * @return
	 */
	public String getLogFile() {
		return preferences.get("logfile", null);
	}

	/**
	 * Sets the preferences backed log file
	 * 
	 * @param logFile
	 */
	public void setLogFile(final String logFile) {
		preferences.put("logfile", logFile);
	}

	/**
	 * Returns the preferences backed number of backups log4j should store
	 * 
	 * @return
	 */
	public String getNumberOfBackups() {
		return preferences.get("numberOfBackups", null);
	}

	/**
	 * Sets the prefences backed number of backups
	 * 
	 * @param numberOfBackups
	 */
	public void setNumberOfBackups(final String numberOfBackups) {
		preferences.put("numberOfBackups", numberOfBackups);
	}

	/**
	 * Returns the preferences backed size of backups log4j should store
	 * 
	 * @return
	 */
	public String getSizeOfBackups() {
		return preferences.get("sizeOfBackups", null);
	}

	/**
	 * Sets the preferences backed size of backups
	 * 
	 * @param sizeOfBackups
	 */
	public void setSizeOfBackups(final String sizeOfBackups) {
		preferences.put("sizeOfBackups", sizeOfBackups);
	}

}