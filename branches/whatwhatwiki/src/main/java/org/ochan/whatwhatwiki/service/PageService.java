package org.ochan.whatwhatwiki.service;

import java.util.List;

public interface PageService {

	public void createOrUpdate(RemotePage page);
	
	public List<RemotePage> getAllPages();
	
	public void delete(String key);
	
	public RemotePage get(String key);
}
