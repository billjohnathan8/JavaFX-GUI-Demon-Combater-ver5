package guidemon.model.stats.immutable.instances;

import guidemon.model.entry.Entry; 
import guidemon.model.stats.immutable.definitions.AbilityScoreDefinition; 

/**
 * Ability Scores are the most important Passive Scores in the game. 
 * 
 * The Main 6 Ability Scores: STR, DEX, CON, INT, WIS, CHA generalise and represent all possible skills an actor can attempt in a simple and robust way. 
 * 
 * Unlike normal Passive Scores which may have adjacent Skills Checks or whole Unique Skills - Ability Scores are have correspomding Ability Checks which are special and standardised to the main six. 
 * 
 * The Ability Checks are d20 dice rolled with a bonus on them - deteremined by an AbilityScoreModifierValue that is itself determined by the PassiveScore or AbilityScore. 
 * 
 * Ability Scores are special in that they contain corresponding Saving Throw Scores used for Saving Throw Checks or Saving Throw Rolls - which are made in reaction to another event or effect. E.g. Dodging an attack requires a DEX Save. These Saving Throw Scores are initialised using the Passive Score Value, but are ultimately independent - allowing for rich yet robust representation of an actor's ability (e.g. a character with a low DEX but high DEX Save can dodge better, but is not necessarily faster overall).
 * 
 * Also known as: AbilityScoreInstance
 * 
 * //TODO: Type and AbilityScore and PassiveScore
* This class does not inherit from PassiveScoreEntry, breaking the inheritance chain - but it functions the same as a PassiveScore, despite not being of the same Typing.  
 * 
 * This class is the entry for Ability Scores  
 */
public class AbilityScoreEntry extends Entry {
    private final AbilityScoreDefinition definition; 
    private int scoreValue; 
    private int modifierValue;
    private int savingThrowValue;
    private int savingThrowModifierValue; 

    public AbilityScoreEntry(AbilityScoreDefinition definition, String name) {
        super(name); 
        this.definition = definition; 

        this.scoreValue = definition.getDefaultScoreValue();
        this.modifierValue = definition.getDefaultModifierValue();
        this.savingThrowValue = definition.getDefaultSavingThrowValue();
        this.savingThrowModifierValue = definition.getDefaultSavingThrowModifierValue(); 
    }
    
    //overload this constructor if you want to load from JSON:
    public AbilityScoreEntry(AbilityScoreDefinition definition, String name, int scoreValue, int modifierValue, int savingThrowValue, int savingThrowModifierValue) {
        super(name); 
        this.definition = definition;
        this.scoreValue = scoreValue;
        this.modifierValue = modifierValue;
        this.savingThrowValue = savingThrowValue;
        this.savingThrowModifierValue = savingThrowModifierValue; 
    }

    //getters and setters: 
    public int getScoreValue() { 
        return this.scoreValue; 
    }

    /**
     * For features in items e.g. Giant's Belt of Strength, which sets Strength Score to 20 while equipped. 
     */
    public void setScoreValue(int newScoreValue) { 
        this.scoreValue = newScoreValue; 
    }

    public int getModifierValue() {
        return this.modifierValue; 
    }

    /**
     * For features like Superarm which sets STRmod to 3 while it is on.
     */
    public void setModifierValue(int newModifierValue) { 
        this.modifierValue = newModifierValue; 
    }

    public int getSavingThrowValue() { 
        return this.savingThrowValue; 
    }

    /**
     * For features like speedy dodge, which sets DEX Save to 14
     */
    public void setSavingThrowValue(int newSavingThrowValue) { 
        this.savingThrowValue = newSavingThrowValue; 
    }

    public int getSavingThrowModifierValue() { 
        return this.savingThrowModifierValue; 
    }

    /**
     * For features like great dodge - which sets DEX Save for Dodge to +5
     */
    public void setSavingThrowModifierValue(int newSavingThrowModifierValue) {
        this.savingThrowModifierValue = newSavingThrowModifierValue; 
    }    

    //Core Methods:

    /**
     * Used for features such as +2 to STR
     * 
     * TODO: Cache 
     * 
     * Warning: This is uncached, so changing the maximum cannot be reverted unless you replace with a snapshot. 
     */
    public void modifyScoreValue(int amount) {
        this.scoreValue += amount; 
    } 

    /**
     * Used for features such as +3 to STRmod 
     * 
     * TODO: Cache 
     * 
     * Warning: This is uncached, so changing the maximum cannot be reverted unless you replace with a snapshot. 
     */
    public void modifyModifierValue(int amount) {
        this.modifierValue += amount; 
    }

    /**
     * Used for features such as +2 to DEX Save Score  
     * 
     * TODO: Cache 
     * 
     * Warning: This is uncached, so changing the maximum cannot be reverted unless you replace with a snapshot. 
     */
    public void modifySavingThrowValue(int amount) {
        this.savingThrowValue += amount; 
    }

    /**
     * Used for features such as +1 to STR Saving Throws 
     * 
     * TODO: Cache 
     * 
     * Warning: This is uncached, so changing the maximum cannot be reverted unless you replace with a snapshot. 
     */
    public void modifySavingThrowModifierValue(int amount) {
        this.savingThrowModifierValue += amount; 
    }
}