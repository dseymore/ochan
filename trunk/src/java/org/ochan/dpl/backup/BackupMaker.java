package org.ochan.dpl.backup;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.PostType;
import org.ochan.dpl.service.LocalBlobService;
import org.ochan.dpl.service.LocalPostService;
import org.ochan.dpl.service.LocalThreadService;
import org.ochan.entity.ImagePost;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.service.BlobService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;

/**
 * An Attempt at making backups, for redistributing the shards or switching content over to a replicated environment
 * however, thinking through it.. this wont work.. because the ids WILL have to change when the import happens.
 * @author David Seymore 
 * Dec 26, 2009
 */
public class BackupMaker {

	private static final Log LOG = LogFactory.getLog(BackupMaker.class);
	
	/**
	 * These should all be the proxies.. that lets us suck in the shards
	 */
	private ThreadService threadService;
	private PostService postService;
	private BlobService blobService;
	
	/**
	 * @param threadService the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @param postService the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	/**
	 * @param blobService the blobService to set
	 */
	public void setBlobService(BlobService blobService) {
		this.blobService = blobService;
	}

	public void backupCategory(Long categoryIdentifier, String directoryForEnvironment){
		ThreadCriteria criteria = new ThreadCriteria();
		criteria.setCategory(categoryIdentifier);
		List<Thread> threads = threadService.retrieveThreads(criteria);
		
		OchanEnvironment environment = new BackupEnvironment(directoryForEnvironment);
		
		//we're specifically not setting any of the cascading services to detect any problems..
		//when the backup methods get called, they shouldn't cascade, so as we can preserve links & ids. 
		LocalBlobService backupBlobService = new LocalBlobService();
		backupBlobService.setEnvironment(environment);
		LocalPostService backupPostService = new LocalPostService();
		backupPostService.setEnvironment(environment);
		LocalThreadService backupThreadService = new LocalThreadService();
		backupThreadService.setEnvironment(environment);
		
		///now, just go from thread to thread, recreating them. 
		//which is totally annoying.
		
		for (Thread thread: threads){
			//save the thread
			backupThreadService.backupThread(thread.getIdentifier(), categoryIdentifier, thread.getStartDate());
			
			List<Post> posts = postService.retrieveThreadPosts(thread.getIdentifier());
			for (Post post : posts){
				//create the thread with the first posts
				String content = null;
				Byte[] file = null;
				Byte[] thumbnail = null;
				String filename = null;
				String fileSize = null;
				PostType postType = PostType.TEXT;
				Long blobId = null;
				Long thumbId = null;
				if (post instanceof TextPost){
					content = ((TextPost)post).getComment();
				}
				if (post instanceof ImagePost){
					postType = PostType.IMAGE;
					filename = ((ImagePost)post).getFilename();
					blobId = ((ImagePost)post).getImageIdentifier();
					file = blobService.getBlob(blobId);
					fileSize = ((ImagePost)post).getFileSize();
					thumbId = ((ImagePost)post).getThumbnailIdentifier();
					thumbnail = blobService.getBlob(thumbId);
					backupBlobService.saveBlob(file, blobId);
					backupBlobService.saveBlob(thumbnail, thumbId);
				}
				backupPostService.backupPost(post.getIdentifier(), thread.getIdentifier(), post.getAuthor(), post.getSubject(), post.getEmail(), post.getUrl(), content, blobId, thumbId, fileSize, filename, post.getTime(), postType);
			}
		}
		
		environment.close();
		
	}
	
}
