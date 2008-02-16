package org.Ochan.control;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.Ochan.entity.Category;
import org.Ochan.entity.Thread;
import org.Ochan.service.CategoryService;
import org.Ochan.service.PostService;
import org.Ochan.service.ThreadService;
import org.Ochan.service.ThreadService.ThreadCriteria;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ViewCategoryController implements Controller {

	private CategoryService categoryService;
	private ThreadService threadService;
	private PostService postService;
	private String viewName;

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
	 * @param threadService the threadService to set
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
	 * @param postService the postService to set
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

	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		Map controlModel = new HashMap();
		Long identifier = Long.valueOf(arg0.getParameter("identifier"));
		Category cat = categoryService.getCategory(identifier);
		Map<ThreadCriteria,Object> searchCriteria = new HashMap<ThreadCriteria,Object>();
		searchCriteria.put(ThreadCriteria.CATEGORY, cat.getIdentifier());
		Set<Thread> threads = getThreadService().retrieveThreads(searchCriteria);
		cat.setThreads(threads);
		for (Thread t : threads){
			t.setPosts(getPostService().retrieveThreadPosts(t));
		}
		controlModel.put("category", cat);
		
		return new ModelAndView(viewName, controlModel);
	}

}
