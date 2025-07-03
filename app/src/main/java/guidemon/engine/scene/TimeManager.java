package guidemon.engine.scene;

import guidemon.model.actor.Actor; 

/**
 * Class used to manage the passge of time during combat or outside of it. 
 */
public interface TimeManager {
    /**
     * Advance the clock by one tick (Action, Movement, GM Tick, etc.)
     */
    void tick(); 

    /**
     * If in "combat", advance turns/rounds ; else noop or GM-driven.
     */
    void nextTurn(); 

    /**
     * @return true if you are in a strict combat loop.
     */
    boolean isCombatActive(); 

    /**
     * e.g. getCurrentCombatant() if combat, else null.
     */
    Actor getCurrentActor(); 
}