package org.ochan.whatwhatwiki.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.collections.comparators.ComparableComparator;

@XmlRootElement(name = "RemotePage")
public class RemotePage implements Comparable<RemotePage> {

    private String key;
    private List<RemoteVersion> versions;
    private String latest;

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
	latest = getLatest();
    }

    public String getLatest() {
        if (versions != null && versions.size() > 0) {
            Collections.sort(versions);
            return versions.get(versions.size() - 1).getContent();
        }
        return null;
    }

    public void setLatest(String latest){
	this.latest = latest;
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
