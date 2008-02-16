package org.Ochan.control;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.Ochan.form.PostReplyForm;
import org.Ochan.service.PostService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class PostReplyController extends SimpleFormController {
	
	private PostService postService;
	
	
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
		//Copying the byte array to be a Byte array. fun..
        Byte[] bytes = null;
        if (prf.getFile() != null && prf.getFile().length > 0){
            bytes = new Byte[prf.getFile().length];
            int invariant = 0;
            for( byte b : prf.getFile()){
                bytes[invariant] = new Byte(b);
                invariant++;
            }
        }
		postService.createPost(Long.valueOf(prf.getParent()), prf.getAuthor(), prf.getSubject(), prf.getEmail(), prf.getUrl(), prf.getComment(), bytes);
		
		return super.onSubmit(request, response, command, errors);
	}
}
