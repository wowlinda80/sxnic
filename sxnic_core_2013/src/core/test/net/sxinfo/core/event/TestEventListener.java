package net.sxinfo.core.event;

/**
 * TODO: DOCUMENT ME!
 *
 * @version $Revision: 1.1 $
 * @author ²ÜºÆ
 */
public class TestEventListener implements EventListener {

    /**
     * Is handled?
     */
    private boolean handled;

    /**
     * @see net.sxinfo.core.event.EventListener#handleEvent(net.sxinfo.core.event.Event)
     */
    public void handleEvent(Event event) {
        handled = true;
    }

    /**
     * @see net.sxinfo.core.event.EventListener#getHandledEventClasses()
     */
    public Class[] getHandledEventClasses() {
        return new Class[] { TestEvent.class };
    }

    /**
     * @return Returns the handled.
     */
    public boolean isHandled() {
        return handled;
    }
}
