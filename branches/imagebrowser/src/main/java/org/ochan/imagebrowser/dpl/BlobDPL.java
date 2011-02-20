package org.ochan.imagebrowser.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import java.io.Serializable;

/**
 *
 * @author dseymore
 */
@Entity
public class BlobDPL implements Serializable {

    /**
     * Generated Serial Ver. ID
     */
    private static final long serialVersionUID = -143206161534849350L;

    @PrimaryKey(sequence = "BLOB")
    private Long identifier;

    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    private String dimension;

    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    private Long image;
    private Byte[] data;

    public Byte[] getData() {
        return data;
    }

    public void setData(Byte[] data) {
        this.data = data;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }
}
