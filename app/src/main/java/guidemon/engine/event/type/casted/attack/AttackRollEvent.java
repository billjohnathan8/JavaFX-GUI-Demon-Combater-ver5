package guidemon.engine.event.type.casted.attack;

import java.util.UUID; 
import java.util.List; 

import guidemon.engine.event.type.CancellableEvent;
import guidemon.model.actor.Actor; 

/**
 * Fired immediately after all hit checks for an attack have been rolled with bonuses factored into the calculation. 
 */
public class AttackRollEvent implements CancellableEvent {
    private final UUID attackID; 
    private final List<String> tags; 
    private final Actor caster; 
    private final List<Actor> targets; 
    private final List<Integer> attackRolls;
    private boolean cancelled = false;          //assume false until true, until it is cancelled. 

    public AttackRollEvent(UUID attackID, List<String> tags, Actor caster, List<Actor> targets, List<Integer> attackRolls) {
        this.attackID = attackID; 
        this.tags = tags; 
        this.caster = caster; 
        this.targets = targets; 
        this.attackRolls = attackRolls; 
    }

    //only getters (because of publisher model - give only the necessary information for the subscribers): 
    public UUID getAttackID() {
        return this.attackID; 
    }

    public List<String> getTags() {
        return this.tags; 
    }

    public Actor getCaster() {
        return this.caster;
    }

    public List<Actor> getTargets() {
        return this.targets;
    }

    public List<Integer> getAttackRolls() {
        return this.attackRolls;
    }    

    @Override
    public void cancel() {
        this.cancelled = true; 
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}