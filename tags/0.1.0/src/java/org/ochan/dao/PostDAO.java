package org.ochan.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;

/**
 * 
 * @author David Seymore 
 * Feb 9, 2008
 */
public class PostDAO {
    private static final Log LOG = LogFactory.getLog(PostDAO.class);

    private EntityManagerFactory entityManagerFactory;

    /**
     * @param emf
     */
    @PersistenceUnit(name = "DefaultPersistenceUnit")
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }
    
    /**
     * 
     * @param post
     */
    public void create(Post post){
        if(LOG.isTraceEnabled()){
            LOG.trace("Saving post to thread " + post.getParent().getIdentifier() + " in categories " + post.getParent().getCategory());
        }
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();

            LOG.debug("persist complete.");
        } catch (Exception e) {
            LOG.error("Unable to persist post to thread:" + post.getParent().getIdentifier());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * 
     * @param identifier
     * @return
     */
    public Post getPost(Long identifier){
    	Post post = null;
        LOG.trace("getting post with ID: " + identifier);
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Post p where p.identifier = :identifier");
            query.setParameter("identifier", identifier);
            List results = query.getResultList();
            if (results.size() == 1){
                post = (Post)results.get(0);
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve post: " + identifier,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return post;
    }
    
    /**
     * For a given thread identifier, returns all child posts. 
     * @param identifier
     * @return
     */
    public List<Post> getPostsByThread(Thread parent){
    	List<Post> p = null;
    	LOG.trace("Getting posts with thread identifier: " + parent);
    	EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Post p where p.parent = :identifier");
            query.setParameter("identifier", parent);
            List results = query.getResultList();
            if (results.size() > 0){
            	p = new ArrayList<Post>();
            	for (Object result : results){
            		p.add((Post)result);
            	}
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve post: " + parent,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return p;
    }
    
    public void update(Post p){
    	EntityManager em = this.entityManagerFactory.createEntityManager();
    	try{
    		em.getTransaction().begin();
    		em.merge(p);
    		em.getTransaction().commit();
    	}catch (Exception e) {
            LOG.error("Unable to update post: " + p,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void delete(Long identifier){
    	EntityManager em = this.entityManagerFactory.createEntityManager();
    	try{
    		em.getTransaction().begin();
    		Post post = em.find(Post.class, identifier);
    		em.remove(post);
    		em.getTransaction().commit();
    	}catch (Exception e) {
            LOG.error("Unable to delete post: " + identifier,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
