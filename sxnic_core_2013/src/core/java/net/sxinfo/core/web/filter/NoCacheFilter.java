package net.sxinfo.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 使浏览器不缓存指定类型的页面
 *
 * @version $Revision: 1.2 $, $Date: 2005/08/16 03:52:40 $
 * @author 曹浩
 */
public class NoCacheFilter extends OncePerRequestFilter {

    /**
     * Commons Logging
     */
    private static final Log LOG = LogFactory.getLog(NoCacheFilter.class);

    /**
     * @see org.springframework.web.filter.OncePerRequestFilter
     *      #doFilterInternal(javax.servlet.http.HttpServletRequest,
     *                        javax.servlet.http.HttpServletResponse,
     *                        javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        LOG.debug("Setting no cache: " + request.getServletPath());

        // 设置不缓存
        response.setHeader("Cache-Control",
            "no-cache, no-store,  must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0L);

        filterChain.doFilter(request, response);
    }
}
