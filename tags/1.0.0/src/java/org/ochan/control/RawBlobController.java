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
package org.ochan.control;

import java.util.Date;
import java.util.prefs.Preferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.StatProcessorType;
import org.javasimon.Stopwatch;
import org.ochan.service.BlobService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * The recent images dude shouldn't go through the thumbnail controller, since
 * it is post based so, this goes RIGHT from the blob id to output.. very nice.
 * 
 * @author dseymore
 */
@ManagedResource(description = "RawBlob", objectName = "Ochan:util=controller,name=RawBlob", logFile = "jmx.log")
public class RawBlobController implements Controller {

	private static final Log LOG = LogFactory.getLog(RawBlobController.class);

	private static final Preferences PREFS = Preferences.userNodeForPackage(RawBlobController.class);

	private BlobService blobService;
	private static long numberOfRequests = 0;
	private static long lastTimeInMillis = 0;
	private static long totalTimeInMillis = 0;
	private static Stopwatch requestTime = SimonManager.getStopwatch(PostRedirectController.class.getName() + "Request");

	public RawBlobController() {
		super();
		requestTime.setStatProcessor(StatProcessorType.BASIC.create());
	}

	public static final int MILLISECONDS_IN_A_DAY = 60 * 60 * 24 * 1000;

	/**
	 * @param blobService
	 *            the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The average time in milliseconds the system is encountering on thumnbail requests")
	public long getAverageTimeInMillis() {
		if (totalTimeInMillis != 0 && numberOfRequests != 0) {
			return totalTimeInMillis / numberOfRequests;
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The last time we generated retrieved in milliseconds")
	public long getLastTimeInMIllis() {
		return lastTimeInMillis;
	}

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The total time in milliseconds it has taken to retrieve all images")
	public long getTotalTimeInMIllis() {
		return totalTimeInMillis;
	}

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The number of images that have been clicked on")
	public long getNumberOfRequests() {
		return numberOfRequests;
	}

	/**
	 * 
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		numberOfRequests++;
		long start = new Date().getTime();
		Split requestSplit = requestTime.start();

		// ///////////the work
		Long id = Long.valueOf(request.getParameter("identifier"));
		Byte[] data = blobService.getBlob(id);
		byte[] datum = ArrayUtils.toPrimitive(data);

		// caching please.. expire after a day
		response.setHeader("Cache-Control", "max-age=86400, public");
		response.setDateHeader("Expires", new Date().getTime() + MILLISECONDS_IN_A_DAY);

		if (datum == null) {
			datum = ThumbnailController.getFailImageData(request);
		}
		LOG.debug("file length is " + datum.length);
		response.setContentLength(datum.length);
		response.setContentType("image/jpeg");
		response.setHeader("Content-Disposition", " inline; filename=" + id + ".jpg");

		FileCopyUtils.copy(datum, response.getOutputStream());
		// /////////////end work

		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastTimeInMillis = end - start;
		totalTimeInMillis += end - start;
		requestSplit.stop();
		
		return null;
	}
}
