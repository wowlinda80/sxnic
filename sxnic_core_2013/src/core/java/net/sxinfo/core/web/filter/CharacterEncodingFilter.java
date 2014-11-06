package net.sxinfo.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Character Encoding Filter，默认使用UTF-8
 * 
 * @version $Revision: 1.2 $, $Date: 2005/08/09 02:59:34 $
 * @author 曹浩
 */
public class CharacterEncodingFilter extends OncePerRequestFilter {

    /**
     * 默认Character Encoding
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Character encoding
     */
    private String encoding = DEFAULT_ENCODING;

    /**
     * Force Encoding
     */
    private boolean forceEncoding;

    /**
     * 设置request的Character Encoding. 根据"forceEncoding"
     * 标记来决定是否设置Character Encoding.
     * 
     * @param encoding 要设置的Character Encoding
     * 
     * @see #setForceEncoding
     * @see javax.servlet.ServletRequest#setCharacterEncoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 是否覆盖当前的Character Encoding。默认为false。
     * 或者在ServletRequest.getCharacterEncoding返回no-null时
     * 覆盖Character Encoding设置。
     * 
     * @param forceEncoding 是否强制设置Encoding
     * 
     * @see #setEncoding
     * @see javax.servlet.ServletRequest#getCharacterEncoding
     */
    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

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

        if (this.forceEncoding || request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(this.encoding);
        }

        filterChain.doFilter(request, response);
    }
}
