package org.ochan.service.remote.webservice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;
import org.ochan.job.DeleteThreadJob;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.service.remote.model.RemotePost;
import org.ochan.service.remote.model.RemoteThread;
import org.springframework.jmx.export.annotation.ManagedAttribute;

@Path("/thread/")
public class ThreadSupportImpl implements ThreadSupport {

	private static final Log LOG = LogFactory.getLog(ThreadSupportImpl.class);

	private ThreadService threadService;
	private PostService postService;

	private static Long NEXT_THREAD_GET_COUNT = Long.valueOf(0);
	
	/**
	 * @param threadService
	 *            the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @param postService the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	
	/**
	 * @return the nextGetCount
	 */
	@ManagedAttribute(description="The number of calls received for a next thread. This is used by the ActiveWatcherCounterJob to determine how many watcher windows are open.")
	public Long getNextGetCount() {
		return NEXT_THREAD_GET_COUNT;
	}

	@Override
	@ProduceMime("application/json")
	@GET
	@Path("/delete/{threadId}/")
	public RemoteThread delete(@PathParam("threadId") String id) {
		LOG.info("Marking " + id + " for deletion");
		Thread thread = threadService.getThread(Long.valueOf(id));
		if (thread.getDeleteDate() == null) {
			thread.setDeleteDate(new Date());
		} else {
			thread.setDeleteDate(null);
		}
		Long count = thread.getDeleteCount();
		if (count == null) {
			count = Long.valueOf(0);
		}
		thread.setDeleteCount(Long.valueOf(count.longValue() + 1));
		threadService.updateThread(thread);

		RemoteThread remoteThread = new RemoteThread(thread.getDeleteCount(), thread.getDeleteDate(), null, thread.getIdentifier(), DeleteThreadJob.isDeleteLocked(thread.getDeleteCount()));
		return remoteThread;
	}

	@Override
	@ProduceMime("application/json")
	@GET
	@Path("/status/{threadId}/")
	public RemoteThread status(@PathParam("threadId") String id) {
		LOG.info("getting status for " + id);
		Thread thread = threadService.getThread(Long.valueOf(id));
		//we may have had a thread delete while someone watched it. 
		if (thread != null){
			RemoteThread remoteThread = new RemoteThread(thread.getDeleteCount(), thread.getDeleteDate(), null, thread.getIdentifier(), DeleteThreadJob.isDeleteLocked(thread.getDeleteCount()));
			return remoteThread;
		}
		return null;
	}


	/**
	 * Supports TOAST on the main page.
	 */
	@Override
	@ProduceMime("application/json")
	@GET
	@Path("/next/{threadId}/")
	public RemoteThread next(@PathParam("threadId") String threadId){
		NEXT_THREAD_GET_COUNT++;
		try{
			ThreadCriteria criteria = new ThreadService.ThreadCriteria();
			criteria.setNewerThan(Long.valueOf(threadId));
			
			List<Thread> threads = threadService.retrieveThreads(criteria);
			if (threads != null && threads.size() > 0){
				Thread thread = threads.get(0);
				List<Post> posts = postService.retrieveThreadPosts(thread);
				//we have posts AND they've had 10 seconds to brew up their thumbnail
				if (posts.size() > 0 && posts.get(0).getTime().getTime() + 10000 < new Date().getTime()){
					RemotePost toReturnPost = new RemotePost(posts.get(0));
					RemoteThread toReturn = new RemoteThread(thread.getDeleteCount(), 
							thread.getDeleteDate(), toReturnPost, thread.getIdentifier(), DeleteThreadJob.isDeleteLocked(thread.getDeleteCount()));
					return toReturn;
				}
			}
		}catch(Exception e){
			LOG.error("unable to find next thread?", e);
		}
		RemoteThread rt = new RemoteThread();
		rt.setIdentifier(Long.valueOf(-1));
		return rt;
	}
}
