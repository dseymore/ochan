package org.ochan.service.remote.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.generic.NumberTool;
import org.ochan.job.StatsGeneratorJob;
import org.ochan.service.remote.model.RemoteStatistics;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@Path("/stats/")
@ManagedResource(description = "RESTful statistic grabber", objectName = "Ochan:type=rest,name=Stats", logFile = "jmx.log")
public class InstanceStatisticsImpl implements InstanceStatistics {

	private static final Log LOG = LogFactory.getLog(PostListImpl.class);

	private static Long STAT_GET_COUNT = Long.valueOf(0);


	/**
	 * @return the nextGetCount
	 */
	@ManagedAttribute(description="The number of calls received for current statistics.")
	public Long getStatGetCount() {
		return STAT_GET_COUNT;
	}

	
	@ProduceMime("application/json")
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

}
