package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.SortedArraySet;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.ochan.dpl.BlobDPL;
import org.ochan.dpl.BlobStatDPL;
import org.ochan.dpl.BlobType;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.replication.TransactionTemplate;
import org.ochan.service.BlobService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.persist.EntityCursor;

@ManagedResource(description = "Local Blob Service", objectName = "Ochan:service=local,name=LocalBlobService", logFile = "jmx.log")
public class LocalBlobService implements BlobService {

	private static final Log LOG = LogFactory.getLog(LocalBlobService.class);
	private OchanEnvironment environment;
	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;
	
	private static long get50Count = 0;

	private static Stopwatch saveStopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Save");
	private static Stopwatch searchStopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Search");
	private static Stopwatch getStopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Get");
	private static Stopwatch get50StopWatch = SimonManager.getStopwatch(LocalBlobService.class.getName() + "Get50");
	

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
	 * @return the get50Count
	 */
	@ManagedAttribute(description = "The number of calls to the last 50 blobs.")
	public long get50Count() {
		return get50Count;
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

	/**
	 * @return
	 */
	@ManagedAttribute(description = "The time in nanoseconds of the last call to last 50 blobs.")
	public long getLastGet50Time() {
		return get50StopWatch.getLast();
	}

	// END STATS

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(OchanEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public void deleteBlob(final Long identifier) {
		deleteCount++;
		try {
			new TransactionTemplate(environment){
				public void doInTransaction(){
					if (identifier != null) {
						environment.blobByIdentifier().delete(identifier);
						environment.blobStatisticsByBlobIdentifier().delete(identifier);
					}
				}
			}.run();
		} catch (Exception e) {
			LOG.error("Blob delete fail.", e);
		}
	}

	@Override
	public List<Long> getAllIds() {
		Split split = searchStopWatch.start();
		List<Long> ids = new ArrayList<Long>();
		try {
			EntityCursor<Long> blobs = environment.blobByIdentifier().keys();
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
			BlobDPL blob = environment.blobByIdentifier().get(identifier);
			return blob.getData();
		} catch (Exception e) {
			LOG.error("Blob get fail.", e);
		} finally {
			split.stop();
		}
		return null;
	}

	@Override
	public Long saveBlob(final Byte[] byteArray,final Long id, final BlobType blobType) {
		Split split = saveStopWatch.start();
		createCount++;
		try {
			final BlobStatDPL stat = new BlobStatDPL();
			final BlobDPL dpl = new BlobDPL();
			new TransactionTemplate(environment){
				public void doInTransaction(){
					dpl.setData(byteArray);
					dpl.setIdentifier(id);
					environment.blobByIdentifier().put(dpl);
					stat.setBlobIdentifier(dpl.getIdentifier());
					stat.setSize(byteArray.length);
					stat.setBlobType(blobType);
					environment.blobStatisticsByIdentifier().put(stat);
				}
			}.run();
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
			BlobStatDPL stat = environment.blobStatisticsByBlobIdentifier().get(identifier);
			if (stat != null){
				return stat.getSize();
			}
		} catch (Exception e) {
			LOG.warn("Blob get size fail.", e);
		}
		return 0;
	}
	
	@Override
	public List<Long> getLast50Blobs(BlobType blobType) {
		Split split = get50StopWatch.start();
		get50Count++;
		try{
			//get all the types ids.. this
			EntityCursor<Long> stat = environment.blobStatisticsByBlobType().subIndex(blobType).keys();
			//now, go get the blobstat so that we can get the REAL blog id..
			List<Long> keyDump = new ArrayList<Long>();
			for(Long id : stat){
				keyDump.add(id);
			}
			//CLOSE THAT CURSOR!
			stat.close();
			//trim to 50 first
			Collections.sort(keyDump);
			int size = keyDump.size();
			int start = size >= 50 ? size - 50 : 0;
			List<Long> statKeyList = keyDump.subList(size - 50, size);

			List<Long> finalList = new ArrayList<Long>();
			for(Long id : statKeyList){
				BlobStatDPL statDpl = environment.blobStatisticsByIdentifier().get(id);
				finalList.add(statDpl.getBlobIdentifier());
			}
			return finalList;
		} catch(Exception e){
			LOG.error("Unable to get the last 50 images",e);
		} finally {
			split.stop();
		}
		return null;
	}



}
