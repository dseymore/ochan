package org.ochan.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.ochan.service.ThreadService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Data Loader Utility (for load testing)", objectName = "Ochan:type=util,name=LoaderUtility", logFile = "jmx.log")
public class LoaderUtility {
	
	private static final Log LOG = LogFactory.getLog(LoaderUtility.class);
	
	private ThreadService threadService;
	
	/**
	 * @param threadService the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}
	
	private Stopwatch readFileTime = SimonManager.getStopwatch("LoaderUtility.read");
	private Stopwatch createThreadTime = SimonManager.getStopwatch("LoaderUtility.create");
	
	/**
	 * @return the readFileTime
	 */
	@ManagedAttribute(description="Getting the time it took to read the file.")
	public Long getReadFileTime() {
		return readFileTime.getLast();
	}



	/**
	 * @return the createThreadTime
	 */
	@ManagedAttribute(description="Getting the time it took to create the thread.")
	public Long getCreateThreadTime() {
		return createThreadTime.getLast();
	}



	@ManagedOperation(description="Allows threads to be quickly created via jmx")
	@ManagedOperationParameters({
		@ManagedOperationParameter(description="Category Id", name="categoryId"),
		@ManagedOperationParameter(description="File path", name="filepath")
	})
	public void createThread(Long categoryId, String filepath){
		Split read = readFileTime.start();
		File f = new File(filepath);
		try{
			if (f.exists() && f.canRead()){
				FileInputStream fis = new FileInputStream(f);
				byte[] bits = IOUtils.toByteArray(fis);
				Byte[] bytes = ArrayUtils.toObject(bits);
				read.stop();
				Split create = createThreadTime.start();
				threadService.createThread(categoryId, "Author", "Subject", "http://www.google.com", "email", "Content", bytes);
				create.stop();
				fis.close();
			}
		}catch(Exception e){
			LOG.error("Unable to create your damn thread, homey.",e);
		}
	}
	
}
