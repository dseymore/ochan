package org.ochan.dpl;

import com.sleepycat.je.Environment;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

public interface OchanEnvironment {

	public PrimaryIndex<Long, CategoryDPL> categoryByIdentifier();

	public SecondaryIndex<String, Long, CategoryDPL> categoryByCode();

	public PrimaryIndex<Long, ThreadDPL> threadByIdentifier();

	public SecondaryIndex<Long, Long, ThreadDPL> threadByCategory();

	public PrimaryIndex<Long, PostDPL> postByIdentifier();

	public SecondaryIndex<Long, Long, PostDPL> postByThread();

	public PrimaryIndex<Long, BlobDPL> blobByIdentifier();

	public PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier();

	public PrimaryIndex<Long, BlobStatDPL> blobStatisticsByIdentifier();
	
	public SecondaryIndex<BlobType, Long, BlobStatDPL> blobStatisticsByBlobType();

	public SecondaryIndex<Long, Long, BlobStatDPL> blobStatisticsByBlobIdentifier();

	public PrimaryIndex<Long, SynchroDPL> synchroByIdentifier();

	public Environment getEnvironment();

	public void close();

}
