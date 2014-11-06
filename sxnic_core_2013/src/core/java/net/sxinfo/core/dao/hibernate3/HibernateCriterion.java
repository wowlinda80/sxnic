package net.sxinfo.core.dao.hibernate3;

import org.hibernate.criterion.Criterion;

/**
 * Hibernate Criterion
 *
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class HibernateCriterion {

    /**
     * 查询条件
     */
    private Criterion criterion;

    /**
     * Path
     */
    private String path;

    /**
     * 构造器
     *
     * @param criterion 查询条件
     */
    public HibernateCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    /**
     * 构造器
     *
     * @param path Path
     * @param criterion 查询条件
     */
    public HibernateCriterion(String path, Criterion criterion) {
        this(criterion);
        this.path = path;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @return Returns the criterion.
     */
    public Criterion getCriterion() {
        return criterion;
    }
}
