package guidemon.model.casting;

import java.util.List;
import java.util.Map;

import guidemon.engine.condition.ActivationCondition;
import guidemon.engine.condition.Condition;
import guidemon.model.combat.Duration;
import guidemon.model.stats.ResourceCost; 


/*
 * Bundles all values into a single set of Casting Parameters that strictly deal with the Casting Mechanic. 
 * 
 * Relates purely to space (AOE, Targets, etc.) and Time (CastTime, EffectDelayTime,) 
 * 
 */
public class CastingParameters {
    private CastType castType;                                 //AutoCast, ForcedCast, StandardCast - triggers can have different casts. (Auto-Casts is casted without prompting the user)

    private List<ActivationCondition> castingConditions;       //allows or disallows casting for Active Abilities (aka: Gating Conditions)
    private List<ResourceCost> castingCosts;                   //costs consumed immediately on-cast     

    private CastShape uniqueCastingCircleShape;                //shape that is centered on the caster (Default: Based on the caster - usually a Circle)
    private TargetType mainTargetType; 
    private Map<CastShape, TargetType> areasOfEffect;          //for Casting Areas of Effect (if there are one or multiple)                   

    //For TARGETED: 
    private int numberOfTargets;                               //aka maxTargets 
    private List<Condition> validTargetConditions;             //used for specific targets, occasionally used for TARGETED enum or can be used to exclude allies from AOE enum
    private String confirmedTargetColour; 

    //TODO: Casting and Duration: 
    //How long does it take for the ability to be casted? 
    //Always tick forward by castTime not by AP Cost
    //But Remember that AP Cost does affect castTime.
    //if AP Cost > CastTime => castTime = CastTime (unaffected because more tiring to cast, but time does not accelerate)
    //if AP Cost < CastTime => castTime = CastTime (unaffected, casting is slower than the action itself)
    
    //AP Cost does not affect CastTime
    //CastTime affects AP Cost

    private Duration castTime; 

    //TODO: resolving using a turn versus using an action to cast. 
    //CastTime affects APCost
    //if AP Cost > CastTime => AP Cost = AP Cost (unaffected)
    //if CastTime > AP Cost && Casted DuringTurn => AP Cost = CastTime
    //if CastTime > AP Cost && Casted using just enough AP for AP Cost = AP Cost = AP Cost (unaffected), but for the duration of CastTime - AP Cost, that amount of time will be spend auto-casting ouside of the turn. <- usually the caster is vulnerable here 
    // private int apCostInTurn;      
    // private int apCostOutOfTurn;   

    private Map<Duration, List<ResourceCost>> castTimeMaintainenceCosts; //resource costs for each tickunit passed for the cast time

    //TODO: DelayTime - whenever casted, sometimes the effect takes some time to kick in after that. 
    private Duration castDelayTime;                                      //out of combat delay time is based on animation timed etc.

    /**
     * For full casting parameters
     */
    public CastingParameters(CastType castType, List<ActivationCondition> castingConditions, List<ResourceCost> castingCosts, CastShape uniqueCastingCircleShape, TargetType mainTargetType, Map<CastShape, TargetType> areasOfEffect, int numberOfTargets, List<Condition> validTargetConditions, Duration castTime, Map<Duration, List<ResourceCost>> castTimeMaintainenceCosts, Duration castDelayTime) {
        this.castType = castType;
        this.castingConditions = castingConditions;
        this.castingCosts = castingCosts; 
        this.uniqueCastingCircleShape = uniqueCastingCircleShape;
        this.mainTargetType = mainTargetType; 
        this.areasOfEffect = areasOfEffect; 
        this.numberOfTargets = numberOfTargets; 
        this.validTargetConditions = validTargetConditions;
        //confirmedTargetColour
        this.castTime = castTime; 
        this.castTimeMaintainenceCosts = castTimeMaintainenceCosts; 
        this.castDelayTime = castDelayTime; 
    }

    //other constructors (?)

    //getters and setters: 
    public CastType getCastType() {
        return this.castType; 
    }

    public void setCastType(CastType castType) {
        this.castType = castType;
    }

    public List<ActivationCondition> getCastingConditions() {
        return this.castingConditions; 
    }

    public void setCastingConditions(List<ActivationCondition> castingConditions) {
        this.castingConditions = castingConditions; 
    }

    public List<ResourceCost> getCastingCosts() {
        return this.castingCosts;
    }

    public void setCastingCosts(List<ResourceCost> castingCosts) {
        this.castingCosts = castingCosts; 
    }

    public CastShape getUniqueCastingCircleShape() {
        return this.uniqueCastingCircleShape; 
    }

    public void setUniqueCastingCircleShape(CastShape uniqueCastingCircleShape) {
        this.uniqueCastingCircleShape = uniqueCastingCircleShape; 
    }

    public TargetType getMainTargetType() {
        return this.mainTargetType; 
    }

    public void setMainTargetType(TargetType mainTargetType) {
        this.mainTargetType = mainTargetType; 
    }

    public List<Condition> getValidTargetConditions() {
        return this.validTargetConditions; 
    }

    public void setValidTargetConditions(List<Condition> validTargetConditions) {
        this.validTargetConditions = validTargetConditions; 
    }

    public Duration getCastTime() {
        return this.castTime; 
    }

    public void setCatTime(Duration castTime) {
        this.castTime = castTime; 
    }

    public Map<Duration, List<ResourceCost>> getCastTimeMainainenceCosts() {
        return this.castTimeMaintainenceCosts; 
    }

    public void setCastTimeMaintainenceCosts(Map<Duration, List<ResourceCost>> castTimeMaintainenceCosts) {
        this.castTimeMaintainenceCosts = castTimeMaintainenceCosts; 
    }

    public Duration getCastDelayTime() {
        return this.castDelayTime;
    }

    public void setCastDelayTime(Duration castDelayTime) {
        this.castDelayTime = castDelayTime; 
    }
}