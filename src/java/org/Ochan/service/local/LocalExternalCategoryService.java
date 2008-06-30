package org.Ochan.service.local;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.Ochan.dao.ExternalCategoryDAO;
import org.Ochan.entity.ExternalCategory;
import org.Ochan.service.ExternalCategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LocalExternalCategoryService implements ExternalCategoryService {

	private static final Log LOG = LogFactory
			.getLog(LocalExternalCategoryService.class);

	private ExternalCategoryDAO externalCategoryDAO;

	public ExternalCategoryDAO getExternalCategoryDAO() {
		return externalCategoryDAO;
	}

	public void setExternalCategoryDAO(ExternalCategoryDAO externalCategoryDAO) {
		this.externalCategoryDAO = externalCategoryDAO;
	}

	@Override
	public void createCategory(String name, String description, String host) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("about to create category with name:" + name
					+ " and description: " + description);
		}
		ExternalCategory cat = new ExternalCategory();
		cat.setLongDescription(description);
		cat.setName(name);
		cat.setHost(host);
		externalCategoryDAO.saveExternalCategory(cat);
	}

	@Override
	public void deleteCategory(Long identifier) {
		externalCategoryDAO.deleteExternalCategory(identifier);
	}

	@Override
	public ExternalCategory getCategory(Long identifier) {
		return externalCategoryDAO.getExternalCategory(identifier);
	}

	@Override
	public List<ExternalCategory> retrieveCategories(
			Map<ExternalCategoryCriteria, String> criteria) {
		// capture start of call
		long start = new Date().getTime();

		if (criteria == null || criteria.isEmpty()) {
			return externalCategoryDAO.getAllExternalCategories();
		}
		// capture end of call
		long end = new Date().getTime();
		// compute total time
		// lastSearchTime = end - start;

		return null;
	}

}
