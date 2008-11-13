package org.ochan.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring has a way of handling exceptions INSIDE the default servlet.. instead of relying on shady error-page stuff. 
 * @author David Seymore 
 * Nov 12, 2008
 */
public class FileSizeHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {
		if (arg3 instanceof MaxUploadSizeExceededException){
			return new ModelAndView("errorFileTooBig");
		}
		//pass this thing.
		return null;
	}

}
