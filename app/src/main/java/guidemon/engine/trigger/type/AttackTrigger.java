package guidemon.engine.trigger.type;

import java.util.List; 
import java.util.ArrayList; 

import guidemon.engine.trigger.Trigger;
import guidemon.engine.casting.ReactionService;
import guidemon.engine.condition.ActivationCondition;
import guidemon.engine.event.Event;
import guidemon.engine.event.type.casted.attack.AttackRollEvent;
import guidemon.engine.scene.SceneContext;
import guidemon.engine.dice.roller_service.RollerService; 
import guidemon.model.ability.Ability; 
import guidemon.model.effect.Effect;
import guidemon.model.actor.Actor; 

/**
 * Used by all Reactions
 */
public class AttackTrigger implements Trigger {
    // private final ReactionService reactionService; 
    // private final RollerService rollerService; 
    
    @Override
    /**
     * Listens for attacks and then queries everyone on whether they want to react
     */
    public void register(SceneContext context) {
        context.getEventBus().register(AttackRollEvent.class, evt -> {
            //Get all targets in the scene
            for (Actor target : evt.getTargets()) {
                //1) Get all abilities that are reactions. 
                List<Ability> reactions = new ArrayList<>(); 
                for(Ability a : target.getAbilities()) {
                    //check whether the ability is on AND whether it is a reaction
                    if(a.getOnStatus() && a.getTags().contains("Reaction")) {
                        reactions.add(a);
                    }
                }

                //if reactions is empty - then you are just going to get hit. 
                // if(reactions.isEmpty()) {
                //     return; 
                // }

                //check avaialble resources

                //check casting / activation conditions 

                //2) Poll/Query for Reaction - ask Player or AI whether they want to use any of the valid reactions they have. 
                //display UI and get the chosen reaction. 
                // Effect reactionEffect = /* selected reaction effect */

                // Effect reactionEffect = null; //placeholder 

                // if(reactionEffect == null) {
                //     return; 
                // }

                //4) Activate it just like an active ability. 
                //queue it into a queue so everyone reacts in accordance to their initiative order if applicable 
            }
        });
    }
}