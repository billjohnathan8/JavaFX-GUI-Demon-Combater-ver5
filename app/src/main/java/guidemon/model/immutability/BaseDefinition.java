package guidemon.model.immutability;

import java.util.Objects; 

/**
 * In order to create Definition Objects that are instantiated in each Entry - use the BaseDefintion 
 * 
 * Definition classes only have getters because all their attributes are final, so to avoid a corrupted state by changing the final values - there are no setter methods.  
 * 
 * This class is considered the Core Definition Class from which all other Definition Classes inherit from.  
 */
public abstract class BaseDefinition {
    private final String id;            //ID for the BaseDefinition is not the same as the UUID of an instance 
    private final String displayName; 

    protected BaseDefinition(String id, String displayName) { 
        this.id = Objects.requireNonNull(id); 
        this.displayName = Objects.requireNonNull(displayName);
    }

    //only getters 
    public String getId() { 
        return id; 
    }

    public String getDisplayName() {
        return displayName; 
    }
}