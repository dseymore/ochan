package org.ochan.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.exception.NothingToPostException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class NothingToPostExceptionResolver implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {
		if (arg3 instanceof NothingToPostException){
			NothingToPostException excep = (NothingToPostException)arg3;
			Map<String,Object> stuff = new HashMap<String,Object>();
			stuff.put("threadId",excep.getThreadId());
			stuff.put("categoryId",excep.getCategoryId());
			return new ModelAndView("errorNothingToPost", stuff);
		}
		//pass it on!
		return null;
	}

}
