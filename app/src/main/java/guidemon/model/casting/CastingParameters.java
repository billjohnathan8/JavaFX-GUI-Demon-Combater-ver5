package guidemon.model.casting;

import java.util.List; 

import guidemon.engine.condition.EligibilityCondition; 
import guidemon.model.combat.TickUnit; 

/*
 * Bundles all values into a single set of Casting Parameters that strictly deal with the Casting Mechanic. 
 * 
 * Relates purely to space (AOE, Targets, etc.) and Time (CastTime, EffectDelayTime,) 
 * 
 */
public class CastingParameters {
    //TODO: 
    // private boolean isAutoCast;         //some abilities are auto-casted without prompting the user.

    // private boolean presetPromptCaster; //will prompt the caster before filling in a preset of casting details 

    //TODO: Casting Shape Overlay - Default Shape: Circle
    private int minimumRange;  //For Ring Shape
    private int maximumRange;  //For Circle Shape

    private TargetType targetType; 

    //For TARGETED: 
    private int numberOfTargets;  //aka maxTargets 
    
    //FOR AREA_OF_EFFECT:
    private AreaOfEffectRangeType aoeRangeType; 
    //private List<Shape> aoeShapes; //AOE Shapes - cast and place all of them 

    private List<EligibilityCondition> targetConditions;  //usually always used for TARGETED enum or can be used to exclude allies from AOE enum - mainly used for targeting non-combatants etc. (not the conditions for Unable to target dragonborns etc.)

    //TODO: Casting and Duration: 
    //How long does it take for the ability to be casted? 
    //Always tick forward by castTime not by AP Cost
    //But Remember that AP Cost does affect castTime.
    //if AP Cost > CastTime => castTime = CastTime (unaffected because more tiring to cast, but time does not accelerate)
    //if AP Cost < CastTime => castTime = CastTime (unaffected, casting is slower than the action itself)
    
    //AP Cost does not affect CastTime
    //CastTime affects AP Cost

    private double outOfCombatCastTime; //based on animation or other value
    private TickUnit combatCastTimeTickUnit; 
    private int combatCastTime;         //based on combat   

    //TODO: resolving using a turn versus using an action to cast. 
    //CastTime affects APCost
    //if AP Cost > CastTime => AP Cost = AP Cost (unaffected)
    //if CastTime > AP Cost && Casted DuringTurn => AP Cost = CastTime
    //if CastTime > AP Cost && Casted using just enough AP for AP Cost = AP Cost = AP Cost (unaffected), but for the duration of CastTime - AP Cost, that amount of time will be spend auto-casting ouside of the turn. <- usually the caster is vulnerable here 
    private int apCostInTurn;      
    private int apCostOutOfTurn;   

    //TODO: DelayTime - whenever casted, sometimes the effect takes some time to kick in after that. 
    private double outOfCombatEffectDelayTime;      //based on animation or other value
    private TickUnit combatEffectDelayTimeTickUnit; 
    private int combatEffectDelayTime;              //based on combat 
}