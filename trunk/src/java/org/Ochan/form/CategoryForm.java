package org.Ochan.form;

public class CategoryForm {

    private String name;
    private String description; //text area please (tinymce would be good)
    
    private String copyFromAnotherServer; //boolean
    private String rootUrl;
    private String categoryName;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the copyFromAnotherServer
     */
    public String getCopyFromAnotherServer() {
        return copyFromAnotherServer;
    }
    /**
     * @param copyFromAnotherServer the copyFromAnotherServer to set
     */
    public void setCopyFromAnotherServer(String copyFromAnotherServer) {
        this.copyFromAnotherServer = copyFromAnotherServer;
    }
    /**
     * @return the rootUrl
     */
    public String getRootUrl() {
        return rootUrl;
    }
    /**
     * @param rootUrl the rootUrl to set
     */
    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }
    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }
    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    
}
