package org.ochan.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class ViewThreadController implements Controller {

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
		t.setPosts(getPostService().retrieveThreadPosts(t));
		//ok, lets walk through the posts, and handle the special html for linking between posts.. TODO - make this a better implementation..
		for (Post p : t.getPosts()){
			TextPost tp = (TextPost)p;
			String[] pieces = tp.getComment().split("&gt;&gt;");
			if (pieces != null && pieces.length > 1){
				StringBuilder value = new StringBuilder();
				boolean first = true;
				for (String x : pieces){
					if(first){
						//first one, nothing to do here.
						value.append(x);
						first = false;
					}else{
						int stop = x.indexOf(" ");
						if (stop <= 0){
							stop = x.length();
						}
						String num = x.substring(0,stop);
						if (!StringUtils.isNumeric(num)){
							//(the <br> bit)
							stop = stop - 4;
							num = x.substring(0,stop);
						}
						value.append("<a href=\"#"+num+"\" onclick=\"replyhl("+num+")\">&gt;&gt;"+num+"</a> "+x.substring(stop));
					}
				}
				tp.setComment(value.toString());
			}
		}
		
		controlModel.put("thread", t);
		String author = (String)arg0.getSession().getAttribute("author");
		if (StringUtils.isNotEmpty(author)){
			controlModel.put("author", author);
		}else{
			controlModel.put("author", "Anonymous");
		}

		return new ModelAndView(viewName, controlModel);
	}
}
