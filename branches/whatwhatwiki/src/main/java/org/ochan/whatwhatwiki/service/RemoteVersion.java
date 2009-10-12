package org.ochan.whatwhatwiki.service;

import java.util.Comparator;

public class RemoteVersion implements Comparable<RemoteVersion> {

    private Long version;
    private String content;
    private Comparator comparator = new AscRemoteVersionComparator();

    /**
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(RemoteVersion o) {
        return comparator.compare(this, o);
    }
}
