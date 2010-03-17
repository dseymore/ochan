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
package org.ochan.service.remote.webservice;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.remote.model.RemotePost;
import org.ochan.util.PostLinksAFixARockerJocker;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 
 * @author dseymore
 * 
 */
@Path("/post/")
@ManagedResource(description = "RESTful post grabber", objectName = "Ochan:type=rest,name=Post", logFile = "jmx.log")
public class PostListImpl implements PostList {
	private static final Log LOG = LogFactory.getLog(PostListImpl.class);

	private PostService postService;
	private ThreadService threadService;
	private Ehcache cache;

	private static Long NEXT_POST_GET_COUNT = Long.valueOf(0);

	/**
	 * @return the postService
	 */
	public PostService getPostService() {
		return postService;
	}

	/**
	 * @param postService
	 *            the postService to set
	 */
	public void setPostService(PostService postService) {
		this.postService = postService;
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
	 * @param cache
	 *            the cache to set
	 */
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	/**
	 * @return the nextGetCount
	 */
	@ManagedAttribute(description = "The number of calls received for a next post. This is used by the ActiveWatcherCounterJob to determine how many thread watches are open.")
	public Long getNextGetCount() {
		return NEXT_POST_GET_COUNT;
	}

	/**
	 * @see org.ochan.service.remote.webservice.PostList#next(java.lang.String)
	 *      returns a remote post with -1 identifier if no results are found
	 */
	@ProduceMime("application/json")
	@GET
	@Path("/next/{postId}/")
	public RemotePost next(@PathParam("postId") String id) {
		NEXT_POST_GET_COUNT++;
		Element cachedRemotePost = cache.get(id);
		Long threadId = Long.valueOf(-1);
		if (cachedRemotePost != null && !cachedRemotePost.isExpired()) {
			return (RemotePost) cachedRemotePost.getObjectValue();
		} else {
			// i have the post id.. lets find the thread that owns this post
			Post p = postService.getPost(Long.valueOf(id));
			if (p != null) {
				// then, get that thread
				org.ochan.entity.Thread t = threadService.getThread(p.getParent().getIdentifier());
				threadId = t.getIdentifier();
				// and then see if there is one greater than the current id
				t.setPosts(getPostService().retrieveThreadPosts(t.getIdentifier()));
				Collections.sort(t.getPosts());
				List<Post> posts = t.getPosts();
				RemotePost remote = null;
				boolean next = false;
				// walk through all the posts
				for (Post post : posts) {
					post.setParent(t);
					// if we found the next one
					if (next) {
						// save it
						remote = new RemotePost(post);
						// we need to fix the links..
						remote.setComment(PostLinksAFixARockerJocker.fixMahLinks((TextPost) post, true));
						// save this one.. its the winner
						cache.put(new Element(id, remote));
						break;
					} else {
						// see if this one is the current one
						if (post.getIdentifier().equals(Long.valueOf(id))) {
							next = true;
						}
					}
				}
				// didnt find one? ok..
				if (remote != null) {
					return remote;
				}
			}
			RemotePost rp = new RemotePost();
			rp.setIdentifier(Long.valueOf(-1));
			rp.setParentIdentifier(threadId);
			return rp;
		}
	}

}
