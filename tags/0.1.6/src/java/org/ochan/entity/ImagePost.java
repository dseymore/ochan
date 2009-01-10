package org.ochan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="IMAGE_POST")
public class ImagePost extends TextPost{
    
	private Long imageIdentifier;
	private Long thumbnailIdentifier;
    
    
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
    public ImagePost(Long identifier, Thread parent, String author, String email, String url, String comment, Long imageIdentifier, Long thumbnailIdentifier, String subject, Date time) {
        super(identifier, parent, author, email, url, comment, subject, time);
        this.imageIdentifier = imageIdentifier;
        this.thumbnailIdentifier = thumbnailIdentifier;
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
   
    
    
    
}
