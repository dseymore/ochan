package org.ochan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="TEXT_POST")
public class TextPost extends Post{

    @Lob
    private String comment;
    
    public TextPost(){
        super();
    }
    
    /**
     * 
     * @param parent
     * @param author
     * @param email
     * @param url
     */
    public TextPost(Long identifier, Thread parent, String author, String email, String url, String comment, String subject, Date time) {
        super(identifier, parent, author, email, url, subject, time);
        this.comment = comment;
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


    
    
}
