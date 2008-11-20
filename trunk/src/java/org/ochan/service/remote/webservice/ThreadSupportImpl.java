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
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.service.remote.model.RemotePost;
import org.ochan.service.remote.model.RemoteThread;

@Path("/thread/")
public class ThreadSupportImpl implements ThreadSupport {

	private static final Log LOG = LogFactory.getLog(ThreadSupportImpl.class);

	private ThreadService threadService;
	private PostService postService;

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
			count = new Long(0);
		}
		thread.setDeleteCount(Long.valueOf(count.longValue() + 1));
		threadService.updateThread(thread);

		RemoteThread remoteThread = new RemoteThread(thread.getDeleteCount(), thread.getDeleteDate(), null, thread.getIdentifier());
		return remoteThread;
	}

	@Override
	@ProduceMime("application/json")
	@GET
	@Path("/status/{threadId}/")
	public RemoteThread status(@PathParam("threadId") String id) {
		LOG.info("getting status for " + id);
		Thread thread = threadService.getThread(Long.valueOf(id));
		RemoteThread remoteThread = new RemoteThread(thread.getDeleteCount(), thread.getDeleteDate(), null, thread.getIdentifier());
		return remoteThread;
	}

	
	@Override
	@ProduceMime("application/json")
	@GET
	@Path("/next/{threadId}/")
	public RemoteThread next(@PathParam("threadId") String threadId){
		try{
			Map<ThreadCriteria, Object> criteria = new HashMap<ThreadCriteria, Object>();
			criteria.put(ThreadCriteria.NEWERTHAN, Long.valueOf(threadId));
			List<Thread> threads = threadService.retrieveThreads(criteria);
			if (threads != null && threads.size() > 0){
				Thread thread = threads.get(0);
				List<Post> posts = postService.retrieveThreadPosts(thread);
				if (posts.size() > 0){
					RemotePost toReturnPost = new RemotePost(posts.get(0));
					RemoteThread toReturn = new RemoteThread(thread.getDeleteCount(), 
							thread.getDeleteDate(), toReturnPost, thread.getIdentifier());
					return toReturn;
				}
			}
		}catch(Exception e){
			LOG.error("unable to find next thread?", e);
		}
		RemoteThread rt = new RemoteThread();
		rt.setIdentifier(new Long(-1));
		return rt;
	}
}
