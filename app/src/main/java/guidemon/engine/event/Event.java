package guidemon.engine.event;

/**
 *  Events are just data carriers that you publish on the EventBus so that triggers, UI, logging, etc. can all react without tight coupling.
 * 
 * They are used in Pub/Sub (Publisher / Subscriber) Event-Driven Architecture that this application uses for its core game logic in the game engine. 
 * 
 * This class is a market interface for anything that can be published on the EventBus. 
 * 
 * No methods - just a common type so we can register and dispatch by class. 
 */
public interface Event {
    
}