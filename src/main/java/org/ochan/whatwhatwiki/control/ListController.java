package org.ochan.whatwhatwiki.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.whatwhatwiki.service.FileService;
import org.ochan.whatwhatwiki.service.PageService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListController implements Controller{

	private PageService pageService;
	private FileService fileService;
	/**
	 * @param pageService the pageService to set
	 */
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	/**
	 * @param fileService the fileService to set
	 */
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
		
}
