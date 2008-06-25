package org.Ochan.control;

import static org.Ochan.control.StaticNames.CATEGORY_LIST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.Ochan.entity.Category;
import org.Ochan.form.ThreadForm;
import org.Ochan.service.CategoryService;
import org.Ochan.service.ThreadService;
import org.Ochan.service.CategoryService.CategoryCriteria;
import org.Ochan.util.RemoteFileGrabber;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class ThreadAddController extends SimpleFormController {

	private static final Log LOG = LogFactory.getLog(ThreadAddController.class);

	private CategoryService categoryService;

	private ThreadService threadService;

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

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		// to actually be able to convert Multipart instance to byte[]
		// we have to register a custom editor
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		// now Spring knows how to handle multipart object and convert them
	}

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		ThreadForm tf = (ThreadForm) command;

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		LOG.error("Multipart?: " + isMultipart);
		
		Byte[] bytes = null;
		if (tf.getFileUrl() == null || "".equals(tf.getFileUrl().trim())){
			// Copying the byte array to be a Byte array. fun..
			if (tf.getFile() != null && tf.getFile().length > 0) {
				bytes = new Byte[tf.getFile().length];
				int invariant = 0;
				for (byte b : tf.getFile()) {
					bytes[invariant] = new Byte(b);
					invariant++;
				}
			}
		}else{
			bytes = RemoteFileGrabber.getDataFromUrl(tf.getFileUrl());
		}
		
		LOG.debug("calling thread service to add thread (form): " + tf);
		try{
			threadService.createThread(Long.valueOf(tf.getCategoryIdentifier()), tf.getAuthor(), tf.getSubject(), tf.getUrl(), tf.getEmail(), tf.getComment(), bytes);
		}catch(Exception e){
			//FIXME - handle this so the user gets notified NICELY
			LOG.error("Unable to create thread",e);
		}
		
		return super.onSubmit(request, response, command, errors);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#showForm(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse,
	 *      org.springframework.validation.BindException, java.util.Map)
	 */
	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception {
		List<Category> categories = categoryService.retrieveCategories(new HashMap<CategoryCriteria, String>());
		if (controlModel == null)
			controlModel = new HashMap();
		controlModel.put(CATEGORY_LIST, categories);
		return super.showForm(request, response, errors, controlModel);
	}

}