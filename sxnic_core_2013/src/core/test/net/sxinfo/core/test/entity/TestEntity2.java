package net.sxinfo.core.test.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @version $Revision: 1.2 $
 * @author ²ÜºÆ
 */
@Entity
@Table(name = "TEST_ENTITY2")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestEntity2 implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = 4832614231359932432L;

    /**
     * identifier
     */
    private Long id;

    /**
     * for test
     */
    private String name;

    /**
     * @return Returns the id.
     */
    @Id
    @GeneratedValue
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
        if (!(o instanceof TestEntity2)) {
            return false;
        }

        final TestEntity2 t = (TestEntity2) o;

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
