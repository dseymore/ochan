package org.ochan.whatwhatwiki.control;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author denki
 */
public class UploadForm {

    private String author;
    private String title;
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
