package org.ochan.service;

import java.util.List;
import java.util.Map;

import org.ochan.entity.Thread;

/**
 * 
 * @author David Seymore 
 * Oct 27, 2007
 */
public interface ThreadService {

    /**
     * 
     * @author David Seymore 
     * Oct 27, 2007
     */
    public enum ThreadCriteria{STARTDATE,CATEGORY,DELETEQUEUE};

    /**
     * 
     * @param category
     * @param author
     * @param subject
     * @param url
     * @param email
     * @param content
     * @param file
     */
    public void createThread(Long category, String author, String subject, String url, String email, String content, Byte[] file);
    

    /**
     * 
     * @param criteria
     * @return
     */
    public List<Thread> retrieveThreads(Map<ThreadCriteria, Object> criteria);
    

    /**
     * 
     * @param identifier
     * @return
     */
    public Thread getThread(Long identifier);
    

    /**
     * 
     * @param identifier
     */
    public void deleteThread(Long identifier);
    
    /**
     * 
     * @param thread
     */
    public void updateThread(Thread thread);
}
