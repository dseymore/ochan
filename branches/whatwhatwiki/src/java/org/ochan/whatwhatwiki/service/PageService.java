package org.ochan.whatwhatwiki.service;

public interface PageService {

	public void createOrUpdate();
	
	public List<Page> getAllPages();
	
	public void delete(String key);
	
	public Page get(String key);
}
