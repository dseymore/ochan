/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.ochan.service.remote.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.generic.NumberTool;
import org.ochan.dpl.BlobType;
import org.ochan.job.StatsGeneratorJob;
import org.ochan.service.BlobService;
import org.ochan.service.remote.model.RemoteGroup;
import org.ochan.service.remote.model.RemoteStatistics;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 
 * @author dseymore
 * 
 */
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
	@ManagedAttribute(description = "The number of calls received for current statistics.")
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
		// STATISTICS! YAY!
		NumberTool nt = new NumberTool();
		stats.setTotalContentSize(nt.format(StatsGeneratorJob.getSizeOfAllFiles()));
		stats.setNumberOfFiles(nt.format(StatsGeneratorJob.getNumberOfFiles()));
		stats.setNumberOfPosts(nt.format(StatsGeneratorJob.getNumberOfPosts()));
		stats.setPostswithImages(nt.format(StatsGeneratorJob.getNumberOfImagePosts()));
		stats.setNumberOfThreads(nt.format(StatsGeneratorJob.getNumberOfThreads()));

		return stats;
	}

	@GET
	@ProduceMime("application/json")
	@Path("/images/")
	@Override
	public RemoteGroup getImages() {
		RemoteGroup list = new RemoteGroup();
		// we actually want to return the ids of the full images
		// because the thumbnail controller already does the work to find the
		// thread
		// for this full id and grab the already generated thumb for it.
		// a future enhacnement would be to just use the id as it is.. instead
		// of all that work looking things up.
		for (Long id : blobService.getLast50Blobs(BlobType.THUMB)) {
			list.add(id);
		}
		return list;
	}

}
