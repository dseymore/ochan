package org.ochan.api;

import java.io.Serializable;

import org.ochan.service.ThreadService;

public interface PluginJob extends Serializable{
	
	public void setThreadService(ThreadService threadService);
	public void execute();
}
