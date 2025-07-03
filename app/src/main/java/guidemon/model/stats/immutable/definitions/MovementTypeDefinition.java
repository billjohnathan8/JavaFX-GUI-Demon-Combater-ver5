package guidemon.model.stats.immutable.definitions;

import guidemon.model.immutability.BaseDefinition; 

/**
 * MovementTypes determine what sort of terrain and vertical movement behaviour an actor can use for its token. 
 * 
 * Enum-like definition. Has an instance, because different actors can have the same terrain but differnet maximumspeed. 
 * This class is the Definition for an Entry of MovementType.
 * There is nothing to inherit from, so this class is final.  
 */
public final class MovementTypeDefinition extends BaseDefinition {
    private final int defaultMaximumSpeed;

    //TODO: 
    //valid terrain to cross
    //vertical movement options 

    public MovementTypeDefinition(String id, String displayName, int defaultMaximumSpeed) {
        super(id, displayName); 
        this.defaultMaximumSpeed = defaultMaximumSpeed; 
    }

    //only getters: 
    public int getDefaultMaximumSpeed() {
        return this.defaultMaximumSpeed; 
    }
}