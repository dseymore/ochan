package org.Ochan.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.Ochan.entity.ExternalCategory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExternalCategoryDAO {

	private static final Log LOG = LogFactory.getLog(ExternalCategoryDAO.class);

	private EntityManagerFactory entityManagerFactory;

	@PersistenceUnit(name = "DefaultPersistenceUnit")
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.entityManagerFactory = emf;
	}
	
	public void deleteExternalCategory(Long identifier){
		EntityManager em = this.entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		ExternalCategory cat = (ExternalCategory)em.find(ExternalCategory.class, identifier);
		em.remove(cat);
		em.getTransaction().commit();
	}

	public void saveExternalCategory(ExternalCategory cat) {
		LOG.trace("Saving Externalcategory " + cat.getName());
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(cat);
			em.getTransaction().commit();

			LOG.debug("persist complete.");
		} catch (Exception e) {
			LOG.error("Unable to persist Externalcategory:" + cat.getName(), e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<ExternalCategory> getAllExternalCategories() {
		List<ExternalCategory> results = null;
		LOG.trace("getting all Externalcategories");
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			Query query = em.createQuery("SELECT c FROM ExternalCategory c");
			results = query.getResultList();
		} catch (Exception e) {
			LOG.error("Unable to retrieve categories", e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return results;
	}

	public ExternalCategory getExternalCategory(Long identifier) {
		ExternalCategory category = null;
		LOG.trace("getting Externalcategory with ID: " + identifier);
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT c FROM ExternalCategory c where c.identifier = :identifier");
			query.setParameter("identifier", identifier);
			List results = query.getResultList();
			if (results.size() == 1) {
				category = (ExternalCategory) results.get(0);
			}
		} catch (Exception e) {
			LOG.error("Unable to retrieve category: " + identifier, e);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return category;
	}
}
