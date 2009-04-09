package org.ochan.plugin.rss;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ochan.api.PluginJob;
import org.ochan.service.ThreadService;



public class RssJob implements PluginJob{
	/**
	 * Serial Ver. ID
	 */
	private static final long serialVersionUID = -6003329096268118142L;

	private transient ThreadService threadService;
	private Map<Long, List<String>> configuration;
	private Map<String, List<String>> seen = new HashMap<String, List<String>>();
	private static Date bootupTime = new Date();

	@Override
	public void configure(String json) {
		configuration = new HashMap<Long, List<String>>();
		// temporary until we write the configuration utils.
		List<String> urls = new ArrayList<String>();
		urls.add("http://dseymore.tumblr.com/rss");
		configuration.put(Long.valueOf(1), urls);
	}

	@Override
	public void setThreadService(ThreadService service) {
		this.threadService = service;
	}

	/**
	 * Uses Informa to parse an RSS Feed. and then does awesome to make sure it doesn't double post. 
	 */
	@Override
	public void execute() {
		for (Long categoryId : configuration.keySet()){
			for(String url: configuration.get(categoryId)){
				try{
					ChannelIF channel = FeedParser.parse(new ChannelBuilder(), url);
					for(ItemIF item : channel.getItems()){
						if (seen.get(url) == null){
							seen.put(url, new ArrayList());
						}
						//we do guid & subject because
						//some people dont do guids, and some people dont do subjects.
						//also, lets stop OLD things from being put into the category... just in case we restart the ochan or plugin..
						//just lame to have dupes, or have dupes delete real content. 
						if (!seen.get(url).contains(item.getGuid().getLocation()) && (item.getSubject() == null || !seen.get(url).contains(item.getSubject()))
								&& item.getDate().getTime() >= bootupTime.getTime()){
							seen.get(url).add(item.getGuid().getLocation());
							if (item.getSubject() != null){
								seen.get(url).add(item.getSubject());
							}
							threadService.createThread(categoryId, "Ochan-RSS", item.getSubject() == null ? "" : item.getSubject(), item.getLink().toString(), "", item.getDescription(), null);
						}
					}
				}catch(Exception e){
					//grr
					e.printStackTrace();
				}
				
			}
		}

	}

}
