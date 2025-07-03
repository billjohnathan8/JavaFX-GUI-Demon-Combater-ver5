package guidemon.model.stats.immutable.instances;

import guidemon.model.entry.Entry;
import guidemon.model.stats.immutable.definitions.MovementTypeDefinition;  

public class MovementTypeEntry extends Entry {
    private final MovementTypeDefinition definition; 
    private int maximumSpeed; 

    //TODO: 
    //valid terrain to cross
    //vertical movement options 

    public MovementTypeEntry(MovementTypeDefinition definition, String name) {
        super(name);
        this.definition = definition;
        this.maximumSpeed = definition.getDefaultMaximumSpeed(); 
    }

    //getters and setters: 
    public int getMaximumSpeed() {
        return this.maximumSpeed; 
    }

    public void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = maximumSpeed; 
    }


    //Core Methods: 
    
    /**
     * Add or subtract the amount of maximum speed that a MovementType has. Because of abilities like Waterwalk - adds +10m to Waterspeed. 
     */
    public void modifyMaximumSpeed(int amount) {
        this.maximumSpeed += amount; 
    }
}
