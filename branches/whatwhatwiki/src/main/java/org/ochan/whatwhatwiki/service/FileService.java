package org.ochan.whatwhatwiki.service;

import java.util.List;

public interface FileService {

	public void createOrUpdate(String key, Byte[] data);
	
	public List<String> getAllFileKeys();
	
	public void delete(String key);
	
	public Byte[] get(String key);
}
