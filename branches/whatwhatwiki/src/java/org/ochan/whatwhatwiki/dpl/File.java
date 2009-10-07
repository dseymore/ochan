package org.ochan.whatwhatwiki.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class File {

	@PrimaryKey
	private String key;
	
	private Byte[] data;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
