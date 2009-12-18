package org.ochan.dpl;

import java.io.File;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.rep.ReplicatedEnvironment;
import com.sleepycat.je.rep.ReplicationConfig;
import com.sleepycat.je.rep.StateChangeListener;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

public class SleepyEnvironment {

	private static final Log LOG = LogFactory.getLog(SleepyEnvironment.class);
	private Environment environment;
	private EntityStore entityStore;	

	public PrimaryIndex<Long, CategoryDPL> categoryByIdentifier;
	public SecondaryIndex<String, Long, CategoryDPL> categoryByCode;

	public  PrimaryIndex<Long, ThreadDPL> threadByIdentifier;
	public  SecondaryIndex<Long, Long, ThreadDPL> threadByCategory;

	public  PrimaryIndex<Long, PostDPL> postByIdentifier;
	public  SecondaryIndex<Long, Long, PostDPL> postByThread;

	public  PrimaryIndex<Long, BlobDPL> blobByIdentifier;
	
	public PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier;
	
	public PrimaryIndex<Long, BlobStatDPL> blobStatisticsByIdentifier;
	public SecondaryIndex<Long, Long, BlobStatDPL> blobStatisticsByBlobIdentifier;
	
	public PrimaryIndex<Long, SynchroDPL> synchroByIdentifier;

	public SleepyEnvironment(StateChangeListener stateChangeListener) {
		try {
			EnvironmentConfig myEnvConfig = new EnvironmentConfig();
			myEnvConfig.setLocking(false);
			StoreConfig storeConfig = new StoreConfig();
			if (System.getProperty("bdb.readonly") != null){
				myEnvConfig.setReadOnly(true);
				storeConfig.setReadOnly(true);
			}else{
				myEnvConfig.setAllowCreate(true);
				storeConfig.setAllowCreate(true);
				storeConfig.setDeferredWrite(true);
			}
			
			// Open the environment and entity store
			LOG.warn("Storage Directory: " + System.getProperty("user.dir"));
			if (System.getProperty("bdb.rep") != null){
				//we have to turn on transactionality
				myEnvConfig.setTransactional(true);
				storeConfig.setTransactional(true);
				storeConfig.setDeferredWrite(false);
				//and evil locking. 
				myEnvConfig.setLocking(true);
				ReplicationConfig repConfig = new ReplicationConfig();
				String bdbgroup = System.getProperty("bdb.rep.group") == null ? "local" : System.getProperty("bdb.rep.group");
				String bdbname = System.getProperty("bdb.rep.name") == null ? InetAddress.getLocalHost().getHostName() : System.getProperty("bdb.rep.name");
				String bdbHostPort = System.getProperty("bdb.rep.bind") == null ? "0.0.0.0:5001" : System.getProperty("bdb.rep.bind");
				String bdbHelper = System.getProperty("bdb.rep.helper") == null ? "0.0.0.0:5001" : System.getProperty("bdb.rep.helper");
				LOG.warn("Starting replication with settings: group("+bdbgroup+") name("+bdbname+") host("+bdbHostPort+") helper("+bdbHelper+")");
				repConfig.setGroupName(bdbgroup);
				repConfig.setNodeName(bdbname);
				repConfig.setNodeHostPort(bdbHostPort);
				repConfig.setHelperHosts(bdbHelper);
				environment = new ReplicatedEnvironment(new File(System.getProperty("user.dir")), repConfig, myEnvConfig);
				((ReplicatedEnvironment)environment).setStateChangeListener(stateChangeListener);
			}else{
				environment = new Environment(new File(System.getProperty("user.dir")), myEnvConfig);
			}
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
			LOG.error("Unable to start the database.", e);
		}
	}
	
	/**
	 * @return the environment
	 */
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

}
