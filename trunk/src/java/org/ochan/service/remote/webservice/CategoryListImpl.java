/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.ochan.service.remote.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.ochan.entity.Category;
import org.ochan.service.CategoryService;
import org.ochan.service.remote.model.RemoteCategory;

/**
 * 
 * @author dseymore
 * 
 */
@WebService(endpointInterface = "org.ochan.service.remote.webservice.CategoryList")
public class CategoryListImpl implements CategoryList {

	private CategoryService categoryService;

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
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
		List<Category> localCategoies = categoryService.retrieveCategories();
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
