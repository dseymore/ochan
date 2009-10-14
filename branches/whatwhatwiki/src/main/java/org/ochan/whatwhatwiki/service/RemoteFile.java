/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.whatwhatwiki.service;

import java.util.Date;
import org.apache.commons.collections.comparators.ComparableComparator;

/**
 *
 * @author denki
 */
public class RemoteFile implements Comparable<RemoteFile>{

    @Override
    public int compareTo(RemoteFile o) {
        return new ComparableComparator().compare(this.getKey(), o.getKey());
    }

    private String key;
    private String author;
    private String originalFileName;
    private Date date;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
