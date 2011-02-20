/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.imagebrowser.dpl;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.imagebrowser.service.RemoteImage;

/**
 *
 * @author dseymore
 */
public class SleepyEnvironment {

    private static final Log LOG = LogFactory.getLog(SleepyEnvironment.class);
    private Environment environment;
    private EntityStore entityStore;

    public PrimaryIndex<Long, ImageDPL> imagebyId;
    public PrimaryIndex<Long, BlobDPL> blobById;
    public SecondaryIndex<Long, Long, BlobDPL> blobsByFile;
    public SecondaryIndex<String, Long, BlobDPL> blobsByDimension;

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
			LOG.warn("Storing Database in: " + System.getProperty("user.dir"));
			environment = new Environment(new File(System.getProperty("user.dir")), myEnvConfig);
			entityStore = new EntityStore(environment, "EntityStore", storeConfig);

			imagebyId = entityStore.getPrimaryIndex(Long.class, ImageDPL.class);
			blobById = entityStore.getPrimaryIndex(Long.class, BlobDPL.class);
			blobsByFile = entityStore.getSecondaryIndex(blobById, Long.class, "image");
                        blobsByDimension = entityStore.getSecondaryIndex(blobById, String.class, "dimension");

                        

		} catch (Exception e) {
			LOG.error("Unable to start the database.", e);
		}

                try {
                    String root = System.getProperty("browser.root");
                    if (root == null || StringUtils.isEmpty(root)){
                        root = System.getProperty("user.dir");
                    }
                    LOG.warn("Root of file browser deployment: (set -Dbrowser.root= to override)" + System.getProperty("browser.root"));
                    File rootF = new File(root);
                    if (rootF.exists()){
                        List<RemoteImage> list = file(rootF);
                        Collections.sort(list);
                        for (RemoteImage image : list){
                            ImageDPL dpl = new ImageDPL();
                            dpl.setAbsolutePath(image.getAbsolutePath());
                            this.imagebyId.put(dpl);
                        }
                    }else{
                        LOG.error("Root doesn't exist!");
                    }
                }catch (Exception e){
                    LOG.error("Unable to index the filesystem this browser is configured against",e);
                }
	}

        /**
         * Recursive method to add all files :)
         * @param f
         */
        private List<RemoteImage> file(File f){
            List<RemoteImage> list = new ArrayList<RemoteImage>();
            if (f.isDirectory()){
                for (File phil : f.listFiles()){
                    list.addAll(file(phil));
                }
            }else{
                String path = f.getAbsolutePath();
                if (path.endsWith("png") || path.endsWith("jpg") || path.endsWith("jpeg")){
                    RemoteImage image = new RemoteImage();
                    image.setAbsolutePath(path);
                    try{
                        list.add(image);
                        LOG.info("Added "+path);
                    }catch(Exception e){
                        LOG.error("Unable to add image @" + path,e);
                    }
                }
            }
            return list;
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
