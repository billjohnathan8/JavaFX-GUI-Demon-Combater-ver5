//TODO: stat calculation to be injected into a statblock. 

// package guidemon.engine.character_creation;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.Stack; 

// import guidemon.model.stats.AbilityScore; 
// import guidemon.model.stats.Resource;
// import guidemon.model.stats.ResourceOrb;

// public class StatCalculation {
//     //Default Values: 
//     private static int DEFAULT_BASE_HP = 20; 
//     private static int DEFAULT_BASE_MP = 20; 
//     private static int DEFAULT_BASE_AP = 1; 
//     private static int DEFAULT_FALLING_THRESHOLD = 5; //5m 
//     private static int DEFAULT_SUMMONING_SLOTS = 0; //0 slots
//     private static int DEFAULT_SUMMONING_RANGE = 0; //0m
//     private static int DEFAULT_TRUE_AC = 0;
//     private static int DEFAULT_BASE_WEIGHT = 10;
//     private static int DEFAULT_BASE_CASTING_RANGE = 1; //1m 
//     private static double DEFAULT_BLOODIED_PERCENTAGE = 0.5; 
//     private static double DEFAULT_MANA_MALAISE_PERCENTAGE = 0.0;
//     private static double DEFAULT_TRUDGE_MULTIPLIER = 0.5;
//     private static double DEFAULT_CRITICAL_MULTIPLIER = 2.0;
//     private static double DEFAULT_DEATH_HEALTH_PERCENTAGE = -2.0;


//     List<AbilityScore> abilityScores; 
//     private AbilityScore strength; 
//     private AbilityScore dexterity;
//     private AbilityScore constitution;
//     private AbilityScore intelligence;
//     private AbilityScore wisdom;
//     private AbilityScore charisma;

//     private Resource hitPoints; 
//     private Resource manaPoints; 
//     private List<ResourceOrb> actionPoints; 
//     private Map<String, Integer> movementSpeeds; 
//     private int fallingThreshold; 
//     private Stack<Resource> shieldLayers; 
//     private Stack<Resource> armourLayers;
//     private Map<String, Integer> otherHealthPoints; 
    
//     private Map<Resource, Integer> durationalResources; // e.g. tempHP 

//    private List<ResourceOrb> summoningSlots; 
//     private int maximumSummoningRange; 
//     private int maximumBaseCastingRange; //aka reach - intramovement casting ? 
//     private int trueAC; 
    
//     private AbilityScore spellcastingAbilityScore; 
//     private int baseWeight; 

//     private int[] hitDice; 
//     private int hitDicePerLevel; 
    
//     private int overallLevel; 

//     private Resource hunger; 

//     private int encumbranceThreshold;
//     private double bloodiedPercentage; 
//     private double manaMalaisePercentage; 
//     private double trudgeMultiplier; 
//     private double criticalMultiplier; 
//     private double deathHealthMultiplier; 

//     private ResourceOrb[] stamina; 
//     private boolean useSpecialConcentration; 
//     private int breakHitPoints;
    
//     private boolean isThrowDistanceEqualJumpDistance; //throw-jump
//     private int throwDistance;
//     private int throwHeight;  
//     private int jumpDistance;
//     private int jumpHeight; 
    
//     //First Skills:
//     private int generalAthletics; 
//     private int dodge; 
//     private int precision; 
//     private int persistance; 
//     private int perception; 
//     private int communication; 
    
//     public StatCalculation(int[] stats, int rolledHP, int rolledMP, int baseMaxAP, Optional<Integer> fallingThrehsold) { 
//         setAbilityScores(stats); 
//         setHitPoints(rolledHP);
//         setManaPoints(rolledMP);  
//         setActionPoints(baseMaxAP); 
//         setBaseMovementSpeed();
//         setFallingThreshold(fallingThrehsold); 


//     }

//     //set ability scores 
//     public void setAbilityScores(int[] stats) { 
//         strength = new AbilityScore("Strength", stats[0]);
//         dexterity = new AbilityScore("Dexterity", stats[1]);
//         constitution = new AbilityScore("Constitution", stats[2]);
//         intelligence = new AbilityScore("Intelligence", stats[3]);
//         wisdom = new AbilityScore("Wisdom", stats[4]);
//         charisma = new AbilityScore("Charisma", stats[5]);

//         abilityScores.add(strength);
//         abilityScores.add(dexterity);
//         abilityScores.add(constitution);
//         abilityScores.add(intelligence);
//         abilityScores.add(wisdom);
//         abilityScores.add(charisma);
//     }

//     public List<AbilityScore> getAbilityScores() { 
//         return abilityScores; 
//     }

//     //default way of setting hit points
//     public void setHitPoints(int rolledHitPoints) { 
//         int maxHPValue = rolledHitPoints + (constitution.getModifier() * overallLevel); 

//         hitPoints = new Resource("Hit Points", maxHPValue); 
//     }

//     public Resource getHP() { 
//         return hitPoints; 
//     }

//     public void setOtherHealths() {
//         otherHealthPoints = new HashMap<>(); 
//         otherHealthPoints.put("Overheal", 0); //overhealed HP
//         otherHealthPoints.put("AC-HP", 0); //special type of HP for constructs
  
//         //add other types of health here
//     }

//     //default way of setting mana points 
//     public void setManaPoints(int rolledManaPoints) { 
//         int maxMPValue = rolledManaPoints + (intelligence.getModifier() * overallLevel); 

//         manaPoints = new Resource("Mana Points", maxMPValue);
//     }

//     public Resource getMP() { 
//         return manaPoints; 
//     }

//     //default way of setting action points
//     public void setActionPoints(int baseMaxAP) { 
//         actionPoints = new ArrayList<>();

//         //set all AP as Versatile because it is BaseAP 
//         for (int i = 0 ; baseMaxAP > i ; i++) { 
//             actionPoints.add(new ResourceOrb("Versatile")); 
//         }
//     }

//     public void addActionPointType(ResourceOrb actionPointType, int maxAP) { 
//         for (int i = 0 ; i > maxAP ; i++) { 
//             actionPoints.add(actionPointType); 
//         }
//     }

//     //WIP: CurAP is under combat. 
//     // //ap that Versatile && is not used
//     // public int getCurAP() { 
//     //     int curAP = 0; 

//     //     for (int i = 0 ; actionPoints.size() > i ; i++) { 
//     //         if(actionPoints.get(i).getType().equals("Versatile") && !(actionPoints.get(i).isUsed())) { 
//     //             curAP++;
//     //         }
//     //     }

//     //     return curAP; 
//     // }

//     //ap that is Versatile 
//     public int getMaxAP() { 
//         int maxAP = 0; 

//         for (int i = 0 ; actionPoints.size() > i ; i++) { 
//             if(actionPoints.get(i).getType().equals("Versatile")) { 
//                 maxAP++;
//             }
//         }

//         return maxAP; 
//     }

//     public List<ResourceOrb> getActionPoints() {
//         return actionPoints; 
//     }

//     //default way of setting movement
//     public void setBaseMovementSpeed() { 
//         movementSpeeds = new HashMap<>(); 

//         int baseMaxSPD = convertDexterityModifierToBaseSpeed(dexterity.getModifier()); 

//         movementSpeeds.put("Basic", baseMaxSPD); 
//     }

//     //determine baseMaxSPD from DexterityModifier: 
//     public int convertDexterityModifierToBaseSpeed(int dexMod) {
//         int baseMaxSPD = 4; //lowest possible BaseMaxSPD
        
//         //conditions are mutually exclusive
//         //start from highest to lowest. 
//         if (dexMod >= 10) { 
//             baseMaxSPD = dexMod; 

//         } else if (dexMod >= 9) { 
//             baseMaxSPD = 9; 

//         } else if (dexMod >= 8 && dexMod <= 7) { 
//             baseMaxSPD = 8; 

//         } else if (dexMod >= 6 && dexMod <= 4) {
//             baseMaxSPD = 7;

//          } else if (dexMod >= 3 && dexMod < 0) { 
//             baseMaxSPD = 6; 

//          } else if (dexMod == 0) { 
//             baseMaxSPD = 5; 

//          } else { //dexMod < 0
//             baseMaxSPD = 4; 

//          }

//         return baseMaxSPD ; 
//     }

//     //used to add and remove movement speeds moving forward 
//     public Map<String, Integer> getMovementSpeeds() { 
//         return movementSpeeds; 
//     }
    
//     //WIP: CurSPD() is under combat 

//     //unless otherwise mentioned - set default
//     public void setFallingThreshold(Optional<Integer> optionalFallingThreshold) { 
//         Integer optionalFallingThresholdValue = optionalFallingThreshold.orElse(null); 

//         if(optionalFallingThresholdValue != null) { 
//             fallingThreshold = optionalFallingThresholdValue.intValue(); 

//         } else { 
//             fallingThreshold = DEFAULT_FALLING_THRESHOLD;

//         }
//     }

//     //more relevant after abilities are added 
//     public void setShieldLayers() { 
//         shieldLayers = new Stack<>(); 
//     }

//     public Stack<Resource> getShieldLayers() { 
//         return shieldLayers; 
//     }

//     public void setArmourLayers() { 
//         armourLayers = new Stack<>(); 
//     }

//     public Stack<Resource> getArmourLayers() { 
//         return armourLayers; 
//     }

//     //default way of setting summoning slots
//     public void setSummoningSlots(Optional<Integer> optionalBaseMaxSUM) { 
//         Integer optionalBaseMaxSUMValue = optionalBaseMaxSUM.orElse(null); 

//         if (optionalBaseMaxSUMValue != null) {
//             int baseMaxSUM = optionalBaseMaxSUMValue.intValue(); 
//             summoningSlots = new ArrayList<>(); 

//             //set all SUM as Basic
//             for (int i = 0 ; baseMaxSUM > i ; i++) { 
//                 summoningSlots.add(new ResourceOrb("Basic")); //for basic units 
//             }

//         }

//         //else -> summoningSlots=null
//     }

//     // //WIP: CurSUM is under combat. 
//     // // //SUM that is not used

//     //SUM that has Basic 
//     public int getMaxSUM() {
//         int maxSUM = 0; 

//         for (int i = 0 ; summoningSlots.size() > i ; i++) { 
//             if(summoningSlots.get(i).equals("Basic")) {
//                 maxSUM++;
//             }
//         }

//         return maxSUM; 
//     }

//     //TODO: is there special summoning slots? 
//     //public void addNewSUMType() {}

//     public List<ResourceOrb> getSummoningSlots() { 
//         return summoningSlots; 
//     }

//     //unless otherwise mentioned - set default
//     public void setMaxSummoningRange(Optional<Integer> optionalMaxSummoningRange) { 
//         Integer optionalMaximumSummoningRangeValue = optionalMaxSummoningRange.orElse(null); 

//         if(optionalMaximumSummoningRangeValue != null) { 
//             int maxSUMRange = optionalMaximumSummoningRangeValue.intValue(); 

//             maximumSummoningRange = maxSUMRange; 
        
//         } else { 
//             maximumSummoningRange = DEFAULT_SUMMONING_RANGE; 

//         }
//     }

//     public int getMaxSUMRange() { 
//         return maximumSummoningRange; 
//     }

//     //unless otherwise mentioned - set default
//     public void setBaseCastingRange(Optional<Integer> optionalMaxCastingRange) { 
//         Integer optionalMaxCastingRangeValue = optionalMaxCastingRange.orElse(null); 

//         if(optionalMaxCastingRangeValue != null) { 
//             int maxCastingRange = optionalMaxCastingRangeValue.intValue(); 

//             maximumBaseCastingRange = maxCastingRange; 

//         } else { 
//             maximumBaseCastingRange = DEFAULT_BASE_CASTING_RANGE; 
        
//         }
//     }

//     public int getBaseCastingRange() { 
//         return maximumBaseCastingRange; 
//     }

//     //unless otherwise mentioned - set default
//     public void setTrueAC(Optional<Integer> optionalTrueAC) { 
//         Integer optionalTrueACValue = optionalTrueAC.orElse(null); 

//         if(optionalTrueACValue != null) { 
//             int maxTrueAC = optionalTrueACValue.intValue(); 

//             trueAC = maxTrueAC;

//         } else { 
//             trueAC = DEFAULT_TRUE_AC; 

//         }
//     }

//     public int getTrueAC() { 
//         return trueAC;
//     }

//     //unless otherwise mentioned - set default
//     public void setSpellcastingAbilityScore(Optional<AbilityScore> optionalSpellcastingAbility) { 
//         AbilityScore optionalSpellcastingAbilityValue = optionalSpellcastingAbility.orElse(null); 

//         if(optionalSpellcastingAbilityValue != null) { 
//             spellcastingAbilityScore = optionalSpellcastingAbilityValue; 

//         } else { 
//             spellcastingAbilityScore = intelligence; 

//         }
//     }

//     public AbilityScore getSpellcastingAbilityScore() { 
//         return spellcastingAbilityScore; 
//     }

//     //TODO: make weight tied to the race 
//     //unless otherwise mentioned - set default
//     public void setBaseWeight(Optional<Integer> optionalBaseWeight) { 
//         Integer optionalBaseWeightValue = optionalBaseWeight.orElse(null); 

//         if(optionalBaseWeightValue != null) { 
//             int setBaseWeight = optionalBaseWeightValue.intValue(); 

//             baseWeight = setBaseWeight;   

//         } else { 
//             baseWeight = DEFAULT_BASE_WEIGHT; 

//         }
//     }

//     public int getBaseWeight() { 
//         return baseWeight; 
//     }

//     //TODO: Hit Dice 


//     //TODO: Levelling System and Aggregates 
    
//     //set level beforehand. 
//     public void setOverallLevel(int setOverallLevel) { 
//         overallLevel = setOverallLevel; 
//     }

//     public int getOverallLevel() { 
//         return overallLevel; 
//     }
    
//     // private Resource hunger; 

//     // private int encumbranceThreshold;
//     // private double bloodiedPercentage; 
//     // private double manaMalaisePercentage; 
//     // private double trudgeMultiplier; 
//     // private double criticalMultiplier; 
//     // private double deathHealthMultiplier; 

//     // private ResourceOrb[] stamina; 
//     // private boolean useSpecialConcentration; 
//     // private int breakHitPoints;
    
//     // private boolean isThrowDistanceEqualJumpDistance; //throw-jump
//     // private int throwDistance;
//     // private int throwHeight;  
//     // private int jumpDistance;
//     // private int jumpHeight; 
    
//     // //First Skills:
//     // private int generalAthletics; 
//     // private int dodge; 
//     // private int precision; 
//     // private int persistance; 
//     // private int perception; 
//     // private int communication; 

// }

// //TODO: refactor resourceOrb -> action points
// //TODO: refactor resourceOrb -> summoning slots