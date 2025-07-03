package guidemon.engine.scene;

import guidemon.model.actor.Actor; 

/**
 * Generic TimeManager that is manually ticked by GM or by events in the scene and real-time. 
 */
public class GMTimeManager implements TimeManager {
    private int totalTicks = 0; //TODO: realtime (?)

    //TODO: 
    @Override
    public void tick() {
        totalTicks++; 
    }

    @Override
    public void nextTurn() {
        //No Strict Turns
    }

    /*
     * Occurs out of Combat 
     */
    @Override 
    public boolean isCombatActive() {
        return false; 
    }
    
    /*
     * Occurs out of Combat 
     */
    @Override
    public Actor getCurrentActor() {
        return null;
    }   
}