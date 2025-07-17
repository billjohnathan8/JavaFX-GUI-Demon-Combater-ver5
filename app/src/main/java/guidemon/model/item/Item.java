package guidemon.model.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import guidemon.engine.condition.EligibilityCondition;
import guidemon.model.ability.Ability;
import guidemon.model.effect.Effect;
import guidemon.model.entry.Entry; 
import guidemon.model.inventory.InventorySlot;
import guidemon.model.item.item_model.ItemModel;
import guidemon.model.stats.ItemDurability; 
import guidemon.model.stats.immutable.ItemRarity;
import guidemon.model.stats.immutable.ItemSizeClass;
import guidemon.model.stats.immutable.ItemWeightClass; 
import guidemon.model.stats.immutable.instances.ResourceEntry; 

//TODO: ItemEntry and ItemDefinition
public class Item extends Entry {
    private List<String> owners = new ArrayList<>();                       

    //Cached Stack for forms, states etc.
    private Stack<Item> cachedStates = new Stack<>();  
    private List<Item> forms = new Stack<>();       //TODO: instead, use an ability to transition from one statblock to another. 

    private ItemModel itemModel; 
    private List<String> exItemTags; 
    private ItemRarity rarity;
    private ItemWeightClass weightClass; 
    private ItemSizeClass sizeClass; 

    private int weight;

    private List<EligibilityCondition> prerequisites; 
    private List<String> specialStatuses; 
    private ItemDurability durability; 

    private InventorySlot currentStoredInventorySlot; 
    private List<InventorySlot> validEquipSlots;
    private List<InventorySlot> validStorageSlots;

    private List<Ability> abiliites; 

    private List<ResourceEntry> otherResources; 

    private List<Effect> effects; //current effects on the item. 

    private String description; 

    private String itemInventoryTokenPath;     //space occupied via token image
    private String itemTokenPath;         

    //estimated sell price 

    public Item(String name) {
        super(name);
    }

    //TODO: 
    //isBroken?

    //TODO: 
    //isEquipped?

    //TODO: 
    //isOn? (can the item even be wielded, pass the prerequisites)
}

//weapons have a base damage dice
//weapons also have a DAMmod