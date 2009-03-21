package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.dpl.ThreadDPL;
import org.ochan.entity.Category;
import org.ochan.entity.Thread;
import org.ochan.job.DeleteThreadJob;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.persist.EntityCursor;

@ManagedResource(description = "Local Thread Service", objectName = "Ochan:service=local,name=LocalThreadService", logFile = "jmx.log")
public class LocalThreadService implements ThreadService {

	private PostService postService;
	private SleepyEnvironment environment;
	private static final Log LOG = LogFactory.getLog(LocalThreadService.class);

	/**
	 * @param postService
	 *            the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(SleepyEnvironment environment) {
		this.environment = environment;
	}

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

	@Override
	public void createThread(Long category, String author, String subject, String url, String email, String content, Byte[] file) {
		createCount++;
		try {
			ThreadDPL thread = new ThreadDPL();
			thread.setCategory(category);
			thread.setStartDate(new Date());

			// save the thread
			environment.threadByIdentifier.put(thread);
			// save the post
			postService.createPost(thread.getIdentifier(), author, subject, email, url, content, file);
		} catch (Exception e) {
			LOG.error("Unable to store a new thread.", e);
		}
	}

	@ManagedOperation(description = "Delete a Thread (delete the posts first...)!")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "identifier", description = "The id of the thread as a Long object (L at the end)") })
	@Override
	public void deleteThread(Long identifier) {
		deleteCount++;
		try {
			environment.threadByIdentifier.delete(identifier);
		} catch (Exception e) {
			LOG.error("Unable to delete.", e);
		}
	}

	@ManagedOperation(description = "Unlock a locked thread & reset it's count")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "identifier", description = "The id of the thread as a Long object (L at the end)") })
	public void unlockAndResetThread(Long identifier) {
		Thread t = getThread(identifier);
		if (t != null && DeleteThreadJob.isDeleteLocked(t.getDeleteCount())) {
			t.setDeleteCount(null);
			t.setDeleteDate(null);
			updateThread(t);
		}
	}

	@Override
	public Thread getThread(Long identifier) {
		getCount++;
		try {
			return map(environment.threadByIdentifier.get(identifier));
		} catch (Exception e) {
			LOG.error("Unable to get.", e);
		}
		return null;
	}

	@Override
	public List<Thread> retrieveThreads(Map<ThreadCriteria, Object> criteria) {
		// capture start of call
		long start = new Date().getTime();

		List<Thread> threads = new ArrayList<Thread>();
		try {
			// first lets find by category.
			if (criteria != null && criteria.containsKey(ThreadCriteria.CATEGORY)) {
				EntityCursor<ThreadDPL> threadsByCatDPL = environment.threadByCategory.subIndex((Long) criteria.get(ThreadCriteria.CATEGORY)).entities();
				for (ThreadDPL dpl : threadsByCatDPL) {
					threads.add(map(dpl));
				}
				threadsByCatDPL.close();
			}
			// now, we have a list of category ones..
			if (criteria != null && criteria.containsKey(ThreadCriteria.DELETEQUEUE)) {
				// delete queue, means delete date is not null.
				if (threads.isEmpty()) {
					EntityCursor<ThreadDPL> allthreads = environment.threadByIdentifier.entities();
					for (ThreadDPL dpl : allthreads) {
						if (dpl.getDeleteDate() != null) {
							threads.add(map(dpl));
						}
					}
					allthreads.close();
				} else {
					// drilling down existing return
					List<Thread> newThreads = new ArrayList<Thread>();
					for (Thread t : threads) {
						if (t.getDeleteDate() != null) {
							newThreads.add(t);
						}
					}
					threads = newThreads;
				}
			}
			if (criteria != null && criteria.containsKey(ThreadCriteria.MAX)) {
				if (threads.isEmpty()) {
					EntityCursor<ThreadDPL> allthreads = environment.threadByIdentifier.entities();
					ThreadDPL max = null;
					for (ThreadDPL dpl : allthreads) {
						if (max == null || dpl.getIdentifier().compareTo(max.getIdentifier()) > 0) {
							max = dpl;
						}
					}
					if (max != null) {
						threads.add(map(max));
					}
					allthreads.close();
				} else {
					// drilling down existing return
					List<Thread> newThreads = new ArrayList<Thread>();
					Thread max = null;
					for (Thread t : threads) {
						if (max == null || t.getIdentifier().compareTo(max.getIdentifier()) > 0) {
							max = t;
						}
					}
					newThreads.add(max);
					threads = newThreads;
				}
			}
			if (criteria != null && criteria.containsKey(ThreadCriteria.NEWERTHAN)) {
				if (threads.isEmpty()) {
					EntityCursor<ThreadDPL> allthreads = environment.threadByIdentifier.entities();
					ThreadDPL newer = null;
					for (ThreadDPL dpl : allthreads) {
						if (newer == null && dpl.getIdentifier().compareTo((Long) criteria.get(ThreadCriteria.NEWERTHAN)) > 0) {
							newer = dpl;
							// stop the loop!
							break;
						}
					}
					if (newer != null) {
						threads.add(map(newer));
					}
				} else {
					// drilling down existing return
					List<Thread> newThreads = new ArrayList<Thread>();
					Thread newer = null;
					for (Thread t : threads) {
						if (newer == null && t.getIdentifier().compareTo((Long) criteria.get(ThreadCriteria.NEWERTHAN)) > 0) {
							newer = t;
							break;
						}
					}
					newThreads.add(newer);
					threads = newThreads;
				}
			}
			if (criteria != null && criteria.containsKey(ThreadCriteria.NOTDELETED)) {
				// means delete date is null.
				if (threads.isEmpty()) {
					EntityCursor<ThreadDPL> allthreads = environment.threadByIdentifier.entities();
					for (ThreadDPL dpl : allthreads) {
						if (dpl.getDeleteDate() == null) {
							threads.add(map(dpl));
						}
					}
					allthreads.close();
				} else {
					// drilling down existing return
					List<Thread> newThreads = new ArrayList<Thread>();
					for (Thread t : threads) {
						if (t.getDeleteDate() == null) {
							newThreads.add(t);
						}
					}
					threads = newThreads;
				}
			}

		} catch (Exception e) {
			LOG.error("I suck at finding the threads", e);
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;

		return threads;
	}

	@Override
	public void updateThread(Thread thread) {
		try {
			ThreadDPL dpl = environment.threadByIdentifier.get(thread.getIdentifier());
			dpl.setDeleteCount(thread.getDeleteCount());
			dpl.setDeleteDate(thread.getDeleteDate());
			// and put to update
			environment.threadByIdentifier.put(dpl);
		} catch (Exception e) {
			LOG.error("I suck at updating a thread", e);
		}
	}

	private Thread map(ThreadDPL thread) {
		Category c = new Category();
		c.setIdentifier(thread.getCategory());
		Thread t = new Thread();
		t.setDeleteCount(thread.getDeleteCount());
		t.setDeleteDate(thread.getDeleteDate());
		t.setEnabled("Y");
		t.setIdentifier(thread.getIdentifier());
		t.setStartDate(thread.getStartDate());
		t.setCategory(c);
		return t;
	}

}