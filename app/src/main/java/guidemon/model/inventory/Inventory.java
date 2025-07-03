package guidemon.model.inventory;

import java.util.List; 

import guidemon.model.entry.Entry;

//TODO: InventoryEntry and InventoryEntryDefinition

public class Inventory extends Entry {
    private List<InventorySlot> slots; 

    public Inventory(String name) {
        super(name);
    }
}