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
import org.ochan.job.StatsGeneratorJob;
import org.ochan.service.AnnouncementService;
import org.ochan.service.CategoryService;
import org.ochan.service.ExternalCategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.CategoryService.CategoryCriteria;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class CategoryListController implements Controller {

	private CategoryService categoryService;
	private ExternalCategoryService externalCategoryService;
	private ThreadService threadService;
	private PostService postService;
	private Ehcache cache;
	private String viewName;
	private AnnouncementService announcementService;

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
		List<Category> categories = getCategoryService().retrieveCategories(new HashMap<CategoryCriteria, String>());
		for (Category category : categories){
			Map<ThreadCriteria,Object> searchCriteria = new HashMap<ThreadCriteria,Object>();
			searchCriteria.put(ThreadCriteria.CATEGORY, category.getIdentifier());
			category.setThreads(getThreadService().retrieveThreads(searchCriteria));
		}
		
		List<ExternalCategory> externalList = getExternalCategoryService().retrieveCategories(null);
		
		
		//Get a cached list of all threads and posts.. yay!
		//semi-hack-i-tude.. need to push this down to a service level 
		//(its copypasta from the threadcollectionadapter)
		List<Thread> toreturn = new ArrayList<Thread>();
		Element o = cache.get("1");
		if (o == null || o.getObjectValue() == null || o.isExpired()){
			List<Category> cats = categoryService.retrieveCategories(null);
			for (Category c : cats){
				Map<ThreadCriteria,Object> criteria = new HashMap<ThreadCriteria,Object>();
				criteria.put(ThreadCriteria.CATEGORY, c.getIdentifier());
				List<Thread> threads = threadService.retrieveThreads(criteria);
				//categories have 0 threads to begin with.. 
				if (threads != null){
					for (Thread thread : threads){
						thread.setPosts(postService.retrieveThreadPosts(thread));
						toreturn.add(thread);
					}
				}
			}
			Collections.sort(toreturn);
			cache.put(new Element("1",toreturn));
		}else{
			//unsafe!
			toreturn = (ArrayList<Thread>)o.getObjectValue();
		}
		
		Map controlModel = new HashMap();
		controlModel.put(CATEGORY_LIST, categories);
		controlModel.put(EXTERNAL_CATEGORY_LIST, externalList);

		//get the most recent three image posts (this logic is shit!)
		Post p1 = null;
		Post p2 = null;
		Post p3 = null;
		for (Thread t : toreturn){
			boolean foundOne = false;
			//walk backwards until we find one
			//this handles when there is only one post or less than a lot.
			for (int i = 1; i <= t.getPosts().size() && !foundOne && (
					!(p1 !=null && !p1.getParent().getIdentifier().equals(t.getIdentifier())) 
					|| !(p2 != null && !p2.getParent().getIdentifier().equals(t.getIdentifier())) 
					|| !(p3 != null && !p3.getParent().getIdentifier().equals(t.getIdentifier()))); i++){
				Post p = t.getPosts().get(t.getPosts().size() -i); 
				if ( p instanceof ImagePost){ 
					if (p1 == null){
						p1 = p;
					}else if (p2 == null){
						p2 = p;
					}else if (p3 == null){
						p3 = p;
					}
					if (p3 != null){
						//jump out!
						break;
					}
					foundOne = true;
				}
			}
		}
		controlModel.put("P1",p1);
		controlModel.put("P2",p2);
		controlModel.put("P3",p3);
		
		//find the current most-recent thread's id
		{
			Map<ThreadCriteria, Object> critiera = new HashMap<ThreadCriteria, Object>();
			critiera.put(ThreadCriteria.MAX, "1");
			List<Thread> xyz = threadService.retrieveThreads(critiera);
			if (xyz != null && xyz.size() > 0){
				controlModel.put("currentThread",xyz.get(0).getIdentifier());
			}else{
				controlModel.put("currentThread","0");
			}
		}
		
		//STATISTICS! YAY!
		controlModel.put("totalContentSize", StatsGeneratorJob.getSizeOfAllFiles());
		controlModel.put("totalNumberOfFiles", StatsGeneratorJob.getNumberOfFiles());
		controlModel.put("totalNumberOfPosts", StatsGeneratorJob.getNumberOfPosts());
		controlModel.put("totalNumberOfImagePosts", StatsGeneratorJob.getNumberOfImagePosts());
		controlModel.put("totalNumberOfThreads", StatsGeneratorJob.getNumberOfThreads());
		
		controlModel.put("announcement",announcementService.getAnnouncement());
		
		
		//lets play with cookies to remember the author name
		for(Cookie cookie : arg0.getCookies()){
			if ("ochanAuthor".equals(cookie.getName())){
				arg0.getSession().setAttribute("author",cookie.getValue());
			}
		}
		
		return new ModelAndView(viewName, controlModel);
	}

}
