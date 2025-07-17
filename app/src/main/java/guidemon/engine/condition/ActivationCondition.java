package guidemon.engine.condition;

import java.util.List; 

import guidemon.engine.event.Event; 
import guidemon.engine.scene.SceneContext;
import guidemon.model.actor.Actor; 

/*
 * Used by Ability.activate(...) to guard whether an actor (caster) is allowed to cast an active ability / active effect. 
 * 
 * Context comes from SceneContext (but sometimes CombatContext is involved during Combat). Can be casted in-combat or out-of-combat. 
 * 
 * Typically uses Events. 
 * 
 * Contains the functions of the depracated EventCondition.class. used by TriggerCondition 
 * 
 * This class separates the interface into a different subtype for a separation of concerns. 
 */
public interface ActivationCondition extends Condition {
    //TODO:     

    //overloaded method for events, does not affect any single target - but mainly affects the scene. 
    boolean test(Actor caster, Event e, SceneContext context);

    //overloaded method for events, affects targets auto-casted. 
    boolean test(Actor caster, List<Actor> targets, Event e, SceneContext context);
}