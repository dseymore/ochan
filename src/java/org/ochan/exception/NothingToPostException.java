package org.ochan.exception;

public class NothingToPostException extends Exception {

	/**
	 * Serial Ver. ID
	 */
	private static final long serialVersionUID = -5997548496125785086L;
	private Long threadId;
	private Long categoryId;

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId
	 *            the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the threadId
	 */
	public Long getThreadId() {
		return threadId;
	}

	/**
	 * @param threadId
	 *            the threadId to set
	 */
	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

}
