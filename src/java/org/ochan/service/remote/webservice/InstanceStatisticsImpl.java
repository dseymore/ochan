package org.ochan.service.remote.webservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.generic.NumberTool;
import org.ochan.dpl.BlobType;
import org.ochan.job.StatsGeneratorJob;
import org.ochan.service.BlobService;
import org.ochan.service.remote.model.RemoteLong;
import org.ochan.service.remote.model.RemoteStatistics;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@Path("/stats/")
@ManagedResource(description = "RESTful statistic grabber", objectName = "Ochan:type=rest,name=Stats", logFile = "jmx.log")
public class InstanceStatisticsImpl implements InstanceStatistics {

	private static final Log LOG = LogFactory.getLog(PostListImpl.class);

	private static Long STAT_GET_COUNT = Long.valueOf(0);


	private BlobService blobService;
	
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}


	/**
	 * @return the nextGetCount
	 */
	@ManagedAttribute(description="The number of calls received for current statistics.")
	public Long getStatGetCount() {
		return STAT_GET_COUNT;
	}

	
	@Produces("application/json")
    @GET
	@Override
	@Path("/current/")
	public RemoteStatistics getStatistics() {
		STAT_GET_COUNT++;
		RemoteStatistics stats = new RemoteStatistics();
		//STATISTICS! YAY!
		NumberTool nt = new NumberTool();
		stats.setTotalContentSize(nt.format(StatsGeneratorJob.getSizeOfAllFiles()));
		stats.setNumberOfFiles(nt.format(StatsGeneratorJob.getNumberOfFiles()));
		stats.setNumberOfPosts(nt.format(StatsGeneratorJob.getNumberOfPosts()));
		stats.setPostswithImages(nt.format(StatsGeneratorJob.getNumberOfImagePosts()));
		stats.setNumberOfThreads(nt.format(StatsGeneratorJob.getNumberOfThreads()));
		
		return stats;
	}


	@GET
	@Produces("application/json")
	@Path("/images/")
	@Override
	public Collection<RemoteLong> getImages() {
		List<RemoteLong> list = new ArrayList<RemoteLong>();
		for(Long id : blobService.getLast50Blobs(BlobType.THUMB)){
			list.add(new RemoteLong(id));
		}
		return list;
	}
	
	

}
