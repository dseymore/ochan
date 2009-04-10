package org.ochan.form;


public class ThreadForm {

    private String categoryIdentifier;
    private String author;
    private String subject;
    private String email;
    private String url;
    private String comment;
    private byte[] file;
    private String fileUrl;
    
    /**
     * @return the categoryIdentifier
     */
    public String getCategoryIdentifier() {
        return categoryIdentifier;
    }
    /**
     * @param categoryIdentifier the categoryIdentifier to set
     */
    public void setCategoryIdentifier(String categoryIdentifier) {
        this.categoryIdentifier = categoryIdentifier;
    }
    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @return the file
     */
    public byte[] getFile() {
        return file;
    }
    /**
     * @param file the file to set
     */
    public void setFile(byte[] file) {
        this.file = file;
    }
    /**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	/**
	 * Not including File. COMMENTS ARE INCLUDED!! (may be long)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(getClass().getName());
		buf.append("[");
		buf.append("author:").append(this.getAuthor());
		buf.append("subject:").append(this.getSubject());
		buf.append(",email:").append(this.getEmail());
		buf.append(",url:").append(this.getUrl());
		buf.append(",comment:").append(this.getComment());
		buf.append(",category:").append(this.getCategoryIdentifier());
		buf.append(",fileUrl: ").append(this.getFileUrl());
		buf.append("]");
		return buf.toString();
	}
    
    

}
