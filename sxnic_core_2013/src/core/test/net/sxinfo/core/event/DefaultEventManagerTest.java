package net.sxinfo.core.event;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class DefaultEventManagerTest extends TestCase {

    public void testThis() {
        EventManager m = new DefaultEventManager();

        TestEventListener l = new TestEventListener();
        TestGlobalEventListener l2 = new TestGlobalEventListener();
        Test2EventListener l3 = new Test2EventListener();

        m.registerListener(l);
        m.registerListener(l2);
        m.registerListener(l3);

        m.publishEvent(new TestEvent("testEvent"));

        assertTrue(l.isHandled());
        assertTrue(l2.isHandled());
        assertFalse(l3.isHandled());
    }

    public void testThis2() {
        EventManager m = new DefaultEventManager();

        m.publishEvent(new TestEvent("testEvent"));
    }

    public void testUnregisterListener() {
        EventManager m = new DefaultEventManager();

        TestEventListener l = new TestEventListener();
        TestGlobalEventListener l2 = new TestGlobalEventListener();
        Test2EventListener l3 = new Test2EventListener();

        m.registerListener(l);
        m.registerListener(l2);
        m.registerListener(l3);

        m.unregisterListener(l);
        m.unregisterListener(l2);

        m.publishEvent(new TestEvent("testEvent"));

        assertFalse(l.isHandled());
        assertFalse(l2.isHandled());
        assertFalse(l3.isHandled());
    }

    public void testSetListeners() {
        EventManager m = new DefaultEventManager();

        TestEventListener l = new TestEventListener();
        TestGlobalEventListener l2 = new TestGlobalEventListener();
        Test2EventListener l3 = new Test2EventListener();

        List list = new ArrayList();
        list.add(l);
        list.add(l2);
        list.add(l3);

        m.setListeners(list);

        m.publishEvent(new TestEvent("testEvent"));

        assertTrue(l.isHandled());
        assertTrue(l2.isHandled());
        assertFalse(l3.isHandled());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DefaultEventManagerTest.class);
    }
}
