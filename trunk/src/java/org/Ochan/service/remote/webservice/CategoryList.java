package org.Ochan.service.remote.webservice;

import java.util.List;

import javax.jws.WebService;

import org.Ochan.service.remote.model.RemoteCategory;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

@WebService
@InInterceptors(interceptors = "org.apache.cxf.interceptor.LoggingInInterceptor")
@OutInterceptors(interceptors = "org.apache.cxf.interceptor.LoggingOutInterceptor")
public interface CategoryList {

	public List<RemoteCategory> getCategories();
}
