package net.sxinfo.core.util;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.SortCriteria;
import net.sxinfo.core.util.CookieUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Web Utils
 * 
 * @version $Revision$
 * @author �ܺ�
 */
public final class WebUtils {

    /**
     * commons logging
     */
    private static final Log log = LogFactory.getLog(WebUtils.class);

    public static final String PAGE_SIZE_PARAM_NAME = "pageSize";

    public static final String PAGE_PARAM_NAME = "page";

    public static final String SORT_PARAM_NAME = "sort";

    /**
     * private constructor
     */
    private WebUtils() {
    }

    /**
     * ��ȡ��ǰwebӦ�õĸ�Ŀ¼��ַ
     * 
     * @param sc ServletContext
     * @return ��ǰwebӦ�õĸ�Ŀ¼��ַ
     */
    public static String getWebRootPath(ServletContext sc) {
        if (sc == null) {
            throw new IllegalStateException("ServletContext is null");
        }

        String root = sc.getRealPath("/");
        if (root == null) {
            throw new IllegalStateException(
                    "Cannot set web app root system property when WAR file is not expanded");
        }

        return root;
    }

    /**
     * ��HttpServletRequest�����л�ȡ��ǰҪ��ʾ��ҳ��
     * 
     * @param request HttpServletRequest
     * @return ��ǰҪ��ʾ��ҳ��
     */
    public static int getPageNumber(HttpServletRequest request) {
        return NumberUtils.toInt(request.getParameter(PAGE_PARAM_NAME), 1);
    }

    /**
     * ��ȡpageSize���ȼ��request parameter "pageSize"�����������
     * �ͼ���û���Cookie���ã�����Բ����ھ�ʹ��Ĭ��ֵ
     * 
     * @param request HttpServletRequest
     * @return �û���pageSize����
     */
    public static int getPageSize(HttpServletRequest request) {
        int pageSize = 20;

        if (NumberUtils.toInt(request.getParameter(PAGE_SIZE_PARAM_NAME)) != 0) {
            pageSize = NumberUtils.toInt(request
                    .getParameter(PAGE_SIZE_PARAM_NAME));

            log.debug("Got pageSize from request parameter: " + pageSize);
        } else if (CookieUtils.getCookieValue(request, "pageSize") != null) {
            try {
                pageSize = Integer.parseInt(CookieUtils.getCookieValue(request,
                    "pageSize"));
                log.debug("Got pageSize from cookie: " + pageSize);
            } catch (Exception e) {
            }
        } else {
            log.debug("Return default page size: 20");                    
        }

        return pageSize;
    }
    
    public static int getPageSize(HttpServletRequest request,int defaultSize) {
        int pageSize = defaultSize;

        if (NumberUtils.toInt(request.getParameter(PAGE_SIZE_PARAM_NAME)) != 0) {
            pageSize = NumberUtils.toInt(request
                    .getParameter(PAGE_SIZE_PARAM_NAME));

            log.debug("Got pageSize from request parameter: " + pageSize);
        } else if (CookieUtils.getCookieValue(request, "pageSize") != null) {
            try {
                pageSize = Integer.parseInt(CookieUtils.getCookieValue(request,
                    "pageSize"));
                log.debug("Got pageSize from cookie: " + pageSize);
            } catch (Exception e) {
            }
        } else {
            log.debug("Return default page size: 20");                    
        }

        return pageSize;
    }

    public static SortCriteria getSingleSortCriteria(HttpServletRequest request) {
        return HibernateDaoUtils
                .getSingleSortCriteria(request, SORT_PARAM_NAME);
    }

    public static List<SortCriteria> getSortCriteria(HttpServletRequest request) {
        return HibernateDaoUtils.getSortCriteria(request, SORT_PARAM_NAME);
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            log.debug("Using AJAX request");
            return true;
        }

        return false;
    }

    public static void sendRedirect(HttpServletRequest request,
            HttpServletResponse response, String url) throws IOException {

        if (isAjaxRequest(request)) {
            WebUtils.writeJsToResponse(response, "window.location='"
                    + request.getContextPath() + url + "';");
        } else {
            response.sendRedirect(request.getContextPath() + url);
        }
    }

    public static String getRequestParamPairs(HttpServletRequest request,
            String... paramNames) {

        StringBuffer sb = new StringBuffer();

        for (String param : paramNames) {
            if (request.getParameter(param) != null) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(param + "=" + request.getParameter(param));
            }
        }

        return sb.toString();
    }

    /**
     * Escape Html and Javascript code
     * 
     * @param str A string to escaped
     * @return Escaped String
     */
    public static String escapeHtmlAndJS(String str) {
        return StringEscapeUtils.escapeJavaScript(StringEscapeUtils
                .escapeHtml(str));
    }

    /**
     * ��javascript����ֱ��д��response����
     * 
     * @param response Ҫд���response����
     * @param content Ҫд��javascript����
     * @throws IOException ��д������г��ֵ��κ��쳣
     */
    public static void writeJsToResponse(HttpServletResponse response,
            String content) throws IOException {

        writeToResponse(response, "text/javascript", "UTF-8", content);
    }

    /**
     * ������ֱ��д��response����
     * 
     * @param response Ҫд���response����
     * @param contentType Content Type
     * @param encoding Character Encoding
     * @param content Ҫд������
     * @throws IOException ��д������г��ֵ��κ��쳣
     */
    public static void writeToResponse(HttpServletResponse response,
            String contentType, String encoding, String content)
            throws IOException {

        response.setContentType(contentType);
        response.setCharacterEncoding(encoding);
        response.getWriter().write(content);
        response.flushBuffer();
    }

   
}

