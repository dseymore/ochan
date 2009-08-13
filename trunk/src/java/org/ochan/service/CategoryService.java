package org.ochan.service;

import java.util.List;

import javax.jws.WebService;

import org.ochan.entity.Category;

/**
 * 
 * @author David Seymore Oct 20, 2007
 */
@WebService
public interface CategoryService {

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public void createCategory(Long thisIdentifier, String name, String description, String code);

	/**
	 * 
	 * @param criteria
	 * @return
	 */
	public List<Category> retrieveCategories();

	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public Category getCategory(Long identifier);

	/**
	 * 
	 * @param code
	 * @return
	 */
	public Category getCategoryByCode(String code);

	/**
	 * 
	 * @param identifier
	 */
	public void deleteCategory(Long identifier);

}
