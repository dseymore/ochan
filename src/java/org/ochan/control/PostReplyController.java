package org.ochan.control;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.form.PostReplyForm;
import org.ochan.service.PostService;
import org.ochan.util.RemoteFileGrabber;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class PostReplyController extends SimpleFormController {
	
	private PostService postService;
    public static final int SECONDS_PER_YEAR = 60*60*24*365;
	
	
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
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}


	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		PostReplyForm prf = (PostReplyForm)command;
		//save the username in the session.
		request.getSession().setAttribute("author", prf.getAuthor());
		
		boolean found = false;
		//lets play with cookies to remember the author name
		if(request.getCookies() != null){
			for(Cookie cookie : request.getCookies()){
				if ("ochanAuthor".equals(cookie.getName())){
					found = true;
					cookie.setValue(prf.getAuthor());
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
		return new ModelAndView(new RedirectView("viewThread.Ochan?identifier="+prf.getParent()));
	}
	
	
}
