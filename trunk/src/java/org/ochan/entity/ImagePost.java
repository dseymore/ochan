/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package org.ochan.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="image")
public class ImagePost extends TextPost{
    
	private Long imageIdentifier;
	private Long thumbnailIdentifier;
	private String filename;
	private String fileSize;
    
    
    public ImagePost(){
        super();
    }
    
    /**
     * 
     * @param identifier
     * @param parent
     * @param author
     * @param email
     * @param url
     * @param comment
     * @param data
     * @param thumnbail
     * @param subject
     * @param time
     */
    public ImagePost(Long identifier, Thread parent, String author, String email, String url, String comment, Long imageIdentifier, Long thumbnailIdentifier, String subject, Date time, String filename, String fileSize) {
        super(identifier, parent, author, email, url, comment, subject, time);
        this.imageIdentifier = imageIdentifier;
        this.thumbnailIdentifier = thumbnailIdentifier;
        this.filename = filename;
        this.fileSize = fileSize;
    }

	/**
	 * @return the imageIdentifier
	 */
	public Long getImageIdentifier() {
		return imageIdentifier;
	}

	/**
	 * @param imageIdentifier the imageIdentifier to set
	 */
	public void setImageIdentifier(Long imageIdentifier) {
		this.imageIdentifier = imageIdentifier;
	}

	/**
	 * @return the thumbnailIdentifier
	 */
	public Long getThumbnailIdentifier() {
		return thumbnailIdentifier;
	}

	/**
	 * @param thumbnailIdentifier the thumbnailIdentifier to set
	 */
	public void setThumbnailIdentifier(Long thumbnailIdentifier) {
		this.thumbnailIdentifier = thumbnailIdentifier;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
   
    
    
    
}
