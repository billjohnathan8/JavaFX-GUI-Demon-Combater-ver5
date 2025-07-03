package guidemon.model.stats;

import guidemon.model.stats.immutable.instances.ResourceEntry;

/**
 * Data Object used to bundle a Resource and amount of it. 
 * It is used to outline how much resources a certain activation, casting, etc. will cost in order to successfully cast it. 
 * 
 * Notes: 
 * 
 * This is distinct and different from ResourceEffect.class because of a separation of concerns. 
 * 
 * In order to have multiple resource costs (e.g. HP + AP + MP, create a list - this data object can be instantiated inside of a list)
 */
public class ResourceCost {
    private ResourceEntry resource; //used as the key for map lookups
    private int costAmount;
    
    public ResourceCost(ResourceEntry resource, int costAmount) {
        this.resource = resource; 
        this.costAmount = costAmount; 
    }

    //getters and setters: 
    public ResourceEntry getResource() {
        return this.resource; 
    }

    /**
     * It is possible to change what type of resource is costed. 
     * 
     * E.g. It is possible to convert a 20MP Cost into a 10HP Cost. 
     */
    public void setResource(ResourceEntry resource) {
        this.resource = resource; 
    }

    public int getCostAmount() {
        return this.costAmount;
    }

    /**
     * It is possible to set the costAmount to a certain value. 
     * 
     * E.g. all costs above 100 are set to 100. Or any cost amount modifications results in the costAmount being set to 1 temporarily etc.
     */
    public void setCostAmount(int costAmount) {
        this.costAmount = costAmount; 
    }   

    //Other Cost Features:

    /**
     * Increase or decrease costAmounts. 
     */
    public void modifyCost(int amount) {
        this.costAmount += amount; 
    }

    /**
     * Increase or decrease costAmounts by a factor or multiple of itself 
     */
    public void multiplyCost(double multiple) {
        this.costAmount *= multiple; 
    }
}

