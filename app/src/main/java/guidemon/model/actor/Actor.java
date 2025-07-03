package guidemon.model.actor;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;

import guidemon.model.entry.Entry;
import guidemon.engine.effect.Effect;
import guidemon.model.ability.Ability;
import guidemon.model.combat.DamageType;
import guidemon.model.combat.TickUnit;
import guidemon.model.dice.Dice; 
import guidemon.model.stats.immutable.instances.AbilityScoreEntry; 
import guidemon.model.stats.immutable.instances.ResourceEntry; 
import guidemon.model.stats.immutable.instances.MovementTypeEntry; 
import guidemon.model.stats.immutable.instances.ResourceOrbEntry; 
import guidemon.model.stats.immutable.instances.PassiveScoreEntry; 
import guidemon.model.levels.Level; 
import guidemon.model.inventory.Limb;
import guidemon.model.inventory.Inventory;
import guidemon.model.inventory.InventorySlot; 
import guidemon.model.vision.senses.Sense; 
import guidemon.model.proficiencies.Proficiency; 

//aka character? 
public class Actor extends Entry {
    //control privileges - users that can own this Actor 
    private List<String> owners = new ArrayList<>();                       

    //Cached Stack for forms, states etc.
    private Stack<Actor> cachedStates = new Stack<>();  

    //Ability Scores: 
    private AbilityScoreEntry strength;               //STR
    private AbilityScoreEntry dexterity;              //DEX
    private AbilityScoreEntry constitution;           //CON
    private AbilityScoreEntry intelligence;           //INT
    private AbilityScoreEntry wisdom;                 //WIS
    private AbilityScoreEntry charisma;               //CHA

    //optional ability scores: 
    // private AbilityScoreEntry instinct;            //INS
    // private AbilityScoreEntry balance;             //BAL
    // private AbilityScoreEntry luck;                //LCK
    // private AbilityScoreEntry will;                //WIL

    //Primary Statistics: 
    private ResourceEntry hitPoints;                  //HP
    private ResourceEntry manaPoints;                 //MP
    private List<MovementTypeEntry> movementSpeeds;   //SPD (inclusive BaseSPD)
    private List<ResourceOrbEntry> actionPoints;      //AP (inclusive VersatileAP)
    private Stack<ResourceEntry> shieldLayers;        //SHD (shields, in order of shield layers)
    private Stack<ResourceEntry> armourLayers;        //AC (non-true, in order of armour layers)
    private List<ResourceOrbEntry> summoningSlots;    //SUM

    //maxAP, maxSPD, maxAC are display values calculate at the view. 

    private AbilityScoreEntry healthAbility;          //HPmod - death saves
    private AbilityScoreEntry manaAbility;            //MPmod
    private AbilityScoreEntry spellAbility;           //SPELLmod - spell saves 

    private ResourceEntry mainHealthResource;            //e.g. ACHP vs HP
    private Map<String, ResourceEntry> otherHealthTypes; //e.g. oHEAL, ACHP, etc.

    private int maximumBaseCastingRange;         //BCR
    private int maximumSummoningRange;           //rSUM

    private int fallingThreshold;                //FT
    private int baseWeight;                      //baseW
    private int weight;                          //W - effective / total weight 

    private MovementTypeEntry currentMovementType;  //curMOVEtype 

    private int elevation;                         //current elevation determined by the current tile being stepped on
    private Map<TickUnit, Integer> hoverDuration;  //time spent for hovering
    private boolean isHovering;                    //same boolean used for sinking etc.
    private double currentFallingSpeed;            //FALL_SPD - equivalent to fall distance etc.
    private double currentTerminalVelocity;        //t_SPD

    private int trueAC;                          //tAC (True)

    private List<Dice> hitDice;                  //HD
    private Dice hitDicePerLevel;                //HD per LVL

    //baseToken
    //baseImage

    //Secondary Statistics: (for base values etc.)
    private ResourceEntry saturation;                 //SAT
    private ResourceEntry hunger;                     //HUN

    private int encumbrance;                     //ENC
    private int encumbranceThreshold;            //ET
    private int staminaThreshold;                //ST

    private Map<String, ResourceOrbEntry> stamina;    //STA

    private double trudgeMultiplier;             //xTRUDGE
    private double criticalMultiplier;           //xCRIT

    private List<Integer> criticalChance;        //CRIT%
    private double criticalModifier;             //CRITmod - added true damage on every critical hit 

    private double deathHealthPercentage;        //DHP% 
    private double bloodiedPercentage;           //BLD%
    private double manaMalaisePercentage;        //MM%

    private int bloodiedHP;                      //BLDHP 
    private int malaiseMP;                       //MMMP

    private boolean useSpecialConcentration;     //sCONC
    private int breakHP;                         //BRKHP

    private int moveResistances;                 //MRESIST aka General Resistance to Knockback and Displacement 

    private int lethality;                       //LETH
    private double armourPenetration;            //ARPEN
    private int vampirism;                       //VAMP  
    private double omniVampirism;                //oVAMP

    private Map<TickUnit, Integer> cooldownReduction; //CDR
    private Map<TickUnit, Integer> tenactiy;          //TEN
    private Map<TickUnit, Integer> reducedDuration;   //RED aka Chrono-Ception

    private boolean isThrowDistanceEqualJumpDistance; //isThrowJump
    private int throwDistance;        
    private int throwHeight; 
    private int jumpDistance;
    private int jumpHeight; 

    //First Skills: 
    private PassiveScoreEntry generalAthletics; //based on STR
    private PassiveScoreEntry dodge;            //based on DEX
    private PassiveScoreEntry precision;        //based on DEX
    private PassiveScoreEntry persistence;      //based on CON
    private PassiveScoreEntry perception;       //based on WIS aka Passive Perception
    private PassiveScoreEntry communication;    //based on CHA

    //Levelling: 
    private List<Level> levels;                  //all levels (Major Classes, Sub-Classes (Minor Clases), Job Classes, Races)
    private int overallLevel;                    //LVL
    private int proficiencyBonus;                //aka Universal Proficiency Bonus
    private List<AbilityScoreEntry> naturalAdvantageAbilities; 
    private List<AbilityScoreEntry> naturalDisadvantageAbilities; 

    //optional resources: 
    // private Resource thirst;             //TST
    // private Resource sleep;              //SLP
    // private Resource cleanliness;        //CLN
    // private Resource bowel;              //BOW - for pee and poop etc.
    // private int temperature;             //TEMP

    // private int sanityThreshold;         //SANt
    // private int sanity;                  //SAN - current sanity 

    //faction scores: 
    // private String mainAllegianceFaction;             //WIP

    //inventory 
    private List<Limb> limbs;                          //based on race
    private Inventory primaryInventory;                //main hand, off hand etc.
    private Inventory secondaryInventory;              //accessories, etc.
    private Inventory vanityInventory;                 //for items used on display 
    private Inventory armourInventory;                 //helmet, chestplate, leggings, boots
    private Inventory modularArmourInventory;          //for modular armour 
    private InventorySlot backpack;                    //for backpack item for storage inventory for backpack inventory 
    private Inventory externalHand;                    //e.g. rostered items, summoned items, temporary items etc.

    private List<ResourceEntry> otherResources;             //for other resources
    private List<PassiveScoreEntry> otherPassiveScores;     //for other passive scores 

    private Map<DamageType, Double> damageMultipliers;    //damage weaknesses + damage resistances 
    private Map<DamageType, Integer> damageImmunities;    //key:DamageType, value: Rank
    private Map<DamageType, Integer> damageBanes;         //key:DamageType, value: Rank
    private Map<String, Integer> statusEffectResistances; //key:StatusEffectName, value: Rank
    private Map<String, Integer> statusEffectWeaknesses;  //key:StatusEffectName, value: Rank

    private List<Sense> senses;                        //senses 
    private List<Proficiency> proficiencies;           //crafting recipes, crafting station, item, langauge, skill, generic

    private List<Ability> abilities;                   //including source 

    private List<Effect> effects;                      //list of all effects currently on this statblock <- inside it is active or not. 

    //TODO: WIP
    public Actor(String name) {
        super(name); 
    }

    //TODO: REST OF OTHER METHODS
    public List<ResourceOrbEntry> getActionPoints() {
        return actionPoints; 
    }

    public MovementTypeEntry getCurrentMovementType() {
        return currentMovementType; 
    }
}