package org.ochan.service.local;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Soundex;
import org.ochan.dao.PostDAO;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.BlobService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Local Post Service", objectName = "Ochan:service=local,name=LocalPostService", logFile = "jmx.log")
public class LocalPostService implements PostService {

	private static final Preferences PREFERENCES = Preferences.userNodeForPackage(LocalPostService.class);
	private PostDAO postDAO;
	private ThreadService threadService;
	private BlobService blobService;

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
	public String getTripcodeSeed(){
		return PREFERENCES.get("tripcode","");
	}
	
	@ManagedAttribute(description = "The starting seed to the tripdcode. (for secure tripcodes)")
	public void setTripcodeSeed(String seed){
		PREFERENCES.put("tripcode",seed);
	}
	
	// END STATS

	/**
	 * @return the postDAO
	 */
	public PostDAO getPostDAO() {
		return postDAO;
	}

	/**
	 * @param postDAO
	 *            the postDAO to set
	 */
	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	/**
	 * @return the threadService
	 */
	public ThreadService getThreadService() {
		return threadService;
	}

	/**
	 * @param threadService
	 *            the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}
	
	/**
	 * @param blobService the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}

	/**
	 * @see org.ochan.service.PostService#createPost(java.lang.Long,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.Byte[])
	 */
	public void createPost(Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file) {
		createCount++;
		TextPost p = null;
		if (file == null || file.length == 0) {
			p = new TextPost();
		} else {
			p = new ImagePost();
			//save the data!!!.. get the ID!!!
			((ImagePost) p).setImageIdentifier(blobService.saveBlob(file));
		}
		p.setAuthor(author);
		//TRIPCODE!!
		if (author != null && author.contains("#") && author.length() != author.lastIndexOf("#") + 1){
			int startOfTrip = author.indexOf("#");
			boolean securetrip = author.lastIndexOf("#") != startOfTrip;
			String code = securetrip ? this.getTripcodeSeed() + author.substring(author.lastIndexOf("#") + 1) : author.substring(startOfTrip + 1);
			StringBuffer tripfag = new StringBuffer();
			tripfag.append("!");
			if (securetrip){
				tripfag.append("!");
			}
			tripfag.append(new String(Base64.encodeBase64(code.getBytes())));
			p.setAuthor(author.substring(0,startOfTrip) + tripfag); 
		}
		p.setSubject(subject);
		p.setEmail(email);
		p.setUrl(url);
		p.setComment(comment);
		p.setTime(new Date());
		// retrieve our parent thread
		Thread t = threadService.getThread(parentIdentifier);
		t.setPosts(this.retrieveThreadPosts(t));
		t.getPosts().add(p);
		p.setParent(t);

		postDAO.create(p);
	}

	/**
	 * @see org.ochan.service.PostService#deletePost(java.lang.Long)
	 */
	@ManagedOperation(description="Delete a Post!")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name="identifier",description="The id of the post as a Long object (L at the end)")
	})
	public void deletePost(Long identifier) {
		deleteCount++;
		Post p = getPost(identifier);
		//we have to clean up our blobs too.. 
		if (p instanceof ImagePost){
			ImagePost ip = (ImagePost)p;
			if (ip.getImageIdentifier() != null){
				blobService.deleteBlob(ip.getImageIdentifier());
			}
			if (ip.getThumbnailIdentifier() != null){
				blobService.deleteBlob(ip.getThumbnailIdentifier());
			}
		}
		postDAO.delete(identifier);

	}

	/**
	 * @see org.ochan.service.PostService#getPost(java.lang.Long)
	 */
	public Post getPost(Long identifier) {
		getCount++;
		return postDAO.getPost(identifier);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.Ochan.service.PostService#retrieveThreadPosts(Thread)
	 */
	public List<Post> retrieveThreadPosts(Thread parent) {
		// capture start of call
		long start = new Date().getTime();
		List<Post> list = postDAO.getPostsByThread(parent);
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		
		return list;
	}


	/**
	 * @see org.ochan.service.PostService#retrievePosts(java.util.Map)
	 */
	public List<Post> retrievePosts(Map<PostCriteria, String> criteria) {
		// capture start of call
		long start = new Date().getTime();
		
		// TODO Auto-generated method stub
		
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		lastSearchTime = end - start;
		
		return null;
	}

	/**
	 * @see org.ochan.service.PostService#updatePost(org.ochan.entity.Post)
	 */
	public void updatePost(Post post) {
		postDAO.update(post);
	}

}
