/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.imagebrowser.service.local;

import com.sleepycat.persist.EntityCursor;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.imagebrowser.dpl.ImageDPL;
import org.ochan.imagebrowser.dpl.SleepyEnvironment;
import org.ochan.imagebrowser.service.ImageService;
import org.ochan.imagebrowser.service.RemoteImage;
import org.ochan.whatwhatwiki.service.RemoteFile;

/**
 *
 * @author dseymore
 */
public class LocalImageService implements ImageService {

    private static final Log LOG = LogFactory.getLog(LocalImageService.class);
    private SleepyEnvironment sleepyEnvironment;

    public void setSleepyEnvironment(SleepyEnvironment sleepyEnvironment) {
        this.sleepyEnvironment = sleepyEnvironment;
    }

    @Override
    public void createOrUpdate(String absolutePath) {
        ImageDPL f = new ImageDPL();
        f.setAbsolutePath(absolutePath);
        try {
            sleepyEnvironment.imagebyId.put(f);
        } catch (Exception e) {
            LOG.error("Unable to persist: " + absolutePath, e);
        }
    }

    @Override
    public List<RemoteImage> getAllImages() {
        List<RemoteImage> remoteFileList = new ArrayList<RemoteImage>();
        try {
            EntityCursor<ImageDPL> cursor = sleepyEnvironment.imagebyId.entities();
            for (ImageDPL p : cursor) {
                remoteFileList.add(map(p));
            }
            cursor.close();

            Collections.sort(remoteFileList);
        } catch (Exception e) {
            LOG.error("Unable to list!", e);
        }
        return remoteFileList;
    }

    @Override
    public RemoteImage getFile(Long Id) {
        try{
            return map(sleepyEnvironment.imagebyId.get(Id));
        }catch (Exception e) {
            LOG.error("Unable to list!", e);
        }
        return null;
    }


    private RemoteImage map(ImageDPL file) {
        RemoteImage remoteFile = new RemoteImage();
        remoteFile.setAbsolutePath(file.getAbsolutePath());
        return remoteFile;
    }

    private ImageDPL map(RemoteImage remoteFile) {
        ImageDPL f = new ImageDPL();
        f.setAbsolutePath(remoteFile.getAbsolutePath());
        return f;
    }
}
