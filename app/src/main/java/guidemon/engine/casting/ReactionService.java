package guidemon.engine.casting;

import java.util.List;

import guidemon.model.actor.Actor;
import guidemon.model.effect.Effect; 

public interface ReactionService {
    /**
     * Ask the target what sort of applicable ability / effect they would like to use for a reaction and how many, for the applicable number of hits
     * This call can pop up a dialog or be automated for AI. 
     */
    Effect askReactions(Actor target, List<Integer> incomingAttackRolls);
}