package org.ochan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "EXT_CATEGORY")
public class ExternalCategory {

	private String name;
	private String host;

	@Lob
	private String longDescription;

	@Id
	@Column(updatable = false, unique = true)
	@SequenceGenerator(name = "EXT_CATEGORY_SEQUENCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXT_CATEGORY_SEQUENCE")
	private Long identifier;

	@Version
	@Column(name = "OPTLOCK")
	private Integer version;
	
	public ExternalCategory(){
		
	}

	public ExternalCategory(String name, String host, String longDescription,
			Long identifier, Integer version) {
		super();
		this.name = name;
		this.host = host;
		this.longDescription = longDescription;
		this.identifier = identifier;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
