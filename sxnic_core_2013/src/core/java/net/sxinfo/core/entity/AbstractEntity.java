package net.sxinfo.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

/**
 * Abstract Entity Object，包括所有实体对象中 相同的部分。
 * 
 * @version $Revision$
 * @author 曹浩
 */
@MappedSuperclass
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 614666706582360755L;

	/**
     * Identifier
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    protected String id;
    
    /**
     * Version
     */
    @Version
    @Column(name = "version")
    protected Long version;

    /**
     * 创建时间
     */
    @Index(name = "creationdate_index")
    @Column(nullable = false)
    protected Date creationDate;
    
    protected Date lastUpdateDate;
    
    @Column(length = 30)
    protected String lastEditor;
    
    @Column(nullable = false)
    protected boolean enabled;

    /**
     * 默认构造器
     */
    public AbstractEntity() {
        creationDate = new Date();
        lastUpdateDate = new Date();
        enabled= true;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the creationDate.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractEntity)) {
            return false;
        }

        final AbstractEntity t = (AbstractEntity) o;

        return new EqualsBuilder().append(id, t.getId()).append(creationDate,
            t.getCreationDate()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(creationDate)
                .toHashCode();
    }

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastEditor() {
		return lastEditor;
	}

	public void setLastEditor(String lastEditor) {
		this.lastEditor = lastEditor;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
