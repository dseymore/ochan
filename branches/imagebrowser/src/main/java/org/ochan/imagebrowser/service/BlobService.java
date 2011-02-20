/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ochan.imagebrowser.service;

/**
 *
 * @author dseymore
 */
public interface BlobService {

    public void createOrUpdate(Long imageIdentifier, String dimension, Byte[] data);

    public Byte[] getFile(Long blobId);

    public Long findFile(Long imageId, String dimension);
}
