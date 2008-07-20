package org.ochan.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.form.CategoryForm;
import org.ochan.service.CategoryService;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class CategoryAddController extends SimpleFormController {

    private static final Log LOG = LogFactory.getLog(CategoryAddController.class);
    
    private CategoryService service;
    
    /**
     * @return the service
     */
    public CategoryService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(CategoryService service) {
        this.service = service;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        LOG.trace("Handling submit of new Category.");
        
        CategoryForm form = (CategoryForm)command;
        service.createCategory(form.getName(), form.getDescription());
        
        return super.onSubmit(request, response, command, errors);
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request,
            HttpServletResponse response, BindException errors)
            throws Exception {
        LOG.trace("Handling rendering form of new Category.");
        return super.showForm(request, response, errors);
    }

}
