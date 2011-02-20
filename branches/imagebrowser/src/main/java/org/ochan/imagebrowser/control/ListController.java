/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.imagebrowser.control;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ochan.imagebrowser.service.ImageService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author dseymore
 */
public class ListController implements Controller {

    private String viewName;
    private ImageService imageService;

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        Map controlModel = new HashMap();

        controlModel.put("images", imageService.getAllImages());


        return new ModelAndView(viewName, controlModel);
    }
}
