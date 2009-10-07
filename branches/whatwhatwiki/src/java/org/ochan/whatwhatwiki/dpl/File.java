package org.ochan.whatwhatwiki.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class File {

	@PrimaryKey
	private String key;
	
	private Byte[] data;
}
