package guidemon.engine.combat;

import guidemon.engine.scene.TimeManager;
import guidemon.model.actor.Actor;

/**
 * TimeManager that enforces the passage of time during turn-based combat
 */
public class CombatTimeManager implements TimeManager {
    private final CombatManager combatManager; 

    public CombatTimeManager(CombatManager mgr) {
        this.combatManager = mgr; 
    }

    @Override
    public void tick() {
        //a "tick" could be a single action - provide hooks if needed. 
    }

    @Override
    public void nextTurn() {
        combatManager.nextTurn(); 
    }

    @Override
    public boolean isCombatActive() {
        return !combatManager.isCombatOver(); 
    }

    @Override
    public Actor getCurrentActor() {
        return combatManager.getCurrentCombatant(); 
    }
}