package net.sxinfo.core.dao.hibernate3;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @version $Revision: 1.4 $
 * @author ²ÜºÆ
 */
@Entity
@Table(name = "test_entity1")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestEntity implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -8601860054928242282L;

    /**
     * identifier
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * for test
     */
    private String name;

    /**
     * clob test
     */
    @Lob
    @Column(name="full_text")
    private String fullText;

    public TestEntity() {
		super();
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
     * test
     *
     * @return full text
     */
    public String getFullText() {
        return fullText;
    }

    /**
     * test
     *
     * @param fullText
     */
    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TestEntity)) {
            return false;
        }

        final TestEntity t = (TestEntity) o;

        return new EqualsBuilder().append(id, t.getId()).append(name,
            t.getName()).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id.intValue()).append(name)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name)
                .toString();
    }
}
