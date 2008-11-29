package org.ochan.service.remote.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RemoteThread")
public class RemoteThread {
	
	private Long identifier;
	private Date deleteDate;
	private Long deleteCount;
	private RemotePost firstPost;
	
	public RemoteThread(){
		
	}
	
	public RemoteThread(Long deleteCount, Date deleteDate, RemotePost firstPost, Long identifier) {
		super();
		this.deleteCount = deleteCount;
		this.deleteDate = deleteDate;
		this.firstPost = firstPost;
		this.identifier = identifier;
	}

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
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
	 * @return the firstPost
	 */
	public RemotePost getFirstPost() {
		return firstPost;
	}

	/**
	 * @param firstPost the firstPost to set
	 */
	public void setFirstPost(RemotePost firstPost) {
		this.firstPost = firstPost;
	}
	
	
	
	
	
}
