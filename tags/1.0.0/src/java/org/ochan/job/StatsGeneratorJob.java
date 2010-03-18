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

import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Category;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;
import org.ochan.service.BlobService;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 
 * @author dseymore
 * 
 */
@ManagedResource(description = "Statistics Generation", objectName = "Ochan:type=job,name=StatisticsGeneration", logFile = "jmx.log")
public class StatsGeneratorJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(StatsGeneratorJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(StatsGeneratorJob.class);

	private ThreadService threadService;
	private PostService postService;
	private BlobService blobService;
	private CategoryService categoryService;

	private static Long numberOfFiles;
	private static Long sizeOfAllFiles;
	private static Long numberOfThreads;
	private static Long numberOfPosts;
	private static Long numberOfImagePosts;
	private static long lastSearchTime = 0;

	/**
	 * @param threadService
	 *            the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @param postService
	 *            the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * @param blobService
	 *            the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}

	/**
	 * @param categoryService
	 *            the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * @see org.ochan.util.ManagedQuartzJobBean#executeOnSchedule(org.quartz.JobExecutionContext)
	 */
	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		// capture start of call
		long start = new Date().getTime();

		// do the work!
		try {
			ApplicationContext appCtx = getApplicationContext(context);
			threadService = (ThreadService) appCtx.getBean("proxyThreadService");
			postService = (PostService) appCtx.getBean("proxyPostService");
			blobService = (BlobService) appCtx.getBean("proxyBlobService");
			categoryService = (CategoryService) appCtx.getBean("proxyCategoryService");

			// total size of image content
			long size = 0;
			List<Long> blobIds = blobService.getAllIds();
			for (Long id : blobIds) {
				// Byte[] data = blobService.getBlob(id);
				// its possible that the thing got deleted between the list time
				// and the
				// get time..
				size = size + blobService.getBlobSize(id);
			}
			sizeOfAllFiles = Long.valueOf(size);
			numberOfFiles = Long.valueOf(blobIds.size());
			LOG.debug("Total size in Bytes:" + size);

			long threadCount = 0;
			long postCount = 0;
			long imagePostCount = 0;
			// lets count all the threads, posts, and image posts
			List<Category> categories = categoryService.retrieveCategories();
			for (Category c : categories) {
				ThreadCriteria criteria = new ThreadService.ThreadCriteria();
				criteria.setCategory(c.getIdentifier());
				List<Thread> threads = threadService.retrieveThreads(criteria);
				// categories have 0 threads to begin with..
				if (threads != null) {
					for (Thread thread : threads) {
						threadCount++;
						List<Post> posts = postService.retrieveThreadPosts(thread.getIdentifier());
						for (Post p : posts) {
							postCount++;
							if (p instanceof ImagePost) {
								imagePostCount++;
							}
						}
					}
				}
			}
			numberOfThreads = threadCount;
			numberOfPosts = postCount;
			numberOfImagePosts = imagePostCount;

		} catch (Exception e) {
			LOG.error("Unable to get services", e);
		}

		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
	}

	/**
	 * @see org.ochan.util.ManagedQuartzJobBean#getPreferences()
	 */
	@Override
	public Preferences getPreferences() {
		return PREFERENCES;
	}

	/**
	 * @see org.ochan.util.ManagedQuartzJobBean#getTriggerName()
	 */
	@Override
	public String getTriggerName() {
		return "statsGeneratorTrigger";
	}

	/**
	 * @return the numberOfFiles
	 */
	public static Long getNumberOfFiles() {
		return numberOfFiles;
	}

	/**
	 * @return the sizeOfAllFiles
	 */
	public static Long getSizeOfAllFiles() {
		return sizeOfAllFiles;
	}

	public static Long getNumberOfThreads() {
		return numberOfThreads;
	}

	public static Long getNumberOfPosts() {
		return numberOfPosts;
	}

	public static Long getNumberOfImagePosts() {
		return numberOfImagePosts;
	}

	@ManagedAttribute(description = "The number of files stored.")
	public Long getFileCount() {
		return numberOfFiles;
	}

	@ManagedAttribute(description = "The size in bytes of all files stored.")
	public Long getDataSize() {
		return sizeOfAllFiles;
	}

	@ManagedAttribute(description = "The number of threads in the system.")
	public Long getThreadCount() {
		return numberOfThreads;
	}

	@ManagedAttribute(description = "The number of posts in the system.")
	public Long getPostCount() {
		return numberOfPosts;
	}

	@ManagedAttribute(description = "The number of image posts in the system.")
	public Long getImagePostCount() {
		return numberOfImagePosts;
	}

	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for a list of all blobs.")
	public long getLastSearchTime() {
		return lastSearchTime;
	}

}
