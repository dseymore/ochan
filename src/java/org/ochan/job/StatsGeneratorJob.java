package org.ochan.job;

import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.service.BlobService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.util.ManagedQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Statistics Generation", objectName = "Ochan:type=job,name=StatisticsGeneration", logFile = "jmx.log")
public class StatsGeneratorJob extends ManagedQuartzJobBean implements StatefulJob {

	private static final Log LOG = LogFactory.getLog(StatsGeneratorJob.class.getName());
	private static Preferences PREFERENCES = Preferences.userNodeForPackage(StatsGeneratorJob.class);

	private ThreadService threadService;
	private PostService postService;
	private BlobService blobService;
	
	private static Long numberOfFiles;
	private static Long sizeOfAllFiles;
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
	 * @see org.ochan.util.ManagedQuartzJobBean#executeOnSchedule(org.quartz.JobExecutionContext)
	 */
	@Override
	public void executeOnSchedule(JobExecutionContext context) {
		// capture start of call
		long start = new Date().getTime();

		// do the work!
		try{
			ApplicationContext appCtx = getApplicationContext(context);
			threadService = (ThreadService)appCtx.getBean("localThreadService");
			postService = (PostService)appCtx.getBean("localPostService");
			blobService = (BlobService)appCtx.getBean("localBlobService");
			
			//total size of image content
			long size = 0;
			List<Long> blobIds = blobService.getAllIds();
			for (Long id : blobIds){
				Byte[] data = blobService.getBlob(id);
				//its possible that the thing got deleted between the list time and the 
				//get time.. 
				if (data != null){
					//the number of bytes
					size = size + data.length;
				}
			}
			sizeOfAllFiles = Long.valueOf(size);
			numberOfFiles = Long.valueOf(blobIds.size());
			LOG.debug("Total size in Bytes:" + size);
			
		}catch(Exception e){
			LOG.error("Unable to get services",e);
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
	
	@ManagedAttribute(description="The number of files stored.")
	public Long getFileCount(){
		return numberOfFiles;
	}
	
	@ManagedAttribute(description="The size in bytes of all files stored.")
	public Long getDataSize(){
		return sizeOfAllFiles;
	}
	
	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for a list of all blobs.")
	public long getLastSearchTime() {
		return lastSearchTime;
	}
	
	
	
}
