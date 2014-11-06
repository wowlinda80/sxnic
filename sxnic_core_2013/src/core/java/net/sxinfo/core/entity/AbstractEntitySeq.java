package net.sxinfo.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 * Abstract Entity Object，包括所有实体对象中 相同的部分。
 * 不同的是，继承自本类的实体id为数字序列型，而不是UUID。
 * 
 * @version $Revision$
 * @author 曹浩
 */
@MappedSuperclass
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class AbstractEntitySeq implements Serializable {

	private static final long serialVersionUID = -3103291250234932391L;

	/**
     * Identifier
     */
    @Id
    @TableGenerator(name = "empGen", table = "id_gen", pkColumnName = "gen_key", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "empGen")
    protected Long id;

    /**
     * 创建时间
     */
    @Index(name = "creationdate_index")
    @Column(nullable = false)
    protected Date creationDate;

    /**
     * 默认构造器
     */
    public AbstractEntitySeq() {
        creationDate = new Date();
    }

    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
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
        if (!(o instanceof AbstractEntitySeq)) {
            return false;
        }

        final AbstractEntitySeq t = (AbstractEntitySeq) o;

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
}
