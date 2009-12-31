package org.ochan.service.proxy;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;
import org.ochan.service.PostService;
import org.ochan.service.proxy.config.ShardConfiguration;

public class ProxyPostService implements PostService {

	private ShardConfiguration shardConfiguration;
	private JaxWsProxyFactoryBean postServiceClient;
	private PostService localPostService;
	private Ehcache cache;

	private static final Log LOG = LogFactory.getLog(ProxyPostService.class);

	/**
	 * @param shardConfiguration
	 *            the shardConfiguration to set
	 */
	public void setShardConfiguration(ShardConfiguration shardConfiguration) {
		this.shardConfiguration = shardConfiguration;
	}

	/**
	 * @param postServiceClient
	 *            the postServiceClient to set
	 */
	public void setPostServiceClient(JaxWsProxyFactoryBean postServiceClient) {
		this.postServiceClient = postServiceClient;
	}

	/**
	 * @param localPostService
	 *            the localPostService to set
	 */
	public void setLocalPostService(PostService localPostService) {
		this.localPostService = localPostService;
	}

	@Override
	public String computerAuthor(String author) {
		return localPostService.computerAuthor(author);
	}
	
	/**
	 * @param cache the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	@Override
	public void createPost(Long thisIdentifer, Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file, String filename) {
		if (thisIdentifer != null){
			//hmm.. you're doing it wrong..
			LOG.fatal("No one should pass in an ID but ME!");
		}
		if (shardConfiguration.isShardEnabled()){
			Long identifier = shardConfiguration.getSynchroService().getSync();
			PostService service = get(shardConfiguration.whichHost(identifier));
			service.createPost(identifier, parentIdentifier, author, subject, email, url, comment, file, filename);
		}else{
			localPostService.createPost(null, parentIdentifier, author, subject, email, url, comment, file, filename);
		}
	}

	@Override
	public void deletePost(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			Element o = cache.get(identifier);
			if (o != null || o.getObjectValue() != null){
				cache.remove(identifier);
			}
			PostService service = get(shardConfiguration.whichHost(identifier));
			service.deletePost(identifier);
		} else {
			localPostService.deletePost(identifier);
		}
	}

	@Override
	public Post getPost(Long identifier) {
		if (shardConfiguration.isShardEnabled()) {
			Element o = cache.get(identifier);
			if (o != null && o.getObjectValue() != null && !o.isExpired()){
				return (Post)o.getObjectValue();
			}
			PostService service = get(shardConfiguration.whichHost(identifier));
			Post post = service.getPost(identifier);
			cache.put(new Element(identifier, post));
			return post;
		} else {
			return localPostService.getPost(identifier);
		}
	}

	@Override
	public List<Post> retrieveThreadPosts(Long parent) {
		if (shardConfiguration.isShardEnabled()) {
			List<Post> posts = new ArrayList<Post>();
			//now we need all of the things to be ready to go...
			for(String hosts : shardConfiguration.getShardHosts()){
				PostService service = get(hosts);
				if (service == null){
					LOG.error("Null Service for host: " + hosts);
				}else{
					Thread t = new Thread();
					
					List<Post> postss = service.retrieveThreadPosts(parent);
					if (postss != null){
						posts.addAll(postss);
					}
				}
			}
			return posts;
		} else {
			return localPostService.retrieveThreadPosts(parent);
		}
	}

	@Override
	public void updatePost(Post post) {
		if (shardConfiguration.isShardEnabled()) {
			Element o = cache.get(post.getIdentifier());
			if (o != null || o.getObjectValue() != null){
				cache.remove(post.getIdentifier());
			}
			PostService service = get(shardConfiguration.whichHost(post.getIdentifier()));
			service.updatePost(post);
		} else {
			localPostService.updatePost(post);
		}
	}

	private synchronized PostService get(String host) {
		postServiceClient.setAddress(host + "/remote/post");
		//resetting
		postServiceClient.getClientFactoryBean().setClient(null);
		PostService client = (PostService) postServiceClient.create();
		return client;
	}

}