package org.ochan.plugin.rss;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.net.URL;

import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.ochan.api.PluginJob;
import org.ochan.service.ThreadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.org.catnip.eddie.parser.Parser;
import uk.org.catnip.eddie.FeedData;
import uk.org.catnip.eddie.Entry;
import uk.org.catnip.eddie.Detail;



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
				 	URL u = new URL(url);
					Parser p = new Parser();
					FeedData data = p.parse(u.openStream());
					Iterator entries = data.entries();
					while(entries.hasNext()){
						Entry item = (Entry)entries.next();
						if (seen.get(url) == null){
							seen.put(url,new ArrayList());
						}
                                                //we do guid & subject because
                                                //some people dont do guids, and some people dont do subjects.
                                                //also, lets stop OLD things from being put into the category... just in case we restart the ochan or plugin..
                                                //just lame to have dupes, or have dupes delete real content.
						//System.out.println(item);
						String id = item.get("id");
						
						if ( (id == null || !seen.get(url).contains(id) ) && item.getModified().getTime() >= bootupTime.getTime()){
							seen.get(url).add(id);
							threadService.createThread(categoryId, "Ochan-RSS", item.getTitle() == null ? "" : item.getTitle().getValue(), id, "", item.getSummary().getValue(), null);
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
