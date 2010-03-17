/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package org.ochan.control;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.StatProcessorType;
import org.javasimon.Stopwatch;
import org.ochan.dpl.BlobType;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.service.BlobService;
import org.ochan.service.PostService;
import org.ochan.util.Throttler;
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
	private BlobService blobService;
	
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
	
	/**
	 * The default thumbnail image quality of 80%.. which is probably better quality than it needs to be.. 
	 * .5 works very well for a good match of size & quality for most images. 
	 */
	private static final String THUMBNAIL_IMAGE_QUALITY = ".8";
	
	//statistics are goooood
	private static long numberOfThumbs = 0;
	private static long lastResizeTimeInMillis = 0;
	private static long totalResizeTimeInMillis = 0;
	
	private static long numberOfRequests = 0;
	private static long lastTimeInMillis = 0;
	private static long totalTimeInMillis = 0;	
	
	
	public static final int MILLISECONDS_IN_A_DAY = 60*60*24*1000;
	
	public static final Long REQUESTS_PER_MINUTE = Long.valueOf(120);
	public static final Long GENERATIONS_PER_MINUTE = Long.valueOf(10);
	
	private static Stopwatch requestWaitTime = SimonManager.getStopwatch(ThumbnailController.class.getName() + "Request");
	private static Stopwatch generateWaitTime = SimonManager.getStopwatch(ThumbnailController.class.getName() + "Generate");
	
	@ManagedAttribute(description="The average time a requests waits around being throttled")
	public double getRequestWaitTime(){
		return requestWaitTime.getStatProcessor().getMean();
	}
	@ManagedAttribute(description="The average time a thumbnail generation request waits around being throttled")
	public double getGenerateWaitTime(){
		return generateWaitTime.getStatProcessor().getMean();
	}
	
	
	/**
	 * @return the thumbnailImageQuality
	 */
	@ManagedAttribute(description="The quality of the thumbnail compression from 0 to 1.")
	public String getThumbnailImageQuality() {
		return PREFS.get("quality", THUMBNAIL_IMAGE_QUALITY); 
	}
	/**
	 * @param thumbnailImageQuality the thumbnailImageQuality to set
	 */
	@ManagedAttribute(description="The quality of the thumbnail compression from 0 to 1.")
	public void setThumbnailImageQuality(String thumbnailImageQuality) {
		Float f = Float.valueOf(thumbnailImageQuality);
		if (f.doubleValue() >= 0 && f.doubleValue() <= 1){
			PREFS.put("quality", thumbnailImageQuality);
		}
	}
	
	//image generation is actually incredibly expensive.. so.. lets throttle that shit.
	@ManagedAttribute(description="The number of requests the thumbnailer will pump out a minute")
	public String getRequestsPerMinute(){
		return PREFS.get("requestsPerMinute", REQUESTS_PER_MINUTE.toString());
	}
	@ManagedAttribute(description="The number of requests the thumbnailer will pump out a minute")
	public void setRequestsPerMinute(String requestsPerMinute){
		if (StringUtils.isNumeric(requestsPerMinute)){
			PREFS.put("requestsPerMinute", requestsPerMinute);
		}
	}
	@ManagedAttribute(description="The number of thumbnails that can be generated per minute")
	public String getThumbnailGenerationsPerMinute(){
		return PREFS.get("generationsPerMinute", GENERATIONS_PER_MINUTE.toString());
	}
	@ManagedAttribute(description="The number of thumbnails that can be generated per minute")
	public void setThumbnailGenerationsPerMinute(String generationsPerMinute){
		if (StringUtils.isNumeric(generationsPerMinute)){
			PREFS.put("generationsPerMinute",generationsPerMinute);
		}
	}
	
	/**
	 * The current thumbnail image quality.
	 * @return
	 */
	private float getThumbnailQuality(){
		Float f = Float.valueOf(getThumbnailImageQuality());
		return f;
	}
	
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
		if (totalTimeInMillis != 0 && numberOfRequests != 0){
			return totalTimeInMillis / numberOfRequests;
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
	public long getLastResizeTimeInMillis(){
		return lastResizeTimeInMillis;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The total time in milliseconds it has taken to generate all thumbs")
	public long getTotalResizeTimeInMIllis(){
		return totalResizeTimeInMillis;
	}
	

	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The average time in milliseconds the system is encountering on thumnbail resize requests")
	public long getAverageResizeTimeInMillis(){
		if (totalResizeTimeInMillis != 0 && numberOfThumbs != 0){
			return totalResizeTimeInMillis / numberOfThumbs;
		}
		return 0;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The number of images that have been requested")
	public long getNumberOfRequests(){
		return numberOfRequests;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The last time we generated retrieved in milliseconds")
	public long getLastTimeInMIllis(){
		return lastTimeInMillis;
	}
	/**
	 * 
	 * @return
	 */
	@ManagedAttribute(description="The total time in milliseconds it has taken to retrieve all images")
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
	 * @param blobService the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}
	
	public ThumbnailController() {
		super();
		requestWaitTime.setStatProcessor(StatProcessorType.BASIC.create());
		generateWaitTime.setStatProcessor(StatProcessorType.BASIC.create());
	}


	/**
	 * 
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Split requestSplit = requestWaitTime.start();
		Throttler requestThrottler = new Throttler(Long.valueOf(getRequestsPerMinute()).intValue(),60000);
		requestThrottler.StartRequest();
		requestSplit.stop();
		// capture start of call
		long start = new Date().getTime();
		boolean thumb = request.getParameter("thumb") != null;
		try {
			numberOfRequests++;
			Long id = Long.valueOf(request.getParameter("identifier"));
			Post p = postService.getPost(id);
			ImagePost imagePost = (ImagePost) p;
			byte[] datum = null;
			//if imagepost
			if (p instanceof ImagePost) {
				//if thumb
				if (thumb) {
					if (imagePost.getThumbnailIdentifier() == null){
						Split generateSplit = generateWaitTime.start();
						Throttler generateThrottler = new Throttler(Long.valueOf(getThumbnailGenerationsPerMinute()).intValue(),60000);
						generateThrottler.StartRequest();
						generateSplit.stop();
						//now that we've waited, lets look it up again... with no throttle
						imagePost = (ImagePost)postService.getPost(id);
					}
					
					//we may need to make a thumbnail
					if (imagePost.getThumbnailIdentifier() == null){
						Byte[] data = blobService.getBlob(imagePost.getImageIdentifier());
						if (data != null){
							//oh well.. we're in a bad spot
							LOG.info("Unable to make thumbnail of an imagepost with no image data.");
						}
						datum = ArrayUtils.toPrimitive(data);
						//lets try checking if it is an image.. if not, we need to act on it. 
						BufferedImage image = null;
						
						if(isFileLikeAPdf(imagePost)){
							BufferedImage pdfPage1 = takeCaptureOfPDFPage1(datum);
							if (pdfPage1 != null){
								image = pdfPage1;
							}
						}
						
						if (image == null){
							ByteArrayInputStream bais = new ByteArrayInputStream(datum);
							//this might be the wrong direction.. 
							ImageIO.setUseCache(false);
							try{
								image = ImageIO.read(bais);
							}catch(Exception e){
								LOG.error("Bad image!",e);
								image = null;
							}
							if (image == null || p == null){
								//BAD BAD IMAGE!
								image = getFailImage(request);
							}
						}
						datum = resizeTheImage(image, imagePost, request);
					}else{
						//take the imagePost's thumnbail and make it outputable
						Byte[] thumbnailArray = blobService.getBlob(imagePost.getThumbnailIdentifier());
						datum = ArrayUtils.toPrimitive(thumbnailArray);
					}
				}else{
					//if not thumb
					Byte[] data = blobService.getBlob(imagePost.getImageIdentifier());
					datum = ArrayUtils.toPrimitive(data);
					//lets try checking if it is an image.. if not, we need to act on it. 
					BufferedImage image = null;
					ByteArrayInputStream bais = new ByteArrayInputStream(datum);
					try{
						image = ImageIO.read(bais);
					}catch(Exception e){
						LOG.error("Bad image!",e);
						image = null;
					}
					//bad image, or no image post anymore.. BUT NOT IF IT IS A PDF
					if ((image == null && !isFileLikeAPdf(imagePost)) || p == null){
						//BAD BAD IMAGE!
						datum = getFailImageData(request);
					}
				}
			}else{
				//deleted image
				if(thumb){
					Split generateSplit = generateWaitTime.start();
					Throttler generateThrottler = new Throttler(Long.valueOf(getThumbnailGenerationsPerMinute()).intValue(),60000);
					generateThrottler.StartRequest();
					generateSplit.stop();
					datum = resizeTheImage(getFailImage(request),null,request);
				}else{
					datum = getFailImageData(request);
				}
			}
			
			
			//caching please.. expire after a day
			response.setHeader("Cache-Control", "max-age=86400, public");
			response.setDateHeader("Expires", new Date().getTime() + MILLISECONDS_IN_A_DAY);

			LOG.debug("file length is " + datum.length);
			response.setContentLength(datum.length);
			if (isFileLikeAPdf(imagePost)){
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=" + id+".pdf");
			}else{
				response.setContentType("image/jpeg");
				response.setHeader("Content-Disposition", " inline; filename=" + id+".jpg");
			}
			// convert to non-object
			FileCopyUtils.copy(datum, response.getOutputStream());
		} catch (SocketException se){
			//this happens when a socket is closed mid-stream.
			LOG.trace("Socket exception",se);
		} catch (Exception e) {
			LOG.error("Unable to create thumbnail", e);
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastTimeInMillis = end - start;
		totalTimeInMillis += end - start;
		
		if (lastTimeInMillis > getTimeLength() && !thumb){
			LOG.warn("Thumbnail times are getting excessive: " + lastTimeInMillis + " ms. for thumb id:" + request.getParameter("identifier"));
		}
		return null;
	}

	private BufferedImage convert(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}
	
	@SuppressWarnings("unchecked")
	private BufferedImage takeCaptureOfPDFPage1(byte[] data){
		try{
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			PDDocument document = PDDocument.load(bais);
			//get the first page. 
			List<PDPage> pages = (List<PDPage>)document.getDocumentCatalog().getAllPages();
			PDPage page = pages.get(0);
            BufferedImage image = page.convertToImage();
            document.close();
            return image;
        }catch(Exception e){
        	LOG.error("Unable to convert pdf page 1 into godlike image",e);
        }
		return null;
	}
	
	/**
	 * Does the resizing of the image.. 
	 * @param image
	 * @param imagePost
	 * @param request
	 * @return
	 */
	private byte[] resizeTheImage(BufferedImage image, ImagePost imagePost, HttpServletRequest request){
		try{
			BufferedImage resizedImage = null;
			int width = image.getWidth();
			int height = image.getHeight();
			long startThumb = new Date().getTime();
			numberOfThumbs++;
			//yep, make it
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
			//parameters for compression
			ImageWriteParam param = imgWriter.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(getThumbnailQuality());
			//write the image!
			imgWriter.write(null, new IIOImage(resizedImage,null,null),param);
			byte[] datum = baos.toByteArray();
			//store the thumbnail data in the post and persist
			if (imagePost != null){
				Byte[] thumbData = ArrayUtils.toObject(datum);
				imagePost.setThumbnailIdentifier(blobService.saveBlob(thumbData, null,BlobType.THUMB));
				postService.updatePost(imagePost);
			}
			long endThumb = new Date().getTime();
			lastResizeTimeInMillis = endThumb - startThumb;
			totalResizeTimeInMillis += endThumb - startThumb;
			return datum;
		}catch(Exception e){
			LOG.error("Unable to resize to a thumbnail",e);
			return getFailImageData(request);
		}
	}
	
	/**
	 * Reads the filename to decide if it is a pdf
	 * @param imagePost
	 * @return
	 */
	private boolean isFileLikeAPdf(ImagePost imagePost){
		if(imagePost != null && imagePost.getFilename() != null 
				&& imagePost.getFilename().toLowerCase().endsWith(".pdf")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Read the raw image data for a fail
	 * @param request
	 * @return
	 */
	public static byte[] getFailImageData(final HttpServletRequest request){
		try{
			InputStream stream = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/404-image.png");
			byte[] datum = IOUtils.toByteArray(stream);
			stream.close();
			return datum;
		}catch(Exception e){
			LOG.error("Unable to read out fail image",e);
		}
		return null;
	}
	
	/**
	 * Reads in the fail image as a buffered image for resizing. 
	 * @param request
	 * @return
	 */
	private BufferedImage getFailImage(final HttpServletRequest request){
		try{
			//BAD BAD IMAGE!
			byte[] datum = getFailImageData(request);
			//and reset the image object. 
			ByteArrayInputStream bais = new ByteArrayInputStream(datum);
			BufferedImage image = ImageIO.read(bais);
			return image;
		}catch(Exception e){
			LOG.error("Can't even get the freaking fail image!",e);
		}
		return null;
	}
}
