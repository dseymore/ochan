package org.ochan.whatwhatwiki.control;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.whatwhatwiki.service.FileService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class UploadController extends SimpleFormController {

    private static final Log LOG = LogFactory.getLog(UploadController.class);
    private FileService fileService;

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
        // to actually be able to convert Multipart instance to byte[]
        // we have to register a custom editor
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    // now Spring knows how to handle multipart object and convert them
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        UploadForm form = (UploadForm) command;

        //save the username in the session.
        request.getSession().setAttribute("author", form.getAuthor());
        //handle errors
        Map<String,Object> mapper = new HashMap<String,Object>();
        if (form.getFile() == null){
            mapper.put("error","upload file required.");
        }else{
            Byte[] data = ArrayUtils.toObject(form.getFile().getBytes());
            String filename = form.getFile().getOriginalFilename();
            fileService.createOrUpdate(form.getTitle(), form.getAuthor(), filename, data);
        }
        
        //go back to the list of pages
        return new ModelAndView(new RedirectView("list.www"), mapper);
    }
}