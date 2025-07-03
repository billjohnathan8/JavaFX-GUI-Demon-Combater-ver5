package guidemon.model.stats.immutable.definitions;

//redundant import because this is a class that inherits from another definition class:
// import guidemon.model.immutability.BaseDefinition; 

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
 * This class is the Definition for an Entry of AbilityScore.
 * There is nothing to inherit from, so this class is final.  
 */
public final class AbilityScoreDefinition extends PassiveScoreDefinition {
    private final int defaultModifierValue; 
    private final int defaultSavingThrowValue;
    private final int defaultSavingThrowModifierValue; 

    public AbilityScoreDefinition(String id, String displayName, int defaultValue) {
        super(id, displayName, defaultValue); 

        this.defaultModifierValue = calculateModifier(defaultValue);
        this.defaultSavingThrowValue = defaultValue;
        this.defaultSavingThrowModifierValue = calculateModifier(defaultValue);
    }

    //getters: 
    public int getDefaultModifierValue() { 
        return this.defaultModifierValue; 
    }

    public int getDefaultSavingThrowValue() { 
        return this.defaultSavingThrowValue; 
    }

    public int getDefaultSavingThrowModifierValue() { 
        return this.defaultSavingThrowModifierValue; 
    }

    //Relevant Helper Methods:

    /**
     * This is a private method for just this class and for any AbilityScores.
     * 
     * It is used to calculate modifier values from passive score value inputs.
     * 
     * In order to obtain a Modifier Value, the function of transformations or formula follows these steps:
     * 1. abilityScore -= 10
     * 2. abilityScore /= 2
     * 3. Math.FLOOR(abilityScore) 
     */
    private static int calculateModifier(int abilityScoreValue) {
        int result = abilityScoreValue; 

        result -= 10; 
        result /= 2; 
        Math.floor(result); 
        result = (int) result; 

        return result;
    }
}