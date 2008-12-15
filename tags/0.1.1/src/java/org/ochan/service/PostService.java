package org.ochan.service;

import java.util.List;
import java.util.Map;

import org.ochan.entity.Post;
import org.ochan.entity.Thread;

/**
 * 
 * @author David Seymore 
 * Oct 27, 2007
 */
public interface PostService {

    /**
     * 
     * @author David Seymore 
     * Oct 27, 2007
     */
    public enum PostCriteria{TYPE,THREAD,AUTHOR,EMAIL,URL,TIME};


    /**
     * 
     * @param parentIdentifier
     * @param author
     * @param subject
     * @param email
     * @param url
     * @param comment
     * @param file
     */
    public void createPost(Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file);
    

    /**
     * 
     * @param criteria
     * @return
     */
    public List<Post> retrievePosts(Map<PostCriteria, String> criteria);
    
    
    /**
     * 
     * @param parent
     * @return
     */
    public List<Post> retrieveThreadPosts(Thread parent);

    /**
     * 
     * @param identifier
     * @return
     */
    public Post getPost(Long identifier);
    
    
    /**
     * 
     * @param post
     */
    public void updatePost(Post post);

    /**
     * 
     * @param identifier
     */
    public void deletePost(Long identifier);
}
