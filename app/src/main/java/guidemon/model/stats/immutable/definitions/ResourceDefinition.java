package guidemon.model.stats.immutable.definitions;

import guidemon.model.immutability.BaseDefinition; 

/**
 * Resources are encapsulated structures of data that contain both a current and maximum value. 
 * 
 * Akin to a bottle, resources are filled and or collected by actors and stored on their entries. 
 * 
 * Some resources are gained overtime, some are lost overrtime. Some need to remain at a certain current amount to sustain the actor (e.g. HP), some need to be removed to sustain the actor (e.g. Non-Lethal Damage).  
 * 
 * This class is the Definition for an Entry of Resource.
 * There is nothing to inherit from, so this class is final. 
 */
public final class ResourceDefinition extends BaseDefinition {
    private final int defaultMaximumValue;
    private final String description; 

    public ResourceDefinition (String id, String displayName, String description, int defaultMaximumValue) {
        super(id, displayName); 
        this.description = description;
        this.defaultMaximumValue = defaultMaximumValue;
    }

    //only getters:
    public String getDescription() {
        return this.description; 
    }
    
    public int getDefaultMaximumValue() {
        return this.defaultMaximumValue; 
    }
}