package org.ochan.whatwhatwiki.service;

import java.util.List;

public class RemotePage {

	private String key;
	private List<RemoteVersion> versions;
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
	 * @return the versions
	 */
	public List<RemoteVersion> getVersions() {
		return versions;
	}
	/**
	 * @param versions the versions to set
	 */
	public void setVersions(List<RemoteVersion> versions) {
		this.versions = versions;
	}
	
}
