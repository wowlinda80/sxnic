package net.sxnic.comm.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import net.sxinfo.core.dao.hibernate3.SortCriteria;
import net.sxinfo.core.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * 用于判断一个字段是否可以排序的Tag
 * 
 * @author 曹浩
 */
public class SortTag extends SimpleTagSupport {

    /**
     * Sort Field
     */
    private String sortField;

    /**
     * Key of Message
     */
    private String messageKey;

    /**
     * 是否为多重排序
     */
    private boolean multiSort = false;

    /**
     * 是否使用AJAX
     */
    private boolean ajax = false;

    /**
     * Base URL
     */
    private String baseUrl;

    /**
     * commons logging
     */
    private static final Log log = LogFactory.getLog(SortTag.class);

    /**
     * @see javax.servlet.jsp.tagext.SimpleTag#doTag()
     */
    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest request = (HttpServletRequest) ((PageContext) getJspContext())
                .getRequest();

        // 根据情况设定base url
        if (StringUtils.isBlank(baseUrl)) {
            baseUrl = request.getRequestURI();

            log.debug("baseUrl is requestURI: " + baseUrl);
        } else {
            if (!ajax) {
                // 在baseUrl前加上Context Path
                baseUrl = request.getContextPath() + baseUrl;
            }
            log.debug("baseUrl is: " + baseUrl);
        }

        List<SortCriteria> sortConditions = WebUtils.getSortCriteria(request);

        StringBuffer buf = new StringBuffer();

        buf.append("<span class=\"sortable\"><a href=\"");

        if (ajax) {
            buf.append("#\"");
            buf.append(" ");
            buf.append("onclick=\"MainContent('");
        }
        buf.append(baseUrl);
        buf.append("?page=" +
                (!StringUtils.isBlank(request.getParameter("page")) ? request
                        .getParameter("page") : ""));

        if (multiSort) {
            // 设置sort parameter
            for (SortCriteria sc : sortConditions) {
                String sortField = sc.getPropertyName();
                Boolean asc = sc.isAscending();

                // 不添加本sortField的queryString
                if (!this.sortField.equals(sortField)) {
                    buf.append("&sort=" + sortField + "/" +
                            (asc ? "asc" : "desc"));
                }
            }
        }

        // 专门添加本sortField的queryString
        int sortFieldIndex = sortConditions.indexOf(new SortCriteria(sortField,
                false));
        if (sortFieldIndex > -1) {
            boolean asc = sortConditions.get(sortFieldIndex).isAscending();
            // asc的时候添加desc
            // 但如果已经是desc的时候就不再添加连接，这样用户可以取消排序
            if (asc) {
                buf.append("&sort=" + sortField + "/" + "desc");
            }
        } else {
            buf.append("&sort=" + sortField + "/asc");
        }

        // append other query string
        for (Object s : request.getParameterMap().keySet()) {
            String key = (String) s;
            if (!"sort".equals(key) && !"page".equals(key)) {
                buf.append("&" + key + "=" + request.getParameter(key));
            }
        }

        if (ajax) {
            buf.append("'); return false;");
        }

        buf.append("\" title=\"");

        String propertyMessage = LocalizedTextUtil.findDefaultText(messageKey,
            ActionContext.getContext().getLocale());

        String title = LocalizedTextUtil.findDefaultText("sort.sortby",
            ActionContext.getContext().getLocale(),
            new String[] { propertyMessage });

        buf.append(title + "\"");

        buf.append(">");
        buf.append(propertyMessage);

        if (sortFieldIndex > -1) {
            buf.append(" ");
            if (sortConditions.get(sortFieldIndex).isAscending()) {
                buf.append("<img src=\"" + request.getContextPath() +
                        "/images/arrow_up.gif\" border=\"0\"");
                buf.append(" alt=\"" +
                        LocalizedTextUtil.findDefaultText("sort.sort-asc",
                            ActionContext.getContext().getLocale()) + "\">");
            } else {
                buf.append("<img src=\"" + request.getContextPath() +
                        "/images/arrow_down.gif\" border=\"0\"");
                buf.append(" alt=\"" +
                        LocalizedTextUtil.findDefaultText("sort.sort-desc",
                            ActionContext.getContext().getLocale()) + "\">");
            }
        }

        buf.append("</a></span>");

        log.debug("SortTag: " + buf.toString());

        // output
        JspWriter writer = getJspContext().getOut();
        try {
            writer.print(buf.toString());
        } catch (IOException e) {
            log.error(e);
            throw new JspException(e.toString());
        }
    }

    /**
     * @param sortField The sortField to set.
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * @param baseUrl The baseUrl to set.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * @param messageKey The messageKey to set.
     */
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    /**
     * @param ajax The ajax to set.
     */
    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }

    /**
     * @param multiSort The multiSort to set.
     */
    public void setMultiSort(boolean multiSort) {
        this.multiSort = multiSort;
    }
}
