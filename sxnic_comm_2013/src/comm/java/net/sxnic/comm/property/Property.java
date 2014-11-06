package net.sxnic.comm.property;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.IndexColumn;

/**
 * 属性表
 * 
 * @author 孙宇飞
 */
@Entity
@Table(name = "comm_property")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Property extends AbstractEntity {

	private static final long serialVersionUID = 4894733093734339415L;

	/**
     * key，unique
     */
    @IndexColumn(name = "name_index")
    @Column(unique = true, nullable = false)
    private String propName;

    /**
     * value
     */
    @Column(nullable = false)
    private String propValue;

    /**
     * 备注、描述
     */
    private String description;

    /**
     * 默认构造器
     */
    public Property() {
        super();
        description = "";
    }

    /**
     * 构造器
     * @param propName key
     * @param propValue value
     */
    public Property(String propName, String propValue) {
        this();
        this.propName = propName;
        this.propValue = propValue;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Property)) {
            return false;
        }

        final Property p = (Property) o;

        return new EqualsBuilder().append(propName, p.getPropName()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(propName).toHashCode();
    }

    /**
     * @return the propName
     */
    public String getPropName() {
        return propName;
    }

    /**
     * @param propName the propName to set
     */
    public void setPropName(String propName) {
        this.propName = propName;
    }

    /**
     * @return the propValue
     */
    public String getPropValue() {
        return propValue;
    }

    /**
     * @param propValue the propValue to set
     */
    public void setPropValue(String propValue) {
        this.propValue = propValue;
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
}
