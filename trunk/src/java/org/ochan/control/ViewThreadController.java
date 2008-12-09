package org.ochan.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.util.DeploymentConfiguration;
import org.ochan.util.PostLinksAFixARockerJocker;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class ViewThreadController implements Controller {

	private CategoryService categoryService;
	private ThreadService threadService;
	private PostService postService;
	private String viewName;

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
	 * @param categoryService the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
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
		Long identifier = null; 
		if (arg0.getParameter("identifier") != null){
			identifier = Long.valueOf(arg0.getParameter("identifier"));
		}else{
			identifier = Long.parseLong((String)arg0.getAttribute("identifier"));
		}
		
		Thread t = threadService.getThread(identifier);
		if (t != null){
			t.setPosts(getPostService().retrieveThreadPosts(t));
			//ok, lets walk through the posts, and handle the special html for linking between posts.. TODO - make this a better implementation..
			for (Post p : t.getPosts()){
				TextPost tp = (TextPost)p;
				tp.setComment(PostLinksAFixARockerJocker.fixMahLinks(tp,true));
			}
			
			controlModel.put("category", categoryService.getCategory(t.getCategory().getIdentifier()));
			
			controlModel.put("thread", t);
			
			//lets play with cookies to remember the author name
			if (arg0.getCookies() != null){
				for(Cookie cookie : arg0.getCookies()){
					if ("ochanAuthor".equals(cookie.getName())){
						arg0.getSession().setAttribute("author",cookie.getValue());
					}
				}
			}
			
			String author = (String)arg0.getSession().getAttribute("author");
			if (StringUtils.isNotEmpty(author)){
				controlModel.put("author", author);
			}else{
				controlModel.put("author", "Anonymous");
			}
			
			controlModel.put("blockPosts",DeploymentConfiguration.enforcePostLimit(t.getPosts().size()));
		}else{
			//dead thread! 404 thing
			return new ModelAndView("404",controlModel);
		}

		return new ModelAndView(viewName, controlModel);
	}
}
