package org.ochan.service;

import java.util.List;
import java.util.Map;

import org.ochan.entity.ExternalCategory;

/**
 * 
 * @author David Seymore Oct 20, 2007
 */

public interface ExternalCategoryService {

	public enum ExternalCategoryCriteria {
		NAME, DESCRIPTION, HOST
	};

	/**
	 * 
	 * @param name
	 * @param description
	 * @param host
	 */
	public void createCategory(String name, String description, String host);

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	public List<ExternalCategory> retrieveCategories(
			Map<ExternalCategoryCriteria, String> criteria);

	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public ExternalCategory getCategory(Long identifier);

	/**
	 * 
	 * @param identifier
	 */
	public void deleteCategory(Long identifier);

}