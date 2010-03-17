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

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;

/**
 * The Post object representing existing data transmitted over the wire
 * 
 * @author David Seymore Jul 3, 2008
 */
@XmlRootElement(name = "RemotePost")
public class RemotePost {

	/**
	 * We want to flatten our posts into a single object for remote
	 * transmission.
	 * 
	 * @author David Seymore Jul 3, 2008
	 */
	public enum PostType {
		TEXT, IMAGE
	}

	private String comment;
	private Long identifier;
	private Long parentIdentifier;
	private String author;
	private String email;
	private String url;
	private String subject;
	private Date time;
	private String filename = "";
	private String fileSize = "";
	private PostType type;

	public RemotePost() {
		// nothing to do here.. need a default no-arg
	}

	/**
	 * Construct by example
	 * 
	 * @param example
	 */
	public RemotePost(Post example) {
		if (example instanceof ImagePost) {
			this.type = PostType.IMAGE;
			this.filename = ((ImagePost) example).getFilename();
			this.fileSize = ((ImagePost) example).getFileSize();
		} else {
			this.type = PostType.TEXT;
		}
		TextPost cast = (TextPost) example;
		this.comment = cast.getComment();
		this.identifier = cast.getIdentifier();
		this.parentIdentifier = cast.getParent().getIdentifier();
		this.author = cast.getAuthor();
		this.email = cast.getEmail();
		this.url = cast.getUrl();
		this.subject = cast.getSubject();
		this.time = cast.getTime();
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the parentIdentifier
	 */
	public Long getParentIdentifier() {
		return parentIdentifier;
	}

	/**
	 * @param parentIdentifier
	 *            the parentIdentifier to set
	 */
	public void setParentIdentifier(Long parentIdentifier) {
		this.parentIdentifier = parentIdentifier;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the type
	 */
	public PostType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(PostType type) {
		this.type = type;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}
