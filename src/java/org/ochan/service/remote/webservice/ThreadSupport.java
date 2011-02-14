package org.ochan.service.remote.webservice;

import org.ochan.service.remote.model.RemoteThread;

public interface ThreadSupport {

	public RemoteThread delete(String id);
	
	public RemoteThread status(String id);
	
	public RemoteThread next(String threadId);
}

