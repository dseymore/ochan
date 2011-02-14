package org.ochan.whatwhatwiki.control;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.whatwhatwiki.service.PageService;
import org.ochan.whatwhatwiki.service.RemotePage;
import org.ochan.whatwhatwiki.service.RemoteVersion;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class EditPageController implements Controller {

    private static final Log LOG = LogFactory.getLog(EditPageController.class);
    private PageService pageService;

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }
    
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        
        //author,title,content,comment
        RemotePage page = new RemotePage();
        RemoteVersion version = new RemoteVersion();
         
        //handle submission
        {
            page.setKey((String)arg0.getParameter("title"));
            version.setAuthor((String)arg0.getParameter("author"));
            version.setComment((String)arg0.getParameter("comment"));
            version.setContent((String)arg0.getParameter("content"));
            version.setDate(new Date());

            //save the username in the session.
            arg0.getSession().setAttribute("author", version.getAuthor());

            page.addVersion(version);
        }
        
        pageService.createOrUpdate(page);
        
        
        
        return new ModelAndView(new RedirectView("list.www"));
    }
    
}
