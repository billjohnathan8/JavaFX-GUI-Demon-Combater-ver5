package guidemon.model.effect.type;

import java.util.List;
import java.util.Map; 

import guidemon.model.effect.TemplateEffect;
import guidemon.engine.scene.SceneContext;
import guidemon.model.actor.Actor;
import guidemon.model.combat.DamageType;
import guidemon.model.dice.Dice; 
import guidemon.model.stats.immutable.instances.AbilityScoreEntry;

public class DamageEffect extends TemplateEffect {
    private List<String> damageTags; 

    private int damage; 
    //TODO: resolve the dice rolls
    //include bonuses to damage
    //include critical hit multipliers etc.
    //resolve all runtime components, other effects, etc.
    
    // private Dice damageDice; 
    private double armourPenetration; 

    private AbilityScoreEntry damageAbilityForModifier; 
    private Map<DamageType, Integer> damageTypes; 

    public DamageEffect(String name, List<String> tags, List<String> damageTags, int damage, double armourPenetration, AbilityScoreEntry damageAbilityForModifier, Map<DamageType, Integer> damageTypes) {
        super(name, tags); 
        this.damageTags = damageTags; 
        this.damage = damage; 
        this.armourPenetration = armourPenetration; 
        this.damageAbilityForModifier = damageAbilityForModifier; 
        this.damageTypes = damageTypes; 
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
        for(Actor a : targets) {
            a.takeDamage(damage, damageTypes, armourPenetration); 
        }

        //TODO: DamageDealtEvent in DamageDealtEvent.class 
        // context.getEventBus().publish(new DamageDealtEvent(actor, targets, damage, damageType)); 
    }   
}