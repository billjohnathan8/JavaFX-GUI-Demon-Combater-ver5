package guidemon.engine.event.type.casted;

import java.util.List; 
import java.util.Map;

import guidemon.engine.event.Event;
import guidemon.model.actor.Actor; 
import guidemon.model.combat.DamageType;

/**
 * Fired immediately before damage is applied to a target.
 * 
 * DamageReductionTriggers (e.g. ReduceFireDamage) can listen for this. 
 * 
 * 
 */
public class DamageAboutToBeDealtEvent implements Event {
    private final Actor source;
    private final List<Actor> targets; 

    private final int damageAmount; 
    private final Map<DamageType, Integer> damageTypes; 
    private final List<String> damageTags; 

    //TODO: 
    //armour penetration? 
    //store roll values ?

    public DamageAboutToBeDealtEvent(Actor source, List<Actor> targets, int damageAmount, Map<DamageType, Integer> damageTypes, List<String> damageTags) {
        this.source = source;
        this.targets = targets; 
        this.damageAmount = damageAmount;
        this.damageTypes = damageTypes; 
        this.damageTags = damageTags; 
    }

    //only getters (because of publisher model - give only the necessary information for the subscribers): 
    public Actor getSource() {
        return this.source; 
    }

    public List<Actor> getTargets() {
        return this.targets; 
    }

    public int getDamageAmount() {
        return this.damageAmount; 
    }

    public Map<DamageType, Integer> getDamageTypes() {
        return this.damageTypes; 
    }

    public List<String> getDamageTags() {
        return this.damageTags; 
    }    
}