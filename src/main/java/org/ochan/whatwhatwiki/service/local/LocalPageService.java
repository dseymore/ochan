package org.ochan.whatwhatwiki.service.local;

import com.sleepycat.persist.EntityCursor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.whatwhatwiki.dpl.Page;
import org.ochan.whatwhatwiki.dpl.SleepyEnvironment;
import org.ochan.whatwhatwiki.dpl.Version;
import org.ochan.whatwhatwiki.service.PageService;
import org.ochan.whatwhatwiki.service.RemotePage;
import org.ochan.whatwhatwiki.service.RemoteVersion;

public class LocalPageService implements PageService {

    private static final Log LOG = LogFactory.getLog(LocalPageService.class);
    private SleepyEnvironment sleepyEnvironment;

    /**
     * @param sleepyEnvironment
     *            the sleepyEnvironment to set
     */
    public void setSleepyEnvironment(SleepyEnvironment sleepyEnvironment) {
        this.sleepyEnvironment = sleepyEnvironment;
    }

    @Override
    public void createOrUpdate(RemotePage page) {
        if (page == null) {
            throw new IllegalArgumentException("You gave me nothing!?");
        }
        if (page.getKey() == null) {
            throw new IllegalArgumentException("I need a key!!!");
        }
        // loop through the versions, make sure there is only 1 new one.
        boolean foundOne = false;
        RemoteVersion versionToSave = null;
        for (RemoteVersion version : page.getVersions()) {
            if (version.getVersion() == null) {
                if (foundOne) {
                    throw new IllegalArgumentException("What!? two versions coming to me at once? thats odd, dawg.");
                }
                foundOne = true;
                versionToSave = version;
            }
        }
        if (!foundOne) {
            throw new IllegalArgumentException("I cannot persist nothing... give me a version you want to save for this key..");
        }
        // ok, error checking is over with.
        Page p = map(page);
        Version v = map(page.getKey(), versionToSave);
        try {
            sleepyEnvironment.pageByKey.put(p);
            sleepyEnvironment.versionById.put(v);
        } catch (Exception e) {
            LOG.error("Unable to persist this version", e);
            throw new IllegalStateException("What just happened!", e);
        }
    }

    @Override
    public void delete(String key) {
        // TODO Auto-generated method stub
    }

    @Override
    public RemotePage get(String key) {
        try {
            return map(sleepyEnvironment.pageByKey.get(key));
        } catch (Exception e) {
            LOG.error("Unable to grab key: " + key, e);
        }
        return null;
    }

    @Override
    public List<RemotePage> getAllPages() {
        List<RemotePage> remotePageList = new ArrayList<RemotePage>();
        try {
            EntityCursor<Page> cursor = sleepyEnvironment.pageByKey.entities();
            for (Page p : cursor) {
                remotePageList.add(map(p));
            }
            cursor.close();
        } catch (Exception e) {
            LOG.error("Unable to list!", e);
        }
        return remotePageList;
    }

    private Page map(RemotePage remotePage) {
        Page page = new Page();
        page.setKey(remotePage.getKey());
        return page;
    }

    private RemotePage map(Page page) {
        RemotePage remotePage = new RemotePage();
        remotePage.setKey(page.getKey());
        return remotePage;
    }

    private Version map(String key, RemoteVersion remoteVersion) {
        Version version = new Version();
        version.setContent(remoteVersion.getContent());
        version.setKey(key);
        version.setIdentifier(remoteVersion.getVersion());

        return version;
    }

    private RemoteVersion map(Version version) {
        RemoteVersion remoteVersion = new RemoteVersion();
        remoteVersion.setContent(version.getContent());
        remoteVersion.setVersion(version.getIdentifier());
        return remoteVersion;
    }
}
