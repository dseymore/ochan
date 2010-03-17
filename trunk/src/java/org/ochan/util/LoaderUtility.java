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

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.ochan.service.PostService;
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
	private PostService postService;
	
	/**
	 * @param postService the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * @param threadService the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}
	
	private Stopwatch readFileTime = SimonManager.getStopwatch("LoaderUtility.read");
	private Stopwatch createThreadTime = SimonManager.getStopwatch("LoaderUtility.create");
	private Stopwatch createPostTime = SimonManager.getStopwatch("LoadUtility.creatPost");
	
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

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="Getting the time it took to create the post.")
	public Long getCreatePostTime(){
		return createPostTime.getLast();
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
				threadService.createThread(null, categoryId, "Author", "Subject", "http://www.google.com", "email", "Content", bytes, f.getName());
				create.stop();
				fis.close();
			}else{
				//there is no file
				Split create = createThreadTime.start();
				threadService.createThread(null, categoryId, "Author", "Subject", "http://www.google.com", "email", "Content", null, null);
				create.stop();
			}
		}catch(Exception e){
			LOG.error("Unable to create your damn thread, homey.",e);
		}
	}
	
	@ManagedOperation(description="Allows posts to be quickly created via jmx")
	@ManagedOperationParameters({
		@ManagedOperationParameter(description="Thread Id", name="threadId"),
		@ManagedOperationParameter(description="File path", name="filepath")
	})
	public void createPost(Long threadId, String filepath){
		Split read = readFileTime.start();
		File f = new File(filepath);
		try{
			if (f.exists() && f.canRead()){
				FileInputStream fis = new FileInputStream(f);
				byte[] bits = IOUtils.toByteArray(fis);
				Byte[] bytes = ArrayUtils.toObject(bits);
				read.stop();
				Split create = createPostTime.start();
				postService.createPost(null, threadId, "Author", "", "", "", "", bytes, f.getName());
				create.stop();
				fis.close();
			}else{
				//there is no file
				Split create = createPostTime.start();
				postService.createPost(null, threadId, "Author", "", "", "", "", null, null);
				create.stop();
			}
		}catch(Exception e){
			LOG.error("Unable to create the post.",e);
		}
	}
	
}
