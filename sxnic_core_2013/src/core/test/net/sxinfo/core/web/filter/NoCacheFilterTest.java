package net.sxinfo.core.web.filter;

import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

/**
 * @version $Revision: 1.3 $
 * @author ²ÜºÆ
 */
public class NoCacheFilterTest extends BasicServletTestCaseAdapter {

    private NoCacheFilter filter;

    protected void setUp() throws Exception {
        super.setUp();
        filter = (NoCacheFilter) createFilter(NoCacheFilter.class);
    }

    public void testFilter() {
        doFilter();

        MockHttpServletResponse filteredResponse = (MockHttpServletResponse) getFilteredResponse();

        assertEquals("no-cache, no-store,  must-revalidate", filteredResponse
                .getHeader("Cache-Control"));

        assertEquals("no-cache", filteredResponse.getHeader("Pragma"));

        assertEquals("1970-1-1", filteredResponse.getHeader("Expires"));
    }
}
