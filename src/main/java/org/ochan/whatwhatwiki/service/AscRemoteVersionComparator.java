/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.whatwhatwiki.service;

import java.util.Comparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author denki
 */
public class AscRemoteVersionComparator implements Comparator<RemoteVersion> {

    @Override
    public int compare(RemoteVersion o1, RemoteVersion o2) {
        if (o1 != null && o2 != null) {
            ComparatorChain chain = new ComparatorChain();
            chain.addComparator(new ComparableComparator());
            return chain.compare(o1.getVersion(), o2.getVersion());
        }else{
            return new ComparableComparator().compare(o1, o2);
        }
    }
}
