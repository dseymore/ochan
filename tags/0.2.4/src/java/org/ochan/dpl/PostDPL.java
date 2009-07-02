package org.ochan.dpl;

import java.io.Serializable;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

@Entity(version=1)
public class PostDPL implements Serializable{

	
	/**
	 * Generated Serial Ver. ID
	 */
	private static final long serialVersionUID = 4984578543177980498L;

	@PrimaryKey(sequence = "POSTS")
	private Long identifier;

	@SecondaryKey(relate = Relationship.MANY_TO_ONE)
	private Long parent;

	private PostType type = PostType.TEXT;

	private String author;
	private String email;
	private String url;
	private String subject;
	private Date time;

	private String comment;

	private Long imageIdentifier;
	
	private Long thumbnailIdentifier;
	
	private String filename;

	/**
	 * @return the parent
	 */
	public Long getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Long parent) {
		this.parent = parent;
	}

	/**
	 * @return the type
	 */
	public PostType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PostType type) {
		this.type = type;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
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

	
	
	
}
