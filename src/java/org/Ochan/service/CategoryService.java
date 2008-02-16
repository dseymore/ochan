package org.Ochan.service;

import java.util.List;
import java.util.Map;

import org.Ochan.entity.Category;

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
    public void createCategory(String name, String description);
    
    /**
     * 
     * @param criteria
     * @return
     */
    public List<Category> retrieveCategories(Map<CategoryCriteria, String> criteria);
    
    /**
     * Retrieves an entirely filled category by it's identifier
     * 
     * This is used primarily for transferring service from one frontend host to another
     * @param identifier
     * @return
     */
    public Category getCategory(Long identifier);
    
    /**
     * 
     * @param identifier
     */
    public void deleteCategory(Long identifier);
    
}
