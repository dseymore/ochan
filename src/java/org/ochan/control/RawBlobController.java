package org.ochan.control;

import java.util.Date;
import java.util.prefs.Preferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.service.BlobService;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@ManagedResource(description = "RawBlob", objectName = "Ochan:util=controller,name=RawBlob", logFile = "jmx.log")
public class RawBlobController implements Controller {

	private static final Log LOG = LogFactory.getLog(RawBlobController.class);

	private static final Preferences PREFS = Preferences
			.userNodeForPackage(RawBlobController.class);

	private BlobService blobService;


	public static final int MILLISECONDS_IN_A_DAY = 60*60*24*1000;
	
	/**
	 * @param blobService
	 *            the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}

	/**
	 * 
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.valueOf(request.getParameter("identifier"));
		Byte[] data = blobService.getBlob(id);
		byte[] datum = ArrayUtils.toPrimitive(data);
		
		//caching please.. expire after a day
		response.setHeader("Cache-Control", "max-age=86400, public");
		response.setDateHeader("Expires", new Date().getTime() + MILLISECONDS_IN_A_DAY);

		if (datum == null){
			datum = ThumbnailController.getFailImageData(request);
		}
		LOG.debug("file length is " + datum.length);
		response.setContentLength(datum.length);
		response.setContentType("image/jpeg");
		response.setHeader("Content-Disposition", " inline; filename=" + id+".jpg");
		
		FileCopyUtils.copy(datum, response.getOutputStream());
		return null;
	}
}
