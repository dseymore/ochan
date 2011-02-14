package org.ochan.whatwhatwiki.service.remote.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProduceMime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.whatwhatwiki.service.PageService;
import org.ochan.whatwhatwiki.service.RemotePage;

/**
 *
 * @author denki
 */
@Path("/page/")
public class RestPageService implements org.ochan.whatwhatwiki.service.remote.webservice.PageService {

    private static final Log LOG = LogFactory.getLog(RestPageService.class);
    private org.ochan.whatwhatwiki.service.PageService pageService;

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    @Override
    @ProduceMime("application/json")
    @GET
    @Path("/get/{key}/")
    public RemotePage getPage(@PathParam("key") String key) {
        return pageService.get(key);
    }
}
