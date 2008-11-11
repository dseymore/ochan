package org.ochan.service.local;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ochan.dao.PostDAO;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Local Post Service", objectName = "Ochan:service=local,name=LocalPostService", logFile = "jmx.log")
public class LocalPostService implements PostService {

	private PostDAO postDAO;
	private ThreadService threadService;

	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;

	private static long lastSearchTime = 0;

	/**
	 * @return the createCount
	 */
	@ManagedAttribute(description = "The number of calls to create a category")
	public long getCreateCount() {
		return createCount;
	}

	/**
	 * @return the getCount
	 */
	@ManagedAttribute(description = "The number of calls to get a category.")
	public long getGetCount() {
		return getCount;
	}

	/**
	 * @return the deleteCount
	 */
	@ManagedAttribute(description = "The number of calls to delete a category.")
	public long getDeleteCount() {
		return deleteCount;
	}

	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for categories.")
	public long getLastSearchTime() {
		return lastSearchTime;
	}

	// END STATS

	/**
	 * @return the postDAO
	 */
	public PostDAO getPostDAO() {
		return postDAO;
	}

	/**
	 * @param postDAO
	 *            the postDAO to set
	 */
	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	/**
	 * @return the threadService
	 */
	public ThreadService getThreadService() {
		return threadService;
	}

	/**
	 * @param threadService
	 *            the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @see org.ochan.service.PostService#createPost(java.lang.Long,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.Byte[])
	 */
	public void createPost(Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file) {
		createCount++;
		TextPost p = null;
		if (file == null || file.length == 0) {
			p = new TextPost();
		} else {
			p = new ImagePost();
			((ImagePost) p).setData(file);
		}
		p.setAuthor(author);
		p.setSubject(subject);
		p.setEmail(email);
		p.setUrl(url);
		p.setComment(comment);
		p.setTime(new Date());
		// retrieve our parent thread
		Thread t = threadService.getThread(parentIdentifier);
		t.setPosts(this.retrieveThreadPosts(t));
		t.getPosts().add(p);
		p.setParent(t);

		postDAO.create(p);
	}

	/**
	 * @see org.ochan.service.PostService#deletePost(java.lang.Long)
	 */
	public void deletePost(Long identifier) {
		deleteCount++;
		postDAO.delete(identifier);

	}

	/**
	 * @see org.ochan.service.PostService#getPost(java.lang.Long)
	 */
	public Post getPost(Long identifier) {
		getCount++;
		return postDAO.getPost(identifier);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.Ochan.service.PostService#retrieveThreadPosts(Thread)
	 */
	public List<Post> retrieveThreadPosts(Thread parent) {
		// capture start of call
		long start = new Date().getTime();
		List<Post> list = postDAO.getPostsByThread(parent);
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		
		return list;
	}


	/**
	 * @see org.ochan.service.PostService#retrievePosts(java.util.Map)
	 */
	public List<Post> retrievePosts(Map<PostCriteria, String> criteria) {
		// capture start of call
		long start = new Date().getTime();
		
		// TODO Auto-generated method stub
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		
		return null;
	}

	/**
	 * @see org.ochan.service.PostService#updatePost(org.ochan.entity.Post)
	 */
	public void updatePost(Post post) {
		postDAO.update(post);
	}

}
