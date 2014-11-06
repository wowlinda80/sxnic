package net.sxinfo.core.dao.hibernate3;

import org.hibernate.criterion.Criterion;

/**
 * Hibernate Criterion
 *
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public class HibernateCriterion {

    /**
     * ��ѯ����
     */
    private Criterion criterion;

    /**
     * Path
     */
    private String path;

    /**
     * ������
     *
     * @param criterion ��ѯ����
     */
    public HibernateCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    /**
     * ������
     *
     * @param path Path
     * @param criterion ��ѯ����
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
