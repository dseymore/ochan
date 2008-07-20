package org.ochan.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ochan.entity.comparator.AscendingThreadComparator;

@Entity
@Table(name="THREAD")
public class Thread implements Comparable<Thread>{

    @Id
    @Column(updatable =false, unique=true)
    @SequenceGenerator(name="THREAD_SEQUENCE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="THREAD_SEQUENCE")
    private Long identifier;
    
    private Date startDate;
    
    @ManyToOne
    @JoinColumn(name="CATEGORY_IDENTIFIER", insertable=true, updatable=true)
    private Category category;
    
    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
    private List<Post> posts;
    
    @Transient
    private Comparator<Thread> comparator = new AscendingThreadComparator();
    
    public Thread(){
        
    }
    
    public Thread(Long identifier, Date startDate, Category category,
            List<Post> posts) {
        super();
        this.identifier = identifier;
        this.startDate = startDate;
        this.category = category;
        this.posts = posts;
    }
    
    
    @Override
	public int compareTo(Thread o) {
		return comparator.compare(this, o);
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }
    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    /**
     * @return the posts
     */
    public List<Post> getPosts() {
        return posts;
    }
    /**
     * @param posts the posts to set
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    
}
