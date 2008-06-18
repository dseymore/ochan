package org.Ochan.service.remote.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.Ochan.entity.Category;
import org.Ochan.service.CategoryService.CategoryCriteria;
import org.Ochan.service.local.LocalCategoryService;
import org.Ochan.service.remote.model.RemoteCategory;

@WebService(endpointInterface = "org.Ochan.service.remote.webservice.CategoryList")
public class CategoryListImpl implements CategoryList {

	private LocalCategoryService categoryService;

	public LocalCategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(LocalCategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * Take all of the categories stored locally.. and send them over..
	 * 
	 * TODO - add some logic to handle if this is a mirror.. do we need to tell
	 * our client that the categories are from a mirror so they know not to talk
	 * to them tooo?
	 */
	@Override
	public List<RemoteCategory> getCategories() {
		List<Category> localCategoies = categoryService
				.retrieveCategories(new HashMap<CategoryCriteria, String>());
		List<RemoteCategory> categoriesToSend = new ArrayList<RemoteCategory>();
		if (localCategoies != null) {
			for (Category cat : localCategoies) {
				RemoteCategory rc = new RemoteCategory();
				rc.setName(cat.getName());
				categoriesToSend.add(rc);
			}
		}
		return categoriesToSend;
	}
}
