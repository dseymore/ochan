package org.ochan.imagebrowser.control;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Date;
import java.util.Iterator;
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
import org.ochan.imagebrowser.service.BlobService;
import org.ochan.imagebrowser.service.ImageService;
import org.ochan.imagebrowser.service.RemoteImage;
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
    private ImageService imageService;
    private BlobService blobService;
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
    public static final int MILLISECONDS_IN_A_DAY = 60 * 60 * 24 * 1000;
    public static final Long REQUESTS_PER_MINUTE = Long.valueOf(120);
    public static final Long GENERATIONS_PER_MINUTE = Long.valueOf(10);

//	private static Stopwatch requestWaitTime = SimonManager.getStopwatch(ThumbnailController.class.getName() + "Request");
//	private static Stopwatch generateWaitTime = SimonManager.getStopwatch(ThumbnailController.class.getName() + "Generate");
//
//	@ManagedAttribute(description="The average time a requests waits around being throttled")
//	public double getRequestWaitTime(){
//		return requestWaitTime.getStatProcessor().getMean();
//	}
//	@ManagedAttribute(description="The average time a thumbnail generation request waits around being throttled")
//	public double getGenerateWaitTime(){
//		return generateWaitTime.getStatProcessor().getMean();
//	}
    /**
     * @return the thumbnailImageQuality
     */
    @ManagedAttribute(description = "The quality of the thumbnail compression from 0 to 1.")
    public String getThumbnailImageQuality() {
        return PREFS.get("quality", THUMBNAIL_IMAGE_QUALITY);
    }

    /**
     * @param thumbnailImageQuality the thumbnailImageQuality to set
     */
    @ManagedAttribute(description = "The quality of the thumbnail compression from 0 to 1.")
    public void setThumbnailImageQuality(String thumbnailImageQuality) {
        Float f = Float.valueOf(thumbnailImageQuality);
        if (f.doubleValue() >= 0 && f.doubleValue() <= 1) {
            PREFS.put("quality", thumbnailImageQuality);
        }
    }

    //image generation is actually incredibly expensive.. so.. lets throttle that shit.
    @ManagedAttribute(description = "The number of requests the thumbnailer will pump out a minute")
    public String getRequestsPerMinute() {
        return PREFS.get("requestsPerMinute", REQUESTS_PER_MINUTE.toString());
    }

    @ManagedAttribute(description = "The number of requests the thumbnailer will pump out a minute")
    public void setRequestsPerMinute(String requestsPerMinute) {
        if (StringUtils.isNumeric(requestsPerMinute)) {
            PREFS.put("requestsPerMinute", requestsPerMinute);
        }
    }

    @ManagedAttribute(description = "The number of thumbnails that can be generated per minute")
    public String getThumbnailGenerationsPerMinute() {
        return PREFS.get("generationsPerMinute", GENERATIONS_PER_MINUTE.toString());
    }

    @ManagedAttribute(description = "The number of thumbnails that can be generated per minute")
    public void setThumbnailGenerationsPerMinute(String generationsPerMinute) {
        if (StringUtils.isNumeric(generationsPerMinute)) {
            PREFS.put("generationsPerMinute", generationsPerMinute);
        }
    }

    /**
     * The current thumbnail image quality.
     * @return
     */
    private float getThumbnailQuality() {
        Float f = Float.valueOf(getThumbnailImageQuality());
        return f;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The time in milliseconds that is threshold before logging exceptions due to poor performance.")
    public Long getTimeLength() {
        return new Long(PREFS.get("TIME", LOG_ON_THIS_TIME));
    }

    /**
     *
     * @param value
     */
    @ManagedAttribute(defaultValue = "200", description = "The time in milliseconds that is threshold before logging exceptions due to poor performance.", persistPolicy = "OnUpdate")
    @ManagedOperationParameters(
    @ManagedOperationParameter(description = "Time in Milliseconds", name = "value"))
    @ManagedOperation(description = "")
    public void setTimeLength(String value) {
        PREFS.put("TIME", value);
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The average time in milliseconds the system is encountering on thumnbail requests")
    public long getAverageTimeInMillis() {
        if (totalTimeInMillis != 0 && numberOfRequests != 0) {
            return totalTimeInMillis / numberOfRequests;
        }
        return 0;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The number of thumbnails that have been requested")
    public long getNumberOfThumbs() {
        return numberOfThumbs;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The last time we generated a thumb in milliseconds")
    public long getLastResizeTimeInMillis() {
        return lastResizeTimeInMillis;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The total time in milliseconds it has taken to generate all thumbs")
    public long getTotalResizeTimeInMIllis() {
        return totalResizeTimeInMillis;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The average time in milliseconds the system is encountering on thumnbail resize requests")
    public long getAverageResizeTimeInMillis() {
        if (totalResizeTimeInMillis != 0 && numberOfThumbs != 0) {
            return totalResizeTimeInMillis / numberOfThumbs;
        }
        return 0;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The number of images that have been requested")
    public long getNumberOfRequests() {
        return numberOfRequests;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The last time we generated retrieved in milliseconds")
    public long getLastTimeInMIllis() {
        return lastTimeInMillis;
    }

    /**
     *
     * @return
     */
    @ManagedAttribute(description = "The total time in milliseconds it has taken to retrieve all images")
    public long getTotalTimeInMIllis() {
        return totalTimeInMillis;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * @param blobService the blobService to set
     */
    public void setBlobService(BlobService blobService) {
        this.blobService = blobService;
    }

    public ThumbnailController() {
        super();
//		requestWaitTime.setStatProcessor(StatProcessorType.BASIC.create());
//		generateWaitTime.setStatProcessor(StatProcessorType.BASIC.create());
    }

    /**
     *
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Split requestSplit = requestWaitTime.start();
        Throttler requestThrottler = new Throttler(Long.valueOf(getRequestsPerMinute()).intValue(), 60000);
        requestThrottler.StartRequest();
//		requestSplit.stop();
        // capture start of call
        long start = new Date().getTime();
        try {
            numberOfRequests++;
            Long id = Long.valueOf(request.getParameter("identifier"));
            Long width = Long.valueOf(request.getParameter("width"));
            Long height = Long.valueOf(request.getParameter("height"));
            String dimension = height.toString() + "x" + width.toString();
            LOG.warn("Request: " + id + " " + dimension);
            RemoteImage p = imageService.getFile(id);
            byte[] datum = null;
            //if imagepost
            if (p != null) {

                Long blobId = blobService.findFile(id, dimension);
                if (blobId == null) {
                    File original = new File(p.getAbsolutePath());
                    datum = IOUtils.toByteArray(new FileInputStream(original));
                    //lets try checking if it is an image.. if not, we need to act on it.
                    BufferedImage image = null;

                    if (image == null) {
                        ByteArrayInputStream bais = new ByteArrayInputStream(datum);
                        //this might be the wrong direction..
                        ImageIO.setUseCache(false);
                        try {
                            image = ImageIO.read(bais);
                        } catch (Exception e) {
                            LOG.error("Bad image!", e);
                            image = null;
                        }
                        if (image == null || p == null) {
                            //BAD BAD IMAGE!
                            image = getFailImage(request);
                        }
                    }
                    datum = resizeTheImage(width, height, image, id, dimension, request);
                } else {
                    //take the imagePost's thumnbail and make it outputable
                    Byte[] thumbnailArray = blobService.getFile(blobId);
                    datum = ArrayUtils.toPrimitive(thumbnailArray);
                }
            }

            //caching please.. expire after a day
            response.setHeader("Cache-Control", "max-age=86400, public");
            response.setDateHeader("Expires", new Date().getTime() + MILLISECONDS_IN_A_DAY);

            LOG.debug("file length is " + datum.length);
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition", " inline; filename=" + id + ".jpg");
            // convert to non-object
            FileCopyUtils.copy(datum, response.getOutputStream());
        } catch (SocketException se) {
            //this happens when a socket is closed mid-stream.
            LOG.trace("Socket exception", se);
        } catch (Exception e) {
            LOG.error("Unable to create thumbnail", e);
        }
        // capture end of call
        long end = new Date().getTime();
        // compute total time
        lastTimeInMillis = end - start;
        totalTimeInMillis += end - start;

        if (lastTimeInMillis > getTimeLength()) {
            LOG.warn("Thumbnail times are getting excessive: " + lastTimeInMillis + " ms. for thumb id:" + request.getParameter("identifier"));
        }
        return null;
    }

    private BufferedImage convert(Image im) {
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    /**
     * Does the resizing of the image..
     * @param image
     * @param imagePost
     * @param request
     * @return
     */
    private byte[] resizeTheImage(Long destWidth, Long destHeight, BufferedImage image, Long imageIdentifer, String dimension, HttpServletRequest request) {
        try {
            BufferedImage resizedImage = null;
            int width = image.getWidth();
            int height = image.getHeight();
            long startThumb = new Date().getTime();
            double scale = determineImageScale(width, height, destWidth.intValue(), destHeight.intValue());
            numberOfThumbs++;
            resizedImage = convert(image.getScaledInstance((int)(width * scale), (int)(height * scale), Image.SCALE_FAST));
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
            imgWriter.write(null, new IIOImage(resizedImage, null, null), param);
            byte[] datum = baos.toByteArray();
            //store the thumbnail data in the post and persist
            {
                Byte[] thumbData = ArrayUtils.toObject(datum);
                blobService.createOrUpdate(imageIdentifer, dimension, thumbData);
            }
            long endThumb = new Date().getTime();
            lastResizeTimeInMillis = endThumb - startThumb;
            totalResizeTimeInMillis += endThumb - startThumb;
            return datum;
        } catch (Exception e) {
            LOG.error("Unable to resize to a thumbnail", e);
            return getFailImageData(request);
        }
    }


    private double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {

        double scalex = (double) targetWidth / sourceWidth;
        double scaley = (double) targetHeight / sourceHeight;
        return Math.min(scalex, scaley);
    }

    /**
     * Read the raw image data for a fail
     * @param request
     * @return
     */
    private byte[] getFailImageData(final HttpServletRequest request) {
        try {
            InputStream stream = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/404-image.png");
            byte[] datum = IOUtils.toByteArray(stream);
            stream.close();
            return datum;
        } catch (Exception e) {
            LOG.error("Unable to read out fail image", e);
        }
        return null;
    }

    /**
     * Reads in the fail image as a buffered image for resizing.
     * @param request
     * @return
     */
    private BufferedImage getFailImage(final HttpServletRequest request) {
        try {
            //BAD BAD IMAGE!
            byte[] datum = getFailImageData(request);
            //and reset the image object.
            ByteArrayInputStream bais = new ByteArrayInputStream(datum);
            BufferedImage image = ImageIO.read(bais);
            return image;
        } catch (Exception e) {
            LOG.error("Can't even get the freaking fail image!", e);
        }
        return null;
    }
}
