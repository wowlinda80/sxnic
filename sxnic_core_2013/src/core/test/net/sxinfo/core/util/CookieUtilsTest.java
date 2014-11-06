package net.sxinfo.core.util;

import javax.servlet.http.Cookie;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @version $Revision: 1.1 $, $Date: 2006/07/17 09:54:31 $
 * @author 曹浩
 */
public class CookieUtilsTest extends TestCase {

    private static final Log log = LogFactory.getLog(CookieUtilsTest.class);

    public static void main(String[] args) {
        junit.textui.TestRunner.run(CookieUtilsTest.class);
    }

    public void testSetGetCookie() {
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockHttpServletRequest req = new MockHttpServletRequest();

        CookieUtils.setCookie(res, "name", "value", 1024, "/");

        // 设置刚才set的cookies到request
        req.setCookies(res.getCookies());
        assertNotNull(CookieUtils.getCookie(req, "name"));
    }

    public void testInvalidateCookie() {
        MockHttpServletResponse res = new MockHttpServletResponse();
        CookieUtils.setCookie(res, "name", "value", 1024, "/");

        CookieUtils.invalidateCookie(res, "name");
    }

    public void testEncodeDecode() {
        String encoded = CookieUtils.encodePasswordCookie("username",
            "password");

        log.debug("encoded: " + encoded);

        String[] decoded = CookieUtils.decodePasswordCookie(encoded);

        assertEquals("username", decoded[0]);
        assertEquals("password", decoded[1]);
    }
}
