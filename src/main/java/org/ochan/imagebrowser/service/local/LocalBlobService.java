/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.imagebrowser.service.local;

import com.sleepycat.persist.EntityCursor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.imagebrowser.dpl.BlobDPL;
import org.ochan.imagebrowser.dpl.SleepyEnvironment;
import org.ochan.imagebrowser.service.BlobService;

/**
 *
 * @author dseymore
 */
public class LocalBlobService implements BlobService {

    private static final Log LOG = LogFactory.getLog(LocalImageService.class);
    private SleepyEnvironment sleepyEnvironment;

    public void setSleepyEnvironment(SleepyEnvironment sleepyEnvironment) {
        this.sleepyEnvironment = sleepyEnvironment;
    }

    @Override
    public void createOrUpdate(Long imageIdentifier, String dimension, Byte[] data) {
        BlobDPL f = new BlobDPL();
        f.setData(data);
        f.setDimension(dimension);
        f.setImage(imageIdentifier);
        try {
            sleepyEnvironment.blobById.put(f);
        } catch (Exception e) {
            LOG.error("Unable to persist: " + imageIdentifier, e);
        }
    }

    @Override
    public Long findFile(Long imageId, String dimension) {
        try {
            EntityCursor<BlobDPL> versions = sleepyEnvironment.blobsByFile.subIndex(imageId).entities();
            for (BlobDPL blob : versions) {
                if (blob.getDimension().equalsIgnoreCase(dimension)){
                    return blob.getIdentifier();
                }
            }
        } catch (Exception e) {
            LOG.error("Unable to grab key: " + imageId + " " + dimension, e);
        }
        return null;
    }

    @Override
    public Byte[] getFile(Long blobId) {
        try{
            BlobDPL dpl = sleepyEnvironment.blobById.get(blobId);
            return dpl.getData();
        } catch (Exception e){
            LOG.error("Unable to get: " + blobId,e);
        }
        return null;
    }
}
