package org.ochan.control;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.exception.ThreadOverPostLimitException;
import org.ochan.form.PostReplyForm;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
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


@ManagedResource(description = "ViewThread", objectName = "Ochan:util=controller,name=ViewThread", logFile = "jmx.log")
public class ViewThreadController extends SimpleFormController {

	private CategoryService categoryService;
	private ThreadService threadService;
	private PostService postService;
	private String viewName;
	public static final int SECONDS_PER_YEAR = 60*60*24*365;
	
	
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
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}

	@Override
	protected ModelAndView showForm(HttpServletRequest arg0, HttpServletResponse httpServletResponse, BindException e, Map map) throws Exception {
		numberOfRequests++;
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
	

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		PostReplyForm prf = (PostReplyForm)command;
		//do we block?
		org.ochan.entity.Thread t = threadService.getThread(Long.valueOf(prf.getParent()));
		List<Post> posts = postService.retrieveThreadPosts(t);
		if(posts != null && DeploymentConfiguration.enforcePostLimit(posts.size())){
			ThreadOverPostLimitException xyz = new ThreadOverPostLimitException();
			xyz.setThreadId(t.getIdentifier());
			throw xyz;
		}
		
		//save the username in the session.
		request.getSession().setAttribute("author", prf.getAuthor());
		
		boolean found = false;
		//lets play with cookies to remember the author name
		if(request.getCookies() != null){
			for(Cookie cookie : request.getCookies()){
				if ("ochanAuthor".equals(cookie.getName())){
					found = true;
					cookie.setValue(prf.getAuthor());
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		if (!found){
			Cookie cookie = new Cookie("ochanAuthor",prf.getAuthor());
			cookie.setMaxAge(SECONDS_PER_YEAR);
			response.addCookie(cookie);
		}
		
		//handle anything that isnt a zip upload
		if(prf.getZipFile() == null || prf.getZipFile().length == 0){
			//Copying the byte array to be a Byte array. fun..
			Byte[] bytes = null;
			if (prf.getFileUrl() == null || "".equals(prf.getFileUrl().trim())){
		        if (prf.getFile() != null && prf.getFile().length > 0){
		            bytes = new Byte[prf.getFile().length];
		            int invariant = 0;
		            for( byte b : prf.getFile()){
		                bytes[invariant] = new Byte(b);
		                invariant++;
		            }
		        }
			}else{
				//URL
				bytes = RemoteFileGrabber.getDataFromUrl(prf.getFileUrl());
			}
			//normal image upload or url post
			postService.createPost(Long.valueOf(prf.getParent()), prf.getAuthor(), prf.getSubject(), prf.getEmail(), prf.getUrl(), prf.getComment(), bytes);
		}else{
			ByteArrayInputStream bais = new ByteArrayInputStream(prf.getZipFile());
			ZipInputStream zip = new ZipInputStream(bais);
			ZipEntry entry = zip.getNextEntry();
			if (entry == null){
				//BAD ZIP! TODO- handle it
			}
			while(entry != null){
				//start with some sort of buffer size
				byte[] data = new byte[2048];
				int count;
		        //write the files to the disk
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream dest = new BufferedOutputStream(baos, 2048);
				while ((count = zip.read(data, 0, 2048)) != -1) {
		               dest.write(data, 0, count);
				}
		        dest.flush();
		        dest.close();
				zip.closeEntry();
				data = baos.toByteArray();
				Byte[] bytes = new Byte[data.length];
	            int invariant = 0;
	            for( byte b : data){
	                bytes[invariant] = new Byte(b);
	                invariant++;
	            }
				postService.createPost(Long.valueOf(prf.getParent()), prf.getAuthor(), prf.getSubject(), prf.getEmail(), prf.getUrl(), prf.getComment(), bytes);
				entry = zip.getNextEntry();
			}
		}
		return new ModelAndView(new RedirectView("/chan/thread/"+prf.getParent()));
	}
	
}
