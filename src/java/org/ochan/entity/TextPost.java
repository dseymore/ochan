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
package org.ochan.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "text")
public class TextPost extends Post {

	private String comment;

	public TextPost() {
		super();
	}

	/**
	 * 
	 * @param parent
	 * @param author
	 * @param email
	 * @param url
	 */
	public TextPost(Long identifier, Thread parent, String author, String email, String url, String comment, String subject, Date time) {
		super(identifier, parent, author, email, url, subject, time);
		this.comment = comment;
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

}
