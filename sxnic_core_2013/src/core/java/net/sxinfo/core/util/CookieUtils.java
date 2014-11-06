package net.sxinfo.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Cookie Utils¡£
 * <br/>
 * Original from Atlassian Seraph, Includes code from Jive 1.2.4 (released under the Apache license) 
 *
 * @version $Revision: 1.1 $, $Date: 2006/07/17 09:54:31 $
 * @author ²ÜºÆ
 */
public final class CookieUtils {

    /**
     * commons logging
     */
    private static final Log log = LogFactory.getLog(CookieUtils.class);

    private CookieUtils() {
    }

    //"Tweakable" parameters for the cookie encoding. NOTE: changing these
    //and recompiling this class will essentially invalidate old cookies.
    private final static int ENCODE_XORMASK = 0x7B;

    private final static char ENCODE_DELIMITER = '\002';

    private final static char ENCODE_CHAR_OFFSET1 = 'C';

    private final static char ENCODE_CHAR_OFFSET2 = 'i';

    /**
     * Invalidate the specified cookie and delete it from the response object.
     *
     * @param response The HttpServletResponse object, known as "response" in a JSP page.
     * @param cookieName The name of the cookie you want to delete.
     */
    public static void invalidateCookie(HttpServletResponse response,
            String cookieName) {
        log.debug("CookieUtils.invalidateCookie " + cookieName);
        setCookie(response, cookieName, null, 0, "/");
    }

    /**
     * Returns the specified Cookie object, or null if the cookie does not exist.
     *
     * @param request The HttpServletRequest object, known as "request" in a
     *      JSP page.
     * @param name the name of the cookie.
     * @return the Cookie object if it exists, otherwise null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
        //Otherwise, we have to do a linear scan for the cookie.
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(name)) {
                return cookies[i];
            }
        }

        return null;
    }

    /**
     * Sets a cookie
     *
     * This will also put the cookie in a list of cookies to send with this request's response
     * (so that in case of a redirect occurring down the chain, the first filter
     * will always try to set this cookie again)
     * 
     * @param response HttpServletResponse
     * @param name Name of cookie
     * @param value Value of cookie
     * @param maxAge Max Age of cookie
     * @param path Path of cookie
     * @return Cookie object
     */
    public static Cookie setCookie(HttpServletResponse response, String name,
            String value, int maxAge, String path) {

        log.debug("CookieUtils.setCookie " + name + ":" + value);
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        response.addCookie(cookie);

        return cookie;
    }

    /**
     * Returns the value of the specified cookie as a String. If the cookie
     * does not exist, the method returns null.
     *
     * @param request the HttpServletRequest object, known as "request" in a
     *      JSP page.
     * @param name the name of the cookie
     * @return the value of the cookie, or null if the cookie does not exist.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * Builds a cookie string containing a username and password.<p>
     *
     * Note: with open source this is not really secure, but it prevents users
     * from snooping the cookie file of others and by changing the XOR mask and
     * character offsets, you can easily tweak results.
     *
     * @param username The username.
     * @param password The password.
     * @return String encoding the input parameters, an empty string if one of
     *      the arguments equals <code>null</code>.
     */
    public static String encodePasswordCookie(String username, String password) {
        return encodePasswordCookie(username, password, new String(new char[] {
                ENCODE_DELIMITER, ENCODE_CHAR_OFFSET1, ENCODE_CHAR_OFFSET2 }));
    }

    /**
     * Builds a cookie string containing a username and password, using offsets to customise the encoding.<p>
     *
     * Note: with open source this is not really secure, but it prevents users
     * from snooping the cookie file of others and by changing the XOR mask and
     * character offsets, you can easily tweak results.
     *
     * @param username The username.
     * @param password The password.
     * @param encoding A String used to customise cookie encoding (only the first 3 characters are used)
     * @return String encoding the input parameters, an empty string if one of
     *      the arguments equals <code>null</code>.
     */
    public static String encodePasswordCookie(String username, String password,
            String encoding) {
        StringBuffer buf = new StringBuffer();
        if (username != null && password != null) {
            char delimiter = (encoding != null && encoding.length() > 0) ? encoding
                    .charAt(0)
                    : ENCODE_DELIMITER;
            char offset1 = (encoding != null && encoding.length() > 1) ? encoding
                    .charAt(1)
                    : ENCODE_CHAR_OFFSET1;
            char offset2 = (encoding != null && encoding.length() > 2) ? encoding
                    .charAt(2)
                    : ENCODE_CHAR_OFFSET2;

            byte[] bytes = (username + delimiter + password).getBytes();
            int b;

            for (int n = 0; n < bytes.length; n++) {
                b = bytes[n] ^ (ENCODE_XORMASK + n);
                buf.append((char) (offset1 + (b & 0x0F)));
                buf.append((char) (offset2 + ((b >> 4) & 0x0F)));
            }
        }
        return buf.toString();
    }

    /**
     * Decodes a cookie string containing a username and password.
     * @param cookieVal The cookie value.
     * @return String[] containing the username at index 0 and the password at
     *      index 1, or <code>{ null, null }</code> if cookieVal equals
     *      <code>null</code> or the empty string.
     */
    public static String[] decodePasswordCookie(String cookieVal) {
        return decodePasswordCookie(cookieVal, new String(new char[] {
                ENCODE_DELIMITER, ENCODE_CHAR_OFFSET1, ENCODE_CHAR_OFFSET2 }));
    }

    /**
     * Decodes a cookie string containing a username and password.
     * @param cookieVal The cookie value.
     * @param encoding  A String used to customise cookie encoding (only the first 3 characters are used) - should be the same string you used to encode the cookie!
     * @return String[] containing the username at index 0 and the password at
     *      index 1, or <code>{ null, null }</code> if cookieVal equals
     *      <code>null</code> or the empty string.
     */
    public static String[] decodePasswordCookie(String cookieVal,
            String encoding) {
        // check that the cookie value isn't null or zero-length
        if (cookieVal == null || cookieVal.length() <= 0) {
            return null;
        }

        char delimiter = (encoding != null && encoding.length() > 0) ? encoding
                .charAt(0) : ENCODE_DELIMITER;
        char offset1 = (encoding != null && encoding.length() > 1) ? encoding
                .charAt(1) : ENCODE_CHAR_OFFSET1;
        char offset2 = (encoding != null && encoding.length() > 2) ? encoding
                .charAt(2) : ENCODE_CHAR_OFFSET2;

        // decode the cookie value
        char[] chars = cookieVal.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        int b;
        for (int n = 0, m = 0; n < bytes.length; n++) {
            b = chars[m++] - offset1;
            b |= (chars[m++] - offset2) << 4;
            bytes[n] = (byte) (b ^ (ENCODE_XORMASK + n));
        }
        cookieVal = new String(bytes);
        int pos = cookieVal.indexOf(delimiter);
        String username = (pos < 0) ? "" : cookieVal.substring(0, pos);
        String password = (pos < 0) ? "" : cookieVal.substring(pos + 1);

        return new String[] { username, password };
    }
}
