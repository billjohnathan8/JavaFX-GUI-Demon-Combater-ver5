package guidemon.model.effect.type.casted;

import java.util.List;

import guidemon.engine.scene.SceneContext;
import guidemon.model.actor.Actor;
import guidemon.model.effect.TemplateEffect; 
import guidemon.model.stats.immutable.instances.AbilityScoreEntry; 

/**
 * This is a Reaction. 
 * 
 * When Dodge is used, ignore all incoming damage on successful check. 
 * 
 * This cannot be used to react against AOE. 
 */
public class DodgeEffect extends TemplateEffect {
    private AbilityScoreEntry dodgeCheck; //usually DEX

    public DodgeEffect(String name, List<String> tags, AbilityScoreEntry dodgeCheck) {
        super(name, tags); 
        this.dodgeCheck = dodgeCheck; 
    }

    @Override 
    /**
     * 
     */
    public void apply(Actor caster, List<Actor> targets, SceneContext context) {
        //caster is the defender using this reaction
        //targets is the incoming attacker - only one at a time. 

        //get the latest attack roll
        // AttackRollEvent attackRollEvent = context.getEventBus().latest(AttackRollEvent.class, evt -> evt.getTarget() == actor);
        


    }
    
}
