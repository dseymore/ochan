package org.ochan.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Category;
import org.ochan.service.CategoryService.CategoryCriteria;

public class CategoryDAO {

    private static final Log LOG = LogFactory.getLog(CategoryDAO.class);

    private EntityManagerFactory entityManagerFactory;

    @PersistenceUnit(name = "DefaultPersistenceUnit")
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }

    public void saveCategory(Category cat) {
        LOG.trace("Saving category " + cat.getName());
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cat);
            em.getTransaction().commit();

            LOG.debug("persist complete.");
        } catch (Exception e) {
            LOG.error("Unable to persist category:" + cat.getName(),e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Category> getAllCategories() {
        List<Category> results = null;
        LOG.trace("getting all categories");
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Category c");
            results = query.getResultList();
        } catch (Exception e) {
            LOG.error("Unable to retrieve categories",e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return results;
    }
    
    public Category getCategory(Long identifier){
        Category category = null;
        LOG.trace("getting category with ID: " + identifier);
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Category c where c.identifier = :identifier");
            query.setParameter("identifier", identifier);
            List results = query.getResultList();
            if (results.size() == 1){
                category = (Category)results.get(0);
            }
        } catch (Exception e) {
            LOG.error("Unable to retrieve category: " + identifier,e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return category;
    }
    
    public List<Category> retrieveCategories(Map<CategoryCriteria, String> criteria) {
    	List<Category> results = null;
        LOG.trace("getting categories by search");
        LOG.trace(criteria);
        EntityManager em = this.entityManagerFactory.createEntityManager();
        try {
        	StringBuffer queryString = new StringBuffer();
        	queryString.append("SELECT c FROM Category c");
        	if (criteria.get(CategoryCriteria.NAME) != null){
        		queryString.append(" where c.name = :name");
        	}
        	Query query = em.createQuery(queryString.toString());
            query.setParameter("name", criteria.get(CategoryCriteria.NAME));
            results = query.getResultList();
        } catch (Exception e) {
            LOG.error("Unable to retrieve categories",e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return results;
    }
}
