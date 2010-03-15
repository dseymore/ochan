package org.ochan.control;

import java.util.prefs.Preferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Post;
import org.ochan.service.PostService;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

@ManagedResource(description = "PostRedirect", objectName = "Ochan:util=controller,name=PostRedirect", logFile = "jmx.log")
public class PostRedirectController implements Controller {
	private static final Log LOG = LogFactory.getLog(PostRedirectController.class);

	private static final Preferences PREFS = Preferences.userNodeForPackage(PostRedirectController.class);

	private PostService postService;

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(request.getParameter("identifier"));
		String url = "/chan/thread/";
		Post p = postService.getPostByBlob(id);
		if (p != null) {
			url = url + p.getParent().getIdentifier() + "#" + p.getIdentifier();
		}else{
			response.sendError(response.SC_NOT_FOUND);
		}

		return new ModelAndView(new RedirectView(url));
	}

}
