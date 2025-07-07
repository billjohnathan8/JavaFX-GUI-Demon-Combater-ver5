package guidemon.model.actor.interfaces;

import guidemon.model.stats.immutable.instances.MovementTypeEntry;

//with regards to traversing different terrains and using different movement speeds. 
public interface MovementSwitchable {
    /**
     * A method used to switch from one movement type to another.
     * 
     * Involves token.
     */
    void switchMovementType(MovementTypeEntry movementType);   
}