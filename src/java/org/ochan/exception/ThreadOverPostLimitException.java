package org.ochan.exception;

public class ThreadOverPostLimitException extends Exception {

	/**
	 * Blarh!
	 */
	private static final long serialVersionUID = -7474172798214412637L;
	
	
	private Long threadId;


	/**
	 * @return the threadId
	 */
	public Long getThreadId() {
		return threadId;
	}


	/**
	 * @param threadId the threadId to set
	 */
	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}
	
	

}
