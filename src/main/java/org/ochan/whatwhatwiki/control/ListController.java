package org.ochan.whatwhatwiki.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
        
        //author handling code
        {
            //lets play with cookies to remember the author name
            if (arg0.getCookies() != null){
                    for(Cookie cookie : arg0.getCookies()){
                            if ("wwwAuthor".equals(cookie.getName())){
                                    arg0.getSession().setAttribute("author",cookie.getValue());
                            }
                    }
            }

            String author = (String)arg0.getSession().getAttribute("author");
            if (StringUtils.isNotEmpty(author)){
                    controlModel.put("author", author);
            }else{
                    controlModel.put("author", "Anonymous");
            }
        }
        
        return new ModelAndView(viewName, controlModel);
    }
}
