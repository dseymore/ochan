package org.ochan.dpl;

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

	public PrimaryIndex<Long, CategoryDPL> categoryByIdentifier;

	public  PrimaryIndex<Long, ThreadDPL> threadByIdentifier;
	public  SecondaryIndex<Long, Long, ThreadDPL> threadByCategory;

	public  PrimaryIndex<Long, PostDPL> postByIdentifier;
	public  SecondaryIndex<Long, Long, PostDPL> postByThread;

	public  PrimaryIndex<Long, BlobDPL> blobByIdentifier;
	
	public PrimaryIndex<Long, ExternalCategoryDPL> externalCategoryByIdentifier;

	public SleepyEnvironment() {
		try {
			EnvironmentConfig myEnvConfig = new EnvironmentConfig();
			myEnvConfig.setAllowCreate(true);
			myEnvConfig.setLocking(false);
			StoreConfig storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);

			// Open the environment and entity store
			LOG.warn(System.getProperty("user.dir"));
			environment = new Environment(new File(System.getProperty("user.dir")), myEnvConfig);
			entityStore = new EntityStore(environment, "EntityStore", storeConfig);

			categoryByIdentifier = entityStore.getPrimaryIndex(Long.class, CategoryDPL.class);

			threadByIdentifier = entityStore.getPrimaryIndex(Long.class, ThreadDPL.class);
			threadByCategory = entityStore.getSecondaryIndex(threadByIdentifier, Long.class, "category");

			postByIdentifier = entityStore.getPrimaryIndex(Long.class, PostDPL.class);
			postByThread = entityStore.getSecondaryIndex(postByIdentifier, Long.class, "parent");

			blobByIdentifier = entityStore.getPrimaryIndex(Long.class, BlobDPL.class);
			
			externalCategoryByIdentifier = entityStore.getPrimaryIndex(Long.class, ExternalCategoryDPL.class);
			
			
//			LOG.warn("Staring new category");
//			CategoryDPL cat = new CategoryDPL();
//			cat.setDescription("");
//			cat.setName("foo");
//			categoryByIdentifier.put(cat);
//			LOG.warn("Category id: " + cat.getIdentifier());
//			
//			ThreadDPL thread = new ThreadDPL();
//			thread.setCategory(cat.getIdentifier());
//			thread.setStartDate(new Date());
//			threadByIdentifier.put(thread);
//			LOG.warn("Thread id: " + thread.getIdentifier());
//			
//			BlobDPL blob = new BlobDPL();
//			blob.setData(RemoteFileGrabber.getDataFromUrl("http://www.google.com/logos/spring09.gif"));
//			blobByIdentifier.put(blob);
//			LOG.warn("Blob: "  + blob.getIdentifier());
//			
//			PostDPL post = new PostDPL();
//			post.setComment("!!");
//			post.setParent(thread.getIdentifier());
//			post.setImageIdentifier(blob.getIdentifier());
//			post.setThumbnailIdentifier(blob.getIdentifier());
//			post.setType(PostType.IMAGE);
//			postByIdentifier.put(post);
//			LOG.warn("Post: " + post.getIdentifier());
//			
			
		} catch (Exception e) {
			LOG.error("Unable to start the database.", e);
		}
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
