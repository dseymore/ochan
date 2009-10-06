package org.ochan.entity;


public class ExternalCategory {

	private String name;
	private String host;

	private String longDescription;
	private Long identifier;

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
