package org.Ochan.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Handles an error thrown into the logs by making a statistic of its occurence
 * 
 * Absolutely untested
 * 
 * @author David Seymore Feb 8, 2008
 */
@ManagedResource(description = "How many of each type of exception have occured", objectName = "Ochan:utility=logging,name=ErrorStatistics", logFile = "jmx.log")
public class StatisticalErrorHandler implements ErrorHandler {

	private static Map<String, Integer> errorCountMap = new HashMap<String, Integer>();

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "Makes a report of all excpetions.")
	public String getStatus() {
		StringBuffer buf = new StringBuffer();
		for (String key : errorCountMap.keySet()) {
			buf.append(key + " | " + errorCountMap.get(key) + "\n");
		}
		return buf.toString();
	}

	public void error(String arg0) {
		// DO NOTHING
	}

	public void error(String arg0, Exception arg1, int arg2) {
		handleException(arg1);
	}

	/**
	 * Turns the exception into its class name and makes a count of it
	 * 
	 * @param exception
	 */
	public void handleException(Exception exception) {
		if (exception != null) {
			synchronized (StatisticalErrorHandler.class) {
				if (errorCountMap.get(exception.getClass()) == null) {
					errorCountMap.put(exception.getClass().getName(), new Integer(1));
				} else {
					Integer currentCount = errorCountMap.get(exception.getClass());
					errorCountMap.put(exception.getClass().getName(), new Integer(currentCount.intValue() + 1));
				}
			}
		}
	}

	public void error(String arg0, Exception arg1, int arg2, LoggingEvent arg3) {
		handleException(arg1);
	}

	public void setAppender(Appender arg0) {
		// DO NOTHING
	}

	public void setBackupAppender(Appender arg0) {
		// DO NOTHING
	}

	public void setLogger(Logger arg0) {
		// DO NOTHING
	}

	public void activateOptions() {
		// DO NOTHING
	}

}
