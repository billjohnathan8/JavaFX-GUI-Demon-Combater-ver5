package guidemon.engine.event.type;

import guidemon.engine.event.Event; 

/**
 * Extension of the Event Class that carries extra state. 
 *
 * It contains a flag that listeners (like triggers) can flip to tell the EventBus (Publisher) - do not go ahead with applying this effect, etc.
 * 
 * No Business Logic - just data and flags. 
 */
public interface CancellableEvent extends Event {
    /**
     * Mark this event as "vetoed" or "cancelled" so downstream code won't act on it. 
     */
    void cancel();
    
    /**
     * Check whether any listener has called cancel() yet. 
     */
    boolean isCancelled();
}
