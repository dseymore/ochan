/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.imagebrowser.service;

import org.apache.commons.collections.comparators.ComparableComparator;

/**
 *
 * @author dseymore
 */
public class RemoteImage implements Comparable<RemoteImage> {

    @Override
    public int compareTo(RemoteImage o) {
        return new ComparableComparator().compare(this.getAbsolutePath(), o.getAbsolutePath());
    }
    private Long identifier;
    private String absolutePath;

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long Identifier) {
        this.identifier = Identifier;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
