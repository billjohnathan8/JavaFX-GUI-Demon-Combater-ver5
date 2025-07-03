package guidemon.model.stats.immutable.definitions;

import guidemon.model.immutability.BaseDefinition; 

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
 * This class is the Definition for an Entry of PassiveScore.
 * Class is not final because AbilityScore inherits from this parent class.  
 */
public class PassiveScoreDefinition extends BaseDefinition {
    private final int defaultScoreValue;

    public PassiveScoreDefinition(String id, String name, int defaultScoreValue) {
        super(id, name);
        this.defaultScoreValue = defaultScoreValue; 
    }

    //only getters: 
    public int getDefaultScoreValue() {
        return this.defaultScoreValue; 
    }
}