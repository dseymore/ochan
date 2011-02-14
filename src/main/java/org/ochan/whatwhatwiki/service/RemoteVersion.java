package org.ochan.whatwhatwiki.service;

import java.util.Comparator;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RemoteVersion")
public class RemoteVersion implements Comparable<RemoteVersion> {

    private Long version;
    private String content;
    private String author;
    private Date date;
    private String comment;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
