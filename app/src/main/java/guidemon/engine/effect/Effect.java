package guidemon.engine.effect;

import java.util.List; 

import guidemon.engine.scene.SceneContext; 
import guidemon.model.entry.Entry; 

/*
 * 
 * Belongs on and is added to an Entry
 * 
 * It can be applied during or outside of combat via Active Abilities. 
 * 
 * It is the bulk of what makes an Entry Unique and is what Abilities use to affect another entry. 
 * 
 * This class is the common interface used by all effects. 
 */
public interface Effect {
    
    /**
     * Apply this effect to the target(s) in the given context.
     * The single entry point: apply whatever logic yoyu need.
     * 
     * Append and apply the effects to the entries directly.
     * 
     * The entry can be either an Actor, Item, etc. anything that has a StatBlock and can be affected by some sort of ability, effect, etc. 
     * 
     * Passive Effects - always active given certain conditions.
     * Active Effects - one-time-use, casting has conditions, not the effect itself. <- Usually applied to combatants via Attack or other Active Abilities. 
     * @param caster the ability's caster (null for passive startup) - who casted the ability
     * @param targets list of affected actors
     * @param context the shared context (events, spaceManager, timeManager, roster) - holds SpaceManager, TimeManager, EventBus, etc.
     *
     */
    void apply(Entry caster, List<Entry> targets, SceneContext context); 
}

/*
Conditional: Apply to only ability scores
Conditional: Apply to only primary resources
Conditional: Apply to only secondary resources
Conditional: Apply to only specified rostered resources
Conditional: Apply to all rolls
Conditional: Apply to only specified rolls
 */