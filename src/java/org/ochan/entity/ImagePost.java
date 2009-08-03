package org.ochan.entity;

import java.util.Date;

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
