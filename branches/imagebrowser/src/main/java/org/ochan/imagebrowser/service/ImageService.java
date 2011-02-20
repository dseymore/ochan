/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ochan.imagebrowser.service;

import java.util.List;

/**
 *
 * @author dseymore
 */
public interface ImageService {
	public void createOrUpdate(String absolutePath);

	public List<RemoteImage> getAllImages();

        public RemoteImage getFile(Long Id);

}
