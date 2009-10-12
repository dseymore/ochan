package org.ochan.whatwhatwiki.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ochan.whatwhatwiki.service.FileService;
import org.ochan.whatwhatwiki.service.PageService;
import org.ochan.whatwhatwiki.service.RemotePage;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListController implements Controller {

    private PageService pageService;
    private FileService fileService;
    private String viewName;

    /**
     * @param pageService the pageService to set
     */
    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    /**
     * @param fileService the fileService to set
     */
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        Map controlModel = new HashMap();

        List<RemotePage> pages = pageService.getAllPages();
        controlModel.put("pages",pages);
        
        List<String> files = fileService.getAllFileKeys();
        controlModel.put("files",files);
        
        return new ModelAndView(viewName, controlModel);
    }
}
