package guidemon.model.effect.type.casted;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import guidemon.engine.event.type.casted.attack.AttackRollEvent;
import guidemon.engine.scene.SceneContext;
import guidemon.model.actor.Actor;
import guidemon.model.effect.Effect;
import guidemon.model.effect.TemplateEffect; 
import guidemon.model.dice.Dice; 
import guidemon.model.stats.immutable.instances.AbilityScoreEntry; 
import guidemon.model.effect.interfaces.Compositable; 

//tags = attack tags 
public class AttackEffect extends TemplateEffect implements Compositable {
    private Dice hitCheckDice; 
    private AbilityScoreEntry hitAbility; 
    private int numberOfHits; 
    private List<Effect> subEffects; //could be damage, etc.

    public AttackEffect(String name, List<String> tags, Dice hitCheckDice, AbilityScoreEntry hitAbility, int numberOfHits, List<Effect> subEffects) {
        super(name, tags); 
        this.hitCheckDice = hitCheckDice; 
        this.hitAbility = hitAbility; 
        this.numberOfHits = numberOfHits;
        this.subEffects = subEffects; 
    }

    @Override 
    /**
     * Start an attack. 
     * 
     * This is considered 1 - multiple (any number of) attacks for just 1 cast. 
     * TODO: There exists multiple attacks for multiple casts (e.g. intramovement casting, projectile casting, multi-step workflows, etc.) 
     * 
     * Maximum number of targets has already been resolved at cast. 
     */
    public void apply(Actor caster, List<Actor> targets, SceneContext context) {
        //One shared series of 'attack roll' from the caster
        List<Integer> attackRolls = new ArrayList<>(); 

        for (int i = 0 ; i< numberOfHits ; i++) {
            //TODO: add bonuses to hit based on effects and situational bonuses via triggers
            attackRolls.add(context.getRollerService().rollDice(hitCheckDice, hitAbility.getModifierValue()));
        }

        //Broadcast AttackEvent - before any reactions 
        AttackRollEvent e = new AttackRollEvent(super.getUUID(), super.getTags(), caster, targets, attackRolls);
        context.getEventBus().publish(e); 

        if(e.isCancelled()) {
            //in this case, a reaction has occured - which may change this effect entirely. e.g. Dodge or Counterattack
            return; 
        }

        //For other cases where only a DamageReduction occurs (e.g. Parry, Guard)
        //call a DamageDealtEvent or Effect 


        
        //The rest is handled via Triggers.


        // //2) For each target, query for reaction
        // for (Actor target : targets) {
        //     //rely on triggers 
        //     //poll for reactions 
        //     //timer 
        // }

        // //3) resolve each of the reactions, roll contests accordingly 
        // for (Actor target : targets) {
        //     //resolve contest

        //     //compute final successful hits 
            
        //     //apply sub effects for each successful hit 

        // }

        //4) broadcast summary 
        //AttackResolvedEvent

    }    

    @Override
    /**
     * 
     */
    public List<Effect> getSubEffects() {
        return this.subEffects; 
    }
}