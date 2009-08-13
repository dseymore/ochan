package org.ochan.service.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.entity.Thread;
import org.ochan.service.ThreadService;
import org.ochan.service.proxy.config.ShardConfiguration;

public class ProxyThreadService implements ThreadService {

	private ShardConfiguration shardConfiguration;
	private JaxWsProxyFactoryBean threadServiceClient;
	private ThreadService localThreadService;
	private Map<String, ThreadService> modulus = new HashMap<String, ThreadService>();
	
	private static final Log LOG = LogFactory.getLog(ProxyThreadService.class);
	
	@Override
	public void createThread(Long thisIdentifier, Long category, String author, String subject, String url, String email, String content, Byte[] file, String filename) {
		if (thisIdentifier != null){
			//hmm.. you're doing it wrong..
			LOG.fatal("No one should pass in an ID but ME!");
		}
		if (shardConfiguration.isShardEnabled()){
			Long identifier = shardConfiguration.getSynchroService().getSync();
			ThreadService service = get(shardConfiguration.whichHost(identifier));
			service.createThread(identifier, category, author, subject, url, email, content, file, filename);
		}else{
			localThreadService.createThread(null, category, author, subject, url, email, content, file, filename);
		}
	}

	@Override
	public void deleteThread(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			ThreadService service = get(shardConfiguration.whichHost(identifier));
			service.deleteThread(identifier);
		} else {
			localThreadService.deleteThread(identifier);
		}
	}

	@Override
	public Thread getThread(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			ThreadService service = get(shardConfiguration.whichHost(identifier));
			return service.getThread(identifier);
		} else {
			return localThreadService.getThread(identifier);
		}
	}

	@Override
	public List<Thread> retrieveThreads(ThreadCriteria criteria) {
		if (shardConfiguration.isShardEnabled()) {
			List<Thread> threads = new ArrayList<Thread>();
			//now we need all of the things to be ready to go...
			for(String hosts : shardConfiguration.getShardHosts()){
				ThreadService service = get(hosts);
				if (service == null){
					LOG.error("Null Service for host: " + hosts);
				}else{
					List<Thread> theseThreads = service.retrieveThreads(criteria);
					if (theseThreads != null){
						threads.addAll(theseThreads);
					}
				}
			}
			return threads;
		} else {
			return localThreadService.retrieveThreads(criteria);
		}
	}

	@Override
	public void updateThread(Thread thread) {
		if (shardConfiguration.isShardEnabled()) {
			ThreadService service = get(shardConfiguration.whichHost(thread.getIdentifier()));
			service.updateThread(thread);
		} else {
			localThreadService.updateThread(thread);
		}
	}
	
	private synchronized ThreadService get(String host) {
		if (modulus.get(host) == null) {
			threadServiceClient.setAddress(host + "/remote/thread");
			//resetting
			threadServiceClient.getClientFactoryBean().setClient(null);
			ThreadService client = (ThreadService) threadServiceClient.create();
			modulus.put(host, client);
		}
		return modulus.get(host);
	}

	/**
	 * @param shardConfiguration the shardConfiguration to set
	 */
	public void setShardConfiguration(ShardConfiguration shardConfiguration) {
		this.shardConfiguration = shardConfiguration;
	}

	/**
	 * @param threadServiceClient the threadServiceClient to set
	 */
	public void setThreadServiceClient(JaxWsProxyFactoryBean threadServiceClient) {
		this.threadServiceClient = threadServiceClient;
	}

	/**
	 * @param localThreadService the localThreadService to set
	 */
	public void setLocalThreadService(ThreadService localThreadService) {
		this.localThreadService = localThreadService;
	}

	
	
}
