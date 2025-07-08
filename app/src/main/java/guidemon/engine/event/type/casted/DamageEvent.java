package guidemon.engine.event.type.casted;

import java.util.List; 

import guidemon.engine.event.Event;
import guidemon.model.actor.Actor;  
import guidemon.model.combat.DamageType;


/**
 * Fired immediately before damage is applied to a target.
 * 
 *      - DamageReductionTriggers (e.g. ReduceFireDamage) can list for this. 
 */
public class DamageEvent implements Event {
    private final Actor source;
    private final Actor target;

    private final int amount; 
    private final List<DamageType> damageTypes; 
    private final List<String> damageTags; 

    public DamageEvent(Actor source, Actor target, int amount, List<DamageType> damageTypes, List<String> damageTags) {
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.damageTypes = damageTypes;
        this.damageTags = damageTags;
    }

    //only getters (because of publisher model - give only the necessary information for the subscribers): 
    public Actor getSource() {
        return this.source;
    }

    public Actor getTarget() {
        return this.target;
    }

    public int getAmount() {
        return this.amount;
    }

    public List<DamageType> getDamageTypes() {
        return this.damageTypes;
    }

    public List<String> getDamageTags() {
        return this.damageTags; 
    }
}

//TODO: DamageDealtEvent - immediately fater damage is resolved and applied
// *      - EffectReactionTriggers (e.g. Frostbite Removal) can listen for this. 
//  *      - UI can show floating "-x HP" text.
// What it does: lets any downstream logic (triggers, effects, UI) know exactly who hit whom, for how much, and with what damage type.