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
package org.ochan.service;

import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMimeType;

import org.ochan.entity.Post;

/**
 * 
 * @author David Seymore Oct 27, 2007
 */
@WebService
public interface PostService {

	/**
	 * 
	 * @author David Seymore Oct 27, 2007
	 */
	public enum PostCriteria {
		TYPE, THREAD, AUTHOR, EMAIL, URL, TIME
	};

	/**
	 * 
	 * @param thisIdentifer
	 *            (DONT SET THIS UNLESS ITS FOR SHARDING)
	 * @param parentIdentifier
	 * @param author
	 * @param subject
	 * @param email
	 * @param url
	 * @param comment
	 * @param file
	 *            (null if not an image post)
	 * @param filename
	 *            (null if not an image post)
	 */
	@XmlMimeType("application/octet-stream")
	public void createPost(Long thisIdentifer, Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file, String filename);

	/**
	 * 
	 * @param parent
	 * @return
	 */
	@XmlElementRef
	public List<Post> retrieveThreadPosts(Long parent);

	/**
	 * 
	 * @param identifier
	 * @return
	 */
	@XmlElementRef
	public Post getPost(Long identifier);

	/**
	 * 
	 * @param post
	 */
	@XmlElementRef
	public void updatePost(Post post);

	/**
	 * 
	 * @param identifier
	 */
	public void deletePost(Long identifier);

	/**
	 * Finds the post that owns the blob id parameter
	 * 
	 * @param identifier
	 * @return
	 */
	@XmlElementRef
	public Post getPostByBlob(Long identifier);

	/**
	 * 
	 * @param author
	 * @return
	 */
	public String computerAuthor(String author);
}
