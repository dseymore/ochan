package org.Ochan.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="CATEGORY")
public class Category {

    private String name;
    
    @Lob
    private String longDescription;
    
    @Id
    @Column(updatable =false, unique=true)
    @SequenceGenerator(name="CATEGORY_SEQUENCE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_SEQUENCE")
    private Long identifier;
    
    @OneToMany(mappedBy="category", fetch=FetchType.LAZY)
    private Set<Thread> threads;
    
    @Version
    @Column(name="OPTLOCK")
    private Integer version;
    
    public Category(){
        
    }
    
    public Category(String name, String longDescription, Long identifier,
            Set<Thread> threads, Integer version) {
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
    public Set<Thread> getThreads() {
        return threads;
    }
    /**
     * @param threads the threads to set
     */
    public void setThreads(Set<Thread> threads) {
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
