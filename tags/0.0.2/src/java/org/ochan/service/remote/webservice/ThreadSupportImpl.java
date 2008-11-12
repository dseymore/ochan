package org.ochan.service.remote.webservice;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Thread;
import org.ochan.service.ThreadService;
import org.ochan.service.remote.model.RemoteThread;

@Path("/thread/")
public class ThreadSupportImpl implements ThreadSupport {

	private static final Log LOG = LogFactory.getLog(ThreadSupportImpl.class);

	private ThreadService threadService;

	/**
	 * @param threadService
	 *            the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
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

		RemoteThread remoteThread = new RemoteThread(thread.getDeleteCount(), thread.getDeleteDate(), thread.getIdentifier());
		return remoteThread;
	}

	@Override
	@ProduceMime("application/json")
	@GET
	@Path("/status/{threadId}/")
	public RemoteThread status(@PathParam("threadId") String id) {
		LOG.info("getting status for " + id);
		Thread thread = threadService.getThread(Long.valueOf(id));
		RemoteThread remoteThread = new RemoteThread(thread.getDeleteCount(), thread.getDeleteDate(), thread.getIdentifier());
		return remoteThread;
	}

	
	
}
