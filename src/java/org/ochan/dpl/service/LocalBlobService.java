package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.BlobDPL;
import org.ochan.dpl.BlobStatDPL;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.service.BlobService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.persist.EntityCursor;

@ManagedResource(description = "Local Blob Service", objectName = "Ochan:service=local,name=LocalBlobService", logFile = "jmx.log")
public class LocalBlobService implements BlobService {

	private static final Log LOG = LogFactory.getLog(LocalBlobService.class);
	private SleepyEnvironment environment;
	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;

	private static long lastSearchTime = 0;

	private static long lastSaveTime = 0;
	
	private static long lastGetTime = 0;

	/**
	 * @return the createCount
	 */
	@ManagedAttribute(description = "The number of calls to create a blob")
	public long getCreateCount() {
		return createCount;
	}

	/**
	 * @return the getCount
	 */
	@ManagedAttribute(description = "The number of calls to get a blob")
	public long getGetCount() {
		return getCount;
	}

	/**
	 * @return the deleteCount
	 */
	@ManagedAttribute(description = "The number of calls to delete a blob.")
	public long getDeleteCount() {
		return deleteCount;
	}

	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for a list of all blobs.")
	public long getLastSearchTime() {
		return lastSearchTime;
	}

	/**
	 * @return
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to save a blob.")
	public long getLastSaveTime() {
		return lastSaveTime;
	}
	
	/**
	 * @return
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to get a blob.")
	public long getLastGetTime() {
		return lastGetTime;
	}

	// END STATS

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(SleepyEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public void deleteBlob(Long identifier) {
		deleteCount++;
		try {
			if (identifier != null) {
				environment.blobByIdentifier.delete(identifier);
				environment.blobStatisticsByBlobIdentifier.delete(identifier);
			}
		} catch (Exception e) {
			LOG.error("Blob delete fail.", e);
		}
	}

	@Override
	public List<Long> getAllIds() {
		// capture start of call
		long start = new Date().getTime();
		List<Long> ids = new ArrayList<Long>();
		try {
			EntityCursor<Long> blobs = environment.blobByIdentifier.keys();
			for (Long id : blobs) {
				ids.add(id);
			}
			blobs.close();
		} catch (Exception e) {
			LOG.error("Blob delete fail.", e);
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		return ids;
	}

	@Override
	public Byte[] getBlob(Long identifier) {
		getCount++;
		// capture start of call
		long start = new Date().getTime();
		try {
			BlobDPL blob = environment.blobByIdentifier.get(identifier);
			return blob.getData();
		} catch (Exception e) {
			LOG.error("Blob delete fail.", e);
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastGetTime = end - start;
		return null;
	}

	@Override
	public Long saveBlob(Byte[] byteArray) {
		// capture start of call
		long start = new Date().getTime();
		createCount++;
		try {
			BlobDPL dpl = new BlobDPL();
			dpl.setData(byteArray);
			environment.blobByIdentifier.put(dpl);
			BlobStatDPL stat = new BlobStatDPL();
			stat.setBlobIdentifier(dpl.getIdentifier());
			stat.setSize(byteArray.length);
			environment.blobStatisticsByIdentifier.put(stat);
			return dpl.getIdentifier();
		} catch (Exception e) {
			LOG.error("Blob delete fail.", e);
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSaveTime = end - start;
		return null;
	}

	public int getBlobSize(Long identifier) {
		try {
			BlobStatDPL stat = environment.blobStatisticsByBlobIdentifier.get(identifier);
			return stat.getSize();
		} catch (Exception e) {
			LOG.error("Blob delete fail.", e);
		}
		return 0;
	}

}
