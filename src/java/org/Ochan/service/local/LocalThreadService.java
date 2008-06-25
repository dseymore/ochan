package org.Ochan.service.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.Ochan.dao.ThreadDAO;
import org.Ochan.entity.Category;
import org.Ochan.entity.ImagePost;
import org.Ochan.entity.Post;
import org.Ochan.entity.TextPost;
import org.Ochan.entity.Thread;
import org.Ochan.service.CategoryService;
import org.Ochan.service.ThreadService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Local Thread Service", objectName = "Ochan:service=local,name=LocalThreadService", logFile = "jmx.log")
public class LocalThreadService implements ThreadService {

	private ThreadDAO threadDAO;
	private CategoryService categoryService;

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
	 * @return the threadDAO
	 */
	public ThreadDAO getThreadDAO() {
		return threadDAO;
	}

	/**
	 * @param threadDAO
	 *            the threadDAO to set
	 */
	public void setThreadDAO(ThreadDAO threadDAO) {
		this.threadDAO = threadDAO;
	}

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService() {
		return categoryService;
	}

	/**
	 * @param categoryService
	 *            the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * 
	 */
	public void createThread(Long categoryId, String author, String subject, String url, String email, String content, Byte[] file) {
		createCount++;
		// build a category
		Thread threadToCreate = new Thread();
		// mark the moment
		Date now = new Date();

		Category cat = categoryService.getCategory(categoryId);
		// make a post
		List<Post> posts = new ArrayList<Post>();
		Post post = null;
		if (file != null && file.length > 0) {
			post = new ImagePost();
			((ImagePost) post).setData(file);
		} else {
			post = new TextPost();
		}
		post.setAuthor(author);
		post.setSubject(subject);
		((TextPost) post).setComment(content);
		post.setEmail(email);
		post.setUrl(url);
		post.setTime(now);
		// relate
		post.setParent(threadToCreate);
		// group
		posts.add(post);

		// relate categories
		threadToCreate.setCategory(cat);
		// relate posts
		threadToCreate.setPosts(posts);
		threadToCreate.setStartDate(now);

		threadDAO.create(threadToCreate);
	}

	public void deleteThread(Long identifier) {
		deleteCount++;
		// TODO Auto-generated method stub
	}

	public Thread getThread(Long identifier) {
		getCount++;
		return threadDAO.getThread(identifier);
	}

	public List<Thread> retrieveThreads(Map<ThreadCriteria, Object> criteria) {
		// capture start of call
		long start = new Date().getTime();
		if (criteria == null || criteria.isEmpty()) {
			throw new IllegalArgumentException("You dont honestly expect me to dump my data to you...");
		}
		
		List<Thread> result = threadDAO.search(criteria); 
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;

		return result;
	}

}
