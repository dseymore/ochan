package org.ochan.entity;

import java.util.List;

public class Category {

    private String name;
    
    private String longDescription;
    
    private Long identifier;
    
    private List<Thread> threads;
    
    private Integer version;
    
    public Category(){
        
    }
    
    public Category(String name, String longDescription, Long identifier,
    		List<Thread> threads, Integer version) {
        super();
        this.name = name;
        this.longDescription = longDescription;
        this.identifier = identifier;
        this.threads = threads;
        this.version = version;
    }
    
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
     * @return the longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }
    /**
     * @param longDescription the longDescription to set
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    /**
     * @return the identifier
     */
    public Long getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the threads
     */
    public List<Thread> getThreads() {
        return threads;
    }
    /**
     * @param threads the threads to set
     */
    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    
    
}
