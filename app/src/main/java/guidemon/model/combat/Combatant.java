package guidemon.model.combat;

import java.util.List;

import guidemon.model.actor.Actor;
import guidemon.model.stats.ResourceOrbState;
import guidemon.model.stats.immutable.instances.ResourceOrbEntry;

public class Combatant extends Actor {
    private int initiative; 
    //Corresponding CombatToken 

    //owners
    //Cache of previous states
    //display image

    // //Ability Scores: 
    // private AbilityScore strength;               //STR
    // private AbilityScore dexterity;              //DEX
    // private AbilityScore constitution;           //CON
    // private AbilityScore intelligence;           //INT
    // private AbilityScore wisdom;                 //WIS
    // private AbilityScore charisma;               //CHA
    
    // //optional ability scores: 
    // // private AbilityScore instinct;            //INS
    // // private AbilityScore balance;             //BAL
    // // private AbilityScore luck;                //LCK
    // // private AbilityScore will;                //WIL

    // private AbilityScore healthAbility;          //HPmod - death saves
    // private AbilityScore manaAbility;            //MPmod
    // private AbilityScore spellAbility;           //SPELLmod - spell saves 

    // //Health Layers:
    // private int trueAC;                          //tAC (True) - other damage reduction is caused by passive abilities etc. 
    // //WIP - Damage Reduction
    // private Stack<Resource> shieldLayers;        //SHD (shields, in order of shield layers)
    // private Stack<Resource> armourLayers;        //AC (non-true, in order of armour layers)
    // private List<Resource> otherHealthResources; //e.g. Overheal, Absorption, etc.
    // private Resource mainHealthResource;         //e.g. ACHP vs HP <- used for HP etc.
        
    // //Resources:
    // private List<ActionPoint> actionPoints;      //AP (inclusive VersatileAP)
    private int totalSpeedSpent;                    //spentSPD - all movement already spent on the turn for use during movementType switch
    // private List<MovementType> movementSpeeds;   //SPD (inclusive BaseSPD)
    private List<ResourceOrbEntry> actionPoints;      //AP (inclusive VersatileAP)
    // private Resource manaPoints;                 //MP
    // private List<ResourceOrb> summoningSlots;    //SUM
    // private Resource saturation;                 //SAT
    // private Resource hunger;                     //HUN
    // private Map<String, ResourceOrb> stamina;    //STA - includes breath etc.

    // private int maximumBaseCastingRange;         //BCR 
    // private int maximumSummoningRange;           //rSUM 
    // private int fallingThreshold;                //FT
    // private int weight;                          //effective / total weight 

    // private List<Dice> hitDice;                    //HD

    // private int encumbrance;                     //ENC
    // private int encumbranceThreshold;            //ET
    // private int staminaThreshold;                //ST

    // private double trudgeMultiplier;             //xTRUDGE
    // private double criticalMultiplier;           //xCRIT

    // private List<Integer> criticalChance;        //CRIT%
    // private double criticalModifier;             //CRITmod - added true damage on every critical hit 

    // private double deathHealthPercentage;        //DHP% 
    // private double bloodiedPercentage;           //BLD%
    // private double manaMalaisePercentage;        //MM%

    // private int bloodiedHP;                      //BLDHP 
    // private int malaiseMP;                       //MMMP

    // private boolean useSpecialConcentration;     //sCONC
    // private int breakHP;                         //BRKHP

    // private int moveResistances;                 //MRESIST aka General Resistance to Knockback and Displacement 

    // private int lethality;                       //LETH
    // private double armourPenetration;            //ARPEN
    // private int vampirism;                       //VAMP  
    // private double omniVampirism;                //oVAMP

    // private Map<TickUnit, Integer> cooldownReduction; //CDR
    // private Map<TickUnit, Integer> tenactiy;          //TEN
    // private Map<TickUnit, Integer> reducedDuration;   //RED aka Chrono-Ception

    // private boolean isThrowDistanceEqualJumpDistance; //isThrowJump
    // private int throwDistance;        
    // private int throwHeight; 
    // private int jumpDistance;
    // private int jumpHeight; 

    // //First Skills: 
    // private PassiveScore generalAthletics; //based on STR
    // private PassiveScore dodge;            //based on DEX
    // private PassiveScore precision;        //based on DEX
    // private PassiveScore persistence;      //based on CON
    // private PassiveScore perception;       //based on WIS aka Passive Perception
    // private PassiveScore communication;    //based on CHA
    
    // //Levelling: 
    // private List<Level> levels;                  //all levels (Major Classes, Sub-Classes (Minor Clases), Job Classes, Races)
    // private int overallLevel;                    //LVL
    // private int proficiencyBonus;                //aka Universal Proficiency Bonus
    // private List<AbilityScore> naturalAdvantageAbilities; 
    // private List<AbilityScore> naturalDisadvantageAbilities; 

    //     //optional resources: 
    //     // private Resource thirst;             //TST
    //     // private Resource sleep;              //SLP
    //     // private Resource cleanliness;        //CLN
    //     // private Resource bowel;              //BOW - for pee and poop etc.
    //     // private int temperature;             //TEMP

    //     // private int sanityThreshold;         //SANt
    //     // private int sanity;                  //SAN - current sanity 

    // private List<Resource> otherResources;             //for other resources
    // private List<PassiveScore> otherPassiveScores;     //for other passive scores 

    public Combatant(String name) {
        super(name);
    }   

    //TODO: REST OF OTHER METHODS

    //aka getCurSPD()
    public int getTotalSpeedRemaining() {
        return getCurrentMovementType().getMaximumSpeed() - this.totalSpeedSpent;
    }

    //aka getMaxSPD()
    public int getTotalSpeed() {
        return getCurrentMovementType().getMaximumSpeed(); 
    }
    
    public int getTotalSpeedSpent() {
        return this.totalSpeedSpent; 
    }

    public void setTotalSpeedSpent(int totalSpeedSpent) {
        this.totalSpeedSpent = totalSpeedSpent; 
    }

    public void modifyTotalSpeedSpent(int amount) {
        this.totalSpeedSpent += amount; 
    }




    public List<ResourceOrbEntry> getActionPoints() {
        return this.actionPoints; 
    }

    public void setActionPoints(List<ResourceOrbEntry> actionPoints) {
        this.actionPoints = actionPoints;
    }

    /**
     * Counts the number of Versatile AP that is also FREE
     * 
     * @return curAP
     */
    public int getCurAP() {
        int result = 0;

        for (int i = 0;  i < actionPoints.size() ; i++) {
            if(actionPoints.get(i).getType().equals("Versatile") && actionPoints.get(i).getState().equals(ResourceOrbState.FREE)) {
                result++;
            }
        }
        
        return result; 
    }

    /**
     * Counts the number of Versatile AP.
     * 
     * @return maxAP
     */
    public int getMaxAP() {
        int result = 0;

        for (int i = 0;  i < actionPoints.size() ; i++) {
            if(actionPoints.get(i).getType().equals("Versatile")) {
                result++;
            }
        }
        
        return result; 
    }

    //reset all used AP at the start of a round etc.
    public void resetAP() {
        for(int i = 0 ; i < actionPoints.size() ; i++) {
            if(actionPoints.get(i).getState().equals(ResourceOrbState.USED)) {
                actionPoints.get(i).setState(ResourceOrbState.FREE); 
            }
        }
    }

    //spend AP
    public void spendAP(int amount, String apType) {
        for(int i = 0 ; i < amount ; i++) {
            if(actionPoints.get(i).getType().equals(apType)) {
                actionPoints.get(i).setState(ResourceOrbState.USED);
            }
        }
    }
}