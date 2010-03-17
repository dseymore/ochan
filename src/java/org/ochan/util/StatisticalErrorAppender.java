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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Handles an error thrown into the logs by making a statistic of its occurence
 * 
 * 
 * @author David Seymore Feb 8, 2008
 */
@ManagedResource(description = "How many of each type of exception have occured", objectName = "Ochan:utility=logging,name=ErrorStatistics", logFile = "jmx.log")
public class StatisticalErrorAppender implements Appender {

	private static Map<String, Integer> errorCountMap = new HashMap<String, Integer>();

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "Makes a report of all excpetions.")
	public String getStatus() {
		StringBuilder buf = new StringBuilder();
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
			synchronized (StatisticalErrorAppender.class) {
				if (errorCountMap.get(exception.getClass()) == null) {
					errorCountMap.put(exception.getClass().getName(), new Integer(1));
				} else {
					Integer currentCount = errorCountMap.get(exception.getClass().getName());
					errorCountMap.put(exception.getClass().getName(), Integer.valueOf(currentCount.intValue() + 1));
				}
			}
		}
	}

	public void error(String arg0, Exception arg1, int arg2, LoggingEvent arg3) {
		handleException(arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#addFilter(org.apache.log4j.spi.Filter)
	 */
	@Override
	public void addFilter(Filter arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#clearFilters()
	 */
	@Override
	public void clearFilters() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.Appender#doAppend(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	public void doAppend(LoggingEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#getErrorHandler()
	 */
	@Override
	public ErrorHandler getErrorHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#getFilter()
	 */
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#getLayout()
	 */
	@Override
	public Layout getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.Appender#setErrorHandler(org.apache.log4j.spi.ErrorHandler
	 * )
	 */
	@Override
	public void setErrorHandler(ErrorHandler arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#setLayout(org.apache.log4j.Layout)
	 */
	@Override
	public void setLayout(Layout arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#setName(java.lang.String)
	 */
	@Override
	public void setName(String arg0) {
		// TODO Auto-generated method stub

	}

}
