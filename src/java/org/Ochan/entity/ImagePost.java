package org.Ochan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="IMAGE_POST")
public class ImagePost extends TextPost{
    
    @Lob
    private Byte[] data;
    
    @Lob 
    private Byte[] thumbnail;
    
    
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
    public ImagePost(Long identifier, Thread parent, String author, String email, String url, String comment, Byte[] data, Byte[] thumnbail, String subject, Date time) {
        super(identifier, parent, author, email, url, comment, subject, time);
        this.data = data;
        this.thumbnail = thumnbail;
    }

    /**
     * @return the data
     */
    public Byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Byte[] data) {
        this.data = data;
    }

	/**
	 * @return the thumbnail
	 */
	public Byte[] getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(Byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}
    
    
}
