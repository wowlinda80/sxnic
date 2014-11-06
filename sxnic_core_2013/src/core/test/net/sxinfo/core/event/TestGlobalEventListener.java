package net.sxinfo.core.event;

/**
 * TODO: DOCUMENT ME!
 *
 * @version $Revision: 1.1 $
 * @author ²ÜºÆ
 */
public class TestGlobalEventListener implements EventListener {

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
        return null;
    }

    /**
     * @return Returns the handled.
     */
    public boolean isHandled() {
        return handled;
    }
}
