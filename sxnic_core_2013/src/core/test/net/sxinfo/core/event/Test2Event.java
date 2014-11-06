package net.sxinfo.core.event;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * For Test
 *
 * @version $Revision: 1.2 $
 * @author ²ÜºÆ
 */
public class Test2Event extends Event {

    /**
     * Name
     */
    private String name;

    /**
     * ¹¹ÔìÆ÷
     *
     * @param name Name
     */
    public Test2Event(String name) {
        this.name = name;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }
}
