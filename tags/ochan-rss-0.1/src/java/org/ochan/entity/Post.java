package org.ochan.entity;

import java.util.Comparator;
import java.util.Date;

import org.ochan.entity.comparator.AscendingPostComparator;

public abstract class Post implements Comparable<Post> {

	private Long identifier;

	private Thread parent;

	private String author;

	private String email;

	private String url;

	private String subject;

	private Date time;

	private Comparator<Post> comparator = new AscendingPostComparator();

	public Post() {

	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Post o) {
		return comparator.compare(this, o);
	}

	public Post(Long identifier, Thread parent, String author, String email, String url, String subject, Date time) {
		super();
		this.identifier = identifier;
		this.parent = parent;
		this.author = author;
		this.email = email;
		this.url = url;
		this.subject = subject;
		this.time = time;
	}

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the parent
	 */
	public Thread getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Thread parent) {
		this.parent = parent;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
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
	 * @param email
	 *            the email to set
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
	 * @param url
	 *            the url to set
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
	 * @param subject
	 *            the subject to set
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
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
}
