package org.ochan.imagebrowser.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import java.io.Serializable;

/**
 *
 * @author dseymore
 */
@Entity(version = 3)
public class ImageDPL implements Serializable {

    /**
     * Generated Serial Ver. ID
     */
    private static final long serialVersionUID = 4984578543177980498L;

    @PrimaryKey(sequence = "IMAGES")
    private Long identifier;

    private String absolutePath;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }




}
