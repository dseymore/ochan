package org.Ochan.service.remote.webservice;

import java.util.List;

import javax.jws.WebService;

import org.Ochan.service.remote.model.RemoteCategory;

@WebService
public interface CategoryList {

	public List<RemoteCategory> getCategories();
}
