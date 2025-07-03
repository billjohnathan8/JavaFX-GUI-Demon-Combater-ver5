package guidemon.model.stats.immutable.instances;

import guidemon.model.entry.Entry;
import guidemon.model.stats.immutable.definitions.ResourceDefinition; 

/**
 * Resources are encapsulated structures of data that contain both a current and maximum value. 
 * 
 * Akin to a bottle, resources are filled and or collected by actors and stored on their entries. 
 * 
 * Some resources are gained overtime, some are lost overrtime. Some need to remain at a certain current amount to sustain the actor (e.g. HP), some need to be removed to sustain the actor (e.g. Non-Lethal Damage).  
 * 
 * Also known as: ResourceInstance 
 * 
 * This class is the entry for Resources 
 */
public class ResourceEntry extends Entry {
    private final ResourceDefinition definition; 
    private int currentValue;  //is always set to max by default - change it after instantiation via setters if necessary.
    private int maximumValue; 

    public ResourceEntry(ResourceDefinition definition, String name) {
        super(name); 
        this.definition = definition; 
        this.currentValue = definition.getDefaultMaximumValue(); 
        this.maximumValue = this.currentValue = definition.getDefaultMaximumValue(); 
    }

    //overload this constructor if you want to load from JSON:
    public ResourceEntry(ResourceDefinition definition, String name, int currentValue, int maximumValue) {
        super(name); 
        this.definition = definition; 
        this.currentValue = currentValue;
        this.maximumValue = maximumValue; 
    }

    //getters and setters: 
    public int getCurrentValue() {
        return this.currentValue; 
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue; 
    }
    
    public int getMaximumValue() {
        return this.maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue; 
    }

    //Core Methods: 

    /**
     * Used for features such as taking damage, resource cost etc.
     */
    public void modifyCurrent(int amount) {
        this.currentValue += amount; 
    }

    /**
     * Warning: This is uncached, so changing the maximum cannot be reverted unless you replace with a snapshot. 
     */
    public void modifyMaximum(int amount) {
        this.maximumValue += amount; 
    }
}