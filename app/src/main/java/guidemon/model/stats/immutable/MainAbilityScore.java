package guidemon.model.stats.immutable;

/**
 * This enumerator represents the Main Six Ability Scores in NHe: STR, DEX, CON, INT, WIS, CHA
 * 
 * This class is used for enumerations of these core ability score where needed. 
 */
public enum MainAbilityScore {
    STRENGTH("STR"),        //Represents the general physical capabilities of its owner
    DEXTERITY("DEX"),       //Represents a mixture of the speed, precision, flexibility, agility, and nimbleness of its owner 
    CONSTITUTION("CON"),    //Represents the will to live, endurance, tolerance to pain and damage, and overall survivability of its owner
    INTELLIGENCE("INT"),    //Represents the amount of intellect and knowledge of its owner - it is an aggregation of the memory-capabilities and logical thought-processes of its owner.   
    WISDOM("WIS"),          //Represents the kinesthetic knowledge, survival skills, intangible experience, sensory capabilities and generalised instincts of its owner. 
    CHARISMA("CHA");        //Represents and generalises the owner's abilities to interact within a social environment either through a commanding presence, deception, performance, fear, amicability, social manipulation, or other means of fitting within a conversation or compelling another person. 

    private String abbreviation; //all abbreviations are always strictly and only three letters.

    private MainAbilityScore(String abbreviation) {
        this.abbreviation = abbreviation; 
    }

    public String getAbbreviation() {
        return this.abbreviation; 
    }
}

    //Optional: 
    /*
     * LUCK("LCK")                      //Represents the user's luck without any form of intervention from anything, it is usually used for Neutral, Pure, Luck, and Fate Rolls - it is also known as FATE - this is partially related to the KARMA mechanic 
     * INSTINCT("INS"),                 //Represents the owner's keen non-physical sense and detection. I.e, a sense of something being awry, premonitions, and other instincts - it is also known as 'gut feeling'. While this is a more specific version of WIS, it specialises in this regard to cover all the areas that WIS does not, which pertains to largely non-physiological means, e.g. via Magic, Fate, Gut Feeling - partially related to the SANITY mechanic. 
     * BALANCE("BAL"),                  //Represents the owner's ability to balance their physical body and regulate emotional balance during physically nebulous or mentally tumultuous periods - this is partially related to the SANITY mechanic. 
     * WILL("WIL"),                     //Represents the owner's ability to survive insurmountable odds and or impart or enact their will on the world no matter what - also known as Willpower - partially related to the SANITY mechanic. 
     * 
     * FAME("FAM"),                     //Represents the - owner's ability to leverage on existing bonds in the world and gain what they want through social connections also known as Renown Score, Fame, Infamy, or Heroism
     * AURA("AUR");                     //Represents the user's ability to surpass expectations set upon them by others. This is related to CHA and WIL 
     */