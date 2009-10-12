package org.ochan.whatwhatwiki.dpl;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

public class SleepyEnvironment {
	private static final Log LOG = LogFactory.getLog(SleepyEnvironment.class);
	private Environment environment;
	private EntityStore entityStore;

	public PrimaryIndex<String, org.ochan.whatwhatwiki.dpl.File> fileByKey;
	public PrimaryIndex<String, Page> pageByKey;
	public PrimaryIndex<Long, Version> versionById;
	public SecondaryIndex<String, Long, Version> versionsByKey;

	public SleepyEnvironment() {
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
			LOG.warn(System.getProperty("user.dir"));
			environment = new Environment(new File(System.getProperty("user.dir")), myEnvConfig);
			entityStore = new EntityStore(environment, "EntityStore", storeConfig);

			fileByKey = entityStore.getPrimaryIndex(String.class, org.ochan.whatwhatwiki.dpl.File.class);
			pageByKey = entityStore.getPrimaryIndex(String.class, Page.class);
			versionById = entityStore.getPrimaryIndex(Long.class, Version.class);
			versionsByKey = entityStore.getSecondaryIndex(versionById, String.class, "key");
			
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
