package org.ochan.dpl.backup;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.BlobDPL;
import org.ochan.dpl.BlobStatDPL;
import org.ochan.dpl.CategoryDPL;
import org.ochan.dpl.ExternalCategoryDPL;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.PostDPL;
import org.ochan.dpl.SynchroDPL;
import org.ochan.dpl.ThreadDPL;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

/**
 * An implementation of the Ochan Environment for backups of the ENTIRE environment into a single
 * environment; so, a replicated, sharded, or neither environment can be backed up 
 * and eventually reconsolidated into a new environment with the import process. 
 * @author David Seymore 
 * Dec 26, 2009
 */
public class BackupEnvironment implements OchanEnvironment{

	private static final Log LOG = LogFactory.getLog(BackupEnvironment.class);
	
	public BackupEnvironment(String directory){
		try {
			EnvironmentConfig myEnvConfig = new EnvironmentConfig();
			myEnvConfig.setLocking(false);
			StoreConfig storeConfig = new StoreConfig();
				myEnvConfig.setAllowCreate(true);
				storeConfig.setAllowCreate(true);
				storeConfig.setDeferredWrite(true);
			
			// Open the environment and entity store
			LOG.warn("Backup Directory: " + directory);
			environment = new Environment(new File(directory), myEnvConfig);
			entityStore = new EntityStore(environment, "EntityStore", storeConfig);

			categoryByIdentifier = entityStore.getPrimaryIndex(Long.class, CategoryDPL.class);
			categoryByCode = entityStore.getSecondaryIndex(categoryByIdentifier, String.class, "code");

			threadByIdentifier = entityStore.getPrimaryIndex(Long.class, ThreadDPL.class);
			threadByCategory = entityStore.getSecondaryIndex(threadByIdentifier, Long.class, "category");

			postByIdentifier = entityStore.getPrimaryIndex(Long.class, PostDPL.class);
			postByThread = entityStore.getSecondaryIndex(postByIdentifier, Long.class, "parent");

			blobByIdentifier = entityStore.getPrimaryIndex(Long.class, BlobDPL.class);
			
			externalCategoryByIdentifier = entityStore.getPrimaryIndex(Long.class, ExternalCategoryDPL.class);
			
			blobStatisticsByIdentifier = entityStore.getPrimaryIndex(Long.class, BlobStatDPL.class);
			blobStatisticsByBlobIdentifier = entityStore.getSecondaryIndex(blobStatisticsByIdentifier, Long.class, "blobIdentifier");
			
			synchroByIdentifier = entityStore.getPrimaryIndex(Long.class, SynchroDPL.class);
			
		} catch (Exception e) {
			LOG.error("Unable to start the database for the backup", e);
			throw new RuntimeException(e);
		}
	}
	private Environment environment;
	private EntityStore entityStore;	

	
	private PrimaryIndex<Long, CategoryDPL> categoryByIdentifier;
	private SecondaryIndex<String, Long, CategoryDPL> categoryByCode;

	private PrimaryIndex<Long, ThreadDPL> threadByIdentifier;
	private SecondaryIndex<Long, Long, ThreadDPL> threadByCategory;

	private PrimaryIndex<Long, PostDPL> postByIdentifier;
	private SecondaryIndex<Long, Long, PostDPL> postByThread;

	private PrimaryIndex<Long, BlobDPL> blobByIdentifier;
	
	private PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier;
	
	private PrimaryIndex<Long, BlobStatDPL> blobStatisticsByIdentifier;
	private SecondaryIndex<Long, Long, BlobStatDPL> blobStatisticsByBlobIdentifier;
	
	private PrimaryIndex<Long, SynchroDPL> synchroByIdentifier;

	public Environment getEnvironment() {
		return environment;
	}

	public void close() {
		try {
			entityStore.sync();
			entityStore.close();
			environment.cleanLog();
			environment.close();
		} catch (Exception e) {
			LOG.error("Unable to close.. OMG!", e);
		}

	}

	@Override
	public PrimaryIndex<Long, BlobDPL> blobByIdentifier() {
		return blobByIdentifier;
	}
	@Override
	public SecondaryIndex<Long, Long, BlobStatDPL> blobStatisticsByBlobIdentifier() {
		return blobStatisticsByBlobIdentifier;
	}

	@Override
	public PrimaryIndex<Long, BlobStatDPL> blobStatisticsByIdentifier() {
		return blobStatisticsByIdentifier;
	}

	@Override
	public SecondaryIndex<String, Long, CategoryDPL> categoryByCode() {
		return categoryByCode;
	}
	@Override
	public PrimaryIndex<Long, CategoryDPL> categoryByIdentifier() {
		return categoryByIdentifier;
	}

	@Override
	public PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier() {
		return externalCategoryByIdentifier;
	}

	@Override
	public PrimaryIndex<Long, PostDPL> postByIdentifier() {
		return postByIdentifier;
	}

	@Override
	public SecondaryIndex<Long, Long, PostDPL> postByThread() {
		return postByThread;
	}

	@Override
	public PrimaryIndex<Long, SynchroDPL> synchroByIdentifier() {
		return synchroByIdentifier;
	}

	@Override
	public SecondaryIndex<Long, Long, ThreadDPL> threadByCategory() {
		return threadByCategory;
	}

	@Override
	public PrimaryIndex<Long, ThreadDPL> threadByIdentifier() {
		return threadByIdentifier;
	}
	
}
