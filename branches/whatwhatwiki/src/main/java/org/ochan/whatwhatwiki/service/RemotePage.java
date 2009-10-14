package org.ochan.whatwhatwiki.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.comparators.ComparableComparator;

public class RemotePage implements Comparable<RemotePage> {

    private String key;
    private List<RemoteVersion> versions;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the versions
     */
    public List<RemoteVersion> getVersions() {
        return versions;
    }

    /**
     * @param versions the versions to set
     */
    public void setVersions(List<RemoteVersion> versions) {
        this.versions = versions;
    }

    public String getLatest() {
        if (versions != null && versions.size() > 0) {
            Collections.sort(versions);
            return versions.get(0).getContent();
        }
        return null;
    }

    public void addVersion(RemoteVersion remoteVersion) {
        if (versions == null) {
            versions = new ArrayList<RemoteVersion>();
        }
        versions.add(remoteVersion);
    }

    @Override
    public int compareTo(RemotePage o) {
        return new ComparableComparator().compare(this.getKey(), o.getKey());
    }
}
