package guidemon.model.vision.senses;

import guidemon.model.entry.Entry;

//TODO: SenseEntry vs SenseDefinition

public class Sense extends Entry {  
    private SenseType type; 
    private int maximumRange;
    //TODO: Range Shape

    private int rank; 

    public Sense(String name) {
        super(name);
    }
}