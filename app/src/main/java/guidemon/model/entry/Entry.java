package guidemon.model.entry;

import java.util.UUID; 

/**
 * Basis for any sort of item, thing, or object in the NHe System 
 * 
 * Deprecated Alias: StatBlock
 * Also Known as: NHEntry or NHE Entry 
 * Not to be confused with: Map.Entry which is an interface
 * 
 * It exists in-between run-time and persistence, connecting persistence and run-time game objects. 
 * 
 * It is itself a run-time game object or an instance. 
 * 
 * It Contains UUIDs, Names that act as unique IDs, Timelines, etc.
 */
public class Entry {
    private final UUID uuid; 
    private String name;         //aka: ID (which can be appended with Timeline to create unique variations), names are unique
    //TODO: UniqueName integration 

    public Entry(String name) {
        this.uuid = UUID.randomUUID(); 
        this.name = name; 
    }

    //setters and getters:
    public UUID getUUID() {
        return uuid; 
    }
    
    //UUID is final - so no setting that. 
    
    public String getName() {
        return name; 
    }

    public void setName(String name) {
        this.name = name; 
    }
}

//TODO: 
//For Resources etc. maybe timeline is not a good identifier 

//EntryType and Timeline should be injected into this object

//entryType enum is used and displayed for statblocks
//the entry types are instead subclassed from here 

//timeline is added to name <- but timeline should not be stored as its own enum at run-time <- lore entry 