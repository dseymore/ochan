package org.ochan.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;


@Entity
public class BlobStatDPL {

	@PrimaryKey(sequence="BLOB_STAT")
	private Long identifier;
	
	@SecondaryKey(relate=Relationship.ONE_TO_ONE)
	private Long blobIdentifier;
	private int size;
	
	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}
	/**
	 * @return the blobIdentifier
	 */
	public Long getBlobIdentifier() {
		return blobIdentifier;
	}
	/**
	 * @param blobIdentifier the blobIdentifier to set
	 */
	public void setBlobIdentifier(Long blobIdentifier) {
		this.blobIdentifier = blobIdentifier;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	
}
