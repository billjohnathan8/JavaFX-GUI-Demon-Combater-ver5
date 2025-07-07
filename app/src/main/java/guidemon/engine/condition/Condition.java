package guidemon.engine.condition;

import java.util.List;

import guidemon.engine.scene.SceneContext;
import guidemon.model.actor.Actor;

/**
 * This is a pure predicate.
 * 
 * "Should I fire when this Event comes in?"
 * 
 * Also known as: Conditional
 */
public interface Condition {
    boolean test(Actor caster, List<Actor> targets, SceneContext context);   
}