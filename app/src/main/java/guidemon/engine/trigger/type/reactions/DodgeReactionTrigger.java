package guidemon.engine.trigger.type.reactions;

import guidemon.engine.event.type.casted.attack.AttackRollEvent;
import guidemon.engine.trigger.Trigger; 
import guidemon.engine.scene.SceneContext;
import guidemon.engine.event.type.casted.attack.AttackRollEvent; 
import guidemon.model.actor.Actor;
import guidemon.model.ability.Ability;
import guidemon.model.stats.immutable.instances.AbilityScoreEntry;

public class DodgeReactionTrigger implements Trigger {
    private final Actor owner; 
    private final Ability dodgeAbility; 
    private final AbilityScoreEntry dodgeAbilityScore; 

    public DodgeReactionTrigger(Actor owner, Ability dodgeAbility, AbilityScoreEntry dodgeAbilityScore) {
        this.owner = owner;
        this.dodgeAbility = dodgeAbility;
        this.dodgeAbilityScore = dodgeAbilityScore; 
    }

    @Override
    /**
     * 
     */
    public void register(SceneContext context) {
        context.getEventBus().register(AttackRollEvent.class, (evt) -> {
            //Only react for me
            if(evt.getTargets() != owner) {
                return; 
            }

            //Check whether this reaction is valid for casting 
            if(!dodgeAbility.canCastReaction(owner, evt, context)) {
                return; 
            }

            //TODO: Prompt the Player / AI
            // if(!context.getUIService().confirmReaction(owner, "Dodge", evt)) return; 
            //based on the number of hits of the incoming attack, the target can queue multiple reactions, repeating the same one. just keep listening for them. 


            //ask how many dodges to do, based on hits. 
            // int numberOfRepetitions = 0; //TODO: replace with taking in user input. 
            // for (int i = 0 ; i < numberOfRepetitions ; i++)

            //3 hits
            //dodge, dodge, counter attack
            //parry 3 hits
            //map the number of hits

            // for(int i )

            //Cast and spend resource costs
            dodgeAbility.activate(owner, evt, context);


        });
        

    }
    
}
