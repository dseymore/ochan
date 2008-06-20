package org.Ochan.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.Ochan.entity.Category;
import org.Ochan.service.CategoryService;
import org.Ochan.service.ThreadService;
import org.Ochan.util.RemoteFileGrabber;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class ShareController implements Controller {

	private static Log LOG = LogFactory.getLog(ShareController.class);

	private CategoryService categoryService;
	private ThreadService threadService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// ok, lets handle some of these parameters
		String ctype = arg0.getParameter("ctype");
		String url = arg0.getParameter("url");
		LOG.debug("Parameter ctype:" + ctype);
		LOG.debug("Parameter url: " + url);

		Long identifier = new Long(-1);
		
		synchronized(identifier){
			// if a category doesn't exist for the "share" lets create one.
			Map searchCatCrit = new HashMap();
			searchCatCrit.put(CategoryService.CategoryCriteria.NAME, "share");
			List<Category> cats = categoryService.retrieveCategories(searchCatCrit);
			
			if (cats == null || cats.size() <= 0){
				categoryService.createCategory("share", "Items shared through the share protocol");
				cats = categoryService.retrieveCategories(searchCatCrit);
				identifier = cats.get(0).getIdentifier();
			}else{
				//the first one is what we want
				identifier = cats.get(0).getIdentifier();
			}
		}

		// TODO we need to handle the EXTERNAL CATEGORY too, and publish to that
		// one should someone try to share to us.

		// create a new thread.. and if its ctype=url, then make it a text post,
		// if its a ctype of image... make it a image post, use the description
		// as the text to the post.
		if (ctype != null && "image".equalsIgnoreCase(ctype)) {
			threadService.createThread(identifier, null, "share", url, null, null, RemoteFileGrabber.getDataFromUrl(url));			
		} else if (ctype != null && "url".equalsIgnoreCase(ctype)) {
			threadService.createThread(identifier, null, "share", url, null, "shared url: " + url, null);
		}
		
		return new ModelAndView("admin");
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public ThreadService getThreadService() {
		return threadService;
	}

	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	
	
}
