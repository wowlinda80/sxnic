package net.sxinfo.core.web.filter;

import javax.servlet.http.HttpServletRequest;

import com.mockrunner.servlet.BasicServletTestCaseAdapter;

/**
 * @version $Revision: 1.1 $
 * @author ²ÜºÆ
 */
public class CharacterEncodingFilterTest extends BasicServletTestCaseAdapter {

    private CharacterEncodingFilter filter;

    protected void setUp() throws Exception {
        super.setUp();
        filter = (CharacterEncodingFilter) createFilter(CharacterEncodingFilter.class);
    }

    public void testFilterDefault() {
        doFilter();

        HttpServletRequest filteredRequest = (HttpServletRequest) getFilteredRequest();

        assertEquals("UTF-8", filteredRequest.getCharacterEncoding());
    }

    public void testFilter() {
        filter.setEncoding("GBK");
        filter.setForceEncoding(true);

        doFilter();

        HttpServletRequest filteredRequest = (HttpServletRequest) getFilteredRequest();

        assertEquals("GBK", filteredRequest.getCharacterEncoding());
    }

    public void testFilter2() throws Exception {
        filter.setEncoding("GBK");
        filter.setForceEncoding(false);
        getWebMockObjectFactory().getMockRequest().setCharacterEncoding(null);

        doFilter();

        HttpServletRequest filteredRequest = (HttpServletRequest) getFilteredRequest();

        assertEquals("GBK", filteredRequest.getCharacterEncoding());
    }

    public void testFilter3() throws Exception {
        filter.setEncoding("GBK");
        filter.setForceEncoding(false);
        getWebMockObjectFactory().getMockRequest()
                .setCharacterEncoding("UTF-8");

        doFilter();

        HttpServletRequest filteredRequest = (HttpServletRequest) getFilteredRequest();

        assertEquals("UTF-8", filteredRequest.getCharacterEncoding());
    }
}
