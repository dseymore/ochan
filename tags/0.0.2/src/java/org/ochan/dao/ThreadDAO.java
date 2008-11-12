package org.ochan.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Post;
import org.ochan.entity.Thread;
import org.ochan.service.ThreadService.ThreadCriteria;

public class ThreadDAO {
    private static final Log LOG = LogFactory.getLog(ThreadDAO.class);

    private EntityManagerFactory entityManagerFactory;
    private PostDAO postDao;

    @PersistenceUnit(name = "DefaultPersistenceUnit")
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }
    public void setPostDao(PostDAO postDao) {
        this.postDao = postDao;
    }


    public void create(Thread thread){
        if(LOG.isTraceEnabled()){
            LOG.trace("Saving thread  in categories " + thread.getCategory());
        }
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(thread);
            em.getTransaction().commit();
            //now save the post(s) (potentially more that just one)
            for(Post p : thread.getPosts()){
                if (p.getParent() == null){
                    p.setParent(thread);
                }
                postDao.create(p);
            }
            LOG.debug("persist complete.");
        } catch (Exception e) {
            LOG.error("Unable to persist thread",e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Thread getThread(Long identifier){
    	Thread thread = null;
        LOG.trace("getting thread with ID: " + identifier);
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT t FROM Thread t where t.identifier = :identifier");
            query.setParameter("identifier", identifier);
            List results = query.getResultList();
            if (results.size() == 1){
                thread = (Thread)results.get(0);
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve thread: " + identifier,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return thread;
    }
    
    /**
     * 
     * @param criteria
     * @return
     */
    public List<Thread> search(Map<ThreadCriteria, Object> criteria){
    	List<Thread> threads = null;
    	LOG.trace("Searching for threads with criteria.");
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
        	StringBuffer queryString = new StringBuffer("SELECT t FROM Thread t where ");
        	//build our string ... nasty
        	if (criteria.get(ThreadCriteria.CATEGORY) != null){
        		queryString.append(" t.category.identifier = :category ");
        	}
        	if (criteria.get(ThreadCriteria.DELETEQUEUE) != null){
        		queryString.append(" t.deleteDate != null ");
        	}
            Query query = em.createQuery(queryString.toString());
            //then add our parameters...
            //may not need these 'ifs'
            if (criteria.get(ThreadCriteria.CATEGORY) != null){
            	query.setParameter("category", criteria.get(ThreadCriteria.CATEGORY));
        	}
            List results = query.getResultList();
            if (results.size() > 0){
            	threads = new ArrayList<Thread>();
	            for(Object result : results){
	                threads.add((Thread)result);
	            }
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve threads for search criteria",e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return threads;
    }
    
    /**
     * Deletes the thread.
     * @param thread
     */
    public void delete(Long identifier){
    	LOG.trace("About to delete thread: " + identifier);
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
        	em.getTransaction().begin();
        	Thread thread = em.find(Thread.class, identifier);
        	em.remove(thread);
    		em.getTransaction().commit();
        	LOG.info("Thread deleted");
        } catch (Exception e) {
            LOG.error("Unable to delete thread.",e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * Updates a thread
     * @param thread
     */
    public void update(Thread thread){
    	EntityManager em = this.entityManagerFactory.createEntityManager();
    	try{
    		em.getTransaction().begin();
    		em.merge(thread);
    		em.getTransaction().commit();
    	}catch (Exception e) {
            LOG.error("Unable to update thread: " + thread,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
