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
 * HibernateDao的工具类
 *
 * @version $Revision: 1.3 $
 * @author 曹浩
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
     * 默认构造器
     */
    private HibernateDaoUtils() {
        // 避免被实例化
    }

    /**
     * 创建一个排序条件对应的HibernateOrder对象。
     * 排序条件的格式为 first.second.property/asc|desc
     *
     * @param sortField 排序条件，字符串，格式为 first.second.property/asc|desc
     * @param asc 是否为升序，false意味着降序
     * @return 排序条件对应的HibernateOrder对象，
     *         当排序条件为null或空字符串时返回null
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

        // 加了alias前缀后的排序条件
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
     * 根据Request parameter获取对应的单个SortCriteria对象，
     * 如果没有对应的排序属性，为了同HibernateDao的处理方式一致，将会返回一个
     * propery name为空的SortCriteria对象，HibernateDao在收到这样的
     * 对象的时候就默认为不排序。
     *
     * @param request HttpServletRequest
     * @param sortParamName sort parameter
     * @return 对应的单个SortCriteria对象
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
     * 根据Request parameter获取对应的SortCriteria对象列表
     *
     * @param request HttpServletRequest
     * @param sortParamName sort parameter
     * @return 对应的SortCriteria对象列表
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

            // 开始设置排序条件
            if (!StringUtils.isBlank(s)) {
                String[] sort = s.split("/");

                if (sort.length == 1) {
                    sortField = sort[0];
                    // 默认升序
                    sortConditions.add(new SortCriteria(sortField, true));
                } else if (sort.length == 2) {
                    sortField = sort[0];
                    String sortOrder = sort[1];

                    // 升序？ 降序?
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
