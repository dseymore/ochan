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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.StatProcessorType;
import org.javasimon.Stopwatch;
import org.ochan.entity.Post;
import org.ochan.service.PostService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

/**
 * On the main page we have the 'recent' images gig. From there, we just get of
 * the most recent thumbnails that have been saved. In order for the user to be
 * able to see the thread that the thumbnail is part of, we have to walk through
 * a few hoops to find the thread and post for that thumb. Rather than doing
 * that in the big batches that the recent images feature requires; we're off
 * loading it to when the user actually clicks on an image.
 * 
 * @author dseymore
 * 
 */
@ManagedResource(description = "PostRedirect", objectName = "Ochan:util=controller,name=PostRedirect", logFile = "jmx.log")
public class PostRedirectController implements Controller {
	private static final Log LOG = LogFactory.getLog(PostRedirectController.class);

	private static final Preferences PREFS = Preferences.userNodeForPackage(PostRedirectController.class);

	private PostService postService;
	private static long numberOfRequests = 0;
	private static long lastTimeInMillis = 0;
	private static long totalTimeInMillis = 0;
	private static Stopwatch requestTime = SimonManager.getStopwatch(PostRedirectController.class.getName() + "Request");

	public PostRedirectController() {
		super();
		requestTime.setStatProcessor(StatProcessorType.BASIC.create());
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

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The number of images that have been clicked on")
	public long getNumberOfRequests() {
		return numberOfRequests;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		numberOfRequests++;
		long start = new Date().getTime();
		Split requestSplit = requestTime.start();

		// ///////////the work
		Long id = Long.valueOf(request.getParameter("identifier"));
		String url = "/chan/thread/";
		Post p = postService.getPostByBlob(id);
		if (p != null) {
			url = url + p.getParent().getIdentifier() + "#" + p.getIdentifier();
		} else {
			response.sendError(response.SC_NOT_FOUND);
		}
		// /////////////end work

		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastTimeInMillis = end - start;
		totalTimeInMillis += end - start;
		requestSplit.stop();
		return new ModelAndView(new RedirectView(url));
	}

}
