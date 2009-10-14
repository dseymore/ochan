package org.ochan.whatwhatwiki.service;

import java.util.List;

public interface FileService {

	public void createOrUpdate(String key, String author, String originalFileName, Byte[] data);
	
	public List<RemoteFile> getAllFileKeys();
        
        public RemoteFile getFile(String key);
	
	public void delete(String key);
	
	public Byte[] get(String key);
}
