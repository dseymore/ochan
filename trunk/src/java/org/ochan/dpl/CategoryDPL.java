package org.ochan.dpl;

import java.io.Serializable;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

@Entity(version=1)
public class CategoryDPL implements Serializable{

	
	/**
	 * Generated Serial Ver. ID
	 */
	private static final long serialVersionUID = -2736914815724878570L;

	@PrimaryKey(sequence = "CATEGORY")
	private Long identifier;

	private String name;
	private String description;
	
	@SecondaryKey(relate=Relationship.ONE_TO_ONE)
	private String code; 
	
	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	
	

}
