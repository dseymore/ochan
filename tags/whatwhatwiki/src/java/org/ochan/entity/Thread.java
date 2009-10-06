package org.ochan.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.ochan.entity.comparator.AscendingThreadComparator;

@XmlRootElement
public class Thread implements Comparable<Thread> {

	private Long identifier;

	private Date startDate;

	/**
	 * The date of the deletion process starting
	 */
	private Date deleteDate;
	/**
	 * The number of times the thread has been requested to be deleted
	 */
	private Long deleteCount;

	private Category category;

	private List<Post> posts;

	private Comparator<Thread> comparator = new AscendingThreadComparator();

	private String enabled = "Y";

	public Thread() {

	}

	public Thread(Category category, Long deleteCount, Date deleteDate, Long identifier, List<Post> posts, Date startDate, String enabled) {
		super();
		this.category = category;
		this.deleteCount = deleteCount;
		this.deleteDate = deleteDate;
		this.identifier = identifier;
		this.posts = posts;
		this.startDate = startDate;
		if (StringUtils.isNotBlank(enabled)) {
			this.enabled = enabled;
		}
	}

	@Override
	public int compareTo(Thread o) {
		return comparator.compare(this, o);
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the posts
	 */
	public List<Post> getPosts() {
		return posts;
	}

	/**
	 * @param posts
	 *            the posts to set
	 */
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate
	 *            the deleteDate to set
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * @return the deleteCount
	 */
	public Long getDeleteCount() {
		return deleteCount;
	}

	/**
	 * @param deleteCount
	 *            the deleteCount to set
	 */
	public void setDeleteCount(Long deleteCount) {
		this.deleteCount = deleteCount;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return StringUtils.equals("Y", enabled);
	}

}
