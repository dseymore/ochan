package org.ochan.plugin.rss;

import org.ochan.api.PluginJob;
import org.ochan.service.ThreadService;

public class RssJob implements PluginJob{

	public void setThreadService(ThreadService service){
		//NOTHING
	}

	public void execute(){
		System.out.println("Hello World");	
	}

}
