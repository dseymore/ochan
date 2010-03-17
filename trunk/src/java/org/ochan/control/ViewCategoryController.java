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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Category;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.exception.CategoryOverThreadLimitException;
import org.ochan.exception.NothingToPostException;
import org.ochan.form.ThreadForm;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.util.DeploymentConfiguration;
import org.ochan.util.PostLinksAFixARockerJocker;
import org.ochan.util.RemoteFileGrabber;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * This produces the list of threads for the category view.
 * 
 * @author dseymore
 * 
 */
@ManagedResource(description = "ViewCategory", objectName = "Ochan:util=controller,name=ViewCategory", logFile = "jmx.log")
public class ViewCategoryController extends SimpleFormController {
	private static final Log LOG = LogFactory.getLog(ViewCategoryController.class);

	public static final int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;

	private CategoryService categoryService;
	private ThreadService threadService;
	private PostService postService;
	private String viewName;

	private static long numberOfRequests = 0;

	/**
	 * Statistics
	 * 
	 * @return
	 */
	@ManagedAttribute(description = "The number of thumbnails that have been requested")
	public long getNumberOfRequests() {
		return numberOfRequests;
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
	 * @return the threadService
	 */
	public ThreadService getThreadService() {
		return threadService;
	}

	/**
	 * @param threadService
	 *            the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @return the postService
	 */
	public PostService getPostService() {
		return postService;
	}

	/**
	 * @param postService
	 *            the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName
	 *            the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BindException e, Map map) throws Exception {
		numberOfRequests++;
		if (StringUtils.isEmpty(httpServletRequest.getParameter("identifier"))) {
			return new ModelAndView(new RedirectView("/categoryList.Ochan"));
		}

		Map controlModel = new HashMap();
		Long identifier = null;
		Category cat = null;
		// lets handle those codes :D
		if (StringUtils.isNumeric(httpServletRequest.getParameter("identifier"))) {
			identifier = Long.valueOf(httpServletRequest.getParameter("identifier"));
			cat = categoryService.getCategory(identifier);
		} else {
			cat = categoryService.getCategoryByCode(httpServletRequest.getParameter("identifier"));
			identifier = cat.getIdentifier();
		}
		if (cat != null) {
			ThreadCriteria criteria = new ThreadService.ThreadCriteria();
			criteria.setCategory(cat.getIdentifier());
			List<Thread> threads = getThreadService().retrieveThreads(criteria);
			cat.setThreads(threads);
			if (threads != null) {
				for (Thread t : threads) {
					t.setPosts(getPostService().retrieveThreadPosts(t.getIdentifier()));
					//FIXME - there could potentially be a problem with a thread/post relationship..
					//if there is no posts for that thread, don't add it to the list. 
					for (Post p : t.getPosts()) {
						if (p instanceof TextPost) {
							TextPost tp = (TextPost) p;
							tp.setComment(PostLinksAFixARockerJocker.fixMahLinks(tp, false));
							// now lets try and truncate what could be a
							// GIGANTIC amount of text
							if (tp.getComment().split("</p>").length >= 2) {
								String[] split = tp.getComment().split("</p>");
								tp.setComment(split[0] + split[1] + "</p>...");
							} else if (tp.getComment().split("<br>").length >= 2) {
								String[] split = tp.getComment().split("<br>");
								tp.setComment(split[0] + split[1] + "<br>...");
							}
						}
					}
				}
				Collections.sort(cat.getThreads());
			}
			controlModel.put("category", cat);
			// lets play with cookies to remember the author name
			if (httpServletRequest.getCookies() != null) {
				for (Cookie cookie : httpServletRequest.getCookies()) {
					if ("ochanAuthor".equals(cookie.getName())) {
						httpServletRequest.getSession().setAttribute("author", cookie.getValue());
					}
				}
			}

			String author = (String) httpServletRequest.getSession().getAttribute("author");
			if (StringUtils.isNotEmpty(author)) {
				controlModel.put("author", author);
			} else {
				controlModel.put("author", "Anonymous");
			}

			// categories, unlike threads, can have 0 children.
			if (cat.getThreads() != null) {
				controlModel.put("blockPosts", DeploymentConfiguration.enforceThreadLimit(cat.getThreads().size()));
			}
		} else {
			// dead category! 404 thing
			return new ModelAndView("404", controlModel);
		}
		return new ModelAndView(viewName, controlModel);
	}

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object o, BindException e) throws Exception {
		// FIXME: cut and paste from ThreadAddController... ick
		// move this to a service?
		ThreadForm tf = (ThreadForm) o;

		// handling limits
		final Long categoryIdentifier = Long.valueOf(tf.getCategoryIdentifier());
		ThreadCriteria criteria = new ThreadService.ThreadCriteria();
		criteria.setCategory(categoryIdentifier);
		final List<Thread> threads = getThreadService().retrieveThreads(criteria);
		if (threads != null && DeploymentConfiguration.enforceThreadLimit(threads.size())) {
			CategoryOverThreadLimitException exception = new CategoryOverThreadLimitException();
			exception.setCategoryId(categoryIdentifier);
			throw exception;
		}

		// save the username in the session.
		request.getSession().setAttribute("author", tf.getAuthor());

		boolean found = false;
		// lets play with cookies to remember the author name
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("ochanAuthor".equals(cookie.getName())) {
					found = true;
					cookie.setValue(tf.getAuthor());
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		if (!found) {
			Cookie cookie = new Cookie("ochanAuthor", tf.getAuthor());
			cookie.setMaxAge(SECONDS_PER_YEAR);
			response.addCookie(cookie);
		}

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		LOG.info("Multipart?: " + isMultipart);

		String filename = null;
		Byte[] bytes = null;
		if (tf.getFileUrl() == null || "".equals(tf.getFileUrl().trim())) {
			// Copying the byte array to be a Byte array. fun..
			if (tf.getFile() != null && tf.getFile().getBytes().length > 0) {
				bytes = new Byte[tf.getFile().getBytes().length];
				bytes = ArrayUtils.toObject(tf.getFile().getBytes());
				filename = tf.getFile().getOriginalFilename();
			}
		} else {
			bytes = RemoteFileGrabber.getDataFromUrl(tf.getFileUrl());
			filename = RemoteFileGrabber.getFilenameFromUrl(tf.getFileUrl());
		}

		if (StringUtils.isBlank(tf.getComment()) && bytes == null) {
			NothingToPostException exception = new NothingToPostException();
			exception.setCategoryId(categoryIdentifier);
			throw exception;
		}

		LOG.debug("calling thread service to add thread (form): " + tf);
		try {
			threadService.createThread(null, Long.valueOf(tf.getCategoryIdentifier()), tf.getAuthor(), tf.getSubject(), tf.getUrl(), tf.getEmail(), tf.getComment(), bytes, filename);
		} catch (Exception ex) {
			// FIXME - handle this so the user gets notified NICELY
			LOG.error("Unable to create thread", ex);
		}

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("identifier", tf.getCategoryIdentifier());

		return new ModelAndView(new RedirectView("/chan/" + tf.getCategoryIdentifier()));
	}
}
