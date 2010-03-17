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
package org.ochan.dpl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.dpl.ExternalCategoryDPL;
import org.ochan.dpl.OchanEnvironment;
import org.ochan.dpl.replication.TransactionTemplate;
import org.ochan.entity.ExternalCategory;
import org.ochan.service.ExternalCategoryService;

import com.sleepycat.persist.EntityCursor;

public class LocalExternalCategoryService implements ExternalCategoryService {

	private static final Log LOG = LogFactory.getLog(LocalExternalCategoryService.class);
	private OchanEnvironment environment;
		
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(OchanEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public void createCategory(String name, String description, String host) {
		try{
			final ExternalCategoryDPL dpl = new ExternalCategoryDPL();
			dpl.setHost(host);
			dpl.setLongDescription(description);
			dpl.setName(name);
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.externalCategoryByIdentifier().put(dpl);
				}
			}.run();
		}catch(Exception e){
			LOG.error("Unable to create category",e);
		}
	}

	@Override
	public void deleteCategory(final Long identifier) {
		try{
			new TransactionTemplate(environment){
				public void doInTransaction(){
					environment.externalCategoryByIdentifier().delete(identifier);
				}
			}.run();
		}catch(Exception e){
			LOG.error("Unable to delete category",e);
		}
	}

	@Override
	public ExternalCategory getCategory(Long identifier) {
		try{
			return map(environment.externalCategoryByIdentifier().get(identifier));
		}catch(Exception e){
			LOG.error("Unable to get category",e);
		}
		return null;
	}

	@Override
	public List<ExternalCategory> retrieveCategories(Map<ExternalCategoryCriteria, String> criteria) {
		List<ExternalCategory> cats = new ArrayList<ExternalCategory>();
		try{
			EntityCursor<ExternalCategoryDPL> dpls = environment.externalCategoryByIdentifier().entities();
			for (ExternalCategoryDPL dpl : dpls){
				cats.add(map(dpl));
			}
			dpls.close();
		}catch(Exception e){
			LOG.error("Unable to get categories",e);
		}
		return cats;
	}

	private ExternalCategory map(ExternalCategoryDPL dpl){
		ExternalCategory cat = new ExternalCategory();
		cat.setHost(dpl.getHost());
		cat.setIdentifier(dpl.getIdentifier());
		cat.setLongDescription(dpl.getLongDescription());
		cat.setName(dpl.getName());
		return cat;
	}
}
