package org.ochan.whatwhatwiki.service.local;

import com.sleepycat.persist.EntityCursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.whatwhatwiki.dpl.File;
import org.ochan.whatwhatwiki.dpl.SleepyEnvironment;
import org.ochan.whatwhatwiki.service.FileService;
import org.ochan.whatwhatwiki.service.RemoteFile;

public class LocalFileService implements FileService {

    private static final Log LOG = LogFactory.getLog(LocalFileService.class);
    private SleepyEnvironment sleepyEnvironment;

    public void setSleepyEnvironment(SleepyEnvironment sleepyEnvironment) {
        this.sleepyEnvironment = sleepyEnvironment;
    }

    @Override
    public void createOrUpdate(String key, String author, String originalFileName, Byte[] data) {
        File f = new File();
        f.setKey(key);
        f.setDate(new Date());
        f.setAuthor(author);
        f.setOriginalFileName(originalFileName);
        f.setData(data);
        try {
            sleepyEnvironment.fileByKey.put(f);
        } catch (Exception e) {
            LOG.error("Unable to persist: " + key, e);
        }
    }

    @Override
    public void delete(String key) {
        try {
            sleepyEnvironment.fileByKey.delete(key);
        } catch (Exception e) {
            LOG.error("Unable to delete:" + key);
        }
    }

    @Override
    public Byte[] get(String key) {
        try {
            return sleepyEnvironment.fileByKey.get(key).getData();
        } catch (Exception e) {
            LOG.error("Unable to retrieve file bytes:" + key, e);
        }
        return null;
    }

    @Override
    public List<RemoteFile> getAllFileKeys() {
        List<RemoteFile> remoteFileList = new ArrayList<RemoteFile>();
        try {
            EntityCursor<File> cursor = sleepyEnvironment.fileByKey.entities();
            for (File p : cursor) {
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
    public RemoteFile getFile(String key) {
        try {
            return map(sleepyEnvironment.fileByKey.get(key));
        } catch (Exception e) {
            LOG.error("Unable to retrieve file:" + key, e);
        }
        return null;
    }

    private RemoteFile map(File file) {
        RemoteFile remoteFile = new RemoteFile();
        remoteFile.setAuthor(file.getAuthor());
        remoteFile.setKey(file.getKey());
        remoteFile.setOriginalFileName(file.getOriginalFileName());
        remoteFile.setDate(file.getDate());
        return remoteFile;
    }

    private File map(RemoteFile remoteFile, Byte[] data) {
        File f = new File();
        f.setAuthor(remoteFile.getAuthor());
        f.setData(data);
        f.setKey(remoteFile.getKey());
        f.setOriginalFileName(remoteFile.getOriginalFileName());
        return f;
    }
}
