package org.ochan.atom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Person;
import org.apache.abdera.model.Content.Type;
import org.apache.abdera.parser.stax.FOMFactory;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;
import org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Category;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.util.DeploymentConfiguration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Syndication wrapper", objectName = "Ochan:type=feed,name=ThreadCollector", logFile = "jmx.log")
public class ThreadCollectionAdapter extends AbstractEntityCollectionAdapter<Thread> {

	private static final Log LOG = LogFactory.getLog(ThreadCollectionAdapter.class);
	//Statistics
	private static long getCount = 0;
	private static long lastSearchTime = 0;
	
	private ThreadService threadService;
	private CategoryService categoryService;
	private PostService postService;
	
	private Ehcache cache;
	
	
	
	/**
	 * @param cache the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	/**
	 * @return the threadService
	 */
	public ThreadService getThreadService() {
		return threadService;
	}

	/**
	 * @param threadService the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService() {
		return categoryService;
	}

	/**
	 * @param categoryService the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * @return the postService
	 */
	public PostService getPostService() {
		return postService;
	}

	/**
	 * @param postService the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	/**
	 * @return the getCount
	 */
	@ManagedAttribute(description = "The number of calls to get the feed")
	public long getGetCount() {
		return getCount;
	}
	
	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for threads.")
	public long getLastSearchTime() {
		return lastSearchTime;
	}
	
	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#deleteEntry(java.lang.String, org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public void deleteEntry(String resourceName, RequestContext request) throws ResponseContextException {
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getContent(java.lang.Object, org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public Object getContent(Thread entry, RequestContext request) throws ResponseContextException {
		FOMFactory ff = new FOMFactory();
		Content content = ff.newContent(Type.HTML);
		content.setValue(((TextPost)entry.getPosts().get(0)).getComment());
		return content;
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getEntries(org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public Iterable<Thread> getEntries(RequestContext request) throws ResponseContextException {
		getCount++;
		long start = new Date().getTime();
		List<Thread> toreturn = new ArrayList<Thread>();
		Element o = cache.get("1");
		
		//Category filtration
		Long categoryId = null;
		//and thread filtration
		Long threadId = null;
		//lets see if we're getting a specific category, or thread, or the entire bundle. 
		if (request.getUri().toString() != null && request.getUri().toString().split("/").length > 3){
			String[] split = request.getUri().toString().split("/");
			categoryId = Long.valueOf(split[3]);
			if (request.getUri().toString().split("/").length > 4){
				threadId = Long.valueOf(split[4]);
			}
		}
		
		if (threadId == null){
			if (o == null || o.getObjectValue() == null || o.isExpired()){
				List<Category> categories = categoryService.retrieveCategories(null);
				for (Category c : categories){
					Map<ThreadCriteria,Object> criteria = new HashMap<ThreadCriteria,Object>();
					criteria.put(ThreadCriteria.CATEGORY, c.getIdentifier());
					List<Thread> threads = threadService.retrieveThreads(criteria);
					//categories have 0 threads to begin with.. 
					if (threads != null){
						for (Thread thread : threads){
							thread.setPosts(postService.retrieveThreadPosts(thread));
							toreturn.add(thread);
						}
					}
				}
				Collections.sort(toreturn);
				cache.put(new Element("1",toreturn));
			}else{
				//unsafe!
				toreturn = (ArrayList<Thread>)o.getObjectValue();
			}
			if (categoryId != null && threadId == null){
				//filtering by category
				//lets filter it out to be just what we want from this category.
				List<Thread> threadsForCategory = new ArrayList<Thread>();
				for (Thread t : toreturn){
					if (categoryId.equals(t.getCategory().getIdentifier())){
						threadsForCategory.add(t);
					}
				}
				toreturn = threadsForCategory;
			}
		}else if (categoryId != null && threadId != null){
			//we have to do some acrobatics to make this a list of threads... 
			Thread parent = threadService.getThread(threadId);
			List<Post> posts = postService.retrieveThreadPosts(parent);
			Collections.sort(posts);
			parent.setPosts(posts);
			List<Thread> mockThreads = new ArrayList<Thread>();
			//add the honest parent
			for (Post post : posts){
				if (mockThreads.isEmpty()){
					//the first one is the parent
					mockThreads.add(parent);
				}else{
					Thread t = new Thread();
					//use the parent's identifer because thats how we link to it
					t.setIdentifier(parent.getIdentifier());
					List<Post> children = new ArrayList<Post>();
					children.add(post);
					t.setPosts(children);
					mockThreads.add(t);
				}
			}
			toreturn = mockThreads;
		}
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		
		return toreturn;
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getEntry(java.lang.String, org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public Thread getEntry(String resourceName, RequestContext request) throws ResponseContextException {
		return null;
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getId(java.lang.Object)
	 */
	@Override
	public String getId(Thread entry) throws ResponseContextException {
		return String.valueOf(entry.getIdentifier());
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getName(java.lang.Object)
	 */
	@Override
	public String getName(Thread entry) throws ResponseContextException {
		if (StringUtils.isNotEmpty(entry.getPosts().get(0).getSubject())){
			return String.valueOf(entry.getPosts().get(0).getSubject());
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
			return sdf.format(entry.getPosts().get(0).getTime());
		}
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getTitle(java.lang.Object)
	 */
	@Override
	public String getTitle(Thread entry) throws ResponseContextException {
		if (StringUtils.isNotEmpty(entry.getPosts().get(0).getSubject())){
			return String.valueOf(entry.getPosts().get(0).getSubject());
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
			return sdf.format(entry.getPosts().get(0).getTime());
		}
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getUpdated(java.lang.Object)
	 */
	@Override
	public Date getUpdated(Thread entry) throws ResponseContextException {
		return entry.getPosts().get(0).getTime();
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#postEntry(java.lang.String, org.apache.abdera.i18n.iri.IRI, java.lang.String, java.util.Date, java.util.List, org.apache.abdera.model.Content, org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public Thread postEntry(String title, IRI id, String summary, Date updated, List<Person> authors, Content content, RequestContext request) throws ResponseContextException {
		
		return null;
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#putEntry(java.lang.Object, java.lang.String, java.util.Date, java.util.List, java.lang.String, org.apache.abdera.model.Content, org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public void putEntry(Thread entry, String title, Date updated, List<Person> authors, String summary, Content content, RequestContext request) throws ResponseContextException {
		
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractCollectionAdapter#getAuthor(org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public String getAuthor(RequestContext request) throws ResponseContextException {
		return "ochan";
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractCollectionAdapter#getId(org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public String getId(RequestContext request) {
		return "Threads";
	}

	/**
	 * @see org.apache.abdera.protocol.server.CollectionInfo#getTitle(org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public String getTitle(RequestContext request) {
		return "Recent Threads";
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractCollectionAdapter#getHref()
	 */
	@Override
	public String getHref() {
		return "/atom/atom/threads";
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractCollectionAdapter#getHref(org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	public String getHref(RequestContext request) {
		return "/atom/atom/threads";
	}

	/**  
	 * The default generated urls dont really work..they try and go down into the atompub spec
	 * to find the feed item.. which, i dont wanna do. and then on top of that, it is always setup as an
	 * edit link, which most readers dont understand or give a flying shit about.
	 * 
	 * which causes us to override the addEntryDetails method. 
	 * 
	 * So, lets generate a nice url.
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#getLink(java.lang.String, java.lang.Object, org.apache.abdera.i18n.iri.IRI, org.apache.abdera.protocol.server.RequestContext)
	 */
	@Override
	protected String getLink(String name, Thread entryObj, IRI feedIri, RequestContext request) {
		DeploymentConfiguration config = new DeploymentConfiguration(); 
		String link = "http://"+config.getHostname() + ":" + config.getPort() + "/chan/thread/" + entryObj.getIdentifier() + "#" + entryObj.getPosts().get(0).getIdentifier();
		return link;
	}

	/**
	 * @see org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter#addEntryDetails(org.apache.abdera.protocol.server.RequestContext, org.apache.abdera.model.Entry, org.apache.abdera.i18n.iri.IRI, java.lang.Object)
	 */
	@Override
	protected String addEntryDetails(RequestContext request, Entry e, IRI feedIri, Thread entryObj) throws ResponseContextException {
		String realLink = getLink(getName(entryObj),entryObj, feedIri, request);
		String ret = super.addEntryDetails(request, e, feedIri, entryObj);
		//Atom spec says the url to the item that the feed entry is about, is the 'alternate' link, not an edit link.. what a hassle
		e.addLink(realLink, "alternate");
		return ret;
	}
	
	
	

}
