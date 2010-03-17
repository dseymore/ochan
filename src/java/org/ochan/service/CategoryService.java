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
