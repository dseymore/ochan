package org.ochan.service;

import java.util.List;
import java.util.Map;

import org.ochan.entity.Category;

/**
 * 
 * @author David Seymore 
 * Oct 20, 2007
 */
public interface CategoryService {

    public enum CategoryCriteria{NAME, DESCRIPTION};

    
    /**
     * 
     * @param name
     * @param description
     */
    public void createCategory(String name, String description, String code);
    
    /**
     * 
     * @param criteria
     * @return
     */
    public List<Category> retrieveCategories(Map<CategoryCriteria, String> criteria);
    
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
    public Category getCategory(String code);
    
    /**
     * 
     * @param identifier
     */
    public void deleteCategory(Long identifier);
    
}
