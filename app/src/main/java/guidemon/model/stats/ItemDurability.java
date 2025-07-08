package guidemon.model.stats;

import guidemon.model.stats.immutable.ItemDurabilityType; 

public class ItemDurability {
    private ItemDurabilityType type; 

    private int currentValue; 
    private int maximumValue; 

    public ItemDurability(int maximumValue, ItemDurabilityType type) {
        this(maximumValue, maximumValue, type);
    }

    public ItemDurability(int currentValue, int maximumValue, ItemDurabilityType type) {
        this.currentValue = currentValue;
        this.maximumValue = maximumValue; 
        this.type = type; 
    }

    //getters and setters: 
    public ItemDurabilityType getType() {
        return this.type; 
    }

    public void setType(ItemDurabilityType type) {
        this.type = type; 
    }

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
    public void modifyCurrentValue(int amount) {
        this.currentValue += amount; 
    }
}
