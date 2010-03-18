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

	/**
	 * Returns the filename based on the url.
	 * 
	 * @param url
	 * @return
	 */
	public static String getFilenameFromUrl(String url) {
		try {
			int indexOfQuestionMark = url.indexOf('?');
			if (indexOfQuestionMark < 0) {
				indexOfQuestionMark = url.length();
			}
			int indexOfLastSlash = url.substring(0, indexOfQuestionMark).lastIndexOf('/');
			String filename = url.substring(indexOfLastSlash + 1, indexOfQuestionMark);
			return filename;
		} catch (Exception e) {
			LOG.error("Unable to figure out the url's filename: " + url, e);
			return "";
		}

	}

}
