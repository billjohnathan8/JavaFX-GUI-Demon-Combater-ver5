package guidemon.model.item.item_model;

import java.util.List; 

import guidemon.model.inventory.InventorySlot; 

//Item that stores modular components and is customisable. 
public interface Modularable {
    List<InventorySlot> getModifierSlots();
}
