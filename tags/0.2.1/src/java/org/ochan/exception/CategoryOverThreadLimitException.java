package org.ochan.exception;

public class CategoryOverThreadLimitException extends Exception {

	/**
	 * Blahgsakh!
	 */
	private static final long serialVersionUID = -2336906358623593180L;
	
	private Long categoryId;

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
	
}
