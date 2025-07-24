package guidemon.model.ability;

import java.util.ArrayList;
import java.util.List; 
import java.util.Map;
import java.util.Set;

import guidemon.engine.condition.Condition;
import guidemon.engine.condition.ActivationCondition;
import guidemon.engine.condition.EligibilityCondition;
import guidemon.engine.scene.SceneContext;
import guidemon.engine.trigger.Trigger;
import guidemon.engine.event.Event;
import guidemon.model.actor.Actor;
import guidemon.model.casting.CastingParameters;
import guidemon.model.combat.TickUnit;
import guidemon.model.combat.Duration; 
import guidemon.model.effect.Effect;
import guidemon.model.entry.Entry;
import guidemon.model.stats.ResourceCost; 


/**
 * An Ability is an Entry that provides its owner a list of Effects or certain other entries. In the fashion of Providing A while it is active, and removing A when it is deactivated or removed completely. 
 * 
 * There are two main kinds of abilities: Active & Passive
 * 
 * Active - It is casted by the owner (in this instance labelled as the 'caster') only one-time to apply a list of effects on another (or any other number of) actor(s) (in this instance called the 'target' or 'targets', depending on the number of allowable targets determined by the casting parameters).
 * 
 * Passive - It is an ability that simply exists and remains activated and usually is granted either by itself or by another ability (i.e, via Linchpin Abilities). It grants the user a list of effects that usually lasts long-term, i.e, unless this ability is deactivated or removed completely. Deactivation can still occur if certain conditions outlined by the ability are not met.  
 * 
 * At run-time, this object is not actually a buffer, it is the real and actual Data Object used to store game data at run-time. 
 * 
 * Corresponding the StatBlock for this entry is just a display, and simply loads the data from this entry to persist the data and display to the view 
 */
public class Ability extends Entry {
    //load this into a statblock for display or from statblock for id, name, description 
    
    private AbilityType type;
    private List<String> tags; 
    
    //TODO: move to a method that checks all requisites etc.
    private boolean isOn;                                       //is this ability currently going to take effect? or grant any other abilities (in the case of LinchpinAbilities)
    private List<EligibilityCondition> requisites;              //ensures that this ability remains 'on' (note: this is different from casting conditions) 

    private Map<Trigger, Effect> triggers;                      //for conditional Passive that may trigger on passive or active effects or Active Abilities that are auto-casted 
    
    private Map<Duration, List<ResourceCost>> maintenanceCosts; //resource costs for this ability just remaining on in combat or outside of combat (i.e, every 1AP or every 100ms)

    private CastingParameters castingParameters;

    private List<Effect> effects;                               //what does this ability do? what will it do to its targets (self for passive, etc. OR the targets of the cast)

    private String description; 

    private Duration cooldownDuration;                          //how long until the ability can be casted again - is used as a part of the ActivationCondition 

    //Constructors: 

    /**
     * This constructor is used to create the entry via super. 
     */
    public Ability(String name) {
        super(name); 

        this.isOn = false;  //Default: assume false until it is true - then turn it on
    }

    /**
     * This constructor is generally used for Passive Abilities - which mainly affect the actor owning this ability. 
     */
    public Ability(String name, AbilityType type, List<String> tags, String description, List<EligibilityCondition> requisites, Map<Trigger, Effect> triggers, List<Effect> effects, Map<Duration, List<ResourceCost>> maintenanceCosts) {
        this(name); 
        this.type = type; 
        this.tags = tags;
        this.description = description;

        this.requisites = requisites; 
        this.triggers = triggers;

        //no active part of this ability
        this.castingParameters = null;

        this.effects = effects; 

        //default: no cooldown period
        this.cooldownDuration = null; 

        this.maintenanceCosts = maintenanceCosts; 
    }

    /**
     * This constructor is generally used for Active Abilities that do not have any auto-casting. I.e, they prompt the user for activation. 
     */
    public Ability(String name, AbilityType type, List<String> tags, String description, List<EligibilityCondition> requisites, CastingParameters castingParameters, List<Effect> effects, Duration cooldownDuration, Map<Duration, List<ResourceCost>> maintenanceCosts) {
        this(name); 
        this.type = type; 
        this.tags = tags; 
        this.description = description; 

        this.requisites = requisites; 
        this.triggers = null;    //no trigger, since the 'trigger' is the activation condition of the Active Ability

        this.castingParameters = castingParameters; 

        this.effects = effects; 

        this.cooldownDuration = cooldownDuration; 
        
        this.maintenanceCosts = maintenanceCosts; 
    }

    /**
     * This constructor is generally used for Active Abilities that are automatically casted aka, auto-casted.
     * 
     * These abilities are casted without prompting the user. 
     */
    public Ability(String name, AbilityType type, List<String> tags, String description, List<EligibilityCondition> requisites, Map<Trigger, Effect> triggers, CastingParameters castingParameters, List<Effect> effects) {
        this(name);
        this.type = type;
        this.tags = tags; 
        this.description = description; 

        this.requisites = requisites; 
        this.triggers = triggers;        //what triggers the auto-cast? 

        //TODO: auto-cast determines this from an event etc.
        this.castingParameters = castingParameters; 

        this.effects = effects;

        //default: no cooldown period
        this.cooldownDuration = cooldownDuration; 
        
        //default: no maintenance because very short
        this.maintenanceCosts = maintenanceCosts; 
    }

    //getters and setters: 
    public AbilityType getType() {
        return this.type; 
    }

    public void setType(AbilityType type) {
        this.type = type; 
    }

    /**
     * Use this getter to add and remove tags individually.
     */
    public List<String> getTags() {
        return this.tags; 
    }

    /**
     * Rarely used. But if used - use this setter to change out an entire group of effects in one instantaneous transaction. 
     */
    public void setTags(List<String> tags) {
        this.tags = tags; 
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description; 
    }

    public boolean getOnStatus() {
        return this.isOn;
    }

    /*
     * Set the isOnStatus manually 
     */
    public void setOnStatus(boolean isOn) {
        this.isOn = isOn; 
    }

    /*
     * Use this getter to add and remove requisites individually.
     * 
     * This is also generally used during the stage when the Engine parses Conditions
     */
    public List<EligibilityCondition> getRequisites() {
        return this.requisites; 
    }

    /**
     * Rarely used. But if used - use this setter to change out an entire group of EligibilityConditions for requisites in one instantaneous transaction. 
     */
    public void setRequisites(List<EligibilityCondition> requisites) {
        this.requisites = requisites; 
    }

    /*
     * Use this getter to add and remove triggers individually.
     * 
     * This is also generally used during the stage when the Engine parses Conditions
     */
    public Map<Trigger, Effect> getTriggers() {
        return this.triggers; 
    }

    /*
     * Rarely used. But if used - use this setter to change out an entire group of Triggers for requisites in one instantaneous transaction.
     */
    public void setTriggers(Map<Trigger, Effect> triggers) {
        this.triggers = triggers; 
    }

    /*
     * Use this getter to edit data in the CastingParameters.
     */
    public CastingParameters getCastingParameters() {
        return this.castingParameters; 
    }

    /*
     * Rarely used. But if used - use this setter to change out an entire set of CastingParameters by replacing the entire object instance.
     * 
     * Usually used when other abilities that modify CastingParameteres by a fixed value are used. 
     */
    public void setCastingParameters(CastingParameters castingParameters) {
        this.castingParameters = castingParameters; 
    }

    /*
     * Use this getter to add and remove effects individually.
     */
    public List<Effect> getEffects() {
        return this.effects; 
    }

    /*
     * Rarely used. But if used - use this setter to change out an entire group of effects  in one instantaneous transaction.
     * 
     * Usually used when an ability completely changes a series of effects. E.g. Counterspell - which renders all effects to be empty and voided.
     */
    public void setEffects(List<Effect> effects) {
        this.effects = effects; 
    }

    /**
     * Use this getter to retrieve the cooldown duration.
     */
    public Duration getCooldownDuration() {
        return this.cooldownDuration; 
    }

    /**
     * Use this setter to set a new cooldown duration. 
     */
    public void setCooldownDuration(Duration cooldownDuration) {
        this.cooldownDuration = cooldownDuration; 
    }

    /**
     * Use this getter to add and remove maintenance costs at different durations or the resource costs per that one duration individually. 
     */
    public Map<Duration, List<ResourceCost>> getMaintenanceCosts() {
        return this.maintenanceCosts; 
    }

    /**
     * Rarely used. But if used - use this setter to change out an entire group of maintenance costs in one instantaneous transaction.
     */
    public void setMaintenanceCosts(Map<Duration, List<ResourceCost>> maintenanceCosts) {
        this.maintenanceCosts = maintenanceCosts; 
    }

    //Core Methods: 

    /*
     * Set the isOnStatus of this ability to On. 
     */
    public void turnOn() {
        this.isOn = true; 
    }

    /*
     * Set the isOnStatus of this ability to Off. 
     */
    public void turnOff() {
        this.isOn = false; 
    }

    /*
     * Toggle the isOnStatus of this ability to the opposite state.
     * 
     * I.e, if an ability was Off, then turn it on.
     *      if an ability was On, then turn it off. 
     */
    public void toggleOnOff() {
        boolean isOnStatus = this.isOn;

        if(isOnStatus == true) {
            this.isOn = false; 

        } else {
            this.isOn = true; 

        }
    }

    //Core Methods: 
    /**
     * This is an important Core Method. 
     * 
     * Registers Passive Triggers when a Passive Ability is loaded
     */
    public void register(SceneContext context) {
        //Hooks all triggers that could trigger. 
        Set<Trigger> keyTriggers = triggers.keySet(); 

        List<Trigger> keyTriggersList = new ArrayList<>(keyTriggers); 

        keyTriggersList.forEach(t -> t.register(context));

        if(type.equals(AbilityType.PASSIVE)) {
            //apply "always-on" effects immediately
            effects.forEach(e -> e.apply(null, context.getActors(), context));
        }
    }

    /**
     * Check whether an active ability is valid for casting. 
     */
    public boolean canCast(Actor caster, List<Actor> targets, SceneContext context) {
        //0) Note: Casting Shape / AOE Overlay is always possible, just that obstruction of Area of Effect / Line of Effect is resolved at PreCast. 

        //1) Check whether the ability is On, based on eligibility condition - but this guard check is just fine 
        if(!this.isOn) {
            return false; 
        }

        //2) Check whether the Activation Conditions / GatingCondition are met
        for (ActivationCondition ac : castingParameters.getCastingConditions()) {
            if(!ac.test(caster, targets, context)) {
                return false;
            }
        }

        //3) Check Resource Costs: 
        //TODO: Search Resources for ResourceCosts

        //4) Check Valid Targets: 
        for (Condition c : castingParameters.getValidTargetConditions()) {
            if(c.test(caster, targets, context)) {
                return false; 
            }
        }

        //4) All checks passed? Means that this active ability is valid for casting. 
        return true; 
    }

    /**
     * Check whether an Active Ability that is a Reaction is valid for casting as a Reaction. 
     */
    public boolean canCastReaction(Actor caster, Event event, SceneContext context) {
        //1) Check whether the ability is On, based on eligibility condition - but this guard check is just fine 
        if(!this.isOn) {
            return false; 
        }

        //2) Check whether the Activation Conditions / GatingCondition are met
        for (ActivationCondition ac : castingParameters.getCastingConditions()) {
            if(!ac.test(caster, event, context)) {
                return false;
            }
        }

        //3) Check Resource Costs: 
        //TODO: Search Resources for ResourceCosts
        
        //4) All checks passed? Means that this active ability is valid for casting. 
        return true; 
    }

    /**
     * This is an important Core Method.
     *
     * Activate an Active Ability.
     * 
     * Can happen during combat or out-of-combat. 
     * 
     * TODO: Overloaded method.
     */
    public void activate(Actor caster, List<Actor> targets, SceneContext context) {
        //1. Check whether this ability is allowed / valid to cast.
        if(!canCast(caster, targets, context)) {
            return; 
        }

        //2. Deduce and Reduce all Resource Costs
        spendCastingCosts(caster); 
            //search for resources (deduce)
            //reduce it. 
    
        //3. Broadcast ResourceCost as ResourceCostEvent 
        
        //4. Let the AbilityProcessor drive the Pipeline to apply the effects.
    }

    /**
     * This is an important Core Method. 
     * 
     * Activate an Active Ability that is a Reaction. 
     * 
     * Can happen during combat or out-of-combat. 
     * 
     * TODO: Overloaded Method
     */
    public void activate(Actor caster, Event event, SceneContext context) {
        if(!canCastReaction(caster, event, context)) { 
            return; 
        }

        //2. Deduce and Reduce all Resource Costs
        spendCastingCosts(caster); 
            //search for resources (deduce)
            //reduce it. 
    
        //3. Broadcast ResourceCost as ResourceCostEvent 
        
        //4. Let the AbilityProcessor drive the Pipeline to apply the effects.
    }

    //TODO: 
    public void spendCastingCosts(Actor caster) {
        //Deduce all resources

        //Reduce all resources 
    }
}

//TODO: Ability Progression
//TODO: Ability Prerequisites, SkillTress, etc.