package org.ochan.whatwhatwiki.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

@Entity(version=1)
public class Page {

	@PrimaryKey
	private String key;
	
	private List<Version> versions;
}
