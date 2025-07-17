package guidemon.model.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import guidemon.engine.effect.Effect;
import guidemon.model.ability.Ability;
import guidemon.model.actor.interfaces.Damagable;
import guidemon.model.combat.DamageType;
import guidemon.model.combat.TickUnit;
import guidemon.model.dice.Dice;
import guidemon.model.entry.Entry;
import guidemon.model.inventory.Inventory;
import guidemon.model.inventory.InventorySlot;
import guidemon.model.inventory.Limb;
import guidemon.model.levels.Level;
import guidemon.model.proficiencies.Proficiency;
import guidemon.model.stats.DamageMultiplier;
import guidemon.model.stats.HealthLayer;
import guidemon.model.stats.ResourceOrbState;
import guidemon.model.stats.immutable.instances.AbilityScoreEntry;
import guidemon.model.stats.immutable.instances.MovementTypeEntry;
import guidemon.model.stats.immutable.instances.PassiveScoreEntry;
import guidemon.model.stats.immutable.instances.ResourceEntry;
import guidemon.model.stats.immutable.instances.ResourceOrbEntry;
import guidemon.model.vision.senses.Sense; 

//aka character? 
public class Actor extends Entry implements Damagable {
    //control privileges - users that can own this Actor 
    private List<String> owners = new ArrayList<>();                       

    //Cached Stack for forms, states etc.
    private Stack<Actor> cachedStates = new Stack<>();  
    private List<Actor> forms = new Stack<>();       //TODO: instead, use an ability to transition from one statblock to another. 

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
    private Stack<HealthLayer> shieldLayers;          //SHD (shields, in order of shield layers)
    private Stack<HealthLayer> armourLayers;          //AC (non-true, in order of armour layers)
    private List<ResourceOrbEntry> summoningSlots;    //SUM

    //maxAP, maxSPD, maxAC are display values calculate at the view. 

    private AbilityScoreEntry healthAbility;          //HPmod - death saves
    private AbilityScoreEntry manaAbility;            //MPmod
    private AbilityScoreEntry spellAbility;           //SPELLmod - spell saves 

    private int nonLethalDamage;                         //if this value == HP for sum total of health (mainHealthLayer + otherHealths, then apply Unconscious until it is all removed)
    private HealthLayer mainHealthLayer;                 //e.g. ACHP vs HP
    private Stack<HealthLayer> otherHealthLayers;        //e.g. oHEAL, Absorption          
    private Map<String, ResourceEntry> otherHealthTypes; //e.g. ACHP, etc.

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
    private Inventory externalHand;                         //e.g. rostered items, summoned items, temporary items etc.

    //TODO: turn otherResources and otherPassiveScores into Map for faster lookup time (?)
    private List<ResourceEntry> otherResources;             //for other resources
    private List<PassiveScoreEntry> otherPassiveScores;     //for other passive scores 

    private List<Sense> senses;                             //senses 
    private List<Proficiency> proficiencies;                //crafting recipes, crafting station, item, langauge, skill, generic

    private List<Ability> abilities = new ArrayList<>();    //including source
                                                            //TODO: abilities is final 

    private List<Effect> effects = new ArrayList<>();       //list of all effects currently on this statblock <- inside it is active or not. 
                                                            //TODO: effects ais final 

    //TODO: getters and setters

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

    public MovementTypeEntry getCurrentMovementType() {
        return this.currentMovementType; 
    }

    public void setCurrentMovementType(MovementTypeEntry currentMovementType) {
        this.currentMovementType = currentMovementType; 
    }

    //TODO: Add Shields

    //TODO: Remove Shields

    //TODO: getTotalShield()

    //TODO: Add Armour Layer

    //TODO: Remove Armour Layer

    //TODO: getTotalAC()

    /**
     * Add and remove abilities from here.
     * 
     * @return abilities
     */
    public List<Ability> getAbilites() {
        return this.abilities; 
    }

    //no setter - change actor/statblock entirely if the entire ability list is replaced

    /**
     * Add and remove effects from here. 
     * 
     * TODO: integration with abiliites.
     * 
     * @return effects
     */
    public List<Effect> getEffects() {
        return this.effects; 
    }

    //no setter - TODO: effects (?)
    
    //TODO: WIP
    public Actor(String name) {
        super(name); 
    }

    /**
     * Method for taking damage with Armour Penetration.
     * 
     * Refer to the main takeDamage() function. 
     */
    // public void takeDamage(int initialDamage) {
    //     // takeDamage(initialDamage, 0.0); 

    // }

    /**
     * Main method by which an actor takes damage to their health. 
     * 
     * The actor uses their main health as a gauge for how 'alive' or how healthy the actor is. I.e, whether they can survive future attacks or the general state of being of the actor. All actors aim to keep this above 0 and most definitely above their deathHP to avoid death. 
     * 
     * The DamageEvent will deal damage in the following hierarchy of HealthLayers unless specified otherwise via other overloaded methods to take damage:
     * 0. Handle External Damage Reduction (e.g. Parry, Guard, etc.)
     * 1. Handle Armour Penetration and Layer-Specific Damage
     * 2. True Armour Class (tAC)
     * 3. Shield Layers: 
     *     3.1: Handle Damage Reduction from Effects
     *     3.2: Handle Damage Immunities and Banes (resolve ranks as well)
     *     3.3: Handle Damage Multipliers (resolve ranks as well)
     * 4. Armour Layers: 
     *     4.1: Handle Damage Reduction from Effects
     *     4.2: Handle Damage Immunities and Banes (resolve ranks as well)
     *     4.3: Handle Damage Multipliers (resolve ranks as well)
     * 5. Other Health Layers: (e.g. oHEAL, absorptionHP)
     *     5.1: Handle Damage Reduction from Effects
     *     5.2: Handle Damage Immunities and Banes (resolve ranks as well)
     *     5.3: Handle Damage Multipliers (resolve ranks as well) 
     * 6. Main Health Layer (aka Layer 0, there exists only one layer and while most actors use HP, it is determined on a per-actor-basis (e.g. Constructs use AC-HP))
     *     6.1: Handle Damage Reduction from Effects
     *     6.2: Handle Damage Immunities and Banes (resolve ranks as well)
     *     6.3: Handle Damage Multipliers (resolve ranks as well) 
     * 
     * Apply Unconscious when at 0HP and perform Death Saves. 
     * Apply Dead when HP == DeathHP
     * 
     * All Damage involving decimals or double values are always Rounded Up.
     * 
     * Some HealthLayers are Irrecoverable - meaning that once their resource goes down to 0, they 'break' or 'expire' i.e, they are permanently removed from the stack. 
     * 
     * TODO: Some HealthLayers have ImpedingRank - which prevents all damage from flowing through the HealthBrakdown of this damage precedence.
     * 
     * This method's usage is different from resource deduction. 
     */
    @Override
    public void takeDamage(int initialDamage, Map<DamageType, Integer> initialDamageTypes, double initialArmourPenetration) {
        //0. - External Damage Reduction is Handled outside of the Actor and reduces the initalDamage
        
        //TODO: Process Damage Event
        int damage = initialDamage; 
        Map<DamageType, Integer> damageTypes = initialDamageTypes; //inclusive of rank
        double armourPenetrationForDamage = initialArmourPenetration; 
        //This processes both lethal and nonlethal damage. TODO: Lethal and Nonlethal damage 

        //Step #1:
        damage = handleArmourPenetration(damage, damageTypes, armourPenetrationForDamage); //returns the standardWorkflowDamage used for the rest of this method.

        //Step #2:
        damage = handleTrueAC(damage); 

        //Step #3: 
        damage = handleShieldLayers(damage, damageTypes);

        //Step #4: 
        damage = handleArmourLayers(damage, damageTypes);

        //Step #5: 
        damage = handleOtherHealthLayers(damage, damageTypes);

        //Step #6:
        handleMainHealthLayer(damage);  

        boolean isDowned = isDowned(); 
        boolean isDead = isDead(); 

        if(isDowned) {
            //apply Unconscious
            //remove from initiative

        } else if(isDead) {
            //trigger event to perform Apogee Action.

        }
    }

    /**
     * Method for taking damage starting a different specified layer of the Health Layers. 
     * 
     * Refer to the main takeDamage() function.
     */
    // public void takeDamage(int initialDamage, String layerType, int layerNumber) {
    //     takeDamage(initialDamage, layerType, 0.0, layerNumber);
    // }

    /**
     * Method for taking damage starting a different specified layer of the Health Layers while also dealing Armour Penetration.
     * 
     * Refer to the main takeDamage() function.
     */
    // public void takeDamage(int initialDamage, String layerType, double armourPenetration, int layerNumber) {
        
    // }

    /**
     * Method for the First Precedence or Layer of the HealthBreakdown for Damage. 
     * 1. Handle Armour Penetration
     * 
     * All ArmourPenetration Damage Penetrates: TrueAC, Shield Layers, Armour Layers. It directly targets the otherHealthLayers and Main Health Layer.
     */
    private int handleArmourPenetration(int initialDamage, Map<DamageType, Integer> initialDamageTypes, double initialArmourPenetration) {
        int damage = initialDamage; 
        Map<DamageType, Integer> damageTypes = initialDamageTypes; //inclusive of rank
        double armourPenetrationForHandlingArmourPenetration = initialArmourPenetration; 

        int armourPenetratingDamage = (int) Math.ceil(damage * armourPenetrationForHandlingArmourPenetration); 
        int standardWorkflowDamage = (int) Math.ceil(damage * (1.0 - armourPenetrationForHandlingArmourPenetration));

        armourPenetratingDamage = handleOtherHealthLayers(armourPenetratingDamage, damageTypes); 

        handleMainHealthLayer(armourPenetratingDamage);

        return standardWorkflowDamage; 
    }

    /**
     * Method for the TrueAC Layer of True Damage Reduction.
     * 
     * This occurs regardless of rank etc.
     * 2. Handle TrueAC
     */
    public int handleTrueAC(int damage) {
        int standardWorkflowDamage = damage;
        standardWorkflowDamage -= trueAC; 

        return standardWorkflowDamage; 
    }

    /**
     * Method for processing damage taken towards Shield Layers in the HealthBreakdown for Damage. 
     * 3. Handle Shield Layers
     * 
     * All Shields are considered Irrecoverable HealthLayers. 
     */
    public int handleShieldLayers(int initialDamage, Map<DamageType, Integer> initialDamageTypes) {
        int standardWorkflowDamage = initialDamage; 
        Map<DamageType, Integer> damageTypes = initialDamageTypes; //inclusive of rank

        while(!shieldLayers.isEmpty() && standardWorkflowDamage > 0) {
            HealthLayer topShield = shieldLayers.peek(); 

            int resolvedDamage = resolveDamageByHealthLayer(topShield, standardWorkflowDamage, damageTypes);

            //settle the difference. if the health layer is min - the damage is bigger and the loop continues, else healthLayer is reduced by the damage and the loop breaks.  
            int smallerValue = Math.min(resolvedDamage, topShield.getResource().getCurrentValue()); 

            topShield.getResource().modifyCurrent(-smallerValue);
            standardWorkflowDamage -= smallerValue;  

            //Remove Broken Shields
            if(topShield.getResource().getCurrentValue() <= 0) {
                shieldLayers.pop(); 
            }
        }

        return standardWorkflowDamage; 
    }

    /**
     * Method for processing damage taken towards Armour Layers in the HealthBreakdown for Damage. 
     * 4. Handle Armour Layers
     * 
     * All Armour are NEVER considered Irrecoverable HealthLayers. 
     */
    public int handleArmourLayers(int initialDamage, Map<DamageType, Integer> initialDamageTypes) {
        int standardWorkflowDamage = initialDamage; 
        Map<DamageType, Integer> damageTypes = initialDamageTypes; //inclusive of rank

        Stack<HealthLayer> tempArmourStack = new Stack<>(); 
        while(!armourLayers.isEmpty() && standardWorkflowDamage > 0) {
            HealthLayer topArmour = armourLayers.pop(); 

            int resolvedDamage = resolveDamageByHealthLayer(topArmour, standardWorkflowDamage, damageTypes);

            //settle the difference. if the health layer is min - the damage is bigger and the loop continues, else healthLayer is reduced by the damage and the loop breaks.  
            int smallerValue = Math.min(resolvedDamage, topArmour.getResource().getCurrentValue()); 

            topArmour.getResource().modifyCurrent(-smallerValue);
            standardWorkflowDamage -= smallerValue;  

            //Set to zero to avoid negative armour. 
            if(topArmour.getResource().getCurrentValue() <= 0) {
                topArmour.getResource().setCurrentValue(0); 
            }

            //Always keep armour, even if it hits 0. (minValue = 0)
            tempArmourStack.push(topArmour); 
        }

        //Restore Armour Stack in reverse to maintain order: 
        while(!tempArmourStack.isEmpty()) {
            armourLayers.push(tempArmourStack.pop());
        }

        return standardWorkflowDamage; 
    }

    /**
     * Method for processing damage taken towards Other Health Layers in the HealthBreakdown for Damage. 
     * 5. Handle Other Health Layers
     * 
     * Some HealthLayers are considered Irrecoverable, some are NOT. screen for it via control flow in if-else statements. 
     * 
     * Used by both Armour Piercing and Normal HealthBreakdown Damage Workflows. 
     */
    public int handleOtherHealthLayers(int initialDamage, Map<DamageType, Integer> initialDamageTypes) {
        int damage = initialDamage; 
        Map<DamageType, Integer> damageTypes = initialDamageTypes; //inclusive of rank

        Stack<HealthLayer> tempOtherHealthStack = new Stack<>();
        while(!otherHealthLayers.isEmpty() && damage > 0) {
            HealthLayer topHealthLayer = otherHealthLayers.pop(); 

            //settle the difference. if the health layer is min - the armourPenetratingDamage is bigger and the loop continues, else healthLayer is reduced by the armourPenetratingDamage and the loop breaks.  
            int smallerValue = Math.min(damage, topHealthLayer.getResource().getCurrentValue()); 

            //actually process the damage taken
            topHealthLayer.getResource().modifyCurrent(-smallerValue);
            damage -= smallerValue;

            if(!topHealthLayer.getIsIrrecoverable()) {
                //If it is negative or at zero - keep it that way. 
                if(topHealthLayer.getResource().getCurrentValue() <= 0) {
                    topHealthLayer.getResource().setCurrentValue(0);
                }

                //Always keep otherHealthLayers, even if it hits 0. (minValue for a HealthLayer is 0)
                tempOtherHealthStack.push(topHealthLayer);

            }

            //Else: Remove 'Expired' Healths, via otherHealthLayers.pop(); 
        }

        //Restore OtherHealthStack in reverse to maintain order: (so long as the health layer is NOT irrecoverable)
        while(!tempOtherHealthStack.isEmpty()) {
            HealthLayer layer = tempOtherHealthStack.pop();

            if(!layer.getIsIrrecoverable()) {
                otherHealthLayers.push(layer); 
            }
        }

        return damage; 
    }

    /**
     * Method for processing damage taken towards the Main Health Layer in the HealthBreakdown for Damage. 
     * 6. Handle the Main Health Layer
     * 
     * Some HealthLayers are considered Irrecoverable, some are NOT. screen for it via control flow in if-else statements. 
     */
    public void handleMainHealthLayer(int initialDamage) {
        int damage = initialDamage; 
    
        mainHealthLayer.getResource().modifyCurrent(-damage); 

        // if(mainHealthLayer.getResource.getCurrentValue() < 0 && )

        //if negative - apply unconscious etc.
    }

    /**
     * Helper Function for resolving damage against each unique HealthLayer. 
     * 
     * Step #1: Handle Damage Reduction from Effects stored inside a HealthLayer
     * Step #2: Handle Damage Immunities and Banes inside a Health Layer. Resolve the ranks accordingly. 
     * Step #3: Handle Damage Multipliers inside a Health Layer. Resolve the ranks accordingly. 
     * 
     * Just resolve damage here. Do not apply to the actual layer resource here. 
     */
    private int resolveDamageByHealthLayer(HealthLayer layer, int damage, Map<DamageType, Integer> damageTypes) {
        int resultantDamage = damage;

        //Handle Damage Reduction: (Specifies how much damage reduction for each DamageType where applicable) - does not have ranks
        for (Map.Entry<DamageType, Integer> entry : layer.getDamageReduction().entrySet()) {
            DamageType damageReductionDamageType = entry.getKey();
            int damageReductionAmount = entry.getValue();

            if(damageTypes.containsKey(damageReductionDamageType)) {
                resultantDamage -= damageReductionAmount; 
            }
        }

        //Handle Damage Immunities: 
        //For each additional DamageType that the actor is not immune to - have the actor take just a fraction of the damage. I.e, they take the 'fire' part of a Fire (50%), Lightning = Aleta Damage Event, if the user is immune to only Lightning Damage but not Fire.
        //Round Up
        //Has Ranks
        int totalDamageTypes = damageTypes.size(); 
        int damageTypesCounter = 0; 
        for (Map.Entry<DamageType, Integer> entry : layer.getDamageImmunities().entrySet()) {
            DamageType damageImmunityDamageType = entry.getKey();
            int rank = entry.getValue(); 
    
            if(damageTypes.containsKey(damageImmunityDamageType) && damageTypes.get(damageImmunityDamageType) < rank) {
                damageTypesCounter++;

            }
        } 

        double damageTypeCoverage = damageTypesCounter / totalDamageTypes;  
        resultantDamage = (int) Math.ceil(resultantDamage * damageTypeCoverage); 

        //Handle Damage Banes: 
        //Apply Effects
        //Send Events?

        //Handle Damage Multipliers: 
        //Round Up
        //Has Ranks
        for (int i = 0 ; i < layer.getDamageMultipliers().size() ; i++) {
            DamageMultiplier multiplier = layer.getDamageMultipliers().get(i);
            DamageType damageType = multiplier.getDamageType(); 

            if(damageTypes.containsKey(damageType)) {
                //Handle Rank Differences:
                int rankDifference = 0; //if no weakness or resistance, start off at rank 0. 
                
                //Handle Rank Difference for Resistances. 
                //Reduce RankDifference by the rank of the resistance 
                //if the rankDifference becomes negative - then no bonus damage is done. 
                if(multiplier.getMultiplier() >= 1.0) {
                    rankDifference -= multiplier.getRank(); 
                    
                //Handle Rank Difference for Weaknesses.
                //Increase RankDifference by the rank of the weakness
                //if the rankDifference becomes positive - then bonus damage is done. 
                } else if (multiplier.getMultiplier() < 1.0) {
                    rankDifference += multiplier.getRank(); 

                }

                //Rank Difference is exponential: 
                //RankDifferenceMultiplier = 2^(RankDifference / 2)
                //Ensures that RankDifference = 0, means 1.0 (does not affect the original mulitplier value)
                double rankDifferenceMultiplier = Math.pow(2, rankDifference / 2);

                //Handle the Actual Damage Multiplier Calculation:
                resultantDamage = (int) Math.ceil(resultantDamage * multiplier.getMultiplier() * rankDifferenceMultiplier); 

                //TODO: Apply Different Effects via DamageRank
                //Send an Event? 
            }
        }

        return resultantDamage; 
    }
    
    /**
     * Checks whether the actor has less than 0HP.
     * 
     * If yes, the actor is rendered unconscious and downed. 
     * 
     * This would remove them from the initiative list in a combat encounter so long as their HP remained at 0. 
     */
    public boolean isDowned() {
        boolean isDowned = mainHealthLayer.getResource().getCurrentValue() <= 0; 

        return isDowned;
    }

    /**
     * Checks whether the actor has HP <= DeathHP.
     * 
     * Round down the DeathHP Value for each calculation. 
     * 
     * If yes, the actor is considered dead. 
     * 
     * This would prompt a Apogee Action. (End of the Character's Life)
     */
    public boolean isDead() {
        int currentDeathHP = (int) Math.floor(hitPoints.getMaximumValue() * deathHealthPercentage); 

        boolean isDead = mainHealthLayer.getResource().getCurrentValue() <= currentDeathHP;

        return isDead; 
    }

    public boolean isAlive() {
        boolean isAlive = !isDead();

        return isAlive; 
    }
}