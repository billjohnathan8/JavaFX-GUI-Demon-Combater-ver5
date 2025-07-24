package guidemon.model.effect.type.damage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guidemon.engine.dice.roller_service.critical_damage.ICriticalHitRollingStrategy;
import guidemon.engine.event.type.casted.DamageAboutToBeDealtEvent;
import guidemon.engine.event.type.casted.DamageDealtEvent;
import guidemon.engine.scene.SceneContext;
import guidemon.model.actor.Actor;
import guidemon.model.combat.DamageType;
import guidemon.model.dice.Dice;
import guidemon.model.effect.TemplateEffect;
import guidemon.model.stats.immutable.instances.AbilityScoreEntry; 

/**
 * The kind of damage dealt in just one hit.
 * 
 * Bundles all information related to - what sort of damage it is, how to roll the final damage value, armour penetration and modular targeting, and damage types.  
 */
public class DamageEffect extends TemplateEffect {
    private List<String> damageTags;        //if it is a critical hit - specify here. 

    //resolve all runtime components, other effects, etc.

    private Dice damageDice; 
    private AbilityScoreEntry damageAbilityForModifier; //aka DAMability
    private double armourPenetration; 

    //critical strategy
    private ICriticalHitRollingStrategy criticalHitStrategy; 
    private double trueCriticalDamagePercentage;                   //used externally to create an injected trueCriticalDamageEffect
    private int trueCriticalDamageAmount;                          //the actual portion of total damage dealt - stored as an integer. 
    private DamageEffect trueCriticalDamageEffect;                 //a portion of the total damage dealt (after critical hit damage calculation) becomes pure true damage, applied as a separate effect immediately after the initial critical hit damage. 
          
    //any specific health layer is being targeted (?)

    private Map<DamageType, Integer> damageTypes; 

    public DamageEffect(String name, List<String> tags, List<String> damageTags, Dice damageDice, AbilityScoreEntry damageAbilityForModifier, double armourPenetration, Map<DamageType, Integer> damageTypes, ICriticalHitRollingStrategy criticalHitStrategy) {
        super(name, tags); 
        this.damageTags = damageTags; 
        this.damageDice = damageDice; 
        this.damageAbilityForModifier = damageAbilityForModifier; 
        this.armourPenetration = armourPenetration; 
        this.damageTypes = damageTypes; 
        this.criticalHitStrategy = criticalHitStrategy; 

        // //default
        // this.trueCriticalDamagePercentage = 0.0;
        // this.trueCriticalDamageEffect = null;
    }

    public DamageEffect(String name, List<String> tags, List<String> damageTags, Dice damageDice, AbilityScoreEntry damageAbilityForModifier, double armourPenetration, Map<DamageType, Integer> damageTypes, ICriticalHitRollingStrategy criticalHitStrategy, double trueCriticalDamagePercentage, DamageEffect trueCriticalDamageEffect) {
        super(name, tags); 
        this.damageTags = damageTags; 
        this.damageDice = damageDice; 
        this.damageAbilityForModifier = damageAbilityForModifier; 
        this.armourPenetration = armourPenetration; 
        this.damageTypes = damageTypes; 
        this.criticalHitStrategy = criticalHitStrategy; 
        // this.trueCriticalDamagePercentage = trueCriticalDamagePercentage;
        // this.trueCriticalDamageEffect = trueCriticalDamageEffect;
        // this.trueCriticalDamageEffect
    }

    /**
     * Override abstract method 'apply' common for all Effect Classes. 
     * 
     * Attack happens immediately after successful cast. 
     * This happens immediately after / per successful hit on attack. 
     * 
     * This applies damage to targets via the caster. 
     */
    @Override
    public void apply(Actor caster, List<Actor> targets, SceneContext context) {
        caster.setDamageModifierAbility(damageAbilityForModifier);

        Map<String, Integer> rollBonuses = new HashMap<>();
        rollBonuses.put("Roll End Bonus", damageAbilityForModifier.getModifierValue()); 

        int damage = 0; 

        // if (damageTags.contains("Critical")) {
        //     //calculate damage based on critical hit strategy.
            // damage += context.getRollerService().rollCriticalHitDamage(criticalHitStrategy, damageDice, damageAbilityForModifier.getModifierValue(), caster.getCriticalMultiplier(), caster.getCriticalDamageModifier());

        // } else { 
        //     //calculate damage normally: Roll Damage Dice -> Sum -> += DAMmod from DAMability 
        //     damage += context.getRollerService().rollDice(damageDice, rollBonuses); 

        // }

        //TODO: True Critical Damage - optional reroll flag for an additional damage event - true critical damage. 
// else if (damageTags.contains("True Critical")) {
//             //calculate damage based on critical hit strategy. 
//             damage += context.getRollerService().rollCriticalHitDamage(criticalHitStrategy, damageDice, damageAbilityForModifier.getModifierValue(), caster.getCriticalMultiplier(), caster.getCriticalDamageModifier());

//             //add the true critical damage - rounded up 
//             int trueCriticalDamage = (int) Math.round(damage * trueCriticalDamagePercentage);
//             List<String> trueCriticalHitDamageTags = new ArrayList<>(); 
//             DamageEffect trueCriticalHitDamageEffect = new DamageEffect("True Critical Hit Damage Effect")


//             String name, List<String> tags, List<String> damageTags, Dice damageDice, AbilityScoreEntry damageAbilityForModifier, double armourPenetration, Map<DamageType, Integer> damageTypes, ICriticalHitRollingStrategy criticalHitStrategy, double trueCriticalDamagePercentage



        //Fire an event for Right Before Damage is Applied - used for DamageReductionTriggers (outside of the actor)  //damage bonuses from effects, triggers, etc.
        context.getEventBus().publish(new DamageAboutToBeDealtEvent(caster, targets, damage, damageTypes, damageTags)); 

        for(Actor a : targets) {
            a.takeDamage(damage, damageTypes, armourPenetration); 
        }

//add the pure true critical damage rounded up - responsibility is at the hit, rather than here in this particular effect.

        //Fire an event for Right after Damage is Applied - used for EffectReactions (outside of the actor)
        context.getEventBus().publish(new DamageDealtEvent(caster, targets, damage, damageTypes, damageTags)); 
    }   

    //double the dice rolled vs take the maximum vs double the maximum
}

    // // your uniform 1…sides roll
    // private int rollDice(int sides) {
    //     return RNG.nextInt(sides) + 1;
    // }

    // //overloaded method for dice input
    // public int rollDice(Dice dice) {
    //     int sides = dice.getDiceFace(); 
    //     int numberOfDice = dice.getNumberOfDice(); 

    //     int result = 0; 

    //     for(int i = 0 ; i < numberOfDice ; i++) {
    //         result = rollDice(sides);
    //     }

    //     return result; 
    // }