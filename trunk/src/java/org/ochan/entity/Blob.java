package org.ochan.entity;


public class Blob {

	private Long identifier;

	private Byte[] data;

	
	public Blob(){
		
	}
	
	public Blob(Byte[] data, Long identifier) {
		super();
		this.data = data;
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
	 * @return the data
	 */
	public Byte[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Byte[] data) {
		this.data = data;
	}
	
	
}
