package org.ochan.util;

import java.util.prefs.Preferences;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Utility class to wrap the normal XML configurable multipart file dude with a live configurable JMX version
 * @author David Seymore 
 * Nov 12, 2008
 */
@ManagedResource(objectName = "System:type=config,name=UploadConfiguration", description = "Upload file management", log = true, logFile = "jmx.log")
public class ManagedCommonsMultipartResolver extends CommonsMultipartResolver {
	
	private static final Log LOG = LogFactory.getLog(ManagedCommonsMultipartResolver.class);
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(ManagedCommonsMultipartResolver.class);
	
	/**
	 * Sensible default of 1 Mib
	 */
	private static final long DEFAULT_MAX_1_MIB = 1000000;
	
	/**
	 * Constructor that auto-starts to the last preferences value
	 */
	public ManagedCommonsMultipartResolver(){
		super();
		//we have to set the value at startup.. its not used via getter
		LOG.info("Resetting max file upload size");
		super.setMaxUploadSize(Long.valueOf(this.getMaxUploadSize()).longValue());
	}
	
	@ManagedAttribute(description="The filesize in bytes for the maximum upload size")
	public String getMaxUploadSize(){
		return PREFERENCES.get("maxSize", String.valueOf(DEFAULT_MAX_1_MIB));
	}
	
	@ManagedAttribute(description="The filesize in bytes for the maximum upload size")
	public void setMaxUploadSize(String value){
		try{
			Long val = Long.valueOf(value);
			//if we get this far, things are all right!
			PREFERENCES.put("maxSize", value);
			//call the method of my super!
			//calling our static reference.. which is the ref to the one SPRING is using.. yay, no restart needed!
			super.setMaxUploadSize(val.longValue());
		}catch(Exception e){
			LOG.error("unable to set max file upload size:", e);
		}
	}

	/**
	 * Need to set it into the dude .. we're getting .. 
	 */
	@Override
	public FileUpload getFileUpload() {
		FileUpload upload = super.getFileUpload();
		upload.setFileSizeMax(Long.valueOf(this.getMaxUploadSize()));
		upload.setSizeMax(Long.valueOf(this.getMaxUploadSize()));
		return upload;
	}
	
	
}
