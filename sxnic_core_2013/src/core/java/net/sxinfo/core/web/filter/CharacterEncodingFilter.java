package net.sxinfo.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Character Encoding Filter��Ĭ��ʹ��UTF-8
 * 
 * @version $Revision: 1.2 $, $Date: 2005/08/09 02:59:34 $
 * @author �ܺ�
 */
public class CharacterEncodingFilter extends OncePerRequestFilter {

    /**
     * Ĭ��Character Encoding
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
     * ����request��Character Encoding. ����"forceEncoding"
     * ����������Ƿ�����Character Encoding.
     * 
     * @param encoding Ҫ���õ�Character Encoding
     * 
     * @see #setForceEncoding
     * @see javax.servlet.ServletRequest#setCharacterEncoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * �Ƿ񸲸ǵ�ǰ��Character Encoding��Ĭ��Ϊfalse��
     * ������ServletRequest.getCharacterEncoding����no-nullʱ
     * ����Character Encoding���á�
     * 
     * @param forceEncoding �Ƿ�ǿ������Encoding
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
