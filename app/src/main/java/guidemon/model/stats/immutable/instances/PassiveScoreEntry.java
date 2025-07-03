package guidemon.model.stats.immutable.instances;

import guidemon.model.entry.Entry; 
import guidemon.model.stats.immutable.definitions.PassiveScoreDefinition; 

/**
 * Passive Scores (otherwise known as Scores or RPG Statistics in other systems) are integer number values that represent a certain state or characteristic of an actor or entry. 
 * 
 * Similar to how height and weight or the three-sizes can represent how large or strong a person is or how their body looks like in-real life, respectively - a Score represents certain attributes or states about an entry or actor.
 * 
 * For example, if a character's Strength Score is 20, they are very physically capable. 
 * 
 * Another example, if a character's Deception Score is 25, they are very good at the Deception Skill and hence are very good at deceiving people.  
 * 
 * Generally speaking, the higher the score is - the more bonuses an actor can gain to certain skills or active abilities related to that score. 
 * 
 * Also known as: PassiveScoreInstance 
 * 
 * This class is the entry for Passive Scores  
 */
public class PassiveScoreEntry extends Entry {
    private final PassiveScoreDefinition definition;
    private int scoreValue; //aka CurrentValue - it is always set by definition by default - change it after instantiation via setters if necessary.
    
    public PassiveScoreEntry(PassiveScoreDefinition definition, String name) {
        super(name); 
        this.definition = definition;
        this.scoreValue = definition.getDefaultScoreValue(); 
    }

    //overload this constructor if you want to load from JSON:
    public PassiveScoreEntry(PassiveScoreDefinition definition, String name, int scoreValue) {
        super(name);
        this.definition = definition;
        this.scoreValue = scoreValue; 
    }

    //getters and setters: 
    public int getScoreValue() {
        return this.scoreValue; 
    }

    /**
     * For features in items e.g. Giant's Belt of Strength, which sets Strength Score to 20 while equipped. 
     */
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue; 
    }

    //Core Methods: 

    /**
     * Used for features such as ability damage 
     * 
     * TODO: Cache 
     * 
     * Warning: This is uncached, so changing the maximum cannot be reverted unless you replace with a snapshot. 
     */
    public void modifyScoreValue(int amount) {
        this.scoreValue += amount; 
    }
}

/**
 * 

 */