package guidemon.model.ability;

import java.util.List; 

import guidemon.engine.condition.ActivationCondition;
import guidemon.engine.condition.EligibilityCondition;
import guidemon.engine.scene.SceneContext;
import guidemon.engine.trigger.Trigger;
import guidemon.model.actor.Actor;
import guidemon.model.casting.CastingParameters;
import guidemon.model.combat.TickUnit;
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
    private List<String> abilityTags; 
    
    //TODO: move to a method that checks all requisites etc.
    private boolean isOn; //is this ability currently going to take effect? or grant any other abilities (in the case of LinchpinAbilities)
    private List<EligibilityCondition> requisites; //ensures that this ability remains 'on' (note: this is different from casting conditions) 
    private List<Trigger> triggers;     //for conditional Passive Abilities that may trigger passive or active effects. 

    //TODO: Maintenance Costs - resource costs for this ability just being on. 

    private List<ActivationCondition> castingConditions; //allows or disallows casting for Active Abilities (aka: Gating Conditions)
    private CastingParameters castingParameters;  
    private List<ResourceCost> castingCosts;    

    private List<Effect> effects;                       //what does this ability do? what will it do to its targets (self for passive, etc. OR the targets of the cast)

    private String description; 

    //TODO: Cooldown
    private TickUnit cooldownTickUnit;
    private int cooldownTime; 

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
    public Ability(String name, AbilityType type, List<String> abilityTags, String description, List<EligibilityCondition> requisites, List<Trigger> triggers, List<Effect> effects) {
        this(name); 
        this.type = type; 
        this.abilityTags = abilityTags;
        this.description = description;

        this.requisites = requisites; 
        this.triggers = triggers;

        //no active part of this ability
        this.castingConditions = null;
        this.castingParameters = null;
        this.castingCosts = null;

        this.effects = effects; 

        //default: no cooldown period
    }

    /**
     * This constructor is generally used for Active Abilities that do not have any auto-casting. I.e, they prompt the user for activation. 
     */
    public Ability(String name, AbilityType type, List<String> abilityTags, String description, List<EligibilityCondition> requisites, List<ActivationCondition> castingConditions, CastingParameters castingParameters, List<ResourceCost> castingCosts, List<Effect> effects) {
        this(name); 
        this.type = type; 
        this.abilityTags = abilityTags; 
        this.description = description; 

        this.requisites = requisites; 
        this.triggers = null;    //no trigger, since the 'trigger' is the activation condition of the Active Ability

        this.castingConditions = castingConditions;
        this.castingParameters = castingParameters; 
        this.castingCosts = castingCosts; 

        this.effects = effects; 

        //default: no cooldown period

    }

    /**
     * This constructor is generally used for Active Abilities that are automatically casted aka, auto-casted. 
     * 
     * These abilities are casted without prompting the user. 
     */
    public Ability(String name, AbilityType type, List<String> abilityTags, String description, List<EligibilityCondition> requisites, List<Trigger> triggers, List<ActivationCondition> castingConditions, CastingParameters castingParameters, List<ResourceCost> castingCosts, List<Effect> effects) {
        this(name);
        this.type = type;
        this.abilityTags = abilityTags; 
        this.description = description; 

        this.requisites = requisites; 
        this.triggers = triggers;        //what triggers the auto-cast? 

        //TODO: auto-cast determines this from an event etc.
        this.castingConditions = castingConditions;
        this.castingParameters = castingParameters; 
        this.castingCosts = castingCosts; 

        this.effects = effects;

        //default: no cooldown period
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
    public List<String> getAbilityTags() {
        return this.abilityTags; 
    }

    /**
     * Rarely used. But if used - use this setter to change out an entire group of effects in one instantaneous transaction. 
     */
    public void setAbilityTags(List<String> abilityTags) {
        this.abilityTags = abilityTags; 
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
    public List<Trigger> getTriggers() {
        return this.triggers; 
    }

    /*
     * Rarely used. But if used - use this setter to change out an entire group of Triggers for requisites in one instantaneous transaction.
     */
    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers; 
    }

    /*
     * Use this getter to add and remove ActivationConditions for castingConditions individually.
     * 
     * This is also generally used during the stage when the Engine parses Conditions
     */
    public List<ActivationCondition> getCastingConditions() {
        return this.castingConditions; 
    }

    /*
     * Rarely used. But if used - use this setter to change out an entire group of ActivationConditions for castingConditions in one instantaneous transaction.
     */
    public void setCastingConditions(List<ActivationCondition> castingConditions) {
        this.castingConditions = castingConditions; 
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
     * Use this getter to add and remove ResourceCosts for castingCosts individually.
     */
    public List<ResourceCost> getCastingCosts() {
        return this.castingCosts; 
    }

    /*
     * Rarely used. But if used - use this setter to change out an entire group of ResourceCosts for castingCosts in one instantaneous transaction.
     */
    public void setCastingCosts(List<ResourceCost> castingCosts) {
        this.castingCosts = castingCosts; 
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
        triggers.forEach(t -> t.register(context)); 

        if(type.equals(AbilityType.PASSIVE)) {
            //apply "always-on" effects immediately
            effects.forEach(e -> e.apply(null, context.getActors(), context));
        }
    }

    /**
     * This is an important Core Method.
     *
     * Activate an Active Ability.
     * 
     * Can happen during cobat or out-of-combat. 
     * 
     * TODO: Overloaded method.
     */
    public void activate(Actor caster, List<Actor> targets, SceneContext context) {
        //1. Check ActivationCondition / GatingCondition
        if(isOn == false) {
            throw new IllegalStateException("Cannot case because ability is OFF");
        }

        for(ActivationCondition ac : castingConditions) {
            if(!ac.test(caster, targets, context)) {
                //TODO: only activate during combat or outside, etc.

                //TODO: toString() for ActivationCondition
                throw new IllegalStateException("Cannot cast: " + ac);
            }
        }

        //2. Reduce / Deduce Resources
        
        //throw a resource cost event. 
        //search for resources. 
        //reduce it

        // this.castingCosts = null;

        //3. Let the AbilityProcessor drive the Pipeline 
    }
}

//TODO: Ability Progression
//TODO: Ability Prerequisites, SkillTress, etc.