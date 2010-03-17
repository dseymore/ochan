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
package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.BlobType;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.PostDPL;
import org.ochan.dpl.PostType;
import org.ochan.dpl.replication.TransactionTemplate;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.BlobService;
import org.ochan.service.PostService;
import org.ochan.util.FileUtils;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.persist.EntityCursor;

@ManagedResource(description = "Local Post Service", objectName = "Ochan:service=local,name=LocalPostService", logFile = "jmx.log")
public class LocalPostService implements PostService {

	private BlobService blobService;
	private OchanEnvironment environment;
	private static final Log LOG = LogFactory.getLog(LocalPostService.class);
	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(LocalPostService.class);

	/**
	 * @param blobService
	 *            the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(OchanEnvironment environment) {
		this.environment = environment;
	}

	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;

	private static long lastSearchTime = 0;
	
	private static long blobGetCount = 0;

	/**
	 * @return the createCount
	 */
	@ManagedAttribute(description = "The number of calls to create a post")
	public long getCreateCount() {
		return createCount;
	}
	
	/**
	 * @return the blobGetCount
	 */
	@ManagedAttribute(description = "The number of calls to get the post for a blob")
	public long getblobGetCount() {
		return blobGetCount;
	}

	/**
	 * @return the getCount
	 */
	@ManagedAttribute(description = "The number of calls to get a category.")
	public long getGetCount() {
		return getCount;
	}

	/**
	 * @return the deleteCount
	 */
	@ManagedAttribute(description = "The number of calls to delete a category.")
	public long getDeleteCount() {
		return deleteCount;
	}

	/**
	 * @return the lastSearchTime
	 */
	@ManagedAttribute(description = "The time in milliseconds of the last call to search for categories.")
	public long getLastSearchTime() {
		return lastSearchTime;
	}

	@ManagedAttribute(description = "The starting seed to the tripdcode. (for secure tripcodes)")
	public String getTripcodeSeed() {
		return PREFERENCES.get("tripcode", "");
	}

	@ManagedAttribute(description = "The starting seed to the tripdcode. (for secure tripcodes)")
	public void setTripcodeSeed(String seed) {
		PREFERENCES.put("tripcode", seed);
	}

	// END STATS

	/**
	 * Computes the author's name if they are using tripcodes.. 100% copied from
	 * the hibernate implementation previously used. wont be a copy once I
	 * delete all that.
	 */
	@Override
	public String computerAuthor(String author) {
		if (author != null && author.contains("#") && author.length() != author.lastIndexOf("#") + 1) {
			int startOfTrip = author.indexOf("#");
			boolean securetrip = author.lastIndexOf("#") != startOfTrip;
			String code = securetrip ? this.getTripcodeSeed() + author.substring(author.lastIndexOf("#") + 1) : author.substring(startOfTrip + 1);
			StringBuilder tripfag = new StringBuilder();
			tripfag.append("!");
			if (securetrip) {
				tripfag.append("!");
			}
			tripfag.append(new String(Base64.encodeBase64(code.getBytes())));
			return author.substring(0, startOfTrip) + tripfag.toString();
		}
		return author;
	}

	@Override
	public void createPost(Long thisIdentifier, Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file, String filename) {
		createCount++;
		try {
			final PostDPL post = new PostDPL();
			post.setIdentifier(thisIdentifier);
			post.setParent(parentIdentifier);
			post.setAuthor(computerAuthor(author));
			post.setSubject(subject);
			post.setEmail(email);
			post.setUrl(url);
			post.setComment(comment);
			post.setTime(new Date());
			post.setFilename(filename);
			if (file != null && file.length > 0) {
				//we should save the resolution and human readable file size here
				String fileSize = FileUtils.byteCountToDisplaySize(file.length);
				post.setFileSize(fileSize);
				post.setType(PostType.IMAGE);
				post.setImageIdentifier(blobService.saveBlob(file, null, BlobType.FULL));
			} else {
				post.setType(PostType.TEXT);
			}
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.postByIdentifier().put(post);
				}
			}.run();
		} catch (Exception e) {
			LOG.error("Unable to persist post.", e);
		}
	}

	@Override
	@ManagedOperation(description = "Delete a Post!")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "identifier", description = "The id of the post as a Long object (L at the end)") })
	public void deletePost(final Long identifier) {
		deleteCount++;
		try {
			final PostDPL post = environment.postByIdentifier().get(identifier);
			// deleting the files
			if (post.getImageIdentifier() != null) {
				blobService.deleteBlob(post.getImageIdentifier());
				blobService.deleteBlob(post.getThumbnailIdentifier());
			}
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.postByIdentifier().delete(identifier);
				}
			}.run();
		} catch (Exception e) {
			LOG.error("Unable to delete post.", e);
		}
	}

	@Override
	public Post getPost(Long identifier) {
		getCount++;
		try {
			return map(environment.postByIdentifier().get(identifier));
		} catch (Exception e) {
			LOG.error("Unable to get post.", e);
		}
		return null;
	}
	
	public Post getPostByBlob(Long identifier){
		blobGetCount++;
		int  count = 0;
		PostDPL post = null;
		if (identifier != null){
			try{
				EntityCursor<PostDPL> posts1 = environment.postByImage().subIndex(identifier).entities();
				if (posts1 != null){
					for (PostDPL dpl : posts1){
						post = dpl;
						count++;
					}
				}
				EntityCursor<PostDPL> posts2 = environment.postByThumbnail().subIndex(identifier).entities();
				if (posts2 != null){
					for (PostDPL dpl : posts2){
						post = dpl;
						count++;
					}
				}
			} catch (Exception e) {
				LOG.error("Unable to retrieve thread post.", e);
			}
		}
		if (count > 1){
			LOG.error("more than one post found for the blob id: " + identifier);
		}
		return map(post);
	}


	@Override
	public List<Post> retrieveThreadPosts(Long parent) {
		List<Post> posts = new ArrayList<Post>();
		// capture start of call
		long start = new Date().getTime();
		if (parent != null){
			try {
				EntityCursor<PostDPL> postDPL = environment.postByThread().subIndex(parent).entities();
				for (PostDPL dpl : postDPL) {
					posts.add(map(dpl));
				}
				postDPL.close();
			} catch (Exception e) {
				LOG.error("Unable to retrieve thread post.", e);
			}
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;

		return posts;
	}

	@Override
	public void updatePost(Post post) {
		try {
			final PostDPL dpl = environment.postByIdentifier().get(post.getIdentifier());
			if (post instanceof ImagePost) {
				dpl.setImageIdentifier(((ImagePost) post).getImageIdentifier());
				dpl.setThumbnailIdentifier(((ImagePost) post).getThumbnailIdentifier());
				dpl.setFilename(((ImagePost) post).getFilename());
			}
			TextPost tp = (TextPost) post;
			dpl.setAuthor(tp.getAuthor());
			dpl.setComment(tp.getComment());
			dpl.setEmail(tp.getEmail());
			dpl.setSubject(tp.getSubject());
			dpl.setTime(tp.getTime());
			dpl.setUrl(tp.getUrl());
			// and update
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.postByIdentifier().put(dpl);
				}
			}.run();
		} catch (Exception e) {
			LOG.error("Unable to update post.", e);
		}
	}

	private Post map(PostDPL post) {
		if (post != null){
			TextPost p = new TextPost();
			if (PostType.IMAGE.equals(post.getType())) {
				p = new ImagePost();
				((ImagePost) p).setImageIdentifier(post.getImageIdentifier());
				((ImagePost) p).setThumbnailIdentifier(post.getThumbnailIdentifier());
				((ImagePost) p).setFilename(post.getFilename());
				((ImagePost) p).setFileSize(post.getFileSize());
			}
			p.setAuthor(post.getAuthor());
			p.setComment(post.getComment());
			p.setEmail(post.getEmail());
			p.setIdentifier(post.getIdentifier());
			Thread t = new Thread();
			t.setIdentifier(post.getParent());
			p.setParent(t);
			p.setSubject(post.getSubject());
			p.setTime(post.getTime());
			p.setUrl(post.getUrl());
			return p;
		}else{
			LOG.info("Null post.. probably deleted.");
		}
		return null;
	}

}
