package org.ochan.service.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.service.BlobService;
import org.ochan.service.proxy.config.ShardConfiguration;

public class ProxyBlobService implements BlobService {

	private ShardConfiguration shardConfiguration; 
	private JaxWsProxyFactoryBean blobServiceClient;
	private BlobService localBlobService;
	private Map<String, BlobService> modulus = new HashMap<String, BlobService>();
	
	private static final Log LOG = LogFactory.getLog(ProxyBlobService.class);
		
	

	/**
	 * @param shardConfiguration the shardConfiguration to set
	 */
	public void setShardConfiguration(ShardConfiguration shardConfiguration) {
		this.shardConfiguration = shardConfiguration;
	}

	/**
	 * @param blobServiceClient the blobServiceClient to set
	 */
	public void setBlobServiceClient(JaxWsProxyFactoryBean blobServiceClient) {
		this.blobServiceClient = blobServiceClient;
	}

	/**
	 * @param localBlobService the localBlobService to set
	 */
	public void setLocalBlobService(BlobService localBlobService) {
		this.localBlobService = localBlobService;
	}

	@Override
	public void deleteBlob(Long identifier) {
		if (shardConfiguration.isShardEnabled()){
			BlobService service = get(shardConfiguration.whichHost(identifier));
			service.deleteBlob(identifier);
		}else{
			localBlobService.deleteBlob(identifier);
		}
	}

	@Override
	public List<Long> getAllIds() {
		if (shardConfiguration.isShardEnabled()){
			List<Long> ids = new ArrayList<Long>();
			for(String hosts : shardConfiguration.getShardHosts()){
				ids.addAll(get(hosts).getAllIds());
			}
			return ids;
		}else{
			return localBlobService.getAllIds();
		}
	}

	@Override
	public Byte[] getBlob(Long identifier) {
		if (shardConfiguration.isShardEnabled()){
			BlobService service = get(shardConfiguration.whichHost(identifier));
			return service.getBlob(identifier);
		}else{
			return localBlobService.getBlob(identifier);
		}
	}

	@Override
	public int getBlobSize(Long identifier) {
		if (shardConfiguration.isShardEnabled()){
			BlobService service = get(shardConfiguration.whichHost(identifier));
			return service.getBlobSize(identifier);
		}else{
			return localBlobService.getBlobSize(identifier);
		}
	}

	@Override
	public Long saveBlob(Byte[] byteArray, Long id) {
		if (id != null){
			//hmm.. you're doing it wrong..
			LOG.fatal("No one should pass in an ID but ME!");
		}
		if (shardConfiguration.isShardEnabled()){
			Long identifier = shardConfiguration.getSynchroService().getSync();
			BlobService service = get(shardConfiguration.whichHost(identifier));
			return service.saveBlob(byteArray, identifier);
		}else{
			//normal behaviour
			return localBlobService.saveBlob(byteArray, null);
		}
	}

	private synchronized BlobService get(String host){
		if (modulus.get(host) == null){
			blobServiceClient.setAddress(host + "/remote/blob");
			//resetting
			blobServiceClient.getClientFactoryBean().setClient(null);
			BlobService client = (BlobService)blobServiceClient.create();
			modulus.put(host, client);
		}
		return modulus.get(host);
	}
}
