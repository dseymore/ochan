/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package org.ochan.service.proxy;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.dpl.replication.StateChangeListener;
import org.ochan.entity.Thread;
import org.ochan.service.ThreadService;
import org.ochan.service.proxy.config.ShardConfiguration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Proxy Threadd Service", objectName = "Ochan:type=proxy,name=ProxyThreadService", logFile = "jmx.log")
public class ProxyThreadService implements ThreadService {

	private ShardConfiguration shardConfiguration;
	private JaxWsProxyFactoryBean threadServiceClient;
	private ThreadService localThreadService;
	private Ehcache cache;
	private StateChangeListener stateChangeListener;
	
	private static final Log LOG = LogFactory.getLog(ProxyThreadService.class);
	
	@Override
	public void createThread(Long thisIdentifier, Long category, String author, String subject, String url, String email, String content, Byte[] file, String filename) {
		if (thisIdentifier != null){
			//hmm.. you're doing it wrong..
			LOG.fatal("No one should pass in an ID but ME!");
		}
		if (shardConfiguration.isShardEnabled()){
			LOG.debug("Calling shared");
			Long identifier = shardConfiguration.getSynchroService().getSync();
			ThreadService service = get(shardConfiguration.whichHost(identifier));
			service.createThread(identifier, category, author, subject, url, email, content, file, filename);
		}else if(!stateChangeListener.isMaster()){
			//replication.. we aren't the master
			LOG.debug("Sending to master node");
			ThreadService service = get(stateChangeListener.getMasterNodeName());
			service.createThread(null, category, author, subject, url, email, content, file, filename);
		}else{
			localThreadService.createThread(null, category, author, subject, url, email, content, file, filename);
		}
	}

	@Override
	public void deleteThread(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get(identifier);
			if (o != null || o.getObjectValue() != null){
				cache.remove(identifier);
			}
			ThreadService service = get(shardConfiguration.whichHost(identifier));
			service.deleteThread(identifier);
		}else if(!stateChangeListener.isMaster()){
			//replication.. we aren't the master
			LOG.debug("Sending to master node");
			ThreadService service = get(stateChangeListener.getMasterNodeName());
			service.deleteThread(identifier);
		} else {
			localThreadService.deleteThread(identifier);
		}
	}

	@Override
	public Thread getThread(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get(identifier);
			if (o != null && o.getObjectValue() != null && !o.isExpired()){
				return (Thread)o.getObjectValue();
			}
			ThreadService service = get(shardConfiguration.whichHost(identifier));
			Thread thread = service.getThread(identifier);
			cache.put(new Element(identifier, thread));
			return thread;
		} else {
			return localThreadService.getThread(identifier);
		}
	}

	@Override
	public List<Thread> retrieveThreads(ThreadCriteria criteria) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
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
			
			//now we have to normalize some of the scans..
			if (criteria.getMax() != null){
				List<Thread> nowTheThreads = new ArrayList<Thread>();
				//find the max
				Thread max = null;
				for (Thread t : threads) {
					if (max == null || t.getIdentifier().compareTo(max.getIdentifier()) > 0) {
						max = t;
					}
				}
				nowTheThreads.add(max);
				threads = nowTheThreads;
			}
			if (criteria.getNewerThan() != null){
				List<Thread> nowTheThreads = new ArrayList<Thread>();
				Thread newer = null;
				for (Thread t : threads) {
					if (newer == null && t.getIdentifier().compareTo((Long) criteria.getNewerThan()) > 0) {
						newer = t;
						break;
					}
				}
				nowTheThreads.add(newer);
				threads = nowTheThreads;
			}
			
			return threads;
		} else {
			return localThreadService.retrieveThreads(criteria);
		}
	}

	@Override
	public void updateThread(Thread thread) {
		if (shardConfiguration.isShardEnabled()) {
			LOG.debug("Calling shared");
			Element o = cache.get(thread.getIdentifier());
			if (o != null || o.getObjectValue() != null){
				cache.remove(thread.getIdentifier());
			}
			ThreadService service = get(shardConfiguration.whichHost(thread.getIdentifier()));
			service.updateThread(thread);
		}else if(!stateChangeListener.isMaster()){
			//replication.. we aren't the master
			LOG.debug("Sending to master node");
			ThreadService service = get(stateChangeListener.getMasterNodeName());
			service.updateThread(thread);
		} else {
			localThreadService.updateThread(thread);
		}
	}
	
	private synchronized ThreadService get(String host) {
		threadServiceClient.setAddress(host + "/remote/thread");
		//resetting
//		threadServiceClient.getClientFactoryBean().setClient(null);
		ThreadService client = (ThreadService) threadServiceClient.create();
		return client;
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

	/**
	 * @param cache the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	/**
	 * @param stateChangeListener the stateChangeListener to set
	 */
	public void setStateChangeListener(StateChangeListener stateChangeListener) {
		this.stateChangeListener = stateChangeListener;
	}
	

	@ManagedOperation
	public void bustCache(){
		this.cache.flush();
	}
	
	@ManagedAttribute
	public int getCacheHitCount(){
		return this.cache.getHitCount();
	}
	
	@ManagedAttribute
	public int getCacheMissCountExpired(){
		return this.cache.getMissCountExpired();
	}
	
	@ManagedAttribute
	public int getCacheMissCountNotFound(){
		return this.cache.getMissCountNotFound();
	}
	
	@ManagedAttribute
	public int getCacheSize(){
		return this.cache.getSize();
	}
	
}
