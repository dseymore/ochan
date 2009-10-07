package org.ochan.whatwhatwiki.service;

public interface FileService {

	public void createOrUpdate();
	
	public List<File> getAllFiles();
	
	public void delete(String key);
	
	public File get(String key);
}
