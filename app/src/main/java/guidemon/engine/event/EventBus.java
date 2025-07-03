package guidemon.engine.event;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * A lightweight publish/subscribe EventBus.
 * 
 * Listeners register for a particular event class.
 * When you publish an event, all listeners whose registered class is assignable from the event's class will be invoked.
 * 
 * How it Works Step-by-Step: 
 * 1. Data Structure
    EventBus.java
    private final Map<Class<?>, List<Consumer<?>>> listeners = new ConcurrentHashMap<>();
 *
 * It uses a thread-safe ConcurrentHashMap so multiple threads can register/publish concurrently. 
 * Each key is an event class (e.g. DamageEvent.class) mapping to a CopyOnWriteArrayList of handlers. 
 * 
 * 2. Registered Listeners
    EventBus.java
    public <E> void register(Class<E> eventType, Consumer<E> listener) { … }
 * 
 * You call eventBus.register(DamageEvent.class, evt -> { … }).
 * computeIfAbsent creates the list if needed, then adds your listener.
 * We use CopyOnWriteArrayList so that you can safely add/remove listeners while another thread is iterating over them during a publish. 
 * 
 * 3. Unregistering
    EventBus.java
    public <E> void unregister(Class<E> eventType, Consumer<E> listener) { … }
 * 
 * Removes a listener when, say, a combatant dies or you unload a module, keeping the map clean.
 * 
 * 4. Publishing Events
    EventBus.java
    public void publish(Object event) { … }
 * 
 * Iterates all registered event types (for (Entry<Class<?>>, List<…>> entry : listeners.entrySet())).
 * Checks registeredType.isAssignableFrom(publishedType) so that a listener on a supertype (e.g. Event.class) will also fire. 
 * Casts each raw Consumer<?> to Consumer<Object> (safe because of the isAssignableFrom check) and calls accept (event). 
 * 
 * 5. Usage in the Engine
 * When you boostrap: 
    EventBus bus = new EventBus();
 *
 * A trigger registers itself: 
    bus.register(DamageDealtEvent.class, evt -> {
        if ( condition on evt ) 
            effect.apply(...);
    });
 *  
 * When an effect wants to announce something happened: 
    bus.publish(new DamageDealtEvent(actor, target, amount, type));
 * 
 * Because every piece of your combat system (turn starts, resource checks, reactions, status changes) is modelled as an event, this single EventBus binds them all together: 
 * 1. Decouping: Publishers of events don't need to know who's listening.
 * 2. Ordering: You can extend it to buffer or reorder events if needed. 
 * 3. Modularity: New systems (logging, UI Updates, Analytics) can subscribe to any event without touching the core engine. 
 *  
 */
public class EventBus {
    //Map from event type to a thread-safe list of handlers
    private final Map<Class<?>, List<Consumer<?>>> listeners = new ConcurrentHashMap<>(); 

    /** 
     * Register a listener for events of type E (or any subtype).
     * @param eventType the class of event to listen for
     * @param listener  a Consumer that handles the event
    */
    public <E> void register(Class<E> eventType, Consumer<E> listener) {
        listeners
            .computeIfAbsent(eventType, key -> new CopyOnWriteArrayList<>())
            .add(listener);
    }

    /**
     * Unregister a listener if you no longer want to receive events.
     */
    public <E> void unregister(Class<E> eventType, Consumer<E> listener) {
        List<Consumer<?>> regs = listeners.get(eventType);
        if (regs != null) {
            regs.remove(listener);

            if (regs.isEmpty()) {
                listeners.remove(eventType);
            }
        }
    }

    /**
     * Publish an event to all matching listeners.
     * Any listener registered on a superclass or interface of the event
     */
    @SuppressWarnings("unchecked")
    public void publish(Object event) {
        Class<?> publishedType = event.getClass();

        // For each registered entry, if the event is an instance of its key,
        // invoke all its handlers.
        for (Map.Entry<Class<?>, List<Consumer<?>>> entry : listeners.entrySet()) {
            Class<?> registeredType = entry.getKey();
            if (registeredType.isAssignableFrom(publishedType)) {
                for (Consumer<?> rawListener : entry.getValue()) {
                    // We know rawListener expects something assignable from publishedType
                    Consumer<Object> listener = (Consumer<Object>) rawListener;
                    listener.accept(event);
                }
            }
        }
    }
}