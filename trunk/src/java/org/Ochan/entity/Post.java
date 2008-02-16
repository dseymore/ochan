package org.Ochan.entity;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.Ochan.entity.comparator.AscendingPostComparator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "POST")
public abstract class Post implements Comparable<Post> {

	@Id
	@Column(updatable = false, unique = true)
	@SequenceGenerator(name = "POST_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQUENCE")
	private Long identifier;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "THREAD_IDENTIFIER", insertable = true, updatable = false)
	private Thread parent;

	private String author;

	private String email;

	private String url;

	private String subject;

	private Date time;

	@Transient
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
