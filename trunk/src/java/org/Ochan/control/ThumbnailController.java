package org.Ochan.control;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.Ochan.entity.ImagePost;
import org.Ochan.entity.Post;
import org.Ochan.service.PostService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@ManagedResource(description = "Thumbnail", objectName = "Ochan:util=controller,name=Thumnbailer", logFile = "jmx.log")
public class ThumbnailController implements Controller {

	private static final Log LOG = LogFactory.getLog(ThumbnailController.class);

	private static final Preferences PREFS = Preferences.userNodeForPackage(ThumbnailController.class);

	private PostService postService;

	/**
	 * The default width of a thumbnail
	 */
	private static final String THUMB_MAX_WIDTH = "160";
	/**
	 * The default height of a thumbnail
	 */
	private static final String THUMB_MAX_HEIGHT = "160";
	/**
	 * The default thumbnail generation length of time that would cause an exception to be logged
	 */
	private static final String LOG_ON_THIS_TIME = "200"; 
	
	//statistics are goooood
	private static long totalTimeInMillis = 0;
	private static long numberOfThumbs = 0;
	private static long lastTimeInMillis = 0;
	
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The width of the image in pixels.")
	public Long getThumbWidth(){
		return new Long(PREFS.get("WIDTH", THUMB_MAX_WIDTH));
	}
	/**
	 * 
	 * @param value
	 */
	@ManagedAttribute(defaultValue="160", description="The width of the image in pixels.", persistPolicy="OnUpdate")
	@ManagedOperationParameters(@ManagedOperationParameter(description="Width in pixels", name="value"))
	@ManagedOperation(description="")
	public void setThumbWidth(String value){
		PREFS.put("WIDTH", value);
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The height of the image in pixels.")
	public Long getThumbHeight(){
		return new Long(PREFS.get("HEIGHT", THUMB_MAX_HEIGHT));
	}
	/**
	 * 
	 * @param value
	 */
	@ManagedAttribute(defaultValue="160", description="The height of the image in pixels.", persistPolicy="OnUpdate")
	@ManagedOperation(description="")
	@ManagedOperationParameters(@ManagedOperationParameter(description="Height in pixels", name="value"))
	public void setThumbHeight(String value){
		PREFS.put("HEIGHT", value);
	}
	
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The time in milliseconds that is threshold before logging exceptions due to poor performance.")
	public Long getTimeLength(){
		return new Long(PREFS.get("TIME", LOG_ON_THIS_TIME));
	}
	/**
	 * 
	 * @param value
	 */
	@ManagedAttribute(defaultValue="200", description="The time in milliseconds that is threshold before logging exceptions due to poor performance.", persistPolicy="OnUpdate")
	@ManagedOperationParameters(@ManagedOperationParameter(description="Time in Milliseconds", name="value"))
	@ManagedOperation(description="")
	public void setTimeLength(String value){
		PREFS.put("TIME", value);
	}
	
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The average time in milliseconds the system is encountering on thumnbail requests")
	public long getAverageTimeInMillis(){
		if (totalTimeInMillis != 0 && numberOfThumbs != 0){
			return totalTimeInMillis / numberOfThumbs;
		}
		return 0;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The number of thumbnails that have been requested")
	public long getNumberOfThumbs(){
		return numberOfThumbs;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The last time we generated a thumb in milliseconds")
	public long getLastTimeInMIllis(){
		return lastTimeInMillis;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The total time in milliseconds it has taken to generate all thumbs")
	public long getTotalTimeInMIllis(){
		return totalTimeInMillis;
	}
	

	/**
	 * @return the postService
	 */
	public PostService getPostService() {
		return postService;
	}

	/**
	 * @param postService
	 *            the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * 
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// capture start of call
		long start = new Date().getTime();
		try {
			numberOfThumbs++;
			Long id = Long.valueOf(request.getParameter("identifier"));
			boolean thumb = request.getParameter("thumb") != null;
			Post p = postService.getPost(id);
			if (p instanceof ImagePost) {
				ImagePost imagePost = (ImagePost) p;
				Byte[] data = imagePost.getData();
				// convert to non-object
				//DATUM is out output
				byte[] datum = new byte[data.length];
				int i = 0;
				for (Byte val : data) {
					datum[i] = val.byteValue();
					i++;
				}
				if (thumb) {
					//we may need to make a thumbnail
					if (imagePost.getThumbnail() == null){
						//yep, make it
						BufferedImage image = null;
						BufferedImage resizedImage = null;
						ByteArrayInputStream bais = new ByteArrayInputStream(datum);
						image = ImageIO.read(bais);
						int width = image.getWidth();
						int height = image.getHeight();
						if (width > height) {
							resizedImage = convert(image.getScaledInstance(getThumbWidth().intValue(), -1, Image.SCALE_FAST));
						} else {
							resizedImage = convert(image.getScaledInstance(-1, getThumbHeight().intValue(), Image.SCALE_FAST));
						}
						Iterator writers = ImageIO.getImageWritersByMIMEType("image/jpeg");
						ImageWriter imgWriter = (ImageWriter) writers.next();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageOutputStream imgStream = ImageIO.createImageOutputStream(baos);
						imgWriter.setOutput(imgStream);
						imgWriter.write(resizedImage);
						datum = baos.toByteArray();
						//store the thumbnail data in the post and persist
						{
							Byte[] thumbData = new Byte[datum.length];
							for (int j = 0; j < datum.length; j++){
								thumbData[j] = new Byte(datum[j]);
							}
							imagePost.setThumbnail(thumbData);
							postService.updatePost(imagePost);
						}
					}else{
						//take the imagePost's thumnbail and make it outputable
						datum = new byte[imagePost.getThumbnail().length];
						int x = 0;
						for (Byte val : imagePost.getThumbnail()) {
							datum[x] = val.byteValue();
							x++;
						}
					}
				}
				// pick one
				// response.setContentType("image/gif");
				// response.setContentType("image/x-png");
				response.setContentType("image/jpeg");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);

				LOG.debug("file length is " + datum.length);
				response.setContentLength(datum.length);
				response.setHeader("Content-Disposition", " inline; filename=" + "TODO");
				// convert to non-object
				FileCopyUtils.copy(datum, response.getOutputStream());
			}
		} catch (Exception e) {
			LOG.error("Unable to create thumbnail", e);
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastTimeInMillis = end - start;
		totalTimeInMillis += lastTimeInMillis;
		
		if (lastTimeInMillis > getTimeLength()){
			LOG.warn("Thumbnail times are getting excessive: " + lastTimeInMillis + " ms. for thumb id:" + request.getParameter("identifier"));
		}
		return null;
	}

	public BufferedImage convert(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}
}