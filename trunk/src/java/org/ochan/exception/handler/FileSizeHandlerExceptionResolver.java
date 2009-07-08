package org.ochan.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.util.ManagedCommonsMultipartResolver;
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
			Map<String,Object> stuff = new HashMap<String,Object>();
			ManagedCommonsMultipartResolver commonsMultipartResolver = new ManagedCommonsMultipartResolver();
			stuff.put("currentLimit", commonsMultipartResolver.getMaxUploadSize());
			stuff.put("threadId",arg0.getAttribute("parent"));
			stuff.put("categoryId",arg0.getAttribute("categoryIdentifier"));
			return new ModelAndView("errorFileTooBig", stuff);
		}
		//pass this thing.
		return null;
	}
}
