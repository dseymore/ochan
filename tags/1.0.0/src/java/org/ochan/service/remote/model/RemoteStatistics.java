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
package org.ochan.service.remote.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RemoteStatistics")
public class RemoteStatistics {

	private String numberOfFiles;
	private String totalContentSize;
	private String numberOfThreads;
	private String numberOfPosts;
	private String postswithImages;

	/**
	 * @return the numberOfFiles
	 */
	public String getNumberOfFiles() {
		return numberOfFiles;
	}

	/**
	 * @param numberOfFiles
	 *            the numberOfFiles to set
	 */
	public void setNumberOfFiles(String numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}

	/**
	 * @return the totalContentSize
	 */
	public String getTotalContentSize() {
		return totalContentSize;
	}

	/**
	 * @param totalContentSize
	 *            the totalContentSize to set
	 */
	public void setTotalContentSize(String totalContentSize) {
		this.totalContentSize = totalContentSize;
	}

	/**
	 * @return the numberOfThreads
	 */
	public String getNumberOfThreads() {
		return numberOfThreads;
	}

	/**
	 * @param numberOfThreads
	 *            the numberOfThreads to set
	 */
	public void setNumberOfThreads(String numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}

	/**
	 * @return the numberOfPosts
	 */
	public String getNumberOfPosts() {
		return numberOfPosts;
	}

	/**
	 * @param numberOfPosts
	 *            the numberOfPosts to set
	 */
	public void setNumberOfPosts(String numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

	/**
	 * @return the postswithImages
	 */
	public String getPostswithImages() {
		return postswithImages;
	}

	/**
	 * @param postswithImages
	 *            the postswithImages to set
	 */
	public void setPostswithImages(String postswithImages) {
		this.postswithImages = postswithImages;
	}

}
