package org.Ochan.service.remote.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;

import org.Ochan.entity.Post;
import org.Ochan.service.PostService;
import org.Ochan.service.ThreadService;
import org.Ochan.service.remote.model.RemotePost;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("/post/")
public class PostListImpl implements PostList {
	private static final Log LOG = LogFactory.getLog(PostListImpl.class);
	
	private PostService postService;
	private ThreadService threadService;
	
	/**
	 * @return the postService
	 */
	public PostService getPostService() {
		return postService;
	}

	/**
	 * @param postService the postService to set
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
	 * @param threadService the threadService to set
	 */
	public void setThreadService(ThreadService threadService) {
		this.threadService = threadService;
	}

	/**
	 * @see org.Ochan.service.remote.webservice.PostList#next(java.lang.String)
	 * returns a remote post with -1 identifier if no results are found
	 */
	@ProduceMime("application/json")
    @GET
    @Path("/next/{postId}/")
	public RemotePost next(@PathParam("postId") String id) {
		//i have the post id.. lets find the thread that owns this post
		Post p = postService.getPost(Long.valueOf(id));
		if (p != null){
			//then, get that thread
			org.Ochan.entity.Thread t = threadService.getThread(p.getParent().getIdentifier());
			//and then see if there is one greater than the current id	
			t.setPosts(getPostService().retrieveThreadPosts(t));
			List<Post> posts = t.getPosts();
			RemotePost remote = null;
			boolean next = false;
			//walk through all the posts
			for (Post post : posts){
				post.setParent(t);
				//if we found the next one
				if (next){
					//save it
					remote = new RemotePost(post);
					break;
				}else{
					//see if this one is the current one
					if (post.getIdentifier().equals(Long.valueOf(id))){
						next = true;
					}
				}
			}
			//didnt find one? ok.. 
			if (remote != null){
				return remote;
			}
		}
		RemotePost rp = new RemotePost();
		rp.setIdentifier(new Long(-1));
		return rp;
	}
	
	

}
