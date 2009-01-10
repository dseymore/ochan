package org.ochan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="BLOB")
public class Blob {

	@Id
	@Column(updatable = false, unique = true)
	@SequenceGenerator(name = "BLOB_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BLOB_SEQUENCE")
	private Long identifier;

	@Lob
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
