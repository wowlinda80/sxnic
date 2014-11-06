package net.sxinfo.core.dao.hibernate3;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @version $Revision: 1.4 $
 * @author ²ÜºÆ
 */
public class HibernateDaoUtilsTest extends TestCase {

    public void testCreateHibernateOrder() {
        HibernateOrder o = HibernateDaoUtils.createHibernateOrder(
            "test1.test2.name", true);

        assertEquals("test1.test2", o.getPath());
        assertNotNull(o);
    }

    public void testCreateHibernateOrderNull() {
        assertNull(HibernateDaoUtils.createHibernateOrder("", false));
        assertNull(HibernateDaoUtils.createHibernateOrder(null, false));
    }

    public void testGetSortConditions() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addParameter("sort", "username/asc");
        req.addParameter("sort", "fullname/desc");

        assertEquals(2, HibernateDaoUtils.getSortCriteria(req, "sort").size());

        SortCriteria username = new SortCriteria("username", false);
        SortCriteria fullname = new SortCriteria("fullname", false);
        SortCriteria notExists = new SortCriteria("notExists", false);
        assertEquals(true, HibernateDaoUtils.getSortCriteria(req, "sort").get(
            HibernateDaoUtils.getSortCriteria(req, "sort").indexOf(username))
                .isAscending());
        assertEquals(false, HibernateDaoUtils.getSortCriteria(req, "sort").get(
            HibernateDaoUtils.getSortCriteria(req, "sort").indexOf(fullname))
                .isAscending());

        assertEquals(-1, HibernateDaoUtils.getSortCriteria(req, "sort")
                .indexOf(notExists));

        assertEquals("username", HibernateDaoUtils.getSortCriteria(req, "sort")
                .get(0).getPropertyName());
        assertEquals("fullname", HibernateDaoUtils.getSortCriteria(req, "sort")
                .get(1).getPropertyName());
    }

    public void testGetSingleSortCriteria() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addParameter("sort", "username/asc");
        req.addParameter("sort", "fullname/desc");

        assertEquals(2, HibernateDaoUtils.getSortCriteria(req, "sort").size());

        assertEquals("username", HibernateDaoUtils.getSingleSortCriteria(req,
            "sort").getPropertyName());
        assertEquals(true, HibernateDaoUtils.getSingleSortCriteria(req, "sort")
                .isAscending());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(HibernateDaoUtilsTest.class);
    }
}
