package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.PostDPL;
import org.ochan.dpl.PostType;
import org.ochan.dpl.SleepyEnvironment;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.BlobService;
import org.ochan.service.PostService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sleepycat.persist.EntityCursor;

@ManagedResource(description = "Local Post Service", objectName = "Ochan:service=local,name=LocalPostService", logFile = "jmx.log")
public class LocalPostService implements PostService {

	private BlobService blobService;
	private SleepyEnvironment environment;
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
	public void setEnvironment(SleepyEnvironment environment) {
		this.environment = environment;
	}

	// STATS
	private static long createCount = 0;

	private static long getCount = 0;

	private static long deleteCount = 0;

	private static long lastSearchTime = 0;

	/**
	 * @return the createCount
	 */
	@ManagedAttribute(description = "The number of calls to create a category")
	public long getCreateCount() {
		return createCount;
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
	public void createPost(Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file, String filename) {
		createCount++;
		try {
			PostDPL post = new PostDPL();
			post.setParent(parentIdentifier);
			post.setAuthor(computerAuthor(author));
			post.setSubject(subject);
			post.setEmail(email);
			post.setUrl(url);
			post.setComment(comment);
			post.setTime(new Date());
			post.setFilename(filename);
			if (file != null && file.length > 0) {
				post.setType(PostType.IMAGE);
				post.setImageIdentifier(blobService.saveBlob(file));
			} else {
				post.setType(PostType.TEXT);
			}
			environment.postByIdentifier.put(post);
		} catch (Exception e) {
			LOG.error("Unable to persist post.", e);
		}
	}

	@Override
	@ManagedOperation(description = "Delete a Post!")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "identifier", description = "The id of the post as a Long object (L at the end)") })
	public void deletePost(Long identifier) {
		deleteCount++;
		try {
			PostDPL post = environment.postByIdentifier.get(identifier);
			// deleting the files
			if (post.getImageIdentifier() != null) {
				blobService.deleteBlob(post.getImageIdentifier());
				blobService.deleteBlob(post.getThumbnailIdentifier());
			}
			environment.postByIdentifier.delete(identifier);
		} catch (Exception e) {
			LOG.error("Unable to delete post.", e);
		}
	}

	@Override
	public Post getPost(Long identifier) {
		getCount++;
		try {
			return map(environment.postByIdentifier.get(identifier));
		} catch (Exception e) {
			LOG.error("Unable to delete post.", e);
		}
		return null;
	}

	@Override
	public List<Post> retrievePosts(Map<PostCriteria, String> criteria) {
		try {
			// FIXME - never even used.. why does the API have this?
		} catch (Exception e) {
			LOG.error("Unable to retrieve posts.", e);
		}
		return null;
	}

	@Override
	public List<Post> retrieveThreadPosts(Thread parent) {
		// capture start of call
		long start = new Date().getTime();

		List<Post> posts = new ArrayList<Post>();
		try {
			EntityCursor<PostDPL> postDPL = environment.postByThread.subIndex(parent.getIdentifier()).entities();
			for (PostDPL dpl : postDPL) {
				posts.add(map(dpl));
			}
			postDPL.close();
		} catch (Exception e) {
			LOG.error("Unable to retrieve thread post.", e);
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
			PostDPL dpl = environment.postByIdentifier.get(post.getIdentifier());
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
			environment.postByIdentifier.put(dpl);
		} catch (Exception e) {
			LOG.error("Unable to update post.", e);
		}
	}

	private Post map(PostDPL post) {
		TextPost p = new TextPost();
		if (PostType.IMAGE.equals(post.getType())) {
			p = new ImagePost();
			((ImagePost) p).setImageIdentifier(post.getImageIdentifier());
			((ImagePost) p).setThumbnailIdentifier(post.getThumbnailIdentifier());
			((ImagePost) p).setFilename(post.getFilename());
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
	}

}
