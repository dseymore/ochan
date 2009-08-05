package org.ochan.dpl;

import java.io.Serializable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class BlobDPL implements Serializable{

	/**
	 * Generated Serial Ver. ID
	 */
	private static final long serialVersionUID = -143206161534849350L;

	@PrimaryKey(sequence = "BLOB")
	private Long identifier;
	
	private Byte[] data;

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

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}

	
	
}
