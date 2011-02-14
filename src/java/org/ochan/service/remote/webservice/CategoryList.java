package org.ochan.service.remote.webservice;

import java.util.List;

import javax.jws.WebService;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.ochan.service.remote.model.RemoteCategory;

@WebService
@InInterceptors(interceptors = "org.apache.cxf.interceptor.LoggingInInterceptor")
@OutInterceptors(interceptors = "org.apache.cxf.interceptor.LoggingOutInterceptor")
public interface CategoryList {

	public List<RemoteCategory> getCategories();
}
