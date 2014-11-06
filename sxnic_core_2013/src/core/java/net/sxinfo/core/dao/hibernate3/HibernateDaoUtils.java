package net.sxinfo.core.dao.hibernate3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Order;

/**
 * HibernateDao�Ĺ�����
 *
 * @version $Revision: 1.3 $
 * @author �ܺ�
 */
public final class HibernateDaoUtils {

    /**
     * Commons Logging
     */
    private static final Log log = LogFactory.getLog(HibernateDaoUtils.class);

    /**
     * Ascending
     */
    public static final String ASC = "asc";

    /**
     * Descending
     */
    public static final String DESC = "desc";

    /**
     * Ĭ�Ϲ�����
     */
    private HibernateDaoUtils() {
        // ���ⱻʵ����
    }

    /**
     * ����һ������������Ӧ��HibernateOrder����
     * ���������ĸ�ʽΪ first.second.property/asc|desc
     *
     * @param sortField �����������ַ�������ʽΪ first.second.property/asc|desc
     * @param asc �Ƿ�Ϊ����false��ζ�Ž���
     * @return ����������Ӧ��HibernateOrder����
     *         ����������Ϊnull����ַ���ʱ����null
     */
    public static HibernateOrder createHibernateOrder(String sortField,
            boolean asc) {

        if (StringUtils.isBlank(sortField)) {
            return null;
        }

        String path = null;
        String sort = sortField;
        if (sortField.indexOf(".") != -1) {
            path = sortField.substring(0, sortField.lastIndexOf("."));
            sort = sortField.substring(sortField.lastIndexOf(".") + 1);
        }

        Order o = null;

        // ����aliasǰ׺�����������
        String fullSort = sort;
        if (path != null) {
            fullSort = path.replace('.', '_') + "." + sort;
        }

        if (asc) {
            o = Order.asc(fullSort);
        } else {
            o = Order.desc(fullSort);
        }

        log.debug("Create order for: " + sortField + " - path: " + path
                + " - asc: " + asc);

        return new HibernateOrder(path, o);
    }

    /**
     * ����Request parameter��ȡ��Ӧ�ĵ���SortCriteria����
     * ���û�ж�Ӧ���������ԣ�Ϊ��ͬHibernateDao�Ĵ���ʽһ�£����᷵��һ��
     * propery nameΪ�յ�SortCriteria����HibernateDao���յ�������
     * �����ʱ���Ĭ��Ϊ������
     *
     * @param request HttpServletRequest
     * @param sortParamName sort parameter
     * @return ��Ӧ�ĵ���SortCriteria����
     */
    public static SortCriteria getSingleSortCriteria(
            HttpServletRequest request, String sortParamName) {

        if (getSortCriteria(request, sortParamName).isEmpty()) {
            return new SortCriteria(null, false);
        }

        SortCriteria sc = getSortCriteria(request, sortParamName).get(0);
        return sc;
    }

    /**
     * ����Request parameter��ȡ��Ӧ��SortCriteria�����б�
     *
     * @param request HttpServletRequest
     * @param sortParamName sort parameter
     * @return ��Ӧ��SortCriteria�����б�
     */
    public static List<SortCriteria> getSortCriteria(
            HttpServletRequest request, String sortParamName) {

        String[] sorts = request.getParameterValues(sortParamName);
        if (sorts == null) {
            return Collections.EMPTY_LIST;
        }

        List<SortCriteria> sortConditions = new LinkedList<SortCriteria>();

        for (String s : sorts) {
            String sortField = null;
            boolean asc = false;

            // ��ʼ������������
            if (!StringUtils.isBlank(s)) {
                String[] sort = s.split("/");

                if (sort.length == 1) {
                    sortField = sort[0];
                    // Ĭ������
                    sortConditions.add(new SortCriteria(sortField, true));
                } else if (sort.length == 2) {
                    sortField = sort[0];
                    String sortOrder = sort[1];

                    // ���� ����?
                    if (ASC.equalsIgnoreCase(sortOrder)) {
                        asc = true;
                    }

                    sortConditions.add(new SortCriteria(sortField, asc));
                }
            }
        }

        return sortConditions;
    }
}
