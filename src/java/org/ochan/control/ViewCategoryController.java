package org.ochan.control;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ochan.entity.Category;
import org.ochan.entity.Post;
import org.ochan.entity.TextPost;
import org.ochan.entity.Thread;
import org.ochan.form.ThreadForm;
import org.ochan.service.CategoryService;
import org.ochan.service.PostService;
import org.ochan.service.ThreadService;
import org.ochan.service.ThreadService.ThreadCriteria;
import org.ochan.util.PostLinksAFixARockerJocker;
import org.ochan.util.RemoteFileGrabber;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class ViewCategoryController extends SimpleFormController {
    private static final Log LOG = LogFactory.getLog(ViewCategoryController.class);

    private CategoryService categoryService;
    private ThreadService threadService;
    private PostService postService;
    private String viewName;

    /**
     * @return the categoryService
     */
    public CategoryService getCategoryService() {
        return categoryService;
    }

    /**
     * @param categoryService the categoryService to set
     */
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    /**
     * @return the threadService
     */
    public ThreadService getThreadService() {
        return threadService;
    }

    /**
     * @param threadService the threadService to set
     */
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    /**
     * @return the postService
     */
    public PostService getPostService() {
        return postService;
    }

    /**
     * @param postService the postService to set
     */
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    /**
     * @return the viewName
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * @param viewName the viewName to set
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BindException e, Map map) throws Exception {
        Map controlModel = new HashMap();
        Long identifier = Long.valueOf(httpServletRequest.getParameter("identifier"));
        Category cat = categoryService.getCategory(identifier);
        if (cat != null) {
            Map<ThreadCriteria, Object> searchCriteria = new HashMap<ThreadCriteria, Object>();
            searchCriteria.put(ThreadCriteria.CATEGORY, cat.getIdentifier());
            List<Thread> threads = getThreadService().retrieveThreads(searchCriteria);
            cat.setThreads(threads);
            if (threads != null) {
                for (Thread t : threads) {
                    t.setPosts(getPostService().retrieveThreadPosts(t));
                    for (Post p : t.getPosts()) {
                        if (p instanceof TextPost) {
                            TextPost tp = (TextPost) p;
                            tp.setComment(PostLinksAFixARockerJocker.fixMahLinks(tp, false));
                        }
                    }
                }
                Collections.sort(cat.getThreads());
            }
            controlModel.put("category", cat);
            String author = (String) httpServletRequest.getSession().getAttribute("author");
            if (StringUtils.isNotEmpty(author)) {
                controlModel.put("author", author);
            } else {
                controlModel.put("author", "Anonymous");
            }
        } else {
            //dead category! 404 thing
            return new ModelAndView("404", controlModel);
        }
        return new ModelAndView(viewName, controlModel);
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
        // to actually be able to convert Multipart instance to byte[]
        // we have to register a custom editor
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        // now Spring knows how to handle multipart object and convert them
    }


    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object o, BindException e) throws Exception {
        //FIXME: cut and paste from ThreadAddController... ick
        // move this to a service?
        ThreadForm tf = (ThreadForm) o;
        //save the username in the session.
        request.getSession().setAttribute("author", tf.getAuthor());

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        LOG.error("Multipart?: " + isMultipart);

        Byte[] bytes = null;
        if (tf.getFileUrl() == null || "".equals(tf.getFileUrl().trim())) {
            // Copying the byte array to be a Byte array. fun..
            if (tf.getFile() != null && tf.getFile().length > 0) {
                bytes = new Byte[tf.getFile().length];
                int invariant = 0;
                for (byte b : tf.getFile()) {
                    bytes[invariant] = new Byte(b);
                    invariant++;
                }
            }
        } else {
            bytes = RemoteFileGrabber.getDataFromUrl(tf.getFileUrl());
        }

        LOG.debug("calling thread service to add thread (form): " + tf);
        try {
            threadService.createThread(Long.valueOf(tf.getCategoryIdentifier()), tf.getAuthor(), tf.getSubject(), tf.getUrl(), tf.getEmail(), tf.getComment(), bytes);
        } catch (Exception ex) {
            //FIXME - handle this so the user gets notified NICELY
            LOG.error("Unable to create thread", ex);
        }

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("identifier", tf.getCategoryIdentifier());

        return new ModelAndView(new RedirectView("openCategory.Ochan"), dataMap);
    }
}
