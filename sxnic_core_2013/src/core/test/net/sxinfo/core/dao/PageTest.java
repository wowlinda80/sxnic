package net.sxinfo.core.dao;

import java.util.ArrayList;

import junit.framework.TestCase;

public class PageTest extends TestCase {

    public Page createTestPage() {
        return new Page(new ArrayList(), 2, 10, 105);
    }

    public void testGetNextPageNumber() {
        Page page = createTestPage();

        assertEquals(3, page.getNextPageNumber());
    }

    public void testGetNextPageNumber2() {
        Page page = new Page(new ArrayList(), 11, 10, 105);

        assertEquals(-1, page.getNextPageNumber());
    }

    public void testGetPreviousPageNumber() {
        Page page = createTestPage();

        assertEquals(1, page.getPreviousPageNumber());
    }

    public void testGetPreviousPageNumber2() {
        Page page = new Page(new ArrayList(), 1, 10, 105);

        assertEquals(-1, page.getPreviousPageNumber());
    }

    public void testIsFirstPage() {
        Page page = createTestPage();

        assertFalse(page.isFirstPage());
    }

    public void testIsFirstPage2() {
        Page page = new Page(new ArrayList(), 1, 10, 105);

        assertTrue(page.isFirstPage());
    }

    public void testIsLastPage() {
        Page page = createTestPage();

        assertFalse(page.isLastPage());
    }

    public void testIsLastPage2() {
        Page page = new Page(new ArrayList(), 11, 10, 105);

        assertTrue(page.isLastPage());
    }

    public void testHasPreviousPage() {
        Page page = createTestPage();

        assertTrue(page.hasPreviousPage());
    }

    public void testHasPreviousPage2() {
        Page page = new Page(new ArrayList(), 1, 10, 105);

        assertFalse(page.hasPreviousPage());
    }

    public void testHasNextPage() {
        Page page = createTestPage();

        assertTrue(page.hasNextPage());
    }

    public void testHasNextPage2() {
        Page page = new Page(new ArrayList(), 11, 10, 105);

        assertFalse(page.hasNextPage());
    }

    public void testGetResults() {
        Page page = createTestPage();

        assertNotNull(page.getResults());
    }

    public void testGetPage() {
        Page page = createTestPage();

        assertEquals(2, page.getPage());
    }

    public void testGetPageSize() {
        Page page = createTestPage();

        assertEquals(10, page.getPageSize());
    }

    public void testGetTotalPages() {
        Page page = createTestPage();

        assertEquals(11, page.getTotalPages());
    }

    public void testGetTotalPages2() {
        Page page = new Page(new ArrayList(), 2, 10, 100);

        assertEquals(10, page.getTotalPages());
    }

    public void testGetTotalPages3() {
        Page page = new Page(new ArrayList(), 2, 10, 101);

        assertEquals(11, page.getTotalPages());
    }

    public void testGetTotalPages4() {
        Page page = new Page(new ArrayList(), 1, 10, 3);

        assertEquals(1, page.getTotalPages());
    }

    public void testGetTotalResults() {
        Page page = createTestPage();

        assertEquals(105, page.getTotalResults());
    }

    public void testEquals() {
        Page page = createTestPage();
        Page page2 = createTestPage();

        assertEquals(page2, page);
    }

    public void testEquals2() {
        Page page = createTestPage();
        Object o = new Object();

        assertFalse(page.equals(o));
    }

    public void testHashCode() {
        Page page = createTestPage();
        Page page2 = createTestPage();

        assertEquals(page2.hashCode(), page.hashCode());
    }

    public void testToStirng() {
        Page page = createTestPage();

        assertNotNull(page.toString());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PageTest.class);
    }
}
