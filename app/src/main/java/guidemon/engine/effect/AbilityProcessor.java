package guidemon.engine.effect;

import java.util.List; 

import guidemon.engine.event.EventBus; 
import guidemon.engine.scene.SceneContext; 
import guidemon.model.ability.Ability; 
import guidemon.model.actor.Actor; 

/**
 * AbilityProcessor drives the “pipeline” that takes an Ability
 * from invocation all the way through casting and effect application.
 */
public class AbilityProcessor {
    private final EventBus bus; 

    public AbilityProcessor(EventBus bus) {
        this.bus = bus; 
    }

    public void execute(Ability ability, Actor caster, List<Actor> targets, SceneContext context) {
        // bus.publish(new BeginCastEvent(ability, caster, targets)); //depends on auto-cast or not. 
        // … all the other phases …

        for(Effect e : ability.getEffects()) {
            e.apply(caster, targets, context);
        }

        // bus.publish(mew InCombatEvent(ability, caster, targets));
    }   
}