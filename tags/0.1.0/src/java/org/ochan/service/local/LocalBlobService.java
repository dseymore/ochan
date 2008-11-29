package org.ochan.service.local;

import java.util.Date;
import java.util.List;

import org.ochan.dao.BlobDAO;
import org.ochan.entity.Blob;
import org.ochan.service.BlobService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Local Blob Service", objectName = "Ochan:service=local,name=LocalBlobService", logFile = "jmx.log")
public class LocalBlobService implements BlobService {

	private BlobDAO blobDAO;
		
	
	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;

	private static long lastSearchTime = 0;
	

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

	// END STATS
	
	/**
	 * @param blobDAO the blobDAO to set
	 */
	public void setBlobDAO(BlobDAO blobDAO) {
		this.blobDAO = blobDAO;
	}

	@Override
	public void deleteBlob(Long identifier) {
		deleteCount++;
		blobDAO.delete(identifier);
	}

	@Override
	public Byte[] getBlob(Long identifier) {
		getCount++;
		Blob blob = blobDAO.getBlob(identifier);
		if (blob == null){
			return null;
		}
		return blob.getData();
	}

	@Override
	public Long saveBlob(Byte[] byteArray) {
		createCount++;
		Blob b = new Blob(byteArray, null);
		blobDAO.saveBlob(b);
		return b.getIdentifier();
	}

	/**
	 * @see org.ochan.service.BlobService#getAllIds()
	 */
	@Override
	public List<Long> getAllIds() {
		// capture start of call
		long start = new Date().getTime();

		List<Long> xyz = blobDAO.getIdentifiers();
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		
		return xyz;

	}
	
	

}
