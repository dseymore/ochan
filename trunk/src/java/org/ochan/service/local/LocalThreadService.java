package org.ochan.service.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ochan.dao.ThreadDAO;
import org.ochan.entity.Category;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.job.DeleteThreadJob;
import org.ochan.service.BlobService;
import org.ochan.service.CategoryService;
import org.ochan.service.ThreadService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Local Thread Service", objectName = "Ochan:service=local,name=LocalThreadService", logFile = "jmx.log")
public class LocalThreadService implements ThreadService {

	private ThreadDAO threadDAO;
	private CategoryService categoryService;
	private BlobService blobService;

	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;

	private static long lastSearchTime = 0;

	/**
	 * @return the createCount
	 */
	@ManagedAttribute(description = "The number of calls to create a thread")
	public long getCreateCount() {
		return createCount;
	}

	/**
	 * @return the getCount
	 */
	@ManagedAttribute(description = "The number of calls to get a thread.")
	public long getGetCount() {
		return getCount;
	}

	/**
	 * @return the deleteCount
	 */
	@ManagedAttribute(description = "The number of calls to delete a thread.")
	public long getDeleteCount() {
		return deleteCount;
	}

	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for threads.")
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
	 * @param blobService the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
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
			((ImagePost) post).setImageIdentifier(blobService.saveBlob(file));
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

	@ManagedOperation(description="Delete a Thread (delete the posts first...)!")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="identifier",description="The id of the thread as a Long object (L at the end)")
	})
	public void deleteThread(Long identifier) {
		deleteCount++;
		threadDAO.delete(identifier);
	}
	

	@ManagedOperation(description="Unlock a locked thread & reset it's count")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="identifier",description="The id of the thread as a Long object (L at the end)")
	})
	public void unlockAndResetThread(Long identifier) {
		Thread t = getThread(identifier);
		if (t != null && DeleteThreadJob.isDeleteLocked(t.getDeleteCount())){
			t.setDeleteCount(null);
			t.setDeleteDate(null);
			updateThread(t);
		}
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

	@Override
	public void updateThread(Thread thread) {
		threadDAO.update(thread);
		
	}
	
}
