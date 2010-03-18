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

/**
 * Right now this is the central point of persistence for the runtime environment
 * However, I'd like to be able to construct a new environment during runtime to either:
 * <ul>
 * <li>Re-partition the shards</li>
 * <li>Create a backup</li>
 * <li>Replace the environment with a replicated environment at runtime</li>
 * </ul>
 * FIXME - those ideas are crazy.. and very far down the road.  
 * @author David Seymore 
 */
public class SleepyEnvironment implements OchanEnvironment{

	private static final Log LOG = LogFactory.getLog(SleepyEnvironment.class);
	private Environment environment;
	private EntityStore entityStore;	

	
	private PrimaryIndex<Long, CategoryDPL> categoryByIdentifier;
	private SecondaryIndex<String, Long, CategoryDPL> categoryByCode;

	private PrimaryIndex<Long, ThreadDPL> threadByIdentifier;
	private SecondaryIndex<Long, Long, ThreadDPL> threadByCategory;

	private PrimaryIndex<Long, PostDPL> postByIdentifier;
	private SecondaryIndex<Long, Long, PostDPL> postByThread;
	private SecondaryIndex<Long, Long, PostDPL> postByImage;
	private SecondaryIndex<Long, Long, PostDPL> postByThumbnail;

	private PrimaryIndex<Long, BlobDPL> blobByIdentifier;
	
	private PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier;
	
	private PrimaryIndex<Long, BlobStatDPL> blobStatisticsByIdentifier;
	private SecondaryIndex<Long, Long, BlobStatDPL> blobStatisticsByBlobIdentifier;
	private SecondaryIndex<BlobType, Long, BlobStatDPL> blobStatisticsByBlobType;
	
	private PrimaryIndex<Long, SynchroDPL> synchroByIdentifier;

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
				if (System.getProperty("bdb.rep.primary") != null){
					//we're the primary.. which means we conduct the election if the master is lost
					//this is used for 2 node clusters to force a single-node when the other node fails (or loses communication)
					repConfig.setDesignatedPrimary(true);
				}
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
			postByImage = entityStore.getSecondaryIndex(postByIdentifier, Long.class, "imageIdentifier");
			postByThumbnail = entityStore.getSecondaryIndex(postByIdentifier, Long.class, "thumbnailIdentifier");

			blobByIdentifier = entityStore.getPrimaryIndex(Long.class, BlobDPL.class);
			
			externalCategoryByIdentifier = entityStore.getPrimaryIndex(Long.class, ExternalCategoryDPL.class);
			
			blobStatisticsByIdentifier = entityStore.getPrimaryIndex(Long.class, BlobStatDPL.class);
			blobStatisticsByBlobIdentifier = entityStore.getSecondaryIndex(blobStatisticsByIdentifier, Long.class, "blobIdentifier");
			blobStatisticsByBlobType = entityStore.getSecondaryIndex(blobStatisticsByIdentifier, BlobType.class, "blobType");
			
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
	public SecondaryIndex<Long, Long, PostDPL> postByImage() {
		return postByImage;
	}
	
	@Override
	public SecondaryIndex<Long, Long, PostDPL> postByThumbnail() {
		return postByThumbnail;
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
	
	@Override
	public SecondaryIndex<BlobType, Long, BlobStatDPL> blobStatisticsByBlobType(){
		return blobStatisticsByBlobType;
	}
	
}
