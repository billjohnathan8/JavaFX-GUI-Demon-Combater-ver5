package guidemon.model.entry;

/**
 * Used mainly to store UniqueNames and StatBlock Lore Entries that are viewed. 
 */
public class LoreEntry extends Entry {
    //Unique Name becomes the name of the entry
    public LoreEntry(UniqueName name) {
        super(name.getUniqueName());
    }
}