package net.sxinfo.core.dao.hibernate3;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Sort Criteria
 *
 * @version $Revision: 1.1 $
 * @author ²ÜºÆ
 */
public class SortCriteria {

    private boolean isAscending = false;

    private String propertyName;

    /**
     * ¹¹ÔìÆ÷
     *
     * @param ascending
     * @param name
     */
    public SortCriteria(String name, boolean ascending) {
        isAscending = ascending;
        propertyName = name;
    }

    /**
     * @return Returns the isAscending.
     */
    public boolean isAscending() {
        return isAscending;
    }

    /**
     * @return Returns the propertyName.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SortCriteria)) {
            return false;
        }

        final SortCriteria sc = (SortCriteria) o;

        return new EqualsBuilder().append(propertyName, sc.getPropertyName())
                .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(propertyName).toHashCode();
    }
}
