package org.ochan.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.exception.ThreadOverPostLimitException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ThreadOverPostLimitExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {
		if (arg3 instanceof ThreadOverPostLimitException){
			ThreadOverPostLimitException excep = (ThreadOverPostLimitException)arg3;
			Map<String,Object> stuff = new HashMap<String,Object>();
			stuff.put("threadId",excep.getThreadId());
			return new ModelAndView("errorThreadLimitReached", stuff);
		}
		//pass it on!
		return null;
	}

}
