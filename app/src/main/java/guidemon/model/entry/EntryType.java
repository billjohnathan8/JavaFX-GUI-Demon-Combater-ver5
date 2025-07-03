package guidemon.model.entry;

/** 
 * Outlines what kind of Entry it is. 
 */
public enum EntryType {
    BASIC,             //aka BaseEntry - Can be extended into variants, instances, etc. It is very basic. e.g. Iron Swords, Iron Armour, BESTIARY_ENTRIES etc.
    SAVED_STATE,       //instances, unique states or snapshots or one-of-a-kind very different entries
    FORM,              //variants of the same original thing - usually temporary, but sometimes can be permanent  
}

//TODO: WIP
//entryType enum is used and displayed for statblocks
//the entry types are instead subclassed from here 