package org.ochan.form;

public class PostReplyForm {

	private String parent;
	private String author;
	private String subject;
	private String email;
	private String url;
	private String comment;
	private byte[] file;
	private String fileUrl;
	private byte[] zipFile;

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
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
	 * @param email
	 *            the email to set
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
	 * @param url
	 *            the url to set
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
	 * @param comment
	 *            the comment to set
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
	 * @param file
	 *            the file to set
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
	 * @return the zipFile
	 */
	public byte[] getZipFile() {
		return zipFile;
	}

	/**
	 * @param zipFile the zipFile to set
	 */
	public void setZipFile(byte[] zipFile) {
		this.zipFile = zipFile;
	}

	/**
	 * Not including File. COMMENTS ARE INCLUDED!! (may be long)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(getClass().getName());
		buf.append("[");
		buf.append("author:").append(this.getAuthor());
		buf.append(",subject:").append(this.getSubject());
		buf.append(",email:").append(this.getEmail());
		buf.append(",url:").append(this.getUrl());
		buf.append(",comment:").append(this.getComment());
		buf.append(",parent: ").append(this.getParent());
		buf.append("]");
		return buf.toString();
	}
	
	

}
