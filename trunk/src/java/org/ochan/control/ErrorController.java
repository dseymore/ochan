package org.ochan.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
* This class just captures error events.. we may need to do some logic based on the error code
* which is why this exists
* @author dseymore
*/
public class ErrorController implements Controller{

	private static final Log LOG = LogFactory.getLog(ErrorController.class);

	//What do we want to do here ??? any stats? randomness? logic?

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("error",null);
	}
}
