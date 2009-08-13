package org.ochan.service;

import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElementRef;

import org.ochan.entity.Post;
import org.ochan.entity.Thread;

/**
 * 
 * @author David Seymore 
 * Oct 27, 2007
 */
@WebService
public interface PostService {

    /**
     * 
     * @author David Seymore 
     * Oct 27, 2007
     */
    public enum PostCriteria{TYPE,THREAD,AUTHOR,EMAIL,URL,TIME};


    /**
     * 
     * @param thisIdentifer (DONT SET THIS UNLESS ITS FOR SHARDING)
     * @param parentIdentifier
     * @param author
     * @param subject
     * @param email
     * @param url
     * @param comment
     * @param file (null if not an image post)
     * @param filename (null if not an image post)
     */
    public void createPost(Long thisIdentifer, Long parentIdentifier, String author, String subject, String email, String url, String comment, Byte[] file, String filename);
    
    
    /**
     * 
     * @param parent
     * @return
     */
    @XmlElementRef
    public List<Post> retrieveThreadPosts(Thread parent);

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
     * 
     * @param author
     * @return
     */
    public String computerAuthor(String author);
}
