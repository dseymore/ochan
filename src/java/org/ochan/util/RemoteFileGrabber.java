package org.ochan.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RemoteFileGrabber {

	private static final Log LOG = LogFactory.getLog(RemoteFileGrabber.class);

	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Byte[] getDataFromUrl(String url) {
		LOG.info("Attempting file: " + url);

		try {
			URL dest = new URL(url);
			InputStream is = dest.openConnection().getInputStream();
			byte[] bits = IOUtils.toByteArray(is);
			Byte[] bytes = ArrayUtils.toObject(bits);
			return bytes;
		} catch (Exception e) {
			// Fail at download
			LOG.error("Unable to DL url: " + url, e);
			// FIXME - we should handle this and tell the user
			return null;
		}

	}

}
