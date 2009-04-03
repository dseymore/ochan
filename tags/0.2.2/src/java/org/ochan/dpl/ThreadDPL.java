package org.ochan.dpl;

import java.io.Serializable;
import java.util.Date;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

@Entity
public class ThreadDPL implements Serializable{

	/**
	 * Generated Serial Ver. ID
	 */
	private static final long serialVersionUID = -5123259953647511143L;

	@PrimaryKey(sequence = "THREADS")
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

	@SecondaryKey(relate = Relationship.MANY_TO_ONE)
	private Long category;

	private boolean enabled = true;

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate the deleteDate to set
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
	 * @param deleteCount the deleteCount to set
	 */
	public void setDeleteCount(Long deleteCount) {
		this.deleteCount = deleteCount;
	}

	/**
	 * @return the category
	 */
	public Long getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Long category) {
		this.category = category;
	}

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
}
