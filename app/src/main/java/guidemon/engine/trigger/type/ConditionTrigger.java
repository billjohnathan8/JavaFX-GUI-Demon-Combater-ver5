package guidemon.engine.trigger.type;

import guidemon.engine.condition.ActivationCondition;
import guidemon.engine.effect.Effect;
import guidemon.engine.event.Event;
import guidemon.engine.scene.SceneContext;
import guidemon.engine.trigger.Trigger; 



public class ConditionTrigger implements Trigger {
    private final Class<? extends Event> eventType; 
    private final ActivationCondition condition;
    private final Effect effect; 

    public ConditionTrigger(Class<? extends Event> eventType, ActivationCondition condition, Effect effect) {
        this.eventType = eventType;
        this.condition = condition;
        this.effect = effect; 
    }

    //TODO: register()
    @Override
    public void register(SceneContext context) {
        context.getEventBus().register(eventType, event -> {
            // if(condition.test(event, context)) {
            //         // figure out who “owns” this trigger (you might store that in the Condition
            //         // or in the Event—here we assume the Event knows the actor/target)
            //         // Actor actor = event.getSource();             //may need to do type casting 
            //         // List<Actor> targets = event.getTargets();    //may need to do type casting
            //         // effect.apply(actor, targets, context);       //List<Actor> -> List<Entry> (?)  
            // }
        }); 
    }   
}