/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ochan.whatwhatwiki.control;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.whatwhatwiki.service.PageService;
import org.ochan.whatwhatwiki.service.RemotePage;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author denki
 */
public class PreviewController implements Controller {

    private static final Log LOG = LogFactory.getLog(PreviewController.class);
    private String viewName;
    private PageService pageService;

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String key = arg0.getParameter("key");
        RemotePage page = pageService.get(key);

        String processed = processLinks(page.getLatest());
        model.put("content",processed);
        model.put("page",page);

        return new ModelAndView(viewName, model);
    }
    
    private static String LINK = "/wiki/preview/";
    private static String HREF = "<a href=\""+LINK;
    private static String HREF_MID = "\">";
    private static String HREF_END = "</a>";
    
    /**
     * This method takes the original wikified version, and makes the links awesome
     * @param original
     * @return
     */
    public String processLinks(String original){
        String[] broken = original.split("\\[\\[");
        StringBuffer buffer = new StringBuffer();
        for (String thingie: broken){
            int end = thingie.indexOf("]]");
            if (end > -1){
                String key = thingie.substring(0, end);
                buffer.append(makeLink(key)).append(thingie.substring(end + 2));
            }else{
                buffer.append(thingie);
            }
        }
        return buffer.toString();
    }
    private String makeLink(String key){
        StringBuilder builder = new StringBuilder();
        return builder.append(HREF).append(key).append(HREF_MID).append(key).append(HREF_END).toString();
    }
}
