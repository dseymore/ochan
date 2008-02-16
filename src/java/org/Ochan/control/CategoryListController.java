package org.Ochan.control;

import static org.Ochan.control.StaticNames.CATEGORY_LIST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.Ochan.entity.Category;
import org.Ochan.service.CategoryService;
import org.Ochan.service.ThreadService;
import org.Ochan.service.CategoryService.CategoryCriteria;
import org.Ochan.service.ThreadService.ThreadCriteria;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class CategoryListController implements Controller {

	private CategoryService categoryService;
	private ThreadService threadService;
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
	 * Builds a list of categories with threads retrieved
	 * 
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		List<Category> categories = getCategoryService().retrieveCategories(new HashMap<CategoryCriteria, String>());
		for (Category category : categories){
			Map<ThreadCriteria,Object> searchCriteria = new HashMap<ThreadCriteria,Object>();
			searchCriteria.put(ThreadCriteria.CATEGORY, category.getIdentifier());
			category.setThreads(getThreadService().retrieveThreads(searchCriteria));
		}
		
		Map controlModel = new HashMap();
		controlModel.put(CATEGORY_LIST, categories);
		return new ModelAndView(viewName, controlModel);
	}

}
