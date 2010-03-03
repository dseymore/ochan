package org.ochan.service.remote.webservice;

import java.util.Collection;

import org.ochan.service.remote.model.RemoteLong;
import org.ochan.service.remote.model.RemoteStatistics;

public interface InstanceStatistics {
	
	public Collection<RemoteLong> getImages();

	public RemoteStatistics getStatistics();
}
