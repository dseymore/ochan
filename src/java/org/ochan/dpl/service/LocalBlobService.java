package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
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

	private static Stopwatch saveStopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Save");
	private static Stopwatch searchStopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Search");
	private static Stopwatch getStopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Get");

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
	@ManagedAttribute(description = "The time in nanoseconds of the last call to search for a list of all blobs.")
	public long getLastSearchTime() {
		return searchStopWatch.getLast();
	}

	/**
	 * @return
	 */
	@ManagedAttribute(description = "The time in nanoseconds of the last call to save a blob.")
	public long getLastSaveTime() {
		return saveStopWatch.getLast();
	}

	/**
	 * @return
	 */
	@ManagedAttribute(description = "The time in nanoseconds of the last call to get a blob.")
	public long getLastGetTime() {
		return getStopWatch.getLast();
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
		Split split = searchStopWatch.start();
		List<Long> ids = new ArrayList<Long>();
		try {
			EntityCursor<Long> blobs = environment.blobByIdentifier.keys();
			for (Long id : blobs) {
				ids.add(id);
			}
			blobs.close();
		} catch (Exception e) {
			LOG.error("Get blob ids fail.", e);
		} finally {
			split.stop();
		}
		split.stop();
		return ids;
	}

	@Override
	public Byte[] getBlob(Long identifier) {
		getCount++;
		Split split = getStopWatch.start();
		try {
			BlobDPL blob = environment.blobByIdentifier.get(identifier);
			return blob.getData();
		} catch (Exception e) {
			LOG.error("Blob get fail.", e);
		} finally {
			split.stop();
		}
		return null;
	}

	@Override
	public Long saveBlob(Byte[] byteArray, Long id) {
		Split split = saveStopWatch.start();
		createCount++;
		try {
			BlobDPL dpl = new BlobDPL();
			dpl.setData(byteArray);
			dpl.setIdentifier(id);
			environment.blobByIdentifier.put(dpl);
			BlobStatDPL stat = new BlobStatDPL();
			stat.setBlobIdentifier(dpl.getIdentifier());
			stat.setSize(byteArray.length);
			environment.blobStatisticsByIdentifier.put(stat);
			return dpl.getIdentifier();
		} catch (Exception e) {
			LOG.error("Blob save fail.", e);
		} finally {
			split.stop();
		}
		split.stop();
		return null;
	}

	public int getBlobSize(Long identifier) {
		try {
			BlobStatDPL stat = environment.blobStatisticsByBlobIdentifier.get(identifier);
			if (stat != null){
				return stat.getSize();
			}
		} catch (Exception e) {
			LOG.warn("Blob get size fail.", e);
		}
		return 0;
	}

}
