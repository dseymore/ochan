package org.ochan.plugin.rss;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.ochan.api.PluginJob;
import org.ochan.service.ThreadService;
import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class RssJob implements PluginJob{
	/**
	 * Serial Ver. ID
	 */
	private static final long serialVersionUID = -6003329096268118142L;

	private transient ThreadService threadService;
	private Map<Long, List<String>> configuration;
	private Map<String, List<String>> seen = new HashMap<String, List<String>>();
	private static Date bootupTime = new Date();
	private static final Log LOG = LogFactory.getLog(RssJob.class);


	/**
	 * Configuration structure looks like so:
	 *
	 *
	 * Header: ochan-rss
	 * 
	  {'ochan-rss':
	  		{'categories':
	  			[{'category-id':
	  				['http://foo',
	  					'http://foo',
	  					'http://foo'
	  				]}
	  			]
	  		}
	  }
	 */
	public void configure(String json) {
		try{
			Map<Long, List<String>> tempConfiguration = new HashMap<Long, List<String>>();
			
			JSONObject obj = new JSONObject(json);
			AbstractXMLStreamReader reader = new MappedXMLStreamReader(obj);
			
			boolean found = false;
			while(reader.hasNext() && !found){
				reader.next();
				if("ochan-rss".equalsIgnoreCase(reader.getName().getLocalPart())){
					found = true;
				}
			}
			if (found){
				//ok, now lets go into it, and get the categories.
				reader.next(); //the categories heading
				reader.next(); //stepping over the array
				while(reader.hasNext()){
					reader.next(); //the category id
					String categoryId = reader.getName().getLocalPart();
					//we hit the end of the road.
					if (!"ochan-rss".equalsIgnoreCase(categoryId)){
						Long categoryIdLong = Long.valueOf(categoryId);
						if (reader.getText() != null){
							if (tempConfiguration.get(categoryIdLong) == null){
								List<String> urls = new ArrayList<String>();
								tempConfiguration.put(categoryIdLong,urls);
							}
							if (!tempConfiguration.get(categoryIdLong).contains(reader.getText())){
								LOG.info("Monitoring feed:" + reader.getText());
								tempConfiguration.get(categoryIdLong).add(reader.getText());
							}
						}
						reader.next();
					}
				}
			}
			//and now set the config.. it passed parsing.
			configuration = tempConfiguration;
		}catch(Exception e){
			//shit..
			e.printStackTrace();
		}
	}

	public void setThreadService(ThreadService service) {
		this.threadService = service;
	}

	/**
	 * Uses Informa to parse an RSS Feed. and then does awesome to make sure it doesn't double post. 
	 */
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
						if ((item.getGuid() == null || !seen.get(url).contains(item.getGuid().getLocation())) 
								&& (item.getSubject() == null || !seen.get(url).contains(item.getSubject()))
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
