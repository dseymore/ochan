package org.ochan.control;

import static org.ochan.control.StaticNames.CATEGORY_LIST;
import static org.ochan.control.StaticNames.EXTERNAL_CATEGORY_LIST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.ochan.entity.Category;
import org.ochan.entity.ExternalCategory;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;
import org.ochan.service.AnnouncementService;
import org.ochan.service.CategoryService;
import org.ochan.service.ExternalCategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@ManagedResource(description = "ViewMainPage", objectName = "Ochan:util=controller,name=ViewMainPage", logFile = "jmx.log")
public class CategoryListController implements Controller {

	private CategoryService categoryService;
	private ExternalCategoryService externalCategoryService;
	private ThreadService threadService;
	private PostService postService;
	private Ehcache cache;
	private String viewName;
	private AnnouncementService announcementService;

	
	private static long numberOfRequests = 0; 
	/**
	 * Statistics
	 * @return
	 */
	@ManagedAttribute(description="The number of thumbnails that have been requested")
	public long getNumberOfRequests(){
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

	
	
	/**
	 * @param postService the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	
	/**
	 * @param cache the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	/**
	 * @return the externalCategoryService
	 */
	public ExternalCategoryService getExternalCategoryService() {
		return externalCategoryService;
	}

	/**
	 * @param externalCategoryService the externalCategoryService to set
	 */
	public void setExternalCategoryService(ExternalCategoryService externalCategoryService) {
		this.externalCategoryService = externalCategoryService;
	}

	
	/**
	 * @param announcementService the announcementService to set
	 */
	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	/**
	 * Builds a list of categories with threads retrieved
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		numberOfRequests++;
		List<Category> categories = getCategoryService().retrieveCategories();
		for (Category category : categories){
			ThreadCriteria criteria = new ThreadService.ThreadCriteria();
			criteria.setCategory(category.getIdentifier());
			category.setThreads(getThreadService().retrieveThreads(criteria));
		}
		
		List<ExternalCategory> externalList = getExternalCategoryService().retrieveCategories(null);
		
		
		Map controlModel = new HashMap();
		controlModel.put(CATEGORY_LIST, categories);
		controlModel.put(EXTERNAL_CATEGORY_LIST, externalList);
		
		//find the current most-recent thread's id
		{
			ThreadCriteria criteria = new ThreadService.ThreadCriteria();
			criteria.setMax("1");
			List<Thread> xyz = threadService.retrieveThreads(criteria);
			if (xyz != null && xyz.size() > 0 && xyz.get(0) != null){
				controlModel.put("currentThread",xyz.get(0).getIdentifier());
			}else{
				controlModel.put("currentThread","0");
			}
		}
		
		controlModel.put("announcement",announcementService.getAnnouncement());
		
		
		//lets play with cookies to remember the author name
		if (arg0.getCookies() != null){
			for(Cookie cookie : arg0.getCookies()){
				if ("ochanAuthor".equals(cookie.getName())){
					arg0.getSession().setAttribute("author",cookie.getValue());
				}
			}
		}
		return new ModelAndView(viewName, controlModel);
	}

}
