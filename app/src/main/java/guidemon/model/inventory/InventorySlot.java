package guidemon.model.inventory;

import guidemon.model.entry.Entry;
import guidemon.model.item.Item; 

//TODO: InventorySlotEntry and InventorySlotDefinition

public class InventorySlot extends Entry {
    private Item currentItemOccupying; 

    private String slotImagePath;
    private int maximumSpaceCapacity;
    private int currentSpaceOccupied;    

    //Constructors: 

    /**
     * Standard Constructor, for empty items. 
     */
    public InventorySlot(String name, String slotImagePath, int maximumSpaceCapacity) {
        super(name);
        this.slotImagePath = slotImagePath;
        this.maximumSpaceCapacity = maximumSpaceCapacity; 
        this.currentItemOccupying = null; //no item
        this.currentSpaceOccupied = 0;    //no item
    }

    /**
     * Constructor if the inventory slot already has an item. 
     */
    public InventorySlot(String name, String slotImagePath, int maximumSpaceCapacity, Item currentItemOccupying) {
        super(name);
        this.slotImagePath = slotImagePath;
        this.maximumSpaceCapacity = maximumSpaceCapacity; 
        this.currentItemOccupying = currentItemOccupying; 
        // this.currentSpaceOccupied = currentItemOccupying //calculate space remaining 
    }

    //TODO: 
    // public boolean isFull() {
        
    // }
}
