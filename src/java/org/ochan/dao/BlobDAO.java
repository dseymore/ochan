package org.ochan.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Blob;
import org.ochan.entity.Category;

public class BlobDAO {

    private static final Log LOG = LogFactory.getLog(BlobDAO.class);

    private EntityManagerFactory entityManagerFactory;

    @PersistenceUnit(name = "DefaultPersistenceUnit")
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }

    public void saveBlob(Blob blob) {
        LOG.trace("Saving Blob");
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(blob);
            em.getTransaction().commit();

            LOG.debug("persist complete.");
        } catch (Exception e) {
            LOG.error("Unable to persist blob:",e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Blob getBlob(Long identifier){
    	Blob blob = null;
        LOG.trace("getting blob with ID: " + identifier);
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT b FROM Blob b where b.identifier = :identifier");
            query.setParameter("identifier", identifier);
            List results = query.getResultList();
            if (results.size() == 1){
                blob = (Blob)results.get(0);
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve blob: " + identifier,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return blob;
    }
   
    public void delete(Long identifier){
    	EntityManager em = this.entityManagerFactory.createEntityManager();
    	try{
    		em.getTransaction().begin();
    		Category cat = em.find(Category.class, identifier);
    		em.remove(cat);
    		em.getTransaction().commit();
    	}catch (Exception e) {
            LOG.error("Unable to delete cat: " + identifier,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
